package yaroslav.android.battlechess.engine;

/**
 * Abstraction for different events in game
 * @author Yaroslav
 *
 */
public enum Event {
	/**
	 * Represents a finished turn
	 */
	MOVE_DONE,
	
	/**
	 * Represents a threat to a king
	 */
	CHECK,
	
	/**
	 * Represents a finished game
	 */
	MATE,
	
	/**
	 * Represents a stalemate situation
	 */
	STALEMATE,
}