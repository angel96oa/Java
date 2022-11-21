import java.util.ArrayList;

public class PodaAlfaBeta implements Algoritmo{
	
	State initialState = null;
	int depth = 0;
	int color = 0;

	public static long tiempoBusqueda=0;
	public static double nNodos=0;
	
	Double alfa = Double.MAX_VALUE;
	Double beta = Double.MIN_VALUE;

	public PodaAlfaBeta(State ie, int d, int color) {
		this.depth = d;
		this.initialState = ie;
		this.color = color;
	}
	
	public ParActionValue doSearch() {
		
		ParActionValue  valor = Max_Valor(initialState,alfa, beta, depth,color);
		return valor;
	}
	
ParActionValue Max_Valor(State estado, Double alfa, Double beta, int prof, int color) {
		
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
			
			int new_depth = prof -1;
			ParActionValue v = Min_Valor(next, alfa, beta, new_depth,color);
			//v.v = next.heuristica(color, 1);
			if(valor.v < v.v ) {
				valor.v = v.v;
				valor.a = a;
			}
			
			if(valor.v >= beta) {
				return valor;
			}
			alfa=Math.max(alfa, valor.v);
		}
		return valor;
	}
	

	ParActionValue Min_Valor(State estado, Double alfa, Double beta, int prof, int color) {
		
		ParActionValue valor = new ParActionValue(null,Double.MAX_VALUE);
		
		if (prof == 0) {
			valor.v = estado.heuristica(color); //0 porque juega minimo
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
			
			int new_depth=prof-1;
			ParActionValue v = Max_Valor(next, alfa, beta, new_depth, color);
			//v.v = next.heuristica(color, 0);
			if(valor.v > v.v ) {
				valor.v = v.v;
				valor.a = a;
			}
			
			if(valor.v <= alfa) {
				return valor;
			}
			beta=Math.min(beta, valor.v);
			
		}
		return valor;
		
	}
	
	public static void main(String[] args) {
		//State estadoInicial = Utils.getChessInstance();
		//Utils.printBoard(estadoInicial);
		
		State estadoInicial = Utils.getChessInstancePosition(2,3);
		Utils.printBoard(estadoInicial);
		
		
		PodaAlfaBeta poda = new PodaAlfaBeta(estadoInicial, 4, 1);

		tiempoBusqueda = System.currentTimeMillis();
		poda.doSearch();
		tiempoBusqueda=System.currentTimeMillis()-tiempoBusqueda;
		
		System.out.println("tiempo: "+tiempoBusqueda+" ms\n");
		System.out.println("nº nodos: "+nNodos+"\n");

	}
}