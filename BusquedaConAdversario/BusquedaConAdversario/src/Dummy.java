import java.util.ArrayList;
import java.util.Scanner;

public class Dummy {
	public int color = 0;
	public int depth = 0;
	public State estado = null;
	public int nJugadas = 0;

	

	public Dummy(State estado, int nJugadas, int color, int depth) {
		this.estado = estado;
		this.nJugadas = nJugadas;
		this.color = color;
		this.depth = depth;
	}

	public void busca() {

		State estadoAux = estado;
		int x = 0;
		for(int i=nJugadas; i>0; i--) {
			/*
			 * Generamos un arraylist de tipo action que nos guarda las posibles acciones a partir del estado que tenemos
			 */
			ArrayList<Action> sucesores = new ArrayList<Action>();
			sucesores = estado.getPossibleActions(color);
			/*
			 * stateAux y actionAux son arraylist auxiliares para obtener a partir de las acciones sucesoras del estado, todos los posibles estados hijos
			 */
			ArrayList<State> stateAux = new ArrayList<State>();
			ArrayList<Action> actionAux = new ArrayList<Action>();
			/*
			 * recorro mis sucesores para que stateAux pueda recorrerlo y asi ver las opciones que tengo de movimiento
			 */
			while(sucesores.size()>0) {
				actionAux.add(sucesores.get(0));
				Action a1 = sucesores.remove(0);
				State stateAux1 = estado.applyAction(a1);
				stateAux.add(stateAux1);
				
			}
			
			/*
			 * Veo que opciones de movimientos tengo
			 */
			for(int j=0; j<stateAux.size(); j++) {
				System.out.println(j+". Posicion inicial col: "+actionAux.get(j).m_initPos.col+" row: "+actionAux.get(j).m_initPos.row+" va a la posicion col: "+actionAux.get(j).m_finalPos.col+" row:"+actionAux.get(j).m_finalPos.row);
			}
			
			/*
			 * Obtengo por teclado que pieza quiero mover, cojo de stateAux el estado que indico por teclado y lo guardo en una variable fuera del bucle de movimientos
			 * Para poder usarla al inicio del bucle
			 */
			int eleccion;
			Scanner teclado = new Scanner(System.in);
			
			System.out.println("\n\nIndique que opcion quiere mover: ");
			eleccion = teclado.nextInt();
			
			estadoAux = stateAux.get(eleccion);
			
			System.out.println("\nMovimiento nº"+x);
			x++;
			Utils.printBoard(estadoAux);
			System.out.println("\n");
			
			


		}
	}
}
