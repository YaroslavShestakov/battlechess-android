package yaroslav.android.battlechess.engine;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

import yaroslav.android.battlechess.engine.pieces.King;
import yaroslav.android.battlechess.engine.pieces.Piece;

public class MatchEngine {

	public int NUM_ROWS = 8 ;
	public int NUM_COLS = 8 ;
	
	/**
	 * 2-D array of pieces
	 */
	public Piece[][] board = new Piece[NUM_COLS][NUM_ROWS];
	public Player[] players = new Player[2] ;
	int currentPlayerIndex = 0;
	
	private HashMap<Event, ArrayList<EventListener>> listeners = new HashMap<Event, ArrayList<EventListener>>() ;
	
	
	/**
	 * Tests a move for validity and executes it.
	 * 
	 * @param piece Piece to move
	 * @param dest  Destination
	 * @param execute Determines if should be executed if valid
	 * @return true if move is valid
	 */
	public boolean confirmMove(Piece piece, Location dest, boolean execute){
		if (piece == null || !dest.isValid() 
				|| dest.type == Location.PROTECTION || !piece.isOwnedBy(players[currentPlayerIndex])){
			Log.v("MatchEngine",  "Piece is null || not valid tile || wrong player");
			return false ;
		}
		
		ArrayList<Location> moves = piece.getMoves();	
		int index = moves.indexOf(dest);
		if (index != -1){
			if (execute){
				executeMove(piece, moves.get(index));
			}
			return true ;
		}
		return false ;
	}
	
	
	private void executeMove(Piece piece, Location dest){
		if (dest.hasTarget()){
			Log.v("A", "Target detected");
			Piece target = dest.target ;
			target.player.pieces.remove(target);
			board[target.pos.y][target.pos.x] = null ; 	//Empty the target tile
		}
				
		board[piece.pos.y][piece.pos.x] = null ; 		//Empty the initial tile
		board[dest.y][dest.x] = piece ;			 		//Fill the destination tile
		
		piece.pos.x = dest.x ;				 	 		//Change the piece position X
		piece.pos.y = dest.y ;					 		//Change the piece position Y
		
		piece.player.lastMove = dest ;			 		//Set last move to player
		piece.moved = true ;					 		//Mark piece as moved
		
		//Change current player
		currentPlayerIndex = currentPlayerIndex == 0 ? 1 : 0 ;
		//Notify listeners about move done
		dispatchEvent(Event.MOVE_DONE);
		checkForCheck();
	}
	
	private void checkForCheck(){
		Player current = getCurrentPlayer();
		Player opponent = getOpponent(current);
		
		King king = (King) current.getKing();
		
		for (Location loc : opponent.getAttackableTiles()){
			if (loc.equals(king.pos)){
				Log.v("MatchEngine", "Found a king location");
				if (loc.type == Location.OFFENCE || loc.type == Location.MIXED){
					dispatchEvent(Event.CHECK);
				}
			}
		}
	}
	
	/**
	 * Adds an event listener
	 * 
	 * @param event Event to listen to
	 * @param a		Any object which implements EventListener
	 */
	public void addEventListener(Event event, EventListener a){
		if (!listeners.containsKey(event)){
			ArrayList<EventListener> list = new ArrayList<EventListener>();
			listeners.put(event, list);
		}
		
		listeners.get(event).add(a);
	}
	
	/**
	 * Removes an event listener.
	 * @param event
	 * @param listener
	 */
	public void removeEventListener(final Event event, final EventListener listener){
		ArrayList<EventListener> list = listeners.get(event);
		if (list.contains(listener)){
			list.remove(listener);
		}
	}
	
	/**
	 * Notifies event listeners about an event.
	 * @param event
	 */
	private void dispatchEvent(Event event){
		for (EventListener l: listeners.get(event)){
			l.onEvent(event);
		}
	}
	
	/**
	 * Returns an opposite player
	 * 
	 * @param player Player to compare to
	 * @return		 Opponent of the given player
	 */
	public Player getOpponent(Player player){
		return (players[0] == player) ? players[1] : players[0] ;
	}
	
	/**
	 * Checks if the player has a right to move
	 * @param player 
	 * @return true if the given player is current
	 */
	public boolean isCurrentPlayer(Player player){
		return player == players[currentPlayerIndex] ;
	}
	
	/**
	 * Returns the player which has a right to move.
	 * @return the current player ;
	 */
	public Player getCurrentPlayer(){
		return players[currentPlayerIndex] ;
	}
}
