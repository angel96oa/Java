import java.util.Objects;

// This class contains the information needed to represent a state 
// and some useful methods

public class State {
	int[][] m_board = null;
	Position m_agentPos = null;
	int m_agent = -1; // the type of piece
	int m_color = 0; // 0 for white, 1 for black
	int m_boardSize = -1; 

	// constructor
	public State() {

	}

	public State(int[][] board, Position pos, int a){
		m_board = board;
		m_agentPos = pos;
		m_agent = a;

		if (m_agent >11){
			System.out.println("\n*** Invalid piece ***\n");
			System.exit(0);
		}
		else if (m_agent >5) m_color = 1; // black

		m_boardSize = board[0].length;
	}

	// compares if the current state is final, i.e. the agent is in the last row
	public boolean isFinal(){
		if (this.m_agentPos.row == this.m_boardSize-1) return true;
		else return false;
	}

	// hard copy of an State
	public State copy(){
		int[][] cBoard = new int[this.m_boardSize][this.m_boardSize];

		for(int r=0;r<this.m_boardSize;r++)
			for(int c=0; c<this.m_boardSize;c++)
				cBoard[r][c] = this.m_board[r][c];

		return new State(cBoard, this.m_agentPos.copy(),m_agent);
	}

	// apply a given action over the current state -which remains unmodified. Return a new state

	public State applyAction(Action action) {
		State newState = this.copy();
		newState.m_board[action.m_initPos.row][action.m_initPos.col] = Utils.empty;
		newState.m_board[action.m_finalPos.row][action.m_finalPos.col] = newState.m_agent;
		newState.m_agentPos = action.m_finalPos;

		return newState;
	}

	///////////////////////////////////////////////////////
	// 						AÑADIDO						 //
	///////////////////////////////////////////////////////
	@Override
	public boolean equals(Object o) {
		if(o instanceof State) {
			State s = (State)o;
			if((this.m_agentPos.row) ==(s.m_agentPos.row) && (this.m_agentPos.col) ==(s.m_agentPos.col)); //Dos objetos state serán iguales si su posicion es la misma
				System.out.println("misma posicion\n");
				return true;
				
		} else {
			return false;
		}
	}

	//aplica la funcion hash a la posicion 
	@Override
	public int hashCode() {
		return Objects.hash(" "+ m_agentPos.row + ", "+ m_agentPos.col);
	}






	//habria que guardar el estado de todo el tablero, pero usando solo la posicion x e y es mas eficiente porque poda mas arboles









}
