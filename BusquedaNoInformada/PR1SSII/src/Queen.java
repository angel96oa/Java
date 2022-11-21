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
	
	public ArrayList<Action> getPossibleActions(State s){
		ArrayList<Action> list = new ArrayList<Action>(10);
		
		list = this.getHorizontalLeftMoves(s);
		list.addAll(this.getHorizontalRightMoves(s)); //asi añadiriamos los movimientos de la torre + alfil
		list.addAll(this.getVerticalDownMoves(s));
		list.addAll(this.getVerticalUpMoves(s));
		list.addAll(this.getDownRightDiagonalMoves(s));
		list.addAll(this.getUpLeftDiagonalMoves(s));
		list.addAll(this.getUpRightDiagonalMoves(s));
		list.addAll(this.getDownLeftDiagonalMoves(s));
		
		return list;
	}

}
