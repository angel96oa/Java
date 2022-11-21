import java.util.ArrayList;
import java.util.Scanner;

public class BlackWhite {

	public MiniMax algoritmoMiniMax = null;
	public PodaAlfaBeta algoritmoPoda = null;
	public int color = 0;
	public int depth = 0;
	public State estado = null;
	public int nJugadas = 0;
	
	public double nNodosTotal=0;
	public long tiempoTotalBusqueda;
	public long tiempoBusqueda;
	
	ArrayList<ParActionValue> listaMovimientos = new ArrayList<>();

	public BlackWhite(MiniMax minimax, int nJugadas) {
		this.algoritmoMiniMax = minimax;
		this.color=this.algoritmoMiniMax.color;
		this.depth= this.algoritmoMiniMax.depth;
		this.estado=this.algoritmoMiniMax.initialState;
		this.nJugadas = nJugadas;
	}

	public BlackWhite(PodaAlfaBeta poda, int nJugadas) {
		this.algoritmoPoda = poda;
		this.color = this.algoritmoPoda.color;
		this.depth = this.algoritmoPoda.depth;
		this.estado = this.algoritmoPoda.initialState;
		this.nJugadas = nJugadas;	
	}

	public void busca() {

		State estadoAux = estado;
		int x = 0;
		int colorContrario=0;
		
		
		for(int i=nJugadas; i>0; i--) {

			if(color==1) {
				colorContrario=0;
			} else {
				colorContrario=1;
			}
			
			if(algoritmoMiniMax !=null) { //si somos minimax

				MiniMax minimaxAux = new MiniMax(estadoAux, depth, color);
				
				//obtenemos la accion resultante de la busqueda por minimax y con esa accion generamos un estado que es del que partimos el jugador
				tiempoBusqueda=System.currentTimeMillis();
				ParActionValue aux = new ParActionValue(minimaxAux.doSearch());
				tiempoBusqueda = System.currentTimeMillis()-tiempoBusqueda;
				tiempoTotalBusqueda = tiempoTotalBusqueda + tiempoBusqueda;
				
				listaMovimientos.add(aux);
				Action a = new Action(aux.a);
				State estadoAux1 = estadoAux.applyAction(a);
				//hasta aqui la maquina
				
				Utils.printBoard(estadoAux1);
				
				//A partir de aqui es analogo a lo explicado en dummy
				ArrayList<Action> sucesores = new ArrayList<Action>();
				sucesores = estadoAux1.getPossibleActions(colorContrario);
				ArrayList<State> stateAux = new ArrayList<State>();
				ArrayList<Action> actionAux = new ArrayList<Action>();
				//recorro mis sucesores
				while(sucesores.size()>0) {
					actionAux.add(sucesores.get(0));
					Action a1 = sucesores.remove(0);
					State stateAux1 = estadoAux1.applyAction(a1);
					stateAux.add(stateAux1);
					
				}
				
				for(int j=0; j<stateAux.size(); j++) {
					System.out.println(j+". Posicion inicial col: "+actionAux.get(j).m_initPos.col+" row: "+actionAux.get(j).m_initPos.row+" va a la posicion col: "+actionAux.get(j).m_finalPos.col+" row:"+actionAux.get(j).m_finalPos.row);
				}
				
				
				int eleccion;
				Scanner teclado = new Scanner(System.in);
				
				System.out.println("\n\nIndique que opcion quiere mover: ");
				eleccion = teclado.nextInt();
				
				estadoAux = stateAux.get(eleccion);
				
				System.out.println("\nMovimiento nº"+x);
				x++;
				Utils.printBoard(estadoAux);
				System.out.println("\n");
				
				nNodosTotal = nNodosTotal + minimaxAux.nNodos;
				
				
			} else {	//si somos poda
				PodaAlfaBeta poda = new PodaAlfaBeta(estadoAux, depth, color);
				//analogo a lo explicado arriba
				tiempoBusqueda=System.currentTimeMillis();
				ParActionValue aux = new ParActionValue(poda.doSearch());
				tiempoBusqueda = System.currentTimeMillis()-tiempoBusqueda;
				tiempoTotalBusqueda = tiempoTotalBusqueda + tiempoBusqueda;
				
				listaMovimientos.add(aux);
				Action a = new Action(aux.a);
				State estadoAux1 = estadoAux.applyAction(a);
				Utils.printBoard(estadoAux);
				
				//aqui nosotros, analogo a dummy
				ArrayList<Action> sucesores = new ArrayList<Action>();
				sucesores = estadoAux1.getPossibleActions(colorContrario);
				ArrayList<State> stateAux = new ArrayList<State>();
				ArrayList<Action> actionAux = new ArrayList<Action>();
				//recorro mis sucesores
				while(sucesores.size()>0) {
					actionAux.add(sucesores.get(0));
					Action a1 = sucesores.remove(0);
					State stateAux1 = estadoAux1.applyAction(a1);
					stateAux.add(stateAux1);
					
				}
				
				for(int j=0; j<stateAux.size(); j++) {
					System.out.println(j+". Posicion inicial col: "+actionAux.get(j).m_initPos.col+" row: "+actionAux.get(j).m_initPos.row+" va a la posicion col: "+actionAux.get(j).m_finalPos.col+" row:"+actionAux.get(j).m_finalPos.row);
				}
				
				
				int eleccion;
				Scanner teclado = new Scanner(System.in);
				
				System.out.println("\n\nIndique que opcion quiere mover: ");
				eleccion = teclado.nextInt();
				
				estadoAux = stateAux.get(eleccion);
				
				System.out.println("\nMovimiento nº"+x);
				x++;
				Utils.printBoard(estadoAux);
				System.out.println("\n");
				
				nNodosTotal = nNodosTotal + poda.nNodos;
				
				
			}

		}
		

		
		System.out.println("Nodos en total: "+nNodosTotal+" nodos");
		System.out.println("Tiempo de busqueda: "+tiempoTotalBusqueda + " ms");
	}



}
