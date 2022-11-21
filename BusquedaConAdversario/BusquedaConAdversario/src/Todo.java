import java.util.ArrayList;

public class Todo {
	public MiniMax algoritmoMiniMax = null;
	public PodaAlfaBeta algoritmoPoda = null;
	public int color = 0;
	public int depth = 0;
	public State estado = null;
	public int nJugadas = 0;

	public double nNodosTotal=0;
	public long tiempoTotalBusqueda=0;
	public long tiempoBusqueda=0;
	
	public Todo(MiniMax minimax, int nJugadas) {
		this.algoritmoMiniMax = minimax;
		this.color=this.algoritmoMiniMax.color;
		this.depth= this.algoritmoMiniMax.depth;
		this.estado=this.algoritmoMiniMax.initialState;
		this.nJugadas = nJugadas;

	}

	public Todo(PodaAlfaBeta poda, int nJugadas) {
		this.algoritmoPoda = poda;
		this.color = this.algoritmoPoda.color;
		this.depth = this.algoritmoPoda.depth;
		this.estado = this.algoritmoPoda.initialState;
		this.nJugadas = nJugadas;
	}

	public void busca() {

		State estadoAux = estado;
		int x = 0;
		
		
		for(int i=nJugadas; i>0; i--) {

			if(algoritmoMiniMax !=null) { //si somos minimax
				if(i%2==0) {
					color=0;
				} else {
					color=1;
				}
				MiniMax minimaxAux = new MiniMax(estadoAux, depth, color);
				tiempoBusqueda=System.currentTimeMillis();
				ParActionValue aux = new ParActionValue(minimaxAux.doSearch());
				tiempoBusqueda = System.currentTimeMillis()-tiempoBusqueda;
				tiempoTotalBusqueda = tiempoTotalBusqueda + tiempoBusqueda;
				
				
				
				Action a = new Action(aux.a);
				State estadoAux1 = estadoAux.applyAction(a);
				
				
				
				estadoAux = estadoAux1;
				x++;
				System.out.println("Movimiento nº"+x);

				Utils.printBoard(estadoAux);
				System.out.println("\n");
				
				
				
				nNodosTotal = nNodosTotal + minimaxAux.nNodos;
				tiempoTotalBusqueda = tiempoTotalBusqueda + minimaxAux.tiempoBusqueda;
				
				if(estadoAux.isFinal()) {
					if(estadoAux.m_color==0) {
						System.out.println("Se han comido al rey de color blanco\n");
					} else {
						System.out.println("Se han comido al rey de color negro\n");
					}
					
					break;
				}

			} 
			
			if(algoritmoPoda!=null){//si somos poda
				if(i%2==0) {
					color=0;
				} else {
					color=1;
				}
				
				PodaAlfaBeta poda = new PodaAlfaBeta(estadoAux, depth, color);
				tiempoBusqueda=System.currentTimeMillis();
				ParActionValue aux = new ParActionValue(poda.doSearch());
				tiempoBusqueda = System.currentTimeMillis()-tiempoBusqueda;
				tiempoTotalBusqueda = tiempoTotalBusqueda + tiempoBusqueda;
				
				Action a = new Action(aux.a);
				State estadoAux1 = estadoAux.applyAction(a);
				estadoAux = estadoAux1;
				x++;
				
				System.out.println("Movimiento nº"+x);

				Utils.printBoard(estadoAux);
				System.out.println("\n");
				
				nNodosTotal = nNodosTotal + poda.nNodos;
				tiempoTotalBusqueda = tiempoTotalBusqueda + poda.tiempoBusqueda;
				
				if(estadoAux.isFinal()) {
					if(estadoAux.m_color==0) {
						System.out.println("Se han comido al rey de color blanco\n");
					} else {
						System.out.println("Se han comido al rey de color negro\n");
					}
					
					break;
				}
				
			}
			
			
		}
		
		
		
		System.out.println("Nodos en total: "+nNodosTotal+" nodos");
		System.out.println("Tiempo de busqueda: "+tiempoTotalBusqueda+" ms");
	
		
	}
}
