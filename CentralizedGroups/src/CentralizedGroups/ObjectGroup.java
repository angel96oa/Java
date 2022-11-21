/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CentralizedGroups;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Ángel
 */
public class ObjectGroup {

    String nombreGrupo = "";
    int idGrupo = -1;
    ArrayList<GroupMember> listaMiembros = new ArrayList<GroupMember>();
    GroupMember propietario;

    int idAltaMiembro = 0; //contador para los miembros
    boolean bloqueo;
    final ReentrantLock mutex = new ReentrantLock(true);
    final ReentrantLock mutex2 = new ReentrantLock(true);
    final Condition control = mutex.newCondition();

    public ObjectGroup(String aliasG, int idG, String aliasU, String hostU) {
        aliasG = this.nombreGrupo;
        idG = this.idGrupo;
        this.propietario = new GroupMember(aliasU, hostU, idAltaMiembro, idGrupo);
        listaMiembros.add(propietario);
    }

    //Comprueba que el alias que se le pasa por parametro sea miembro del grupo
    public GroupMember isMember(String aliasU) {

        //si es miembro, devuelve el miembro en la variable nuevo del tipo groupmember
        GroupMember nuevo = null;
        mutex.lock();
        try {
            //recorre la lista de miembros comparando el alias que le hemos pasado
            for (int i = 0; i < this.listaMiembros.size(); i++) {
                if (this.listaMiembros.get(i).alias.equals(aliasU)) {
                    nuevo = this.listaMiembros.get(i);
                }
            }
            //desbloquea el proceso
        } finally {
            mutex.unlock();
        }
        //devuelve el objeto que se ha comparado
        return nuevo;

    }

    public GroupMember addMember(String aliasU, String hostU) throws InterruptedException {
        try {
            //Crea la variable existe para comprobar si existe
            GroupMember existe;
            //primero comprueba que otro proceso no este usando addMember
            mutex.lock();
            if (bloqueo) { //si está bloqueado, espera a que se desbloquee
                System.out.println("No se pueden añadir porque esta bloqueado");
                control.await();
            }

            existe = this.isMember(aliasU); //llama al metodo de arriba para comprobar si está ya en el grupo
            if (existe == null) //si existe uno ya en el mismo grupo devuelve null
            {
                return null;
            }
            //si no existe añade uno
            GroupMember nuevo = new GroupMember(aliasU, hostU, idAltaMiembro, idGrupo);
            this.listaMiembros.add(nuevo);
            return nuevo;
        } finally {
            //desbloquea el proceso
            mutex.unlock();
        }

    }

    boolean removeMember(String aliasU) throws InterruptedException {
        mutex.lock();
        try {
            //cogemos un objemos auxiliar de groupmember para comprobar si existe
            GroupMember aux = this.isMember(aliasU);
            mutex.lock();
            if (aux.alias!=null && aux.id !=0) {
                //desde el auxiliar, borramos en la lista de miembros el miembro que 
                //cumple las caracteristicas
                this.listaMiembros.remove(aux);
                //si lo borra, devuelve true
                return true;
            } else {
                //si no lo borra, devuelve falso
                return false;
            }
        } finally {
            mutex.unlock();
        }
    }
    
    //ponemos la variable bloqueo a true
    public void StopMember(){
        mutex.lock();
        try{
            bloqueo = true;
        } finally {
            mutex.unlock();
        }
    }
    
    //la ponemos a false y desbloqueamos el proceso y le damos continuidad
    //con el signal
    public void AllowMembers(){
        mutex.lock();
        try{
            bloqueo = false;
            control.signal();
        } finally {
            mutex.unlock();
        }
    }
    
    //devuelve la lista de miembtos
    public LinkedList<String> ListMembers(){
        LinkedList<String> listaAux = new LinkedList<>();
        mutex.lock();
        try{
            //añade al array auxiliar el alias de cada miembro en orden
            for (int i = 0; i < this.listaMiembros.size(); i++) {
                listaAux.add(this.listaMiembros.get(i).alias);
            }
            //devuelve el array
            return listaAux;
        } finally{
            //desbloquea el proceso
            mutex.unlock();
        }
        
    }
    
}
