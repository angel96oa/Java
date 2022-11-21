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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ObjectGroup {

    //Variables previas.
    private String alias;
    private int id;
    private ArrayList<GroupMember> listaMiembros;
    private GroupMember propietario;
    private int contadorEnvios;
    private ReentrantLock cerrojo, cerrojo2;
    private Condition condicion;
    private boolean stop;
    private LinkedList<ObjectGroup> listaGrupos;

    //Objeto de grupo.
    public ObjectGroup(String galias, int gid, String oalias, String hostname, int puerto) {
        this.alias = galias;
        this.id = gid;
        listaMiembros = new ArrayList<GroupMember>();
        propietario = new GroupMember(oalias, hostname, 0, id, puerto);
        listaMiembros.add(propietario);
        cerrojo = new ReentrantLock();
        cerrojo2 = new ReentrantLock();
        condicion = cerrojo.newCondition();
        stop = false;
        contadorEnvios = listaMiembros.size() - 1;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        try {
            cerrojo.lock();
            this.alias = alias;
        } finally {
            cerrojo.unlock();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        try {
            cerrojo.lock();
            this.id = id;
        } finally {
            cerrojo.unlock();
        }

    }

    public ArrayList<GroupMember> getListaMiembros() {
        try {
            cerrojo.lock();
            return listaMiembros;
        } finally {
            cerrojo.unlock();
        }
    }

    public GroupMember isMember(String alias) {
        try {
            cerrojo.lock();
            for (GroupMember miembro : listaMiembros) {
                if (alias.equals(miembro.getAlias())) {
                    return miembro;
                }
            }
            return null;
        } finally {
            cerrojo.unlock();
        }
    }

    public GroupMember addMember(String alias, String hostname, int puerto) {
        try {

            while (stop) {
                condicion.await();
            }
            cerrojo.lock();
            //Condition
            if (isMember(alias) == null) {
                GroupMember nuevoMiembro = new GroupMember(alias, hostname, listaMiembros.size(), id, puerto);
                listaMiembros.add(nuevoMiembro);
                return nuevoMiembro;
            }
            return null;
        } catch (InterruptedException e) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
            return null;
        } finally {

            condicion.signal();
            cerrojo.unlock();
        }
    }

    public boolean removeMember(String alias) {
        try {
            while (stop) {
                condicion.await();
            }
            GroupMember miembroEliminar = isMember(alias);
            if (miembroEliminar != null && !alias.equals(propietario.getAlias())) {
                cerrojo.lock();
                listaMiembros.remove(miembroEliminar);
                return true;
            }
            return false;
        } catch (InterruptedException e) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally {
            condicion.signal();
            cerrojo.unlock();
        }
    }

    /*
     public void StopMembers() {

     stopMember = true;
        

     }

     public void AllowMembers() {

     try {
     cerrojo.lock();
     stopMember = false;
     condicion.signalAll();
     } catch (Exception e) {

     Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
     } finally {
     cerrojo.unlock();
     }
     }
     */
    public GroupMember getPropietario() {
        return propietario;
    }

    public void Sending() {
        try {
            cerrojo.lock();
            //Establecemos el bloqueo para garantizar la no agregacion o eliminacion de miembros al encontrarnos enviando
            stop = true;
            contadorEnvios += listaMiembros.size() + 1;
        } finally {
            cerrojo.unlock();
        }
    }

    public void EndSending(GroupMember gm) {
        try {
            cerrojo.lock();
            contadorEnvios--;
            stop = false;
            //Una vez finalizado el envio, se permite nuevamente añadir y quitar miembros
            if (contadorEnvios == 0) {
                condicion.signalAll();
            }
        } finally {
            cerrojo.unlock();
        }
    }


    public boolean sendGroupMessage(GroupMember gm, byte[] msg) {
        cerrojo.lock();
        try {
            //Creacion hilo de que extendera de Thread.
            SendingMessage hiloMensaje;
            for (ObjectGroup grupo : listaGrupos) {
                if (grupo.getId() == gm.getIdGrupo()) {
                    GroupMessage mensaje = new GroupMessage(gm, msg);
                    grupo.Sending();

                    for (int i = 0; i < this.contadorEnvios; i++) {
                        hiloMensaje = new SendingMessage(mensaje, grupo);
                        System.out.println("Enviando mensaje:" + msg.toString() + "del Miembro: " + gm.getId() + "al grupo: "
                                + gm.getIdGrupo());
                        hiloMensaje.start();
                    }

                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            cerrojo.unlock();
        }

    }

}
