import java.util.ArrayList;

public class King extends Piece {

	public King(int i) {
		this.color = i;
		if(color == 0) {
			this.tipo = Utils.wKing;
		} else {
			this.tipo = Utils.bKing;
		}
	}
	
	public ArrayList<Action> getPossibleActions(State s, int row, int col){
		ArrayList<Action> list = new ArrayList<Action>(3);
		
		list = this.downKingMoves(s, row, col);
		list.addAll(this.upKingMoves(s, row, col));
		list.addAll(this.leftKingMoves(s, row, col));
		list.addAll(this.rightKingMoves(s, row, col));
		list.addAll(this.downRightKingMoves(s, row, col));
		list.addAll(this.downLeftKingMoves(s, row, col));
		list.addAll(this.upLeftKingMoves(s, row, col));
		list.addAll(this.upRightKingMoves(s, row, col));
		
		return list;
	}
}
