import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

public class Profundidad {

	State estado_inicial = null;
	ArrayList<Action> solucion = null;
	State estado_actual = null;
	double coste_solucion = 1.0;
	Piece agente = null;
	Random generador = null;
	long nodosExpandidos = 0;
	long nodosGenerados=0;

	long tiempoBusqueda;

	Profundidad(State si, int semilla){
		this.estado_inicial = si;
		this.generador = new Random(semilla);

		switch(this.estado_inicial.m_agent) {

		case Utils.wPawn: this.agente = new Pawn(0);
		break;
		case Utils.wRook: this.agente = new Rook(1);
		break;
		case Utils.wBishop: this.agente = new Bishop(2);
		break;
		case Utils.wKnight: this.agente = new Knight(3);
		break;
		case Utils.wQueen: this.agente = new Queen(4);
		break;
		case Utils.wKing: this.agente = new King(5);
		break;
		case Utils.bPawn: this.agente = new Pawn(6);
		break;
		case Utils.bRook: this.agente = new Rook(7);
		break;
		case Utils.bBishop: this.agente = new Bishop(8);
		break;
		case Utils.bKnight: this.agente = new Knight(9);
		break;
		case Utils.bQueen: this.agente = new Queen(10);
		break;
		case Utils.bKing: this.agente = new King(11);
		break;
		case Utils.empty: this.agente = null;
		break;
		}
	}

	public ArrayList<Nodo> sucesores(Nodo nodo){
		ArrayList<Nodo> sucesores = new ArrayList<Nodo>();
		ArrayList<Action> acciones = agente.getPossibleActions(nodo.getEstado());
		estado_actual=estado_inicial;

		try {
			for(int i=0; i<acciones.size(); i++) {

				State nuevoEstado = new State();

				nuevoEstado=estado_actual.applyAction(acciones.get(i));
				Nodo nuevoNodo = new Nodo(nuevoEstado);
				nuevoNodo.setNodoPadre(nodo);
				nuevoNodo.setAction(acciones.get(i));
				//coste((f1, c1)->(f2, c2))=max(|f1-f2|,|c1-c2|)+1
				nuevoNodo.setCoste(acciones.get(i).getCost());
				nuevoNodo.setProfundidad(nuevoNodo.getNodoPadre().getProfundidad()+1);
				sucesores.add(nuevoNodo);
				nodosGenerados++;
			}
		} catch (Exception e) {
			System.out.println("No existe solución manejable\n");
		}
		nodosExpandidos++;
		return sucesores;
	}

	public void busqueda() {

		tiempoBusqueda = System.currentTimeMillis();
		this.solucion = new ArrayList<Action>(100);
		Stack<Nodo> frontera = new Stack<Nodo>();

		Boolean encontrada = false;
		State estado_actual =  this.estado_inicial;
		ArrayList<Nodo> sucesores = new ArrayList<Nodo>();
		HashSet<State> estadosYaPasados = new HashSet<State>();
		Nodo elegido = new Nodo(estado_actual); //Creamos un nodo con el estado inicial
		frontera.add(elegido);
		estadosYaPasados.add(elegido.getEstado());
		/*  explored = []
			...
			if node.state not in explored:
			...
			explored.append(node.state)
		 */


		while(encontrada == false) {
				if(estado_actual.isFinal()) {
					encontrada=true;
					this.estado_inicial=estado_actual;
				} else {
					sucesores=sucesores(elegido);
					for(int i=0; i<sucesores.size(); i++) {
						frontera.push(sucesores.get(i));
					}

					if(frontera.isEmpty()) {
						System.out.println("sin solucion\n");
						break;
					}
					
					Action comprobar = frontera.peek().getAction();

					if(comprobar==null) {
						frontera.pop();
					}

					for(int i=0; i<frontera.size(); i++) {
						elegido = frontera.pop();
						if(!estadosYaPasados.contains(elegido.getEstado())) {
							break;
						} else {
							continue;
						}
					}
					estado_actual = estado_actual.applyAction(elegido.getAction());
					estadosYaPasados.add(estado_actual);
				}
		}

		tiempoBusqueda=System.currentTimeMillis()-tiempoBusqueda;

		while(elegido.getNodoPadre()!=null) {
			solucion.add(elegido.getAction());
			elegido = elegido.getNodoPadre();
			coste_solucion=coste_solucion+elegido.getCoste();
		}
		
		Collections.reverse(solucion);
	}


	@SuppressWarnings("unused")
	public static void main(String[] args) {

		if (args.length != 5){
			System.out.println("\n**Sorry, correct usage require 5 params:");
			System.out.println("Board size: int.");
			System.out.println("Density: (0.1,1]. Probability for each piece to be included.");
			System.out.println("Seed1: int. To initialize the problem instance random number generator (for reproducibility)");
			System.out.println("Agent: {0,1,2,3,4,5} standing for white pawn, rook, bishop, knight, queen or king.");
			System.out.println("Seed2: int. To initialize the Random Search instance random number generator (for reproducibility)");

			System.exit(0);
		}

		int size = Integer.parseInt(args[0]);
		double density = Double.parseDouble(args[1]);
		int seed1 = Integer.parseInt(args[2]);
		int agent = Integer.parseInt(args[3]);
		int seed2 = Integer.parseInt(args[4]);

		if (size<4){
			System.out.println("\nSorry: board to small, modified to 4");
			size = 4;
		}

		if ((density<0.1) || (density>1)){
			System.out.println("\nSorry: bad density value, modified to 0.25");
			density=0.25;
		}

		if ((density*32) > (size*size)){
			System.out.println("\nSorry: too much pieces for the board size, modifying density to 0.25");
			density=0.25;
		}

		if ((agent <0) || (agent>11)){
			System.out.println("\nSorry: bad selected agent, modified to 1 (white rook)");
			agent = Utils.wRook;
		}

		// getting the initial state
		State state = Utils.getProblemInstance(size, density, seed1, agent);
		Utils.printBoard(state);

		Profundidad srs = new Profundidad(state,seed2);
		srs.agente.color=0;
		srs.busqueda();



		if (srs==null) {
			System.out.println("\nSorry, no solution found ....");
		}else {
			System.out.println("Solution length: " + srs.solucion.size());
			System.out.println("Solution cost:   " + srs.coste_solucion);
			System.out.println("Nodos expandidos: "+srs.nodosExpandidos);
			System.out.println("Nodos generados: "+srs.nodosGenerados);
			System.out.println("Solution:\n");
			for(int i=0;i<srs.solucion.size();i++)
				System.out.println((i+1) + ": " + srs.solucion.get(i));

			Utils.printBoard(srs.estado_inicial);
		}
		System.out.println();


	}

}

/*
 * 
 */