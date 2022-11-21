/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.*;
import javax.jms.*;
import javax.naming.*;


/**
 *
 * @author usuario
 */
@Singleton
public class EjbSessionBean implements EjbSessionBeanLocal {

    private int count_msg = 0;
    private LinkedList<String> lista;

    @Override
    public void addMsg(String m) {
        InitialContext iniCtx = null;
        try {
            iniCtx = new InitialContext();
        } catch (NamingException ex) {
            Logger.getLogger(EjbSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        Object tmp = null;
        try {
            tmp = iniCtx.lookup("jms/FactoriaConexiones");
        } catch (NamingException ex) {
            Logger.getLogger(EjbSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        TopicConnectionFactory tcf = (TopicConnectionFactory) tmp;
        try {
            Topic t = (Topic) iniCtx.lookup("jms/Noticias");
        } catch (NamingException ex) {
            Logger.getLogger(EjbSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getNumber() {
        return count_msg;
    }

    @Override
    public LinkedList<String> getList() {
        return lista;
    }

    @Override
    public void addToList(String m) {
        lista.add(m);
    }

}
