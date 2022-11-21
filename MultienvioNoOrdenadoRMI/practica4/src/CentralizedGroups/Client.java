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
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends UnicastRemoteObject implements ClientInterface{


    
    //Variables previas.
    private final ReentrantLock cerrojo = new ReentrantLock(true);                       
    private final Condition condicion = cerrojo.newCondition();                          
    Queue<GroupMessage> colaMensajes = new LinkedList<GroupMessage>();                 
    byte[] mensaje;
    static GroupServerInterface stub;

    public Client() throws RemoteException {
        super();
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
    }

    public static void main(String[] args) {
        //Variables previas.
        Scanner teclado = new Scanner(System.in);
        String oalias;
        Boolean salir = false;
        int puertoServidor;
        
        //Introduccion datos del servidor.
        System.out.println("Indique IP servidor:");
        String ip = teclado.next();
        System.out.println("Indique puerto servidor");
        puertoServidor = teclado.nextInt();
        
        //Introduccion datos del cliente
        System.out.println("Indique Alias Cliente");                   
        oalias = teclado.next();                                                
        System.out.println("Indique puerto cliente");
        int puertoClient = teclado.nextInt();
        
        try {
            
            //Establecemos la politica de seguridad con el fichero correspondiente.
            System.setProperty("java.security.policy", "C:\\Users\\Ángel\\Dropbox\\Universidad\\Sistemas distribuidos\\practicas\\práctica 4\\practica4\\src\\CentralizedGroups\\client-policy");         
            
            
            //Creacion del gestor de seguridad.
            if (System.getSecurityManager() == null) {                            
                System.setSecurityManager(new SecurityManager());               
            }
            
            Client cliente = new Client();   
            
            //Crear un registro de objeto remoto que acepte llamadas por un puerto especifico.
            LocateRegistry.createRegistry(puertoClient);
            
            //Conecta rmiregistry en este ordenador e informa que ObjetoRemoto debe publicarse al exterior.
            Naming.rebind("rmi://" + InetAddress.getLocalHost().getHostAddress() + ":" + puertoClient + "/" + oalias, cliente);
            
            //Devuelve la referencia para el objeto remoto servidor con su nombre especifico.
            stub = (GroupServerInterface) Naming.lookup("rmi://" + ip + ":" + puertoServidor + "/GroupServer");

            while (!salir) {
                System.out.println("Menu Cliente:\n");
                System.out.println("1. Crear grupo");
                System.out.println("2. Eliminar grupo");
                System.out.println("3. Añadir miembro a un grupo");
                System.out.println("4. Eliminar miembro de grupo");
                System.out.println("5. Enviar mensaje a un grupo");
                System.out.println("6. Recibir mensajes");
                System.out.println("7. Comprobar miembro");
                System.out.println("8. Encontrar grupo");
                System.out.println("9. Salir");

                System.out.println("Introduce opción: ");
                String opcion = teclado.next();
                switch (opcion) {
                    case "1": {
                        System.out.println("\n Crear grupo:");
                        System.out.println("Indique el nombre del grupo:");
                        if (stub.createGroup(teclado.next(), oalias, InetAddress.getLocalHost().getHostAddress(), puertoClient) >= 0) {
                            System.out.println("El grupo se ha creado de forma correcta.");
                            
                        } else {
                            System.out.println("El grupo se ha creado de forma incorrecta.");
                        }
                        break;
                    }
                    case "2": {
                        int idGrupo;
                        String propietario;
                        System.out.println("\n Eliminar grupo:");
                        System.out.println("Introduce el id del grupo a eliminar:");
                        idGrupo = teclado.nextInt();
                        System.out.println("Introduce el nombre del propietario:");
                        propietario = teclado.next();
                        if (stub.removeGroup(idGrupo, propietario)) {
                            System.out.println("El grupo " + idGrupo + " ha sido eliminado.");
                        } else {
                            System.out.println("El grupo " + idGrupo + "no ha podido ser eliminado.");
                        }
                        break;
                    }
                    case "3": {
                        int idGrupo;
                        String miembro;
                        String hostname;
                        int puerto;
                        System.out.println("\n Añadir miembro a un grupo:");
                        System.out.println("Introduce el id del grupo:");
                        idGrupo = teclado.nextInt();
                        System.out.println("Introduce el alias del miembro:");
                        miembro = teclado.next();
                        System.out.println("Introduce la IP del miembro:");
                        hostname = teclado.next();
                        System.out.println("Introduce un puerto para el cliente:");
                        puerto = teclado.nextInt();

                        if (stub.addMember(idGrupo, miembro, hostname, puerto) != null) {
                            System.out.println("El miembro " + miembro + " ha sido añadido correctamente");
                        } else {
                            System.out.println("El miembro " + miembro + " no ha sido añadido correctamente");
                        }
                        break;
                    }
                    case "4": {
                        int idGrupo;
                        String miembro;
                        System.out.println("\n Eliminar miembro de un grupo:");
                        System.out.println("Introduce el id del grupo:");
                        idGrupo = teclado.nextInt();
                        System.out.println("Introduce el nombre del miembro:");
                        Scanner p = new Scanner(System.in);
                        miembro = teclado.next();
                        if (stub.removeMember(idGrupo, oalias, miembro)) {
                            System.out.println("El miembro " + miembro + " ha sido eliminado correctamente");
                        } else {
                            System.out.println("El miembro " + miembro + " no ha sido eliminado");
                        }
                        break;
                    }
                    case "5": {
                        byte[] cadena;
                        System.out.println("\n Enviar mensajes a un grupo:");
                        System.out.print("\nIntroduce el id del grupo: ");
                        int id = teclado.nextInt();
                        System.out.println("Introduce un mensaje a enviar:");
                        cadena = teclado.next().getBytes();
                        GroupMember miembro = stub.isMember(id, oalias);
                        if (miembro != null) {
                            if (stub.sendGroupMessage(miembro, cadena)) {
                                System.out.println("Mensaje enviado correctamente.");
                            } else {
                                System.out.println("Error.");
                            }
                        } else {
                            System.out.println("Está intentando enviar un mensaje a un grupo al que no pertenece.");
                        }
                        break;
                    }

                    case "6": {
                        System.out.println("\n Recibir mensajes:");
                        byte[] cadena;
                        String aux2;
                        System.out.print("\nIndique el alias del grupo para recibir el mensaje: ");
                        Scanner scc = new Scanner(System.in);
                        aux2 = scc.nextLine();
                        cadena = cliente.receiveGroupMessage(aux2);
                        if (cadena == null) {
                            System.out.println("\nNo hay mensajes para el grupo " + aux2);
                        } else {
                            System.out.println("Mensaje " + new String(cadena));
                        }
                        break;
                    }
                    case "7": {
                        int idGrupo;
                        String miembro;
                        System.out.println("\n Comprobar miembro:");
                        System.out.println("Indique el id del grupo:");
                        idGrupo = teclado.nextInt();
                        System.out.println("Indique el nombre del miembro:");
                        miembro = teclado.next();

                        if (stub.isMember(idGrupo, miembro) != null) {
                            System.out.println("El miembro pertenece al grupo.");
                        } else {
                            System.out.println("El miembro no pertenece al grupo.");
                        }
                        break;

                    }

                    case "8": {
                        System.out.println("\n Econtrar grupo:");
                        System.out.println("Indique el nombre del grupo a buscar.");
                        int idGrupo = stub.findGroup(teclado.next());

                        if (idGrupo >= 0) {
                            System.out.println("El grupo buscado es el: " + idGrupo);
                        } else {
                            System.out.println("No existe el grupo buscado.");
                        }
                        break;
                    }
                    case "9": {
                        salir = true;
                        System.out.println("\n SALIR \n");
                        System.out.println("Cerrando Cliente: " + oalias);
                        UnicastRemoteObject.unexportObject(cliente, true);
                        break;
                    }
                    default:
                        System.out.println("Ha pulsado una opción incorrecta.");
                        break;

                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void DepositMessage(GroupMessage m) throws RemoteException {
        try {
            cerrojo.lock();
            colaMensajes.add(m);
            condicion.signalAll();
        } finally {
            cerrojo.unlock();
        }
    }

     @Override
    public byte[] receiveGroupMessage(String galias) throws RemoteException {
        cerrojo.lock();
        try {
            int idGrupo = stub.findGroup(galias);
            if (idGrupo == -1) {
                return null;
            }
            while (true) {
                for (GroupMessage m : colaMensajes) {
                    if (m.getEmisor().getIdGrupo() == idGrupo) {
                        byte[] msg = m.getMensaje().clone();
                        colaMensajes.remove(m);
                        return msg;
                    }
                }

                condicion.await();  //Esperamos
            }

        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            cerrojo.unlock();
        }

    }
}
