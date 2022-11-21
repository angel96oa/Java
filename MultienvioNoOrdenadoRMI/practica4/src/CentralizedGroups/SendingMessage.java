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

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendingMessage extends Thread {
    
    int id;
    GroupMessage mensaje;
    ClientInterface cliente;
    ObjectGroup grupo;

    public SendingMessage(GroupMessage mensaje, ObjectGroup grupo) throws RemoteException {

        this.mensaje = mensaje;
        this.grupo = grupo;
        this.id = grupo.getId();

    }

    @Override
    public void run() {
        System.setProperty("java.security.policy", "C:\\Users\\Ángel\\Dropbox\\Universidad\\Sistemas distribuidos\\practicas\\práctica 4\\practica4\\src\\CentralizedGroups\\client-policy");
        //Establecemos la politica de seguridad del fichero client-policy
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        for (GroupMember miembro : grupo.getListaMiembros()) {                  //Se recorren todos los miembros del grupo
            if (!miembro.getAlias().equals(mensaje.getEmisor().getAlias())) {    //Si no es el emisor
                try {
                    cliente = (ClientInterface) Naming.lookup("rmi://" + miembro.getHostname() + ":" + miembro.getPuerto() + "/" + miembro.getAlias());
                    
                   int rand = (int) (Math.random() * 60);
                    Thread.sleep(30 + rand);
                    
                    if (cliente != null) {
                        cliente.DepositMessage(mensaje);
                    }

                } catch (RemoteException | NotBoundException | MalformedURLException ex) {
                    Logger.getLogger(SendingMessage.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Excepción método run");
                } catch (InterruptedException ex) {
                    Logger.getLogger(SendingMessage.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
            grupo.EndSending(miembro);
        }

    }
}
