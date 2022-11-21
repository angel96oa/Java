/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paquete1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.jms.*;
import javax.naming.*;


public class MsgConexion {

public String nombreCola="jms/miCola"; // Nombre externo de la cola
public Context contexto=null; // Contexto JNDI
public QueueConnectionFactory factoria=null; // Factoria de conexiones
public QueueConnection conexionCola=null; // conexion
public QueueSession sesionCola=null; // sesion
public Queue cola = null; // cola de mensajes
    
public boolean inicializaCola() throws NamingException, JMSException{
    if (contexto == null){
// Aun no se ha realizado la inicializacion. Una vez realizada
// no tiene sentido volver a realizarla.
contexto = new InitialContext(); // Obtiene contexto JNDI
// Obtiene factoria de conexion a colas (ha debido ser creada externamente)
factoria = (QueueConnectionFactory) contexto.lookup("jms/__defaultConnectionFactory");
// Obtiene la cola (ha debido ser creada externamente)
cola = (Queue) contexto.lookup(nombreCola);
// Ahora crea la conexion a la cola
conexionCola=factoria.createQueueConnection();
// Crea la sesion
sesionCola = conexionCola.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
conexionCola.start(); // Hay que activar la conexion para empezar.
return true;
}
    else return false;
}

public void cerrarConexion() throws JMSException{
    conexionCola.close();
}
}
