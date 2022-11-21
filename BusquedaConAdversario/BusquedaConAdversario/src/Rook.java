import java.util.ArrayList;


public class Rook extends Piece {
	
	public Rook(int i) { //el int i es el color
		this.color = i;
		if(this.color == 0) {
			this.tipo = Utils.wRook;
		} else {
			this.tipo = Utils.bRook;
		}
	}
	
	public ArrayList<Action> getPossibleActions(State s, int row, int col){
		ArrayList<Action> list = new ArrayList<Action>(10);
		
		list = this.getHorizontalLeftMoves(s, row, col);
		list.addAll(this.getHorizontalRightMoves(s, row, col)); //asi añadiriamos los movimientos de la torre
		list.addAll(this.getVerticalDownMoves(s, row, col));
		list.addAll(this.getVerticalUpMoves(s, row, col));
		
		return list;
	}
}
