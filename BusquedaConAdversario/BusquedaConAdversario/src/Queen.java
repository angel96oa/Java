import java.util.ArrayList;


public class Queen extends Piece {

	public Queen(int i) {
		this.color = i;
		if(this.color == 0) {
			this.color = Utils.wQueen;
		} else {
			this.color = Utils.bQueen;
		}
	}
	
	public ArrayList<Action> getPossibleActions(State s, int col, int row){
		ArrayList<Action> list = new ArrayList<Action>(10);
		
		list = this.getHorizontalLeftMoves(s, col, row);
		list.addAll(this.getHorizontalRightMoves(s, col, row)); //asi añadiriamos los movimientos de la torre + alfil
		list.addAll(this.getVerticalDownMoves(s, col, row));
		list.addAll(this.getVerticalUpMoves(s, col, row));
		list.addAll(this.getDownRightDiagonalMoves(s, col, row));
		list.addAll(this.getUpLeftDiagonalMoves(s, col, row));
		list.addAll(this.getUpRightDiagonalMoves(s, col, row));
		list.addAll(this.getDownLeftDiagonalMoves(s, col, row));
		
		return list;
	}

}
