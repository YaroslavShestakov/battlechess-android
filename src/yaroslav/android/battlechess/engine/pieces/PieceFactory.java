package yaroslav.android.battlechess.engine.pieces;

import yaroslav.android.battlechess.engine.Match;
import yaroslav.android.battlechess.engine.Player;

public class PieceFactory {
	private Player player ;
	private int type ;
	private int x, y ;
	private boolean moved = false ;
	private Match match;
	
	public PieceFactory	setPlayer(Player player){
		this.player = player ;
		return this ;
	}
	
	public PieceFactory setType(int type){
		this.type = type ;
		return this ;
	}
	
	public PieceFactory setPosition(int x, int y){
		this.x = x ;
		this.y = y ;
		return this ;
	}
	
	public PieceFactory setMoved(boolean moved){
		this.moved = moved ;
		return this ;
	}
	
	public Piece create(){
		Piece piece = null ;
		
		switch(type){
			case Piece.PAWN:
				piece = new Pawn(player, x, y);
				break ;
			case Piece.BISHOP:
				piece = new Bishop(player, x, y);
				break ;
			case Piece.ROOK:
				piece = new Rook(player, x, y);
				break ;
			case Piece.KING:
				piece = new King(player, x, y);
				break ;
			case Piece.KNIGHT:
				piece = new Knight(player, x, y);
				break ;
			case Piece.QUEEN:
				piece = new Queen(player, x, y);
				break ;
		}
		
		if (piece != null){
			piece.moved = this.moved ;
			piece.type  = type ;
			piece.match = match ;
		}
		
		return piece ;
	}
	
	public static PieceFactory builder(){
		return new PieceFactory();
	}

	public PieceFactory setMatch(Match match) {
		this.match = match ;
		return this ;
	}
}