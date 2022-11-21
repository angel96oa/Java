/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CentralizedGroups;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Ángel
 */
public interface GroupServerInterface extends Remote{

    int createGroup(String galias, String oalias, String ohostname, int puerto) throws RemoteException;

    int findGroup(String galias) throws RemoteException;

    boolean removeGroup(String galias, String oalias) throws RemoteException;

    boolean removeGroup(int gid, String oalias) throws RemoteException;

    GroupMember addMember(int gid, String alias, String hostname, int puerto) throws RemoteException;

    GroupMember isMember(int gid, String alias) throws RemoteException;

    boolean removeMember(int gid, String oalias, String alias) throws RemoteException;

    boolean sendGroupMessage(GroupMember gm, byte[] msg) throws RemoteException;
}
