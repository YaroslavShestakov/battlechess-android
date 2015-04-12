package yaroslav.android.battlechess.engine;

import java.util.ArrayList;

import yaroslav.android.battlechess.engine.pieces.King;
import yaroslav.android.battlechess.engine.pieces.Piece;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;

public class Player {
	public static final int BLACK = Color.BLACK ;
	public static final int WHITE = Color.WHITE ;
	
	public String name ;
	public int color ;
	public Location lastMove ;
	public ArrayList<Piece> pieces = new ArrayList<Piece>();
	
	public Player(String name, int color){
		this.name = name ;
		this.color = color  ;
	}
	
	public Player setColor(int color){
		this.color = color ;
		return this ;
	}

	public Location getLastMove() {
		return lastMove ;
	}
	
	public ArrayList<Location> getAttackableTiles(){
		ArrayList<Location> attackable = new ArrayList<Location>();
		
		for (Piece piece : pieces){
			ArrayList<Location> moves = piece instanceof King
					? ( (King) piece).getRawMoves()
							: piece.getMoves() ;
					
			for (Location move : moves){
				if (move.type == Location.MIXED || move.type == Location.OFFENCE || move.type == Location.PROTECTION){
					attackable.add(move);
				}
			}
		}
		
		return attackable ;
	}
	
	public Piece getKing(){
		for (Piece piece : pieces){
			if (piece instanceof King)
				return piece ;
		}
		return null ;
	}

	public int getColor() {
		return this.color ;
	}

	public CharSequence getName() {
		return this.name ;
	}
}
