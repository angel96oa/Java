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
	
	public ArrayList<Action> getPossibleActions(State s, int row, int col){
		ArrayList<Action> list = new ArrayList<Action>(10);
		
		list = this.oneLeftTwoDownKnightMoves(s, row, col);
		list.addAll(this.twoLeftOneDownKnightMoves(s, row, col));
		list.addAll(this.oneRightTwoDownKnightMoves(s, row, col));
		list.addAll(this.twoRightOneDownKnightMoves(s, row, col));
		list.addAll(this.oneRightTwoUpKnightMoves(s, row, col));
		list.addAll(this.twoRightOneUpKnightMoves(s, row, col));
		list.addAll(this.oneLeftTwoUpKnightMoves(s, row, col));
		list.addAll(this.twoLeftOneUpKnightMoves(s, row, col));
		
		return list;
	}
}
