import java.util.ArrayList;


public class MiniMax implements Algoritmo {

	State initialState = null;
	int depth = 0;
	int color = 0;

	public static long tiempoBusqueda=0;
	public static double nNodos=0;
	
	public MiniMax(State ie, int d, int color) {
		this.depth = d;
		this.initialState = ie;
		this.color = color;
	}

	public ParActionValue doSearch() {

		ParActionValue valor = Max_Valor(initialState,depth,color);

		return valor;
	}

	ParActionValue Max_Valor(State estado, int prof, int color) {

		ParActionValue valor = new ParActionValue(null,Double.MIN_VALUE);

		
		if (prof == 0) {
			valor.v = estado.heuristica(color); //1 porque juega max
			return valor;
		}
		if(estado.isFinal()) {
			valor.v = estado.heuristica(color);
			return valor;
		}
		
		ArrayList<Action> sucesores = new ArrayList<Action>();
		sucesores = estado.getPossibleActions(color);

		while(sucesores.size()>0) {
			nNodos++;
			Action a = sucesores.remove(0);
			State next = estado.applyAction(a);

			int new_depth = prof-1;
			ParActionValue v = Min_Valor(next,new_depth,color);
			//v.v = next.heuristica(color, 1);
			if(valor.v < v.v ) {
				valor.v = v.v;
				valor.a = a;		
			}
		
			/*System.out.println("\n");
			Utils.printBoard(next);
			System.out.println("\n");
			if(color==0) {
				System.out.println("color que ha movido: blanco\n");
			} else {
				System.out.println("color que ha movido: negro\n");
			}
			System.out.println("\n");
			System.out.println("valor asociado: "+valor.v);*/
		}
		return valor;

	}

	ParActionValue Min_Valor(State estado, int prof, int color) {

		ParActionValue valor = new ParActionValue(null,Double.MAX_VALUE);

	
		
		if(prof==0) {
			valor.v = estado.heuristica(color); //0 porque juega min
			return valor;
		}

		if(estado.isFinal()) {
			valor.v = estado.heuristica(color);
			return valor;
		}

		ArrayList<Action> sucesores = new ArrayList<Action>();
		sucesores = estado.getPossibleActions(color);

		while(sucesores.size()>0) {
			nNodos++;
			Action a = sucesores.remove(0);
			State next = estado.applyAction(a);

			int new_depth = prof-1;
			ParActionValue v = Max_Valor(next,new_depth,color);
			//v.v = next.heuristica(color, 0);
			
			if(valor.v > v.v ) { 
				valor.v = v.v;
				valor.a = a;
			}

			/*System.out.println("\n");
			Utils.printBoard(next);
			System.out.println("\n");
			if(color==0) {
				System.out.println("color que ha movido: blanco\n");
			} else {
				System.out.println("color que ha movido: negro\n");
			}
			System.out.println("\n");
			System.out.println("valor asociado: "+valor.v);*/

		}

		return valor;

	}

	public static void main(String[] args) {
		//State estadoInicial = Utils.getChessInstance();
		//Utils.printBoard(estadoInicial);
		
		State estadoInicial = Utils.getChessInstancePosition(2,3);
		Utils.printBoard(estadoInicial);
		
		
		MiniMax minimax = new MiniMax(estadoInicial, 4, 1);

		tiempoBusqueda = System.currentTimeMillis();
		minimax.doSearch();
		tiempoBusqueda=System.currentTimeMillis()-tiempoBusqueda;
		
		System.out.println("tiempo: "+tiempoBusqueda+" ms\n");
		System.out.println("nº nodos: "+nNodos+"\n");

	}
}
