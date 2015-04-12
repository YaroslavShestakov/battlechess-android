package yaroslav.android.battlechess.gui.views;

import java.util.HashMap;

import yaroslav.android.battlechess.R;
import yaroslav.android.battlechess.engine.pieces.Piece;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * A cache class used to store Bitmap objects for improved performance.
 * 
 * @author Yaroslav Shestakov
 *
 */
public class BitmapRepo {
	
	/**
	 * Cache for black pieces
	 */
	public final HashMap<Integer, Bitmap> black  = new HashMap<Integer, Bitmap>(6);
	/**
	 * Cache for white pieces
	 */
	public final HashMap<Integer, Bitmap> white  = new HashMap<Integer, Bitmap>(6);
	
	private Integer pieceSize = 20 ;
	private Resources resources;
	
	
	/**
	 * Sets resource object to cache from.
	 * @param resources Resource object from a context.
	 */
	public void setResources(Resources resources) {
		this.resources = resources ;
	}
	
	/**
	 * Sets size of bitmaps.
	 * @param pieceSize pixels.
	 */
	public void setPieceSize(int pieceSize){
		this.pieceSize = pieceSize ;
	}
	
	/**
	 * Returns size of cached Bitmaps
	 * @return pixels
	 */
	public int getPieceSize(){
		return pieceSize;
	}
	
	/**
	 * Generates and returns a Bitmap object from a resource.
	 * @param res resource from R class
	 * @return Bitmap object
	 */
	public Bitmap getBMFromResource(int res){
		Bitmap bm = BitmapFactory.decodeResource(resources, res);
		bm = Bitmap.createScaledBitmap(bm, pieceSize, pieceSize, false);
		
		return bm ;
	}
	
	/**
	 * Caches the bitmaps
	 */
	public void fill(){
		black.put(Piece.PAWN, 	getBMFromResource(R.drawable.b_pawn));
		black.put(Piece.ROOK, 	getBMFromResource(R.drawable.b_rook));
		black.put(Piece.BISHOP, getBMFromResource(R.drawable.b_bishop));
		black.put(Piece.KNIGHT, getBMFromResource(R.drawable.b_knight));
		black.put(Piece.QUEEN, 	getBMFromResource(R.drawable.b_queen));
		black.put(Piece.KING, 	getBMFromResource(R.drawable.b_king));
		
		white.put(Piece.PAWN, 	getBMFromResource(R.drawable.w_pawn));
		white.put(Piece.ROOK, 	getBMFromResource(R.drawable.w_rook));
		white.put(Piece.BISHOP, getBMFromResource(R.drawable.w_bishop));
		white.put(Piece.KNIGHT, getBMFromResource(R.drawable.w_knight));
		white.put(Piece.QUEEN, 	getBMFromResource(R.drawable.w_queen));
		white.put(Piece.KING, 	getBMFromResource(R.drawable.w_king));
	}
}
