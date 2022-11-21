/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete1;

import javax.jms.*;
import javax.naming.NamingException;

/**
 *
 * @author Ángel
 */
public class Consumidor {

    public static void main(String[] args) throws NamingException, JMSException {
        int i; //para recibir mensajes
        MsgConexion mc; //Conexion propia a la cola
        QueueReceiver receptor; //receptor
        TextMessage m; //mensaje recibido
        boolean ok; //comprobacion de retorno

        mc = new MsgConexion(); //Crea su objeto MsgConexion
        ok = mc.inicializaCola(); //Inicializa parametros
        if (ok) {
            //prepara receptor sobre cola
            receptor = mc.sesionCola.createReceiver(mc.cola);
            ok = mc.inicializaCola();   // Inicializa parametros
            if (ok) {
// Prepara receptor sobre cola
                receptor = mc.sesionCola.createReceiver(mc.cola);
// Recibe los mensajes:
                for (i = 0; i < 5; i++) {
                    m = (TextMessage) receptor.receive(1000);
                    System.out.println("Mensaje recibido:" + m.getText());
                }
            }
        }
    }
}
