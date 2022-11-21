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
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {

    void DepositMessage(GroupMessage m) throws RemoteException;

    byte[] receiveGroupMessage(String galias) throws RemoteException;
}

