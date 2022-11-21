/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author usuario
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "jms/Noticias")
    ,
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/Noticias")
    ,
        @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "jms/Noticias")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class EjbMessageBean implements MessageListener {
    
    public EjbMessageBean() {
    }
    
    @Override
    public void onMessage(Message message) {
    }
    
}
