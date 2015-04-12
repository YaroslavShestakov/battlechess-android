package yaroslav.android.battlechess.gui.views;

import yaroslav.android.battlechess.R;
import yaroslav.android.battlechess.engine.Location;
import yaroslav.android.battlechess.engine.pieces.Piece;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Class responsible for representing a cell on the chess board.
 * 
 * @author Yaroslav
 *
 */
public class CellView extends View implements OnTouchListener {
	

	/**
	 * No tile marker
	 */
	public final static int NONE = 0 ;
	/**
	 * Selection marker
	 */
	public final static int	SELECTION = 1 ;
	/**
	 * Movement marker
	 */
	public final static int MOVEMENT  = 2 ;
	/**
	 * Attack marker
	 */
	public final static int OFFENCE	  = 3 ;
	
	private BoardView board ;
	private Integer marker = NONE ; ;
	private Paint paint = new Paint();

	/**
	 * Represenation of position
	 */
	public Location pos;
	
	public CellView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	public CellView(Context context, AttributeSet attrs){
		super(context,  attrs);
		init();
	}
	
	public CellView(Context context){
		super(context);
		init();
	}
	
	/**
	 * Receives a Board object
	 * @param board
	 */
	public void setBoard(BoardView board){
		this.board = board ;
	}
	
	private void init(){
		this.setOnTouchListener(this);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);   
        
        if (marker == SELECTION){
	        paint.setColor( Color.BLUE);
	        paint.setStrokeWidth( 1.5f );
	        paint.setStyle( Style.STROKE );
	        canvas.drawRect( 0, 0, getWidth(), getHeight(), paint );
        } else if (marker == MOVEMENT){
	        paint.setColor( Color.GREEN );
	        paint.setStrokeWidth( 1.5f );
	        paint.setStyle( Style.STROKE );
	        canvas.drawRect( 0, 0, getWidth(), getHeight(), paint );
        } else if (marker == OFFENCE){
	        paint.setColor( Color.RED );
	        paint.setStrokeWidth( 1.5f );
	        paint.setStyle( Style.STROKE );
	        canvas.drawRect( 0, 0, getWidth(), getHeight(), paint );
        }
	}
	
	/**
	 * Sets location of this cell.
	 * @param pos Any Location object.
	 */
	public void setLocation(Location pos){
		this.pos = pos ;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		board.onCellTouched(this);
		return false;
	}

	/**
	 * Checks if marked for by the given marker
	 * @param type NONE, SELECTION, MOVEMENT, OFFENCE markers
	 * @return true if has the given marker
	 */
	public boolean isMarked(int type) {
		return (this.marker == type) ;
	}
	
	/**
	 * Marks this cell by the given marker
	 * @param type
	 */
	public void mark(int type){
		this.marker = type ;
		invalidate();
	}
	
	/**
	 * Sets marker to NONE
	 */
	public void unmark(){
		this.marker = NONE ;
		invalidate();
	}
	
	/**
	 * Check is cell is not marked
	 * @return true if marker is NONE
	 */
	public boolean unmarked(){
		return this.marker == NONE ;
	}
}
