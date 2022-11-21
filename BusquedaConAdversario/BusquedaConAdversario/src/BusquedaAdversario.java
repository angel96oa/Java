import java.util.Random;

public class BusquedaAdversario {

	static State estado_inicial = null;
	static Piece agente = null;
	static int colorAgente;
	Random generador = null;
	static long tiempoBusqueda;
	
	
	public static void main(String[] args) {
		if(args.length != 8) {
			System.out.println("\nNumero de parámetros incorrecto, se necesitan 6:\n");
			System.out.println("Método que se va a usar: minimax ó alfabeta o dummy\n");
			System.out.println("Inicio: 1 (tablero inicial) - 2 (tablero aleatorio)\n");
			System.out.println("Profundidad\n");
			System.out.println("Color: white - black - todo\n");
			System.out.println("Semilla 1\n");
			System.out.println("Semilla 2\n");
			System.out.println("tipo de juego:  contra la maquina (1) - solo la maquina (0)\n");
			System.out.println("numero de jugadas\n");
			System.exit(0);
		}
		
		String metodo = args[0];
		int inicio = Integer.parseInt(args[1]);
		int profundidad = Integer.parseInt(args[2]);
		String color = args[3];
		int semilla1 = Integer.parseInt(args[4]);
		int semilla2 = Integer.parseInt(args[5]);
		int tipoJuego = Integer.parseInt(args[6]);
		int nJugadas = Integer.parseInt(args[7]);
		
		if(inicio == 1) {
			estado_inicial = Utils.getChessInstance();
		} else {
			estado_inicial = Utils.getChessInstancePosition(semilla1, semilla2);
		}
		
		System.out.println("Estado Inicial\n");
		Utils.printBoard(estado_inicial);
		System.out.println("\n");
		
		switch(color) {
		case "white":
			colorAgente = 0;
			break;
		
		case "black":
			colorAgente = 1;
			break;
		}
		
		switch(metodo) {
		case "minimax":
			
			MiniMax miniMax = new MiniMax(estado_inicial, profundidad, colorAgente);
			System.out.println("Juega contra la maquina, su color es "+"color"+"\n");
			if(tipoJuego==1) {
				BlackWhite blackwhite = new BlackWhite(miniMax, nJugadas);
				blackwhite.busca();
			} else {
				System.out.println("Solo juega la maquina\n");
				Todo todo = new Todo(miniMax, nJugadas);
				todo.busca();
			}

			break;
		
		case "alfabeta":
			
			PodaAlfaBeta alfabeta = new PodaAlfaBeta(estado_inicial, profundidad, colorAgente);
			
			if(tipoJuego==1) {
				
				System.out.println("Juega contra la maquina, su color es "+"color"+"\n");
				BlackWhite blackwhite = new BlackWhite(alfabeta, nJugadas);
				blackwhite.busca();
			} else {
				System.out.println("Sólo juega la maquina\n");
				Todo todo = new Todo(alfabeta, nJugadas);
				todo.busca();
			}
			
			break;
		
		case "Dummy":
			System.out.println("La máquina no juega, su color es "+colorAgente+"\n");
			Dummy dummy = new Dummy(estado_inicial, nJugadas, colorAgente, profundidad);
			dummy.busca();
			break;
		}
		
	}
}
