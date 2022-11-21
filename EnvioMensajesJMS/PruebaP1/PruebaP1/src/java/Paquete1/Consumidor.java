/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paquete1;


import javax.jms.*;
import javax.naming.*;


public class Consumidor {
    public static void main(String[] args) throws NamingException, JMSException {
   int i; // Para recibir varios mensajes
MsgConexion mc; // Conexion propia a cola
QueueReceiver receptor; // Receptor
TextMessage m; // Mensaje recibido
boolean ok; // Comprobacion retorno

mc = new MsgConexion(); // Crea su objeto MsgConexion
ok=mc.inicializaCola(); // Inicializa parametros
if (ok){
// Prepara receptor sobre cola
receptor = mc.sesionCola.createReceiver(mc.cola);
// Recibe los mensajes:
for (i=0; i<5; i++){
m = (TextMessage)receptor.receive(1000);
System.out.println("Mensaje recibido:" + m.getText());
}
    }
mc.cerrarConexion();
}
}
