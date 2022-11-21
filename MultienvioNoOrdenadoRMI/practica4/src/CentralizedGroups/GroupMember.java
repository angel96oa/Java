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
import java.io.Serializable;

public class GroupMember implements Serializable {

    private String alias; //Alias del miembro
    private String hostname; //Hostname
    private int id; //Identificador del miembro dentro de grupo
    private int idGrupo; //Identificador del grupo
    private int puerto; //Puerto del cliente

        public GroupMember(String alias, String hostname, int id, int idGrupo, int puerto) {
        this.alias = alias;
        this.hostname = hostname;
        this.id = id;
        this.idGrupo = idGrupo;
        this.puerto = puerto;
    }
    
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

}
