package yaroslav.android.battlechess.engine;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.util.Log;
import yaroslav.android.battlechess.engine.pieces.Piece;
import yaroslav.android.battlechess.engine.pieces.PieceFactory;

/**
 * A match class used to hold MatchEngine properties and ability to save & load matches.
 * @author Yaroslav Shestakov
 *
 */
public class Match extends MatchEngine {
	
	
	public Match(){
		super();
	}

	/**
	 * Returns a piece at given position
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return Piece at the given position, otherwise null
	 */
	public Piece pieceAt(int x, int y){
		return board[y][x] ;
	}
	
	/**
	 * Returns a piece at given position
	 * @param pos Location of the piece
	 * @return Piece at the given position, otherwise null
	 */
	public Piece pieceAt(Location pos){
		return pieceAt(pos.x, pos.y);
	}
	
	/**
	 * Returns string representation of this match. Used to save and retrieve.
	 * @return String representation
	 * @throws JSONException
	 */
	public String save() throws JSONException{
		JSONObject setup = new JSONObject();
		JSONObject match = new JSONObject();
		JSONObject board = new JSONObject();

		match.put("board", board);
		match.put("currentPlayerIndex", currentPlayerIndex + 1);
		setup.put("match", match);

		
		JSONArray pieces = new JSONArray();
		
		board.put("rows", 8);
		board.put("cols", 8);
		board.put("pieces", pieces);
		
		for (Piece[] r : this.board){
			JSONArray row = new JSONArray();
			for (Piece piece : r){
				String data = "" ;
				if (piece != null){
					int player = (piece.player.getColor() == Player.WHITE) ? 1 : 2 ;
					//Log.v("Match", String.valueOf(player));
					int type = piece.getType();
					int moved = piece.moved ? 1 : 0 ;
					
					data = player + ":" + type + ":" + moved ;
				} else 
					data = "0" ;
				row.put(data);
			}
			pieces.put(row);
		}
		
		
		return setup.toString() ;
	}
	
	public static class MatchBuilder {
		private Match match ;
		private boolean explicitSize = false ;
		
		public MatchBuilder(){
			this.match = new Match();
		}
		
		public MatchBuilder setPlayers(Player white, Player black){
			white.setColor(Player.WHITE);
			black.setColor(Player.BLACK);
			
			match.players = new Player[]{ white, black } ;
			match.currentPlayerIndex = 0 ;
			
			return this ;
		}
		
		/**
		 * Loads setup to a Match from string representation.
		 * @param setup String representation encased into JSONObject
		 * @return	MatchBuilder object for chain binding
		 * @throws JSONException
		 */
		public MatchBuilder loadSetup(JSONObject setup) throws JSONException {
			JSONObject board = setup.getJSONObject("match").getJSONObject("board");
			
			this.match.NUM_COLS = board.getInt("cols") ;
			this.match.NUM_COLS = board.getInt("rows") ;
			
			this.match.currentPlayerIndex = 
					setup.getJSONObject("match").getInt("currentPlayerIndex") - 1;

			JSONArray pieces = board.getJSONArray("pieces");

				for (int y = 0 ; y < pieces.length() ; y++){
					JSONArray row = pieces.getJSONArray(y);

					for (int x = 0 ; x < row.length() ; x++){
						String piece = row.getString(x);
						String[] props = piece.split(":");

						int pIndex = Integer.valueOf(props[0]);

						if (pIndex != 0){
							int type =   Integer.valueOf(props[1]);
							boolean moved = false ;
	
							if (props.length >= 3){
								moved = props[2].equals("1");
							}
	
							Player player = match.players[pIndex - 1] ;
							
							this.match.board[y][x] = 
									PieceFactory
									.builder()
									.setType(type)
									.setPlayer(player)
									.setPosition(x, y)
									.setMoved(moved)
									.setMatch(match)
									.create();
							
						} else {
							this.match.board[y][x] = null ;
						}
					}

				}

			return this ;
		}
		
		/**
		 * Returns a ready Match object
		 * @return ready Match object
		 */
		public Match create(){
			return this.match ;
		}
	}
}