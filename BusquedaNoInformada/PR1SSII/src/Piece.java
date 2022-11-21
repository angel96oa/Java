import java.util.ArrayList;

@SuppressWarnings("unused")
public class Piece {
	int color = -1;
	int tipo = -1;

	public ArrayList<Action> getPossibleActions(State state) {
		return null;
	}

	// movimiento horizontal a la izquierda
	public ArrayList<Action> getHorizontalLeftMoves(State s) {
		ArrayList<Action> list = new ArrayList<Action>(100);
		int agente_color = this.color;
		int c0, r0;
		c0 = s.m_agentPos.col;
		r0 = s.m_agentPos.row;

		Action action = null;
		Boolean busyCell = false;

		for (int c = c0-1; (c >= 0) && (!busyCell); c--) {
			if (s.m_board[r0][c] == Utils.empty) {
				action = new Action(s.m_agentPos, new Position(r0, c));
				list.add(action);
			} else {
				busyCell = true;
				int enemy = Utils.getColorPiece(s.m_board[r0][c]);
				if (agente_color != Utils.getColorPiece(s.m_board[r0][c])) {
					action = new Action(s.m_agentPos, new Position(r0, c));
					list.add(action);
				}
			}
		}
		return list;
	}

	// movimiento horizontal a la derecha
	public ArrayList<Action> getHorizontalRightMoves(State s) {
		ArrayList<Action> list = new ArrayList<Action>(100);
		int agente_color = this.color;
		int c0, r0;
		c0 = s.m_agentPos.col;
		r0 = s.m_agentPos.row;

		Action action = null;
		Boolean busyCell = false;

		for (int c = c0+1; (c < s.m_boardSize) && (!busyCell); c++) {
			if (s.m_board[r0][c] == Utils.empty) {
				action = new Action(s.m_agentPos, new Position(r0, c));
				list.add(action);
			} else {
				busyCell = true;
				if (agente_color != Utils.getColorPiece(s.m_board[r0][c])) {
					action = new Action(s.m_agentPos, new Position(r0, c));
					list.add(action);
				}
			}
		}
		return list;
	}

