/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CentralizedGroups;

import java.io.Serializable;

/**
 *
 * @author Ángel
 */
public class GroupMember implements Serializable {
    String alias;
    String hostname;
    int id;
    int idGrupo;
      
    public GroupMember(String alias, String hostname, int id, int idGroup){
        alias = this.alias;
        hostname = this.hostname;
        id = this.id;
        idGroup = this.idGrupo;
    }
}
