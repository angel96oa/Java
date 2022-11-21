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
	
	public ArrayList<Action> getPossibleActions(State s){
		ArrayList<Action> list = new ArrayList<Action>(10);
		
		list = this.getHorizontalLeftMoves(s);
		list.addAll(this.getHorizontalRightMoves(s)); //asi añadiriamos los movimientos de la torre
		list.addAll(this.getVerticalDownMoves(s));
		list.addAll(this.getVerticalUpMoves(s));
		
		return list;
	}
}
