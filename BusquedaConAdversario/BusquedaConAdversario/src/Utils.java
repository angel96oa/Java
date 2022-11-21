import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Utils {

	// all the pieces
	static final int wPawn = 0;		//peso 1
	static final int wRook = 1;		//peso 2
	static final int wBishop = 2;	//peso 2
	static final int wKnight = 3;	//peso 2
	static final int wQueen = 4;	//peso 3
	static final int wKing = 5;		//peso 4
	static final int bPawn = 6;
	static final int bRook = 7;
	static final int bBishop = 8;
	static final int bKnight = 9;
	static final int bQueen = 10;
	static final int bKing = 11;
	static final int empty = 12;

	// number of pieces
	static final int diffPieces = 12;
	static int[] numPieces = {8,2,2,2,1,1,8,2,2,2,1,1};

	// name (and letter) of each piece
	static final String[] names = {"wPawn", "wRook", "wBishop", "wKnight", "wQueen", "wKing", 
			"bPawn", "bRook", "bBishop", "bKnight", "bQueen", "bKing"};
	static final String[] letters = {"P","R","B","N","Q","K","p","r","b","n","q","k"," "}; 
	// Note we use N for Knight instead of K (to avoid confussion with King)
	// Note we add " " for empty cell
	
	/**
	 * Get color piece 
	 */
	public static int getColorPiece(int piece) {
		if ((piece >=0) && (piece<=5)) return 0; //white
		else if ((piece>5) && (piece<=11)) return 1; //black
		else {
			System.out.println("\n** Error, wrong piece code\n");
			System.exit(0);
		}

		return -1; //never arrives here, just to avoid compilation error
	}
	/**
	 * fill (by rows) an ArrayList with all the possible coordinates
	 * 
	 * @param n size of the board
	 */

	public static ArrayList<Position> getAllBoardPositions(int n){
		ArrayList<Position> pos = new ArrayList<Position>(n*n);

		for(int r=0;r<n;r++)
			for(int c=0;c<n;c++)
				pos.add(new Position(r,c));

		return pos;
	}

	/**
	 * Print a state (board + agent)
	 */

	public static void printBoard(State state){
		DecimalFormat df = new DecimalFormat("00");

		int size = state.m_boardSize;
		/*if (size>50){
			System.out.println("**Error, board too large to be text-printed ...\n");
			System.exit(0);
		}*/

		// upper row
		System.out.print("   ");
		for(int c=0;c<size;c++) 
			System.out.print( df.format(c) +" ");
		System.out.println();

		System.out.print("  ");
		for(int i=0; i<size;i++) 
			System.out.print("---");
		System.out.println("--");

		// board
		for(int r=0; r<size;r++){
			System.out.print( df.format(r) +"|");
			for(int c=0; c<size; c++)
				System.out.print(" " + letters[state.m_board[r][c]]+"|");
			//botton row
			System.out.println("  ");
			for(int i=0; i<size;i++) 
				System.out.print("---");
			System.out.println("--");
		}

	}


	/*** This method generates a random board problem to play chess.* 
	@param p probability for each piece to be included* 
	@param seed to initiate the random generator (for reproducibility)* @return the initial state (board)*/
	public static State getChessInstancePosition(double p, int seed){
		int n = 8;
		
		int[][] board = new int[n][n];
		for(int r=0;r<n;r++)
			for(int c=0;c<n;c++)
				board[r][c] = Utils.empty;
		Random gen = new Random(seed);
		ArrayList<Position> allPositions = getAllBoardPositions(n);
		// placing the two kings in random position
		int r = gen.nextInt(n*n);
		Position wkingPos = allPositions.remove(r);
		board[wkingPos.row][wkingPos.col] = Utils.wKing;
		numPieces[Utils.wKing]-=1;
		r = gen.nextInt((n*n)-1);
		Position bkingPos = allPositions.remove(r);
		board[bkingPos.row][bkingPos.col] = Utils.bKing;
		numPieces[Utils.bKing]-=1;
		// placing the rest of chess.pieces
		Position pos = null;
		for(int piece=0;piece<diffPieces;piece++)
			for(int j=0;j<numPieces[piece];j++){
				if (gen.nextDouble()<=p){
					r = gen.nextInt(allPositions.size());
					pos = allPositions.remove(r);
					board[pos.row][pos.col] = piece;
				}
			}
		// Creating the instance, i.e., the state
		State state = new State(board);
		state.m_boardSize =8;
		return state;
	}



	///////////////

	public static State getChessInstance(){
		int n = 8;
		int[][] board = new int[n][n];
		// Pawn
		for(int r=0;r<n;r++)
			for(int c=0;c<n;c++)
				board[r][c] = Utils.empty;
		for(int c = 0; c < n; c++) {
			board[1][c] = Utils.wPawn;
			board[n-2][c] = Utils.bPawn;
		}
		//white pieces
		board[0][0] = Utils.wRook; 
		board[0][1] = Utils.wKnight; 
		board[0][2]=Utils.wBishop;
		board[0][n-1] = Utils.wRook; 
		board[0][n-2] = Utils.wKnight; 
		board[0][n-3]=Utils.wBishop;
		board[0][3] = Utils.wKing; 
		board[0][4]=Utils.wQueen;
		//black pieces
		board[n-1][0] = Utils.bRook; 
		board[n-1][1] = Utils.bKnight; 
		board[n-1][2]=Utils.bBishop;
		board[n-1][n-1] = Utils.bRook; 
		board[n-1][n-2] = Utils.bKnight; 
		board[n-1][n-3]=Utils.bBishop;
		board[n-1][3] = Utils.bKing; 
		board[n-1][4]=Utils.bQueen;
		// Creating the instance, i.e., the state
		State state = new State(board);
		state.m_boardSize =8;
		return state;
	}

} // end of class
