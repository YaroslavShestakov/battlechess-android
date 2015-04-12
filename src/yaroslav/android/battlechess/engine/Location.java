package yaroslav.android.battlechess.engine;

import java.util.Vector;

import android.util.Log;

import yaroslav.android.battlechess.engine.pieces.Piece;

/**
 * Represents a position of a piece on the board or movement
 * 
 * @author Yaroslav
 *
 */
public class Location {
	
	/**
	 * Move type representation
	 */
	@SuppressWarnings("javadoc")
	public final static Integer
			STATIC 			= 0000,
			MOVEMENT 		= 0001,
			OFFENCE 		= 0010,
			MIXED			= 0011,
			LONG_MOVEMENT 	= 0100,
			EN_PASSANT 		= 0101,
			PROTECTION		= 0111 ;
	
	/**
	 * Type of the position or movement
	 */
	public Integer type = STATIC ;
	/**
	 * Possible target to attack
	 */
	public Piece target = null ;
	
	/**
	 * x-coordinate
	 */
	public int x = 0 ;
	/**
	 * y-coordinate
	 */
	public int y = 0 ;
	
	
	
	public Location(int x, int y, Integer type){
		this.x = x ;
		this.y = y ;
		this.type = type ;
	}
	
	public Location(int x, int y){
		this(x, y, STATIC);
	}
	
	public Location() {
		// TODO Auto-generated constructor stub
	}

	public Location setX(int x){
		this.x = x ;
		return this ;
	}
	
	public Location setY(int y){
		this.y = y ;
		return this ;
	}
	
	public Location setType(int type){
		this.type = type ;
		return this;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Location)){
			return false ;
		}
		Location p = (Location) o ;
		return (this.x == p.x && this.y == p.y);
	}
	
	public boolean isValid(){
		return
				(x >= 0 && x <= 7)
		&&
				(y >= 0 && y <= 7) ;
	}
	
	public boolean hasTarget(){
		return this.target != null ;
	}
	
	@Override
	public String toString(){
		return "(" + this.x + ", " + this.y + ")" ;
	}
}
