import java.util.ArrayList;

//this class implements the getPossibleActions for each type of piece

public class Pawn extends Piece {


	// constructor
	public Pawn( int color){
		this.color = color;

		if (color==1) tipo = Utils.wPawn;
		else tipo = Utils.bPawn;

	}


	// this method must be completed with all the possible pieces

	public ArrayList<Action> getPossibleActions(State state, int row, int col){

		int c,r;
		Position position = new Position(row, col);
		r=row;
		c=col;

		int[][] board = state.m_board;

		int size = state.m_boardSize;

		ArrayList<Action> list = new ArrayList<Action>(3);

		// white pawn
		if (color == 0){// white pawn

			if (r< (size-1)){
				if (board[r+1][c] == Utils.empty){//standard pawn move
					list.add( new Action(position, new Position(r+1,c)) );
				}

				if ((c>0) && (board[r+1][c-1] != Utils.empty)
						&& (Utils.getColorPiece(board[r+1][c-1]) == 1)){//capture
					list.add( new Action(position, new Position(r+1,c-1)) );
				}

				if ((c < (size - 1)) && (board[r+1][c+1] != Utils.empty)
						&& (Utils.getColorPiece(board[r+1][c+1]) == 1)){//capture
					list.add( new Action(position, new Position(r+1,c+1)) );
				}				
			}

			if ((r==1) && (board[r+1][c] == Utils.empty) && (board[r+2][c] == Utils.empty)){//starting pawn move
				list.add( new Action(position, new Position(r+2,c)) );
			}

		}

		//black pawn
		if(color==1) {
			if(r>0) {
				if (board[r-1][c] == Utils.empty){//standard pawn move
					list.add( new Action(position, new Position(r-1,c)) );
				}

				if ((c < (size - 1)) && (board[r-1][c+1] != Utils.empty)
						&& (Utils.getColorPiece(board[r-1][c+1]) == 0)){//capture
					list.add( new Action(position, new Position(r-1,c+1)) );
				}

				if ((c>0) && (board[r-1][c-1] != Utils.empty)
						&& (Utils.getColorPiece(board[r-1][c-1]) == 0)){//capture
					list.add( new Action(position, new Position(r-1,c-1)) );
				}			
			}

			if ((r==(size-2)) && (board[r-(size-3)][c] == Utils.empty) && (board[r-(size-4)][c] == Utils.empty)){//starting pawn move
				list.add( new Action(position, new Position(r-2,c)) );
			}
		}

		return list;
	}


}
