import java.util.ArrayList;

public class Knight extends Piece {

	public Knight(int i) {
		this.color = i;
		if(this.color == 0) {
			this.tipo = Utils.wKnight;
		} else {
			this.tipo = Utils.bKnight;
		}
	}
	
	public ArrayList<Action> getPossibleActions(State s){
		ArrayList<Action> list = new ArrayList<Action>(10);
		
		list = this.oneLeftTwoDownKnightMoves(s);
		list.addAll(this.twoLeftOneDownKnightMoves(s));
		list.addAll(this.oneRightTwoDownKnightMoves(s));
		list.addAll(this.twoRightOneDownKnightMoves(s));
		list.addAll(this.oneRightTwoUpKnightMoves(s));
		list.addAll(this.twoRightOneUpKnightMoves(s));
		list.addAll(this.oneLeftTwoUpKnightMoves(s));
		list.addAll(this.twoLeftOneUpKnightMoves(s));
		
		return list;
	}
}
