/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CentralizedGroups;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ángel
 */
public class GroupServer extends UnicastRemoteObject implements GroupServerInterface {

    LinkedList<ObjectGroup> objetosGrupo = new LinkedList<ObjectGroup>();
    ObjectGroup grupo;
    ReentrantLock lock = new ReentrantLock(true);
    int idGroup = 0;

    //Creamos un objeto de de servidor de grupos, llamamos a los metodos de la clase padre
    //y cuando se vaya a crear, ponemos un lock, comprobamos si tiene medidas de seguridad
    //y si no lo tiene, se las añadimos.
    public GroupServer() throws RemoteException {
        super();
        lock.lock();
        try {
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    //Creamos un grupo nuevo
    public int createGroup(String galias, String oalias, String ohostname) {
        lock.lock();
        try {
            System.out.println("Estamos creando el nuevo grupo\n");
            if (this.findGroup(galias) == -1) {
                grupo = new ObjectGroup(oalias, idGroup, oalias, ohostname);
            }
        } finally {
            lock.unlock();
        }
        return idGroup;
    }

    @Override
    public int findGroup(String alias) {
        System.out.println("Buscando grupo");
        for (int i = 0; i < objetosGrupo.size(); i++) {
            if (objetosGrupo.get(i).nombreGrupo.equals(alias)) {
                return objetosGrupo.get(i).idGrupo;
            }
        }
        return -1;
    }

    public int findGroup(int gid) throws RemoteException {
        try {
            lock.lock();
            for (int i = 0; i < objetosGrupo.size(); i++) {
                if (grupo.idGrupo == gid) {
                    return grupo.idGrupo;
                }
            }
            return -1; //Si no se ha encontrado
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean removeGroup(String galias, String oalias) {
        lock.lock();
        System.out.println("Eliminado");
        int gdelete = this.findGroup(galias);
        return removeGroup(gdelete, oalias);
    }

    @Override
    public boolean removeGroup(int gid, String oalias) { //segun el identificador
        lock.lock();
        System.out.println("Eliminado");
        grupo = getGroup(gid);
        if (grupo.listaMiembros.get(0).alias.equals(oalias)) {
            objetosGrupo.remove(grupo);
            lock.unlock();
            return true;
        }
        System.out.println("No es el propietario del grupo");
        return false;
    }

    //nos devuelve un grupo al pasarse su id
    public ObjectGroup getGroup(int gid) {
        System.out.println("Buscando grupo");
        if (gid != -1 && gid <= idGroup) {
            for (ObjectGroup og : objetosGrupo) {
                if (og.idGrupo == gid) {
                    System.out.print("Grupo encontrado");
                    return og;
                }
            }
        }
        try {
            throw new Exception();
        } catch (Exception e) {
            System.out.println("Grupo no encontrado");
        }
        return null;
    }

    @Override
    public GroupMember addMember(int id, String alias, String hostname) {
        lock.lock();
        System.out.print("Miembro añadido");
        grupo = getGroup(id);
        if (grupo.isMember(alias) == null) {
            lock.unlock();
            try {
                return grupo.addMember(alias, hostname);
            } catch (InterruptedException ex) {
            }
        }
        return null;
    }

    @Override
    public boolean removeMember(String galias, String alias) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GroupMember isMember(int gid, String alias) {
        lock.lock();
        System.out.println("Realizando comprobación");
        grupo = getGroup(gid);
        lock.unlock();
        return grupo.isMember(alias);
    }

    @Override
    public boolean StopMembers(int gid) {
        lock.lock();
        System.out.println("Bloqueando altas y bajas");
        lock.unlock();
        grupo.StopMember();
        return true;
    }

    @Override
    public boolean AllowMembers(int gid) {
        lock.lock();
        System.out.println("Desbloqueando altas y bajas");
        grupo = getGroup(gid);
        lock.unlock();
        grupo.AllowMembers();
        return true;
    }

    @Override
    public LinkedList<String> ListMembers(int gid) {
        lock.lock();
        LinkedList<String> aux = new LinkedList<>();
        grupo = getGroup(gid);

        for (ObjectGroup og : objetosGrupo) {
            if (gid != -1 && gid <= idGroup) {

                for (int i = 0; i < objetosGrupo.size(); i++) {
                    aux.add(grupo.ListMembers().get(i));
                }
            }

        }
        lock.unlock();
        return aux;

    }

    @Override
    public LinkedList<String> ListGroup() {
        LinkedList<String> listaAux = new LinkedList<>();
        lock.lock();
        for (int i = 0; i < this.objetosGrupo.size(); i++) {
            listaAux.add(objetosGrupo.get(i).nombreGrupo);
        }
        lock.unlock();
        return listaAux;

    }

    public static void main(String[] args) throws MalformedURLException, RemoteException {

        //Vamos a crear el servidor y a ponerlo a esucha mediante el puerto
        GroupServer Server;
        System.setProperty("java.security.policy", "C:\\Users\\Ángel\\Desktop\\CentralizedGroups\\src\\CentralizedGroups\\server-fichero.txt");
        System.setSecurityManager(new SecurityManager());
        int numPuerto = 1099;
        LocateRegistry.createRegistry(numPuerto);
        Server = new GroupServer();
        try {
            //Damos de alta el servidor
            Naming.rebind("//localhost" + ":" + numPuerto + "/HolaServer", Server);
        } catch (MalformedURLException ex) {
            System.out.println("Malformed exception appear when server was luch");
        }
        System.out.println("Servidor iniciado");

    }

}
