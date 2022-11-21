/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CentralizedGroups;

/**
 *
 * @author Ángel
 */
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GroupServer extends UnicastRemoteObject implements GroupServerInterface {

    private LinkedList<ObjectGroup> listaGrupos;
    private ReentrantLock cerrojo;

    public GroupServer() throws RemoteException {
        //Exportacion de objeto.
        super();

        //Inicializacion variables servidor.
        listaGrupos = new LinkedList<ObjectGroup>();
        cerrojo = new ReentrantLock();

        System.setProperty("java.security.policy", "C:\\Users\\Ángel\\Dropbox\\Universidad\\Sistemas distribuidos\\practicas\\práctica 4\\practica4\\src\\CentralizedGroups\\server-policy");

        //Creacion de gestor de seguridad.
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

    }

    @Override
    public int createGroup(String galias, String oalias, String ohostname, int puerto) throws RemoteException {
        ObjectGroup nuevoGrupo = new ObjectGroup(galias, listaGrupos.size(), oalias, ohostname, puerto);
        listaGrupos.add(nuevoGrupo);
        System.out.println("Grupo creado por cliente:" + oalias + " Id de Grupo:" + nuevoGrupo.getId());
        return nuevoGrupo.getId();
    }

    @Override
    public int findGroup(String galias) throws RemoteException {
        try {
            cerrojo.lock();
            for (ObjectGroup grupo : listaGrupos) {
                if (grupo.getAlias().equalsIgnoreCase(galias)) {
                    return grupo.getId();
                }
            }
            return -1; //Si no se ha encontrado
        } finally {
            cerrojo.unlock();
        }
    }

    public int findGroup(int gid) throws RemoteException {
        try {
            cerrojo.lock();
            for (ObjectGroup grupo : listaGrupos) {
                if (grupo.getId() == gid) {
                    return grupo.getId();
                }
            }
            return -1; //Si no se ha encontrado
        } finally {
            cerrojo.unlock();
        }
    }

    @Override
    public boolean removeGroup(String galias, String oalias) throws RemoteException {

        try {
            int indexGrupo = findGroup(galias);
            cerrojo.lock();
            if (indexGrupo != -1 && listaGrupos.get(indexGrupo).getPropietario().getAlias().equalsIgnoreCase(oalias)) {
                listaGrupos.remove(indexGrupo);
                return true;
            }
            return false;
        } finally {
            cerrojo.unlock();
        }
    }

    @Override
    public boolean removeGroup(int gid, String oalias) throws RemoteException {
        try {

            int indexGrupo = findGroup(gid);
            cerrojo.lock();
            if (indexGrupo != -1 && listaGrupos.get(indexGrupo).getPropietario().getAlias().equalsIgnoreCase(oalias)) {
                listaGrupos.remove(indexGrupo);
                System.out.println("Grupo: " + gid + " ha sido eliminado por " + oalias);
                return true;
            }
            return false;
        } finally {
            cerrojo.unlock();
        }
    }

    @Override
    public GroupMember addMember(int gid, String alias, String hostname, int puerto) throws RemoteException {

        // Comprobacion existencia de grupo.
        if (findGroup(gid) != -1) {
            System.out.println("Miembro: " + alias + " ha sido añadido al Grupo: " + gid);
            return listaGrupos.get(findGroup(gid)).addMember(alias, hostname, puerto);
        }
        return null;

    }

    @Override
    public GroupMember isMember(int gid, String alias) throws RemoteException {

        //Comprobacion existencia de grupo
        if (findGroup(gid) != -1) {
            return listaGrupos.get(findGroup(gid)).isMember(alias);
        }
        return null;

    }


    @Override
    public boolean removeMember(int gid, String oalias, String alias) throws RemoteException {
        cerrojo.lock();
        //Comprobacion existencia de grupo y propietario
        if (findGroup(gid) != -1 && listaGrupos.get(findGroup(gid)).getPropietario().getAlias().equalsIgnoreCase(oalias)) {
            cerrojo.unlock();
            return listaGrupos.get(findGroup(gid)).removeMember(alias);
        }
        return false;
    }

    @Override
    public boolean sendGroupMessage(GroupMember gm, byte[] msg) {
        cerrojo.lock();
        try {
            //Creacion hilo de que extendera de Thread.
            SendingMessage hiloMensaje;
            for (ObjectGroup grupo : listaGrupos) {
                if (grupo.getId() == gm.getIdGrupo()) {
                    GroupMessage mensaje = new GroupMessage(gm, msg);
                    grupo.Sending();
                    hiloMensaje = new SendingMessage(mensaje, grupo);
                    System.out.println("Enviando mensaje:" + msg.toString() + "del Miembro: " + gm.getId() + "al grupo: " + 
                            gm.getIdGrupo());
                    hiloMensaje.start();
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            cerrojo.unlock();
        }

    }

    //Ejecucion del servidor.
    public static void main(String[] args) {
        try {
            GroupServer servidor = new GroupServer();

            String ip = "";

            try {
                //Obtencion de ip
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException ex) {
                Logger.getLogger(GroupServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Puerto del servidor.
            int puerto = 1099;

            //Crear registro para el puerto establecido.
            LocateRegistry.createRegistry(puerto);

            //Conecta rmiregistry en este ordenador e informa que ObjetoRemoto debe publicarse al exterior.  
            Naming.rebind("rmi://" + ip.toString() + "/GroupServer", servidor);

            System.out.println("                " + ip + ":" + puerto);
            System.out.println("                Conexión iniciada");

        } catch (RemoteException | MalformedURLException ex) {
            Logger.getLogger(GroupServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
