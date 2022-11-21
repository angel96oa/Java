
// a position is the X and Y coordinates of a board cell

public class Position {

	int row;
	int col;
	
	// constructor
	Position(int r,int c){
		row = r;
		col = c;
	}
	
	// compares if the received position is equals to the member one
	public boolean equals(Position other){
		if (other.row != this.row) return false;
		if (other.col != this.col) return false;
		return true;
	}
	
	// creates a hard copy of the object
	public Position copy(){
		return new Position(this.row,this.col);
	}
	
}// end of class
