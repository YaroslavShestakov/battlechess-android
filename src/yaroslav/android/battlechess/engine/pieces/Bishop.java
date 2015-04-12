package yaroslav.android.battlechess.engine.pieces;

import java.util.ArrayList;

import yaroslav.android.battlechess.engine.Location;
import yaroslav.android.battlechess.engine.Player;

/**
 * @author Yaroslav Shestakov
 *
 */
public class Bishop extends Piece {

	/**
	 * Creates a Bishop piece.
	 * 
	 * @param player
	 * @param x
	 * @param y
	 */
	public Bishop(Player player, int x, int y) {
		super(player, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public
	ArrayList<Location> getMoves() {
		ArrayList<Location> moves = new ArrayList<Location>();
		
		int x = pos.x ;
		int y = pos.y ;	
		
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
