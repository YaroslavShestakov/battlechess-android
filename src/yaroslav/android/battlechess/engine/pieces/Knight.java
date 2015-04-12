package yaroslav.android.battlechess.engine.pieces;

import java.util.ArrayList;

import yaroslav.android.battlechess.engine.Location;
import yaroslav.android.battlechess.engine.Player;

/**
 * Class responsible for Knight piece in chess
 * @author Yaroslav Shestakov
 *
 */
public class Knight extends Piece {

	public Knight(Player player, int x, int y) {
		super(player, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<Location> getMoves() {
		ArrayList<Location> moves = new ArrayList<Location>();
		
		int x = pos.x ;
		int y = pos.y ;
		
		putMove(x + 2, y + 1, moves);
		putMove(x - 2, y + 1, moves);
		putMove(x + 2, y - 1, moves);
		putMove(x - 2, y - 1, moves);
		
		putMove(x + 1, y + 2, moves);
		putMove(x - 1, y + 2, moves);
		putMove(x + 1, y - 2, moves);
		putMove(x - 1, y - 2, moves);	
		
		return moves;
	}
}
