/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete1;
import javax.jms.*;
import javax.naming.NamingException;

public class Productor {
    public static void main(String[] args) throws JMSException, NamingException {
        int i; //Para enviar varios mensajes
        MsgConexion mc; //Conexion propia a la cola
        QueueSender emisor; //emisor
        TextMessage m; //Mensaje a enviar
        boolean ok; //para comprobar el retorno de metodo
        
        mc = new MsgConexion(); //crea el objeto MsgConexion
        ok=mc.inicializaCola(); //Prepara los parametros
        if(ok){
            //prepara emisor
            emisor = mc.sesionCola.createSender(mc.cola);
            //prepara el mensaje
            m=mc.sesionCola.createTextMessage();
            //hace varios envios
            for (i = 0; i < 5; i++) {
                m.setText("Hola Mundo distante"+i);
                emisor.send(m);
            }
        }
    }
}
