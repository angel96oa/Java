
import java.util.Comparator;

public class Nodo {
	
	State estado;
	int coste=0;
	int heuristica=0;
	int profundidad = 0;
	Nodo nodoPadre;
	Action accion;
	
	//En los algoritmos, vamos a llamar a estos comparadores, que van a hacer las funciones definidas abajo
	//Es estatico para que pueda ser llamado sin tener que instanciar cada vez un nodo
	//Es final para que no se pueda modificar, solo cuando se llama a la propia funcion que es
	public static final Comparator<Nodo> porCoste = new evaluadorCoste();
	public static final Comparator<Nodo> porHeuristica = new evaluadorHeuristica();

	
	public Nodo(State estado) {
		this.estado = estado;
	}
	
	public void setEstado(State estado) {
		this.estado = estado;
	}
	
	public State getEstado() {
		return this.estado;
	}
	
	public void setCoste(int coste) {
		this.coste = coste;
	}
	
	public int getCoste() {
		return this.coste;
	}
	
	public void setHeuristica(int heuristica) {
		this.heuristica = heuristica;
	}
	
	public int getHeuristica() {
		return this.heuristica;
	}
	
	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}
	
	public int getProfundidad() {
		return this.profundidad;
	}
	
	public void setNodoPadre(Nodo nodo) {
		this.nodoPadre = nodo;
	}
	
	public Nodo getNodoPadre() {
		return this.nodoPadre;
	}
	
	public void setAction(Action accion) {
		this.accion = accion;
	}
	
	public Action getAction() {
		return this.accion;
	}
	
	//comprobamos si es el mismo nodo, para ello, comprobamos si es un objeto de tipo nodo, y luego si es el mismo estado 
	//Puede haber nodos con distintos estados, entonces no son los mismos nodos
	public boolean equals(Object obj) {
		
		if(!(obj instanceof Nodo)) {
			return false;
		} else {
			return this.estado.equals(((Nodo) obj).getEstado());
		}
		
	}
	
	
	/*
	 * Los return los hacen asi en esta pagina
	 * https://www.geeksforgeeks.org/implement-priorityqueue-comparator-java/
	 */
	public static class evaluadorCoste implements Comparator<Nodo>{
		@Override
		public int compare(Nodo nodoA, Nodo nodoB) {
			
			int valorNodoA = nodoA.getCoste();
			int valorNodoB = nodoB.getCoste();
			
			if(valorNodoA<valorNodoB) {
				return -1;
			} else if(valorNodoB<valorNodoA) {
				return 1;
			} else {
				return 0;
			}
		}
	}
	
	/*
	 * En la pagina indicada anteriormente dice que asi es orden descendente, por tanto el mayor será el primero
	 */
	
	public static class evaluadorHeuristica implements Comparator<Nodo>{
		@Override
		public int compare(Nodo nodoA, Nodo nodoB) {
			
			int valorNodoA = nodoA.getHeuristica();
			int valorNodoB = nodoB.getHeuristica();
			
			if(valorNodoA<valorNodoB) {
				return -1;
			} else if(valorNodoB<valorNodoA) {
				return 1;
			} else {
				return 0;
			}
		}
	}


	


}


