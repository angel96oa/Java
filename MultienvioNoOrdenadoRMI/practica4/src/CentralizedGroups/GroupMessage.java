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

public class GroupMessage implements Serializable{
    
        private byte[] mensaje = new byte[100];
    private GroupMember emisor;

    public GroupMessage(GroupMember emisor, byte[] mensaje) {
        this.emisor = emisor;
        this.mensaje = mensaje;
    }

    public byte[] getMensaje() {
        return mensaje;
    }

    public void setMensaje(byte[] mensaje) {
        this.mensaje = mensaje;
    }

    public GroupMember getEmisor() {
        return emisor;
    }

    public void setEmisor(GroupMember emisor) {
        this.emisor = emisor;
    }

    
}
