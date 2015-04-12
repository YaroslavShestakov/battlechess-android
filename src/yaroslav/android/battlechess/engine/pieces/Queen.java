package yaroslav.android.battlechess.engine.pieces;

import java.util.ArrayList;

import yaroslav.android.battlechess.engine.Player;
import yaroslav.android.battlechess.engine.Location;

public class Queen extends Piece {

	public Queen(Player player, int x, int y) {
		super(player, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public
	ArrayList<Location> getMoves() {
		ArrayList<Location> moves = new ArrayList<Location>();
		
		int x = pos.x ;
		int y = pos.y ;	
		
		/* ROOK MOVES */
		for (int dx = 1 ; x + dx <= 7 ; dx++){
			if (!putMove(x + dx, y, moves))
				break ;
		}
		
		for (int dx = -1 ; x + dx >= 0 ; dx--){
			if (!putMove(x + dx, y, moves))
				break ;
		}
		
		for (int dy = 1 ; y + dy <= 7 ; dy++){
			if (!putMove(x, y + dy, moves))
				break ;
		}
		
		for (int dy = -1 ; y + dy >= 0 ; dy--){
			if (!putMove(x, y + dy, moves))
				break ;
		}
		
		/* BISHOP MOVES */
		
		for (int dx = 1, dy = 1 ; x + dx <= 7 && y + dy <= 7 ; dx++, dy++){
			if (!putMove(x + dx, y + dy, moves))
				break ;
		}
		
		for (int dx = 1, dy = -1 ; x + dx <= 7 && y + dy >= 0 ; dx++, dy--){
			if (!putMove(x + dx, y + dy, moves))
				break ;
		}
		
		for (int dx = -1, dy = -1 ; x + dx >= 0 && y + dy >= 0 ; dx--, dy--){
			if (!putMove(x + dx, y + dy, moves))
				break ;
		}
		
		for (int dx = -1, dy = 1 ; x + dx >= 0 && y + dy <= 7 ; dx--, dy++){
			if (!putMove(x + dx, y + dy, moves))
				break ;
		}
		
		return moves;
	}
}
