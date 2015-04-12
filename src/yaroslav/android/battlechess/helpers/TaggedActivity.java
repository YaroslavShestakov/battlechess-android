package yaroslav.android.battlechess.helpers;

import android.app.Activity;

/**
 * Class containing a TAG constant to help using LogCat.
 * 
 * @author ярослав
 *
 */
public class TaggedActivity extends Activity {
	
	public final String TAG = this.getClass().getName();
	
	protected Logger log = Logger.create(TAG) ;
	
}
