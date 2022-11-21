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
	
	public ArrayList<Action> getPossibleActions(State s){
		ArrayList<Action> list = new ArrayList<Action>(3);
		
		list = this.downKingMoves(s);
		list.addAll(this.upKingMoves(s));
		list.addAll(this.leftKingMoves(s));
		list.addAll(this.rightKingMoves(s));
		list.addAll(this.downRightKingMoves(s));
		list.addAll(this.downLeftKingMoves(s));
		list.addAll(this.upLeftKingMoves(s));
		list.addAll(this.upRightKingMoves(s));
		
		return list;
	}
}
