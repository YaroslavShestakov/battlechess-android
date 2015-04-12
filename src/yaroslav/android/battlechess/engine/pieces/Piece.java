package yaroslav.android.battlechess.engine.pieces;

import static yaroslav.android.battlechess.engine.Location.* ;

import java.util.ArrayList;

import yaroslav.android.battlechess.engine.Match;
import yaroslav.android.battlechess.engine.Player;
import yaroslav.android.battlechess.engine.Location;

/**
 * Class-container responsible for piece representation.
 * @author Yaroslav Shestakov
 *
 */
public abstract class Piece {
	/**
	 * Owner of the piece
	 */
	public Player player ;
	/**
	 * Location of the piece
	 */
	public Location pos = new Location();
	/**
	 * Flag used to determine whether piece already moved
	 */
	public Boolean moved = false ;
	/**
	 * Type of the piece
	 */
	public Integer type ;
	/**
	 * Match where the piece is used
	 */
	public Match match;
	
	public Piece(Player player, int x, int y){
		setPlayer(player);
		pos.setX(x);
		pos.setY(y);
	}
	
	public void setPlayer(Player player){
		this.player = player ;
		player.pieces.add(this);
	}
	
	/**
	 * Checks whether current piece is of opposite player
	 * @param piece
	 * @return
	 */
	public boolean isEnemy(Piece piece){
		return (this.player != piece.player);
	}
	
	/**
	 * Returns possible valid moves, obstacles are counted.
	 * @return
	 */
	public abstract ArrayList<Location> getMoves();
		
	protected boolean putMove(int x, int y, ArrayList<Location> moves){
		Location tile = new Location(x, y);
		if (!tile.isValid()){
			return false ;
		}
		
		Piece target = match.pieceAt(tile);
		if (target != null){
			if (isEnemy(target)){
				tile.target = target ;
				moves.add(tile.setType(MIXED));
			} else {
				moves.add(tile.setType(PROTECTION));
			}
			return false ;
		} else {
			moves.add(tile.setType(MIXED));
			return true ;
		}
	}
	
	/**
	 * Returns the owner of the piece
	 * @param player
	 * @return
	 */
	public boolean isOwnedBy(Player player){
		return this.player == player ;
	}
	
	/**
	 * Returns type of this piece
	 * @return
	 */
	public int getType(){
		if (this instanceof Pawn)
			return PAWN ;
		else if (this instanceof Rook){
			return ROOK ;
		} else if (this instanceof Bishop){
			return BISHOP ;
		} else if (this instanceof Queen){
			return QUEEN ;
		} else if (this instanceof King){
			return KING ;
		} else if  (this instanceof Knight){
			return KNIGHT ;
		}
		return 0 ;
	}
	
	
	
	
	public static final int
		PAWN 	= 1,
		ROOK 	= 2,
		BISHOP 	= 3,
		KNIGHT 	= 4,
		QUEEN 	= 5,
		KING 	= 6 ;
}