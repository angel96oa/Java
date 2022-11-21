/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CentralizedGroups;

import java.util.LinkedList;

/**
 *
 * @author Ángel
 */
public interface GroupServerInterface {
    int createGroup(String galias, String oalias, String ohostname);
    int findGroup(String alias);
    boolean removeGroup(String galias, String oalias);
    boolean removeGroup(int galias, String oalias);
    GroupMember addMember(int id, String alias, String hostname);
    boolean removeMember(String galias, String alias);
    GroupMember isMember(int gid, String alias);
    boolean StopMembers(int gid);
    boolean AllowMembers(int gid);
    
    LinkedList<String> ListMembers(int gid);
    LinkedList<String> ListGroup();
}