	// vertical down moves
	public ArrayList<Action> getVerticalDownMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);
		int agentColor = this.color;

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;
		Action action = null;

		Boolean busyCell = false;
		for (int r = r0 + 1; (r < state.m_boardSize) && (!busyCell); r++) {
			if (state.m_board[r][c0] == Utils.empty) {// add action
				action = new Action(state.m_agentPos, new Position(r, c0));
				list.add(action);
			} else {
				busyCell = true;
				if (agentColor != Utils.getColorPiece(state.m_board[r][c0])) { // capture piece
					action = new Action(state.m_agentPos, new Position(r, c0));
					list.add(action);
				}
			}
		}

		return list;
	}

	// vertical up moves
	public ArrayList<Action> getVerticalUpMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);
		int agentColor = this.color;

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;
		Action action = null;

		Boolean busyCell = false;
		for (int r = r0 - 1; (r >= 0) && (!busyCell); r--) {
			if (state.m_board[r][c0] == Utils.empty) {// add action
				action = new Action(state.m_agentPos, new Position(r, c0));
				list.add(action);
			} else {
				busyCell = true;
				if (agentColor != Utils.getColorPiece(state.m_board[r][c0])) { // capture piece
					action = new Action(state.m_agentPos, new Position(r, c0));
					list.add(action);
				}
			}
		}

		return list;
	}

	/*
	 *  --------------------------------------------------------------------------
	 * 				MOVIMIENTOS DEL ALFIL (BISHOP)
	 *  --------------------------------------------------------------------------
	 */
	// up-right diagonal
	public ArrayList<Action> getUpRightDiagonalMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);
		int agentColor = this.color;

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;
		Action action = null;

		Boolean busyCell = false; // variable para comprobar si la celda está vacia
		for (int r = r0 + 1, c = c0 - 1; (c >= 0) && (r < state.m_boardSize) && !busyCell; r++, c--) {
				if (state.m_board[r][c] == Utils.empty) {// add action
					action = new Action(state.m_agentPos, new Position(r, c));
					list.add(action);
				} else {
					busyCell = true;
					if (agentColor != Utils.getColorPiece(state.m_board[r][c])) { // capture piece
						action = new Action(state.m_agentPos, new Position(r, c));
						list.add(action);
					}
				}
		}
		return list;
	}

	public ArrayList<Action> getUpLeftDiagonalMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);
		int agentColor = this.color;

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;
		Action action = null;

		Boolean busyCell = false; // variable para comprobar si la celda está vacia
		for (int r = r0 - 1, c = c0 - 1; (c >= 0) && (r >= 0) && !busyCell; r--, c--) {
				if (state.m_board[r][c] == Utils.empty) {// add action
					action = new Action(state.m_agentPos, new Position(r, c));
					list.add(action);
				} else {
					busyCell = true;
					if (agentColor != Utils.getColorPiece(state.m_board[r][c])) { // capture piece
						action = new Action(state.m_agentPos, new Position(r, c));
						list.add(action);	
				}
			}

		}
		return list;
	}

	public ArrayList<Action> getDownRightDiagonalMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);
		int agentColor = this.color;

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;
		Action action = null;

		Boolean busyCell = false; // variable para comprobar si la celda está vacia
		for (int r = r0 + 1, c = c0 + 1; (c < state.m_boardSize) && (r < state.m_boardSize) && !busyCell; r++, c++) {
				if (state.m_board[r][c] == Utils.empty) {// add action
					action = new Action(state.m_agentPos, new Position(r, c));
					list.add(action);
				} else {
					busyCell = true;
					if (agentColor != Utils.getColorPiece(state.m_board[r][c])) { // capture piece
						action = new Action(state.m_agentPos, new Position(r, c));
						list.add(action);
					}
				}
		}
		return list;
	}

	public ArrayList<Action> getDownLeftDiagonalMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);
		int agentColor = this.color;

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;
		Action action = null;

		Boolean busyCell = false; // variable para comprobar si la celda está vacia
		for (int r = r0 - 1, c = c0 + 1; (r >= 0) && (c < state.m_boardSize) && !busyCell; r--, c++) {				
				if (state.m_board[r][c] == Utils.empty) {// add action
					action = new Action(state.m_agentPos, new Position(r, c));
					list.add(action);
				} else {
					busyCell = true;
					if (agentColor != Utils.getColorPiece(state.m_board[r][c])) { // capture piece
						action = new Action(state.m_agentPos, new Position(r, c));
						list.add(action);
					}
				}
		}
		return list;
	}

	/*
	 *  --------------------------------------------------------------------------
	 * 				MOVIMIENTOS DEL REY
	 *  --------------------------------------------------------------------------
	 */

	// metodo rey movimiento hacia arriba
	public ArrayList<Action> upKingMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;

		if(color==1) {
			return list;
		}

		if(r0>=1) {
			if (state.m_board[r0 - 1][c0] == Utils.empty) { // movimiento normal de una posicion
				list.add(new Action(state.m_agentPos, new Position(r0 - 1, c0)));
			}

			if((r0>0) && (state.m_board[r0-1][c0]!= Utils.empty) && (Utils.getColorPiece(state.m_board[r0-1][c0]) == 1)) {
				list.add(new Action(state.m_agentPos, new Position(r0 - 1, c0)));
			}
		}

		return list;
	}

	// metodo rey movimiento hacia abajo
	public ArrayList<Action> downKingMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;

		if(color==1) {
			return list;
		}

		if(r0<state.m_boardSize-1) {
			if (state.m_board[r0 + 1][c0] == Utils.empty) { // movimiento normal de una posicion
				list.add(new Action(state.m_agentPos, new Position(r0 + 1, c0)));
			}

			if((r0>0) && (state.m_board[r0+1][c0]!= Utils.empty) && (Utils.getColorPiece(state.m_board[r0+1][c0]) == 1)) {
				list.add(new Action(state.m_agentPos, new Position(r0 + 1, c0)));
			}
		}

		return list;
	}

	// metodo rey movimiento hacia derecha
	public ArrayList<Action> rightKingMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;

		if(color==1) {
			return list;
		}

		if(c0<state.m_boardSize-1) {
			if (state.m_board[r0][c0+1] == Utils.empty) { // movimiento normal de una posicion
				list.add(new Action(state.m_agentPos, new Position(r0, c0+1)));
			}

			if((r0>0) && (state.m_board[r0][c0+1]!= Utils.empty) && (Utils.getColorPiece(state.m_board[r0][c0+1]) == 1)) {
				list.add(new Action(state.m_agentPos, new Position(r0, c0+1)));
			}
		}

		return list;
	}

	// metodo rey movimiento hacia izquierda
	public ArrayList<Action> leftKingMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;

		if(color==1) {
			return list;
		}

		if(c0>=1) {
			if (state.m_board[r0][c0-1] == Utils.empty) { // movimiento normal de una posicion
				list.add(new Action(state.m_agentPos, new Position(r0, c0-1)));
			}
			//nos comemos la pieza
			if((r0>0) && (state.m_board[r0][c0-1]!= Utils.empty) && (Utils.getColorPiece(state.m_board[r0][c0-1]) == 1)) {
				list.add(new Action(state.m_agentPos, new Position(r0, c0-1)));
			}
		}

		return list;
	}

	// metodo rey movimiento hacia arriba-izquierda
	public ArrayList<Action> upLeftKingMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;

		if(color==1) {
			return list;
		}

		if(c0>=1 && r0>=1) {
			if (state.m_board[r0-1][c0-1] == Utils.empty) { // movimiento normal de una posicion
				list.add(new Action(state.m_agentPos, new Position(r0-1, c0-1)));
			}

			if((r0>0) && (state.m_board[r0-1][c0-1]!= Utils.empty) && (Utils.getColorPiece(state.m_board[r0-1][c0-1]) == 1)) {
				list.add(new Action(state.m_agentPos, new Position(r0-1, c0-1)));
			}
		}

		return list;
	}

	// metodo rey movimiento hacia abajo-izquierda
	public ArrayList<Action> downLeftKingMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;

		if(color==1) {
			return list;
		}

		if(r0<state.m_boardSize-1 && c0>=1) {
			if (state.m_board[r0+1][c0-1] == Utils.empty) { // movimiento normal de una posicion
				list.add(new Action(state.m_agentPos, new Position(r0+1, c0-1)));
			}
			if((r0>0) && (state.m_board[r0+1][c0-1]!= Utils.empty) && (Utils.getColorPiece(state.m_board[r0+1][c0-1]) == 1)) {
				list.add(new Action(state.m_agentPos, new Position(r0+1, c0-1)));
			}
		}

		return list;
	}

	// metodo rey movimiento hacia arriba-derecha
	public ArrayList<Action> upRightKingMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;

		if(r0>=1 && c0<state.m_boardSize-1) {
			if (state.m_board[r0-1][c0+1] == Utils.empty) { // movimiento normal de una posicion
				list.add(new Action(state.m_agentPos, new Position(r0-1, c0+1)));
			}

			if((r0>0) && (state.m_board[r0-1][c0+1]!= Utils.empty) && (Utils.getColorPiece(state.m_board[r0-1][c0+1]) == 1)) {
				list.add(new Action(state.m_agentPos, new Position(r0-1, c0+1)));
			}
		}

		return list;
	}

	// metodo rey movimiento hacia abajo-derecha
	public ArrayList<Action> downRightKingMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;

		if(r0<state.m_boardSize-1 && c0<state.m_boardSize-1) {
			if (state.m_board[r0+1][c0+1] == Utils.empty) { // movimiento normal de una posicion
				list.add(new Action(state.m_agentPos, new Position(r0+1, c0+1)));
			}

			if((r0>0) && (state.m_board[r0+1][c0+1]!= Utils.empty) && (Utils.getColorPiece(state.m_board[r0+1][c0+1]) == 1)) {
				list.add(new Action(state.m_agentPos, new Position(r0+1, c0+1)));
			}
		}

		return list;
	}

	/*
	 *  --------------------------------------------------------------------------
	 * 				MOVIMIENTOS DEL CABALLO (KNIGHT)
	 *  --------------------------------------------------------------------------
	 */
	//dos derecha uno arriba
	public ArrayList<Action> twoRightOneUpKnightMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;

		if(color==1) {
			return list;
		}

		if(r0<state.m_boardSize-2 && c0>=1) {
			if (state.m_board[r0+2][c0-1] == Utils.empty) { // movimiento normal de una posicion
				list.add(new Action(state.m_agentPos, new Position(r0+2, c0-1)));
			}

			if((r0>0) && (state.m_board[r0+2][c0-1]!= Utils.empty) && (Utils.getColorPiece(state.m_board[r0+2][c0-1]) == 1)) {
				list.add(new Action(state.m_agentPos, new Position(r0+2, c0-1)));
			}
		}

		return list;
	}

	//uno derecha dos arriba
	public ArrayList<Action> oneRightTwoUpKnightMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;

		if(color==1) {
			return list;
		}

		if(r0>state.m_boardSize-1 && c0>=2) {
			if (state.m_board[r0+1][c0-2] == Utils.empty) { // movimiento normal de una posicion
				list.add(new Action(state.m_agentPos, new Position(r0+1, c0-2)));
			}

			if((r0>0) && (state.m_board[r0+1][c0-2]!= Utils.empty) && (Utils.getColorPiece(state.m_board[r0+1][c0-2]) == 1)) {
				list.add(new Action(state.m_agentPos, new Position(r0+1, c0-2)));
			}
		}

		return list;
	}

	//uno izquierda dos arriba
	public ArrayList<Action> oneLeftTwoUpKnightMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;

		if(color==1) {
			return list;
		}

		if(c0>=2 && r0>=1) {
			if (state.m_board[r0-1][c0-2] == Utils.empty) { // movimiento normal de una posicion
				list.add(new Action(state.m_agentPos, new Position(r0-1, c0-2)));
			}

			if((r0>0) && (state.m_board[r0-1][c0-2]!= Utils.empty) && (Utils.getColorPiece(state.m_board[r0-1][c0-2]) == 1)) {
				list.add(new Action(state.m_agentPos, new Position(r0-1, c0-2)));
			}
		}
		return list;
	}

	//dos izquierda uno arriba
	public ArrayList<Action> twoLeftOneUpKnightMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;

		if(color==1) {
			return list;
		}

		if(c0>=1 && r0>=2) {
			if (state.m_board[r0-2][c0-1] == Utils.empty) { // movimiento normal de una posicion
				list.add(new Action(state.m_agentPos, new Position(r0-2, c0-1)));
			}

			if((r0>0) && (state.m_board[r0-2][c0-1]!= Utils.empty) && (Utils.getColorPiece(state.m_board[r0-2][c0-1]) == 1)) {
				list.add(new Action(state.m_agentPos, new Position(r0-2, c0-1)));
			}
		}

		return list;
	}

	//uno izquierda dos abajo
	public ArrayList<Action> oneLeftTwoDownKnightMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;

		if(color==1) {
			return list;
		}

		if(r0>=1 && c0<state.m_boardSize-2) {
			if (state.m_board[r0-1][c0+2] == Utils.empty) { // movimiento normal de una posicion
				list.add(new Action(state.m_agentPos, new Position(r0-1, c0+2)));
			}

			if((r0>0) && (state.m_board[r0-1][c0+2]!= Utils.empty) && (Utils.getColorPiece(state.m_board[r0-1][c0+2]) == 1)) {
				list.add(new Action(state.m_agentPos, new Position(r0-1, c0+2)));
			}
		}

		return list;
	}

	//dos izquierda uno abajo
	public ArrayList<Action> twoLeftOneDownKnightMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;

		if(color==1) {
			return list;
		}

		if(r0>=2 && c0<state.m_boardSize-1) {
			if (state.m_board[r0-2][c0+1] == Utils.empty) { // movimiento normal de una posicion
				list.add(new Action(state.m_agentPos, new Position(r0-2, c0+1)));
			}
			if((r0>0) && (state.m_board[r0-2][c0+1]!= Utils.empty) && (Utils.getColorPiece(state.m_board[r0-2][c0+1]) == 1)) {
				list.add(new Action(state.m_agentPos, new Position(r0-2, c0+1)));
			}
		}

		return list;
	}

	//dos derecha uno abajo
	public ArrayList<Action> twoRightOneDownKnightMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;

		if(color==1) {
			return list;
		}

		if(r0<state.m_boardSize-2 && c0<state.m_boardSize-1) {
			if (state.m_board[r0+2][c0+1] == Utils.empty) { // movimiento normal de una posicion
				list.add(new Action(state.m_agentPos, new Position(r0+2, c0+1)));
			}
			if((r0>0) && (state.m_board[r0+2][c0+1]!= Utils.empty) && (Utils.getColorPiece(state.m_board[r0+2][c0+1]) == 1)) {
				list.add(new Action(state.m_agentPos, new Position(r0+2, c0+1)));
			}
		}

		return list;
	}

	//uno derecha dos abajo
	public ArrayList<Action> oneRightTwoDownKnightMoves(State state) {
		ArrayList<Action> list = new ArrayList<Action>(10);

		int c0, r0;
		c0 = state.m_agentPos.col;
		r0 = state.m_agentPos.row;

		if(color==1) {
			return list;
		}

		if(r0<state.m_boardSize-1 && c0<state.m_boardSize-2) {
			if (state.m_board[r0+1][c0+2] == Utils.empty) { // movimiento normal de una posicion
				list.add(new Action(state.m_agentPos, new Position(r0+1, c0+2)));
			}
			if((r0>0) && (state.m_board[r0+1][c0+2]!= Utils.empty) && (Utils.getColorPiece(state.m_board[r0+1][c0+2]) == 1)) {
				list.add(new Action(state.m_agentPos, new Position(r0+1, c0+2)));
			}
		}

		return list;
	}

}
