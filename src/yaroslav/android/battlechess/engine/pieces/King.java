package yaroslav.android.battlechess.engine.pieces;

import java.util.ArrayList;

import android.util.Log;

import yaroslav.android.battlechess.engine.Player;
import yaroslav.android.battlechess.engine.Location;

/**
 * Class which responsible for a king piece in chess.
 * @author Yaroslav Shestakov
 *
 */
public class King extends Piece {
	private int[][] presets = new int[][]{
			{ -1, 1	}, 	{ 0, 1 }, 	{ 1, 1 },
			{ -1, 0	},         		{ 1, 0 },
			{ -1, -1}, 	{ 0, -1},	{ 1, -1}
	} ;

	/**
	 * @param player Owner of the piece
	 * @param x		 x coordinate on the board
	 * @param y      y coordinate on the board
	 */
	public King(Player player, int x, int y) {
		super(player, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<Location> getMoves() {
		ArrayList<Location> moves = new ArrayList<Location>();
		
		Player opponent = match.getOpponent(player);
		ArrayList<Location> dangerous = opponent.getAttackableTiles();
		
		
		for (int[] c : presets){
			int x = pos.x + c[0] ;
			int y = pos.y + c[1] ;
			if (!dangerous.contains(new Location(x, y))){
				putMove(x, y, moves);
			}
		}
		
		return moves ;
	}
	
	/**
	 * Returns pseudo-legal moves. Does not count any obstacles or threats.
	 * @return pseudo-legal moves from piece position
	 */
	public ArrayList<Location> getRawMoves(){
		ArrayList<Location> moves = new ArrayList<Location>();
		
		for (int[] c : presets){
			int x = pos.x + c[0] ;
			int y = pos.y + c[1] ;
			moves.add(new Location(x, y, Location.MIXED));
		}
		
		return moves ;
	}
}
