package yaroslav.android.battlechess.engine.pieces;

import java.util.ArrayList;
import static yaroslav.android.battlechess.engine.Location.* ;
import yaroslav.android.battlechess.engine.Player;
import yaroslav.android.battlechess.engine.Location;

public class Rook extends Piece {

	public Rook(Player player, int x, int y) {
		super(player, x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public
	ArrayList<Location> getMoves() {
		ArrayList<Location> moves = new ArrayList<Location>();
		
		int x = pos.x ;
		int y = pos.y ;	
		
		for (int dx = 1 ; x + dx <= 7 ; dx++){
			if (!putMove(x + dx, y, moves))
				break ;
		}
		
		for (int dx = -1 ; x + dx >= 0 ; dx--){
			if (!putMove(x + dx, y, moves))
				break ;
		}
		
		for (int dy = 1 ; y + dy <= 7 ; dy++){
			if (!putMove(x, y + dy, moves))
				break ;
		}
		
		for (int dy = -1 ; y + dy >= 0 ; dy--){
			if (!putMove(x, y + dy, moves))
				break ;
		}
		
		return moves;
	}
}
