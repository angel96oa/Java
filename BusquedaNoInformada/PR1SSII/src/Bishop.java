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
	
	public ArrayList<Action> getPossibleActions(State s){
		ArrayList<Action> list = new ArrayList<Action>(10);
		
		list = this.getDownLeftDiagonalMoves(s);
		list.addAll(this.getDownRightDiagonalMoves(s));
		list.addAll(this.getUpLeftDiagonalMoves(s));
		list.addAll(this.getUpRightDiagonalMoves(s));
		
		return list;
	}
}
