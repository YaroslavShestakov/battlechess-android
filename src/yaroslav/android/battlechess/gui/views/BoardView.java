package yaroslav.android.battlechess.gui.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import yaroslav.android.battlechess.R;
import yaroslav.android.battlechess.engine.Match;
import yaroslav.android.battlechess.engine.Location;
import yaroslav.android.battlechess.engine.pieces.Piece;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Class responsible for displaying the chess board.
 * 
 * @author Yaroslav
 *
 */
public class BoardView extends TableLayout {
	
	private BitmapRepo bitmaps ;
	
	private int[] colors ;
	private Match match ;
	private CellView[][] grid = new CellView[8][8];
	
	private Piece selected ;
	private Matrix pieceMatrix = new Matrix();

	
	/**
	 * Number of rows in the board
	 */
	public final static int NUM_ROWS = 8 ;
	/**
	 * Number of colums in the board
	 */
	public final static int NUM_COLS = 8 ;
	/**
	 * Cell size in pixels
	 */
	public final static int CELL_SIZE = 60 ;
	
	
	/**
	 * Constructs a BoardView object
	 * @param context
	 */
	public BoardView(Context context){
		super(context);
		init();
	}
	
	/**
	 * Constructs a BoardView object
	 * @param context
	 * @param attrs 
	 */
	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init(){
		loadBitmaps();
		drawGrid();
	}
	
	/**
	 * Sets match to get data from.
	 * @param match A valid Match objects.
	 */
	public void setMatch(Match match){
		this.match = match ;
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(CELL_SIZE, CELL_SIZE);
	}
	
	/**
	 * Draws grid for board.
	 */
	public void drawGrid(){
		colors = new int[]{R.drawable.white, R.drawable.black };
		Context context = this.getContext();
		int color ;
		
		for(int y = 0 ; y < NUM_ROWS ; y++){
			TableRow row = new TableRow(context);
			for(int x = 0 ; x < NUM_COLS ; x++){
				color = ( (x+y) % 2 == 0) ? colors[0] : colors[1] ;
				
				
				CellView cell = new CellView(context);
				cell.setBoard(this);
				cell.setLayoutParams(
						new TableRow.LayoutParams(CELL_SIZE, CELL_SIZE));
				
				cell.setBackgroundResource(color);
				cell.setLocation(new Location(x, y));
				
				grid[y][x] = cell ;
				row.addView(cell);
			}
			this.addView(row);
		}
	}
	
	
	/**
	 * Draws pieces to the board
	 * @param canvas A valid Canvas object
	 */
	public void drawPieces(Canvas canvas){		
		for (int y = 0 ; y < 8 ; y++){
			for (int x = 0 ; x < 8 ; x++){
				Piece piece = match.board[y][x];

				if (piece != null){
					HashMap<Integer, Bitmap> repo = (piece.player.color == Color.WHITE) 
							? 	bitmaps.white 
									: bitmaps.black ;
					
					Bitmap source = repo.get(piece.type);
					
					source = Bitmap.createBitmap(source , 0, 0, 
							source .getWidth(), 
							source .getHeight(), pieceMatrix, true);

					
					canvas.drawBitmap(source, x * CELL_SIZE, y * CELL_SIZE, null);
				}
			}
		}
	}

	/**
	 * Handles touches of CellView object. Marks cells and processes moves.
	 * @param cell
	 */
	public void onCellTouched(CellView cell) {
		Piece piece = match.pieceAt(cell.pos) ;
		
		if (piece != null && cell.isMarked(CellView.NONE)){
			unmarkAll();
			
			if (match.isCurrentPlayer(piece.player)){
				ArrayList<Location> moves = piece.getMoves();
				
				if (moves != null){
					CellView toMark = null ;
					for (Location move : moves){
						if (move.type == Location.PROTECTION)
							continue ;
						toMark = grid[move.y][move.x] ;
						if (move.hasTarget())
							toMark.mark(CellView.OFFENCE);
						else {
							toMark.mark(CellView.MOVEMENT);
						}
					}
				}
				
				cell.mark(CellView.SELECTION);
				this.selected = piece ;
			} else
				return ;
		} else if (cell.isMarked(CellView.SELECTION)){
			unmarkAll();
		} else if (cell.isMarked(CellView.MOVEMENT) || cell.isMarked(CellView.OFFENCE)){
			
			if (match.confirmMove(selected, cell.pos, true)){
				
				Log.v("BoardView", 
						"Piece moved from " +
						"("+selected.pos.x+","+selected.pos.y +") to "+
					    "("+cell.pos.x    +","+cell.pos.y + ")");
				
				unmarkAll();
				update();

			} else {
				Log.v("BoardView", "Invalid movement");
			}
		}
	}
	
	/**
	 * Updates and redraws pieces on the board.
	 */
	public void update(){	
		invalidate();
	}
	
	private void unmarkAll(){
		for (int y = 0 ; y < 8 ; y++){
			for (int x = 0 ; x < 8 ; x++){
				grid[y][x].unmark();
			}
		}
		selected = null ;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
	    // execute code here if you want to draw behind children
	    super.dispatchDraw(canvas);
	    // execute code here if you want to draw over children
	    if (match != null){
	    	drawPieces(canvas);
	    } else {
	    	Log.v("BoardView", "No match set, pieces cannot be displayed");
	    }
	}
	
	private void loadBitmaps(){
		bitmaps = new BitmapRepo();
		bitmaps.setResources(getContext().getResources());
		bitmaps.setPieceSize(CELL_SIZE - 5);
		bitmaps.fill();
	}

	/**
	 * Rotates pieces by 1800 degrees
	 */
	public void rotate() {
		pieceMatrix.postRotate(180);
	    invalidate();
	}
}
