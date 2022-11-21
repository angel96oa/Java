
package proyectoremoto;

import javax.jms.*;
import javax.naming.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MsgConexion {


    public String nombreCola = "jms/Noticias"; // Nombre externo de la cola
    public Context contexto = null; // Contexto JNDI
    public TopicConnectionFactory factoria = null; // Factoria de conexiones
    public TopicConnection conexionCola = null; // conexion
    public TopicSession sesionCola = null; // sesion
    public Topic cola = null; // cola de mensajes

    public MsgConexion() {
    }

    public static void main(String[] args){
        try {
            InitialContext iniCtx = new InitialContext();
            TopicConnectionFactory tcf = (TopicConnectionFactory) iniCtx.lookup("jms/FactoriaConexiones");
            Topic t = (Topic) iniCtx.lookup("jms/Noticias");
            TopicConnection conexionTopico = tcf.createTopicConnection();
            TopicSession sesion = conexionTopico.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            TopicPublisher pub = sesion.createPublisher(t);
            conexionTopico.start();            
            TopicSubscriber subscriber = sesion.createSubscriber(t);
            subscriber.setMessageListener(new MessageListener() {

                @Override
               
                public void onMessage(Message message) {
                    if (message instanceof TextMessage) {
                        try {
                            System.out.println("Mensaje recibido:");
                            String recibido = null;
                            TextMessage mes = (TextMessage) message;
                            recibido = mes.getText();
                            System.out.println(recibido);
                        } catch (JMSException ex) {
                        }
                        
                    }

                }
            });
            //conexionTopico.start();
            
            String mensaje = null;
            do {
                System.out.println("Introduce mensaje: ");
                Scanner scan = new Scanner(System.in);
                mensaje = scan.nextLine();
                TextMessage msj = sesion.createTextMessage();
                msj.setText(mensaje);
                pub.publish(msj);
            } while (mensaje!="exit");
            pub.close();
            conexionTopico.close();
        } catch (Exception ex) {}
    }
    
}
