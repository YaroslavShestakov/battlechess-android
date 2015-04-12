 package yaroslav.android.battlechess.engine.pieces;

import static yaroslav.android.battlechess.engine.Location.EN_PASSANT;
import static yaroslav.android.battlechess.engine.Location.LONG_MOVEMENT;
import static yaroslav.android.battlechess.engine.Location.MOVEMENT;
import static yaroslav.android.battlechess.engine.Location.OFFENCE;

import java.util.ArrayList;

import yaroslav.android.battlechess.engine.Location;
import yaroslav.android.battlechess.engine.Player;
import android.util.Log;

public class Pawn extends Piece {
	private int dy ;

	public Pawn(Player player, int x, int y) {
		super(player, x, y);
		this.dy = (player.color == Player.WHITE) ? -1 : 1 ;
	}

	@Override
	public ArrayList<Location> getMoves() {
		ArrayList<Location> moves = new ArrayList<Location>();
		
		/* Get attack tiles of PAWN */
		for (int step = -1; step <= 1 ; step += 2){
			Location dest = new Location(pos.x + step, pos.y + dy, OFFENCE);
			if (dest.isValid()){
				Piece target = match.pieceAt(dest);
				if (target != null && isEnemy(target)){
					dest.target = target ;
					moves.add(dest) ;
				} else {
					Location enPassant = new Location(pos.x + step, pos.y + dy, EN_PASSANT);
					Location enemySpot  = new Location(enPassant.x, pos.y);
					
					if (!enPassant.isValid() || !enemySpot.isValid())
						continue ;
					
					Piece inPassTarget = match.pieceAt(enemySpot);
					
					if (inPassTarget != null && isEnemy(inPassTarget)){
						
						if (inPassTarget instanceof Pawn){
							Location move = inPassTarget.player.getLastMove();
							if (inPassTarget.pos.equals(move) && move.type == LONG_MOVEMENT){
								enPassant.target = inPassTarget ;
								moves.add(enPassant);
							}
						}
					}
				}
			}
		}
		
		/* Get short move of PAWN */
		Location shortMove = new Location(pos.x, pos.y + dy, MOVEMENT);
		if (shortMove.isValid()){
			Piece blocking = match.pieceAt(shortMove);
			if (blocking == null)
				moves.add(shortMove);
		}
		
		/* Get long move of PAWN */
		if (!moved && moves.contains(shortMove)){
			Location jump = new Location(pos.x, pos.y + dy * 2, LONG_MOVEMENT);
			if (jump.isValid()){
				Piece jumpBlock = match.pieceAt(jump);
				if (jumpBlock == null)
					moves.add(jump);
			}
		}
		
		return moves ;
	}
	
}
