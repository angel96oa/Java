/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquete1;

import javax.jms.*;
import javax.naming.*;

/**
 *
 * @author Ángel
 */
public class MsgConexion {

    public String nombreCola = "jms/miCola"; //nombre externo de la cola
    public Context contexto = null; //Contexto JNDI
    public QueueConnectionFactory factoria = null; //Factoria de conexiones
    public QueueConnection conexionCola = null; //conexion
    public QueueSession sesionCola = null; //sesion
    public Queue cola = null; //cola de mensajes

    public MsgConexion() {

    }
    
    public boolean inicializaCola() throws JMSException, NamingException{
        if(contexto==null){
            //Aun no se ha realizado la inicializacion. Una vez realizada
            //no tiene sentido volver a realizarla
            contexto = new InitialContext(); //Obtiene contexto JDNI
            //Obtiene factoria de conexion a colas (ha debido ser creada externamente)
            factoria = (QueueConnectionFactory) contexto.lookup("jms/QueueConnectionFactory");
            //obtiene la cola (ha debido ser creada externamente)
            cola = (Queue) contexto.lookup(nombreCola);
            //Ahora crea la conexion a la cola
            conexionCola=factoria.createQueueConnection();
            //Crea la sesion
            sesionCola = conexionCola.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
            conexionCola.start(); //Hay que activar la conexion para empezar
            return true;
        } else {
            return false;
        }
    }
    
    public boolean cerrarConexion() throws JMSException{
        if(this.conexionCola != null){
            conexionCola.close();
            return true;
        } else {
            return false;
        }
    }

}
