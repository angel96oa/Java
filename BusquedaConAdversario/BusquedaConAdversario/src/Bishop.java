import java.util.ArrayList;

public class Bishop extends Piece {

	public Bishop(int i) {
		this.color = i;
		if(color == 0) {
			this.tipo = Utils.wBishop;
		} else {
			this.tipo = Utils.bBishop;		
		}
	}
	
	public ArrayList<Action> getPossibleActions(State s, int row, int col){
		ArrayList<Action> list = new ArrayList<Action>(10);
		
		list = this.getDownLeftDiagonalMoves(s, row, col);
		list.addAll(this.getDownRightDiagonalMoves(s, row, col));
		list.addAll(this.getUpLeftDiagonalMoves(s, row, col));
		list.addAll(this.getUpRightDiagonalMoves(s, row, col));
		
		return list;
	}
}
