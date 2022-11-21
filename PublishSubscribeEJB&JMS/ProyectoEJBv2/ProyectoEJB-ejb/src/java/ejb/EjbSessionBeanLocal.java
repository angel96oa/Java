/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.LinkedList;
import javax.ejb.Local;

/**
 *
 * @author usuario
 */
@Local
public interface EjbSessionBeanLocal {
    void addMsg(String m);          //Publish New Message
    int getNumber();                //Get number of messages
    LinkedList<String> getList();   //Get current list of news
    void addToList(String m);       //Add a message to the list
}
