
public class SearchChess {
	public static void main(String[] args) {

		if (args.length != 6){
			System.out.println("\n**Sorry, correct usage require 6 params:");
			System.out.println("Estrategia requerida: 1-Anchura, 2-Profundidad, 3-CosteUniforme, 4-PrimeroMejor, 5-A*");
			System.out.println("Board size: int.");
			System.out.println("Density: (0.1,1]. Probability for each piece to be included.");
			System.out.println("Seed1: int. To initialize the problem instance random number generator (for reproducibility)");
			System.out.println("Agent: {0,1,2,3,4,5} standing for white pawn, rook, bishop, knight, queen or king.");
			System.out.println("Seed2: int. To initialize the Random Search instance random number generator (for reproducibility)");

			System.exit(0);
		}

		int algoritmo =Integer.parseInt(args[0]);
		int size = Integer.parseInt(args[1]);
		double density = Double.parseDouble(args[2]);
		int seed1 = Integer.parseInt(args[3]);
		int agent = Integer.parseInt(args[4]);
		int seed2 = Integer.parseInt(args[5]);

		if ((algoritmo<1) ||algoritmo>5){
			System.out.println("\nAlgoritmo inválido");
			size = 4;
		}

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

		if ((agent <0) || (agent>5)){
			System.out.println("\nSorry: bad selected agent, modified to 1 (white rook)");
			agent = Utils.wRook;
		}

		// getting the initial state
		State state = Utils.getProblemInstance(size, density, seed1, agent);
		Utils.printBoard(state);

		switch(algoritmo) {
		case 1: 
			System.out.println("\nBusqueda en anchura\n");

			Anchura srs1 = new Anchura(state, seed2);
			srs1.agente.color=0;
			srs1.busqueda();

			System.out.println("Solution length: " + srs1.solucion.size());
			System.out.println("Solution cost:   " + srs1.coste_solucion);
			System.out.println("Nodos expandidos: "+srs1.nodosExpandidos);
			System.out.println("Nodos generados: "+srs1.nodosGenerados);
			System.out.println("Tiempo de busqueda: "+srs1.tiempoBusqueda +" milisegundos");
			System.out.println("Solution:\n");
			for(int i=0;i<srs1.solucion.size();i++)
				System.out.println((i+1) + ": " + srs1.solucion.get(i));

			Utils.printBoard(srs1.estado_inicial);

			System.out.println();

			break;

		case 2:
			System.out.println("\nBusqueda en Profundidad\n");

			Profundidad srs2 = new Profundidad(state, seed2);
			srs2.agente.color=0;
			srs2.busqueda();

			System.out.println("Solution length: " + srs2.solucion.size());
			System.out.println("Solution cost:   " + srs2.coste_solucion);
			System.out.println("Nodos expandidos: "+srs2.nodosExpandidos);
			System.out.println("Nodos generados: "+srs2.nodosGenerados);
			System.out.println("Tiempo de busqueda: "+srs2.tiempoBusqueda +" milisegundos");
			System.out.println("Solution:\n");
			for(int i=0;i<srs2.solucion.size();i++)
				System.out.println((i+1) + ": " + srs2.solucion.get(i));

			Utils.printBoard(srs2.estado_inicial);

			System.out.println();

			break;

		case 3:
			System.out.println("\nBusqueda en Coste uniforme\n");

			CosteUniforme srs3 = new CosteUniforme(state, seed2);
			srs3.agente.color=0;
			srs3.busqueda();


			System.out.println("Solution length: " + srs3.solucion.size());
			System.out.println("Solution cost:   " + srs3.coste_solucion);
			System.out.println("Nodos expandidos: "+srs3.nodosExpandidos);
			System.out.println("Nodos generados: "+srs3.nodosGenerados);
			System.out.println("Tiempo de busqueda: "+srs3.tiempoBusqueda +" milisegundos");
			System.out.println("Solution:\n");
			for(int i=0;i<srs3.solucion.size();i++)
				System.out.println((i+1) + ": " + srs3.solucion.get(i));

			Utils.printBoard(srs3.estado_inicial);

			System.out.println();

			break;

		case 4:
			System.out.println("\nBusqueda en Primero mejor\n");

			PrimeroMejor srs4 = new PrimeroMejor(state, seed2);
			srs4.agente.color=0;
			srs4.busqueda();

			System.out.println("Solution length: " + srs4.solucion.size());
			System.out.println("Solution cost:   " + srs4.coste_solucion);
			System.out.println("Nodos expandidos: "+srs4.nodosExpandidos);
			System.out.println("Nodos generados: "+srs4.nodosGenerados);
			System.out.println("Tiempo de busqueda: "+srs4.tiempoBusqueda +" milisegundos");
			System.out.println("Solution:\n");
			for(int i=0;i<srs4.solucion.size();i++)
				System.out.println((i+1) + ": " + srs4.solucion.get(i));

			Utils.printBoard(srs4.estado_inicial);

			System.out.println();

			break;

		case 5:
			System.out.println("\nBusqueda en A*\n");

			AEstrella srs5 = new AEstrella(state, seed2);
			srs5.agente.color=0;
			srs5.busqueda();

			System.out.println("Solution length: " + srs5.solucion.size());
			System.out.println("Solution cost:   " + srs5.coste_solucion);
			System.out.println("Nodos expandidos: "+srs5.nodosExpandidos);
			System.out.println("Nodos generados: "+srs5.nodosGenerados);
			System.out.println("Tiempo de busqueda: "+srs5.tiempoBusqueda +" milisegundos");
			System.out.println("Solution:\n");
			for(int i=0;i<srs5.solucion.size();i++)
				System.out.println((i+1) + ": " + srs5.solucion.get(i));

			Utils.printBoard(srs5.estado_inicial);

			System.out.println();

			break;

		default:
			System.out.println("Valor inválido");
			break;

		}
	}
}
