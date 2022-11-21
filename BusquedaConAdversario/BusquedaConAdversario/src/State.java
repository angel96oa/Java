import java.util.ArrayList;

// This class contains the information needed to represent a state 
// and some useful methods

public class State {
	int[][] m_board = null;
	int m_agent = -1; // the type of piece
	int m_color; // 0 for white, 1 for black
	int m_boardSize = -1; 

	
	// constructor
	public State() {

	}

	public State(int[][] board){
		m_board = board;


		if (m_agent >11){
			System.out.println("\n*** Invalid piece ***\n");
			System.exit(0);
		}
		else if (m_agent >5) m_color = 1; // black

		m_boardSize = board[0].length;
	}

	// compares if the current state is final, i.e. the agent is in the last row
	//recorremos la matriz, si no encontramos la ficha, devolvemos true
	public boolean isFinal(){
		if(m_color==0) {
			int aux = 0;
			for(int row = 0; row<m_boardSize; row++) {
				for(int col=0; col<m_boardSize; col++) {
					int variable = m_board[row][col];
					if(variable==5) {
						aux=1;
					}
				}
			}
			if(aux==1) {
				return false;
			} else {
				return true;
			}

		} else {
			int aux = 0;
			for(int row = 0; row<m_boardSize; row++) {
				for(int col=0; col<m_boardSize; col++) {
					int variable = m_board[row][col];
					if(variable==11) {
						aux=1;
					}
				}
			}
			if(aux==1) {
				return false;
			} else {
				return true;
			}
		}
	}


	//peon - peso 1
	//alfil, torre, caballo - peso 2
	//reina  - peso 3
	//rey - peso 4
	
	//fuente heuristica
	//https://github.com/yaseminkilic/ChessAI/blob/2a5864b7c8239b7da6ce70f89e0c87f4be5b1758/src/MinMaxAlgorithm.java#L130
	
	public int heuristica(int color) {
		int gana=0; 
		int negro=0, blanco=0;

		for(int row = 0; row<m_boardSize; row++) {
			for(int col=0; col<m_boardSize; col++) {
				int variable = m_board[row][col];

				if(color==0) {
					if(variable==0) {
						blanco=blanco+1;
					}
					if(variable==1) {
						blanco=blanco+2;
					}
					if(variable==2) {
						blanco=blanco+2;
					}
					if(variable==3) {
						blanco=blanco+2;
					}
					if(variable==4) {
						blanco=blanco+3;
					}
					if(variable==5) {
						blanco=blanco+4;
					}
				} 

				if(color==1) {
					if(variable==6) {
						negro=negro+1;
					}
					if(variable==7) {
						negro=negro+2;
					}
					if(variable==8) {
						negro=negro+2;
					}
					if(variable==9) {
						negro=negro+2;
					}
					if(variable==10) {
						negro=negro+3;
					}
					if(variable==11) {
						negro=negro+4;
					}

				}

			}
		}
		
		
		
		if(color == 0) { //minimizamos blancas
			gana=blanco-negro;
		}
		
		if(color==1) { //minimizando negras
			gana=negro-blanco;
		}
		
		
		return gana;
	}

	
	
	
	// hard copy of an State
	public State copy(){
		int[][] cBoard = new int[this.m_boardSize][this.m_boardSize];

		for(int r=0;r<this.m_boardSize;r++)
			for(int c=0; c<this.m_boardSize;c++)
				cBoard[r][c] = this.m_board[r][c];

		return new State(cBoard);
	}

	// apply a given action over the current state -which remains unmodified. Return a new state

	public State applyAction(Action action) {
		State newState = this.copy();
		int agente = newState.m_board[action.m_initPos.row][action.m_initPos.col];
		newState.m_board[action.m_initPos.row][action.m_initPos.col] = Utils.empty;
		newState.m_board[action.m_finalPos.row][action.m_finalPos.col] = agente;


		return newState;
	}


	//blancas cero - negras uno
	ArrayList<Action> getPossibleActions(int white){
		ArrayList<Action> accionesTotales = new ArrayList<Action>();
		State estadoActual = this.copy();
		for(int row = 0; row<m_boardSize; row++) {
			for(int col=0; col<m_boardSize; col++) {
				int variable = m_board[row][col];

				if(white==0) {
					if(variable==0) {
						Pawn pawn = new Pawn(white);
						accionesTotales.addAll(pawn.getPossibleActions(estadoActual, row, col));
					}
					if(variable==1) {
						Rook rook = new Rook(white);
						accionesTotales.addAll(rook.getPossibleActions(estadoActual, row, col));
					}
					if(variable==2) {
						Bishop bishop = new Bishop(white);
						accionesTotales.addAll(bishop.getPossibleActions(estadoActual, row, col));
					}
					if(variable==3) {
						Knight knight = new Knight(white);
						accionesTotales.addAll(knight.getPossibleActions(estadoActual, row, col));
					}
					if(variable==4) {
						Queen queen = new Queen(white);
						accionesTotales.addAll(queen.getPossibleActions(estadoActual, row, col));
					}
					if(variable==5) {
						King king = new King(white);
						accionesTotales.addAll(king.getPossibleActions(estadoActual, row, col));
					}
				} 

				if(white==1) {
					if(variable==6) {
						Pawn pawn = new Pawn(white);
						accionesTotales.addAll(pawn.getPossibleActions(estadoActual, row, col));
					}
					if(variable==7) {
						Rook rook = new Rook(white);
						accionesTotales.addAll(rook.getPossibleActions(estadoActual, row, col));
					}
					if(variable==8) {
						Bishop bishop = new Bishop(white);
						accionesTotales.addAll(bishop.getPossibleActions(estadoActual, row, col));
					}
					if(variable==9) {
						Knight knight = new Knight(white);
						accionesTotales.addAll(knight.getPossibleActions(estadoActual, row, col));
					}
					if(variable==10) {
						Queen queen = new Queen(white);
						accionesTotales.addAll(queen.getPossibleActions(estadoActual, row, col));
					}
					if(variable==11) {
						King king = new King(white);
						accionesTotales.addAll(king.getPossibleActions(estadoActual, row, col));
					}
				}
			}
		}

		return accionesTotales;
	}

}
