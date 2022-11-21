/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CentralizedGroups;

import com.sun.corba.se.spi.activation.Server;
import java.rmi.AccessException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ángel
 */
public class Client extends UnicastRemoteObject implements ClientInterface {

    int idGrupo; // Identificador del grupo
    GroupServerInterface srv;
    String alias = "", galias = "", ualias = "", hostname = "";
    public Scanner in;

    public Client() throws RemoteException {
        super();
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        System.out.println("Introduce el alias del cliente:");

        in = new Scanner(System.in);
        alias = in.next();
    }

    public void crearGrupo() throws RemoteException {
        System.out.println("Pon nombre de grupo");
        galias = in.next();
        idGrupo = srv.createGroup(galias, alias, "localhost");
        if (idGrupo != -1) {
            System.out.println("Hemos creado tu grupo, de id: " + idGrupo);
        }
    }

    public void EliminarGrupo() throws RemoteException {
        System.out.println("Pon nombre del dueño");
        ualias = in.next();
        System.out.println("Por nombre(a) y por idGrupo(b)");
        String op = in.next();
        switch (op) {
            case "a":
                System.out.println("Pon nombre del grupo");
                galias = in.next();
                if (srv.removeGroup(galias, ualias)) {
                    System.out.println("Eliminado");
                }
                break;
            case "b":
                System.out.println("Pon id del grupo");
                idGrupo = in.nextInt();
                if (srv.removeGroup(idGrupo, ualias)) {
                    System.out.println("Eliminado");
                }
                break;

        }
    }

    public void AñadirMiembro() throws RemoteException {
        System.out.println("Pon nombre de usuario");
        ualias = in.next();
        System.out.println("Pon nombre de host");
        hostname = in.next();
        System.out.println("Pon id de grupo");
        idGrupo = in.nextInt();
        if (srv.addMember(idGrupo, ualias, hostname) != null) {
            System.out.println("Añadido");
        } else {
            System.out.println("Ya existia");
        }
    }

    public void EliminarMiembro() throws RemoteException {
        System.out.println("Pon nombre de usuario");
        ualias = in.next();
        System.out.println("Pon id de grupo");
        idGrupo = in.nextInt();
        if (!srv.removeGroup(idGrupo, ualias)) {
            System.out.println("Ya existía");
        } else {
            System.out.println("Eliminado");
        }
    }

    public void BloquearAltasBajas() throws RemoteException {
        System.out.println("Pon id de grupo");
        idGrupo = in.nextInt();
        srv.StopMembers(idGrupo);
    }

    public void DesbloquearAltasBajas() throws RemoteException {
        System.out.println("Pon id de grupo");
        idGrupo = in.nextInt();
        srv.AllowMembers(idGrupo);
    }

    public void Terminar() {
        try {
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Miembro() throws RemoteException {
        System.out.println("Pon nombre de usuario");
        ualias = in.next();
        System.out.println("Pon id de grupo");
        idGrupo = in.nextInt();
        if (srv.isMember(idGrupo, ualias) != null) {
            System.out.println("Si, es miembro");
        } else {
            System.out.println("No lo es");
        }
    }

    public static void main(String[] args) {
        System.setProperty("java.security.policy", "C:\\Users\\Ángel\\Desktop\\CentralizedGroups\\src\\CentralizedGroups\\cliente-fichero.txt");

        try {
            Client c = new Client();
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            c.srv = (GroupServerInterface) registry.lookup("rmi://localhost:1099/HolaServer");

            int i = 0;

            System.out.println("Elija una accion:");
            while (i < 8) {
                System.out.println("1. Crear grupo");
                System.out.println("2. Eliminar grupo");
                System.out.println("3. Añadir miembro");
                System.out.println("4. Eliminar miembro");
                System.out.println("5. Bloquear altas/bajas");
                System.out.println("6. Desbloquear altas/bajas");
                System.out.println("7. ¿Es miembro de un grupo?");
                System.out.println("8. Terminar");

                // in = new Scanner(System.in);
                String s = c.in.next();
                switch (s) {
                    case "1"://1. Crear grupo
                        c.crearGrupo();
                        break;
                    case "2"://2. Eliminar grupo
                        c.EliminarGrupo();
                        break;
                    case "3"://3. Añadir miembro
                        c.AñadirMiembro();
                        break;
                    case "4"://4. Eliminar miembro
                        c.EliminarMiembro();
                        break;
                    case "5"://5. Bloquear altas/bajas
                        c.BloquearAltasBajas();
                        break;
                    case "6": //6. Desbloquear altas/bajas
                        c.DesbloquearAltasBajas();
                        break;
                    case "7":
                        c.Miembro();
                        break;
                    case "8":
                        c.Terminar();
                        i = 8;
                        break;

                }
                i++;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GroupServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
