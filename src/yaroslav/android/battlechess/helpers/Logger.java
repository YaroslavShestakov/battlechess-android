package yaroslav.android.battlechess.helpers;

import android.util.Log;

public class Logger {
	
	public final String tag ;
	
	private Logger(String tag){
		this.tag = tag ;
	}
	
	public static Logger create(String tag){
		return new Logger(tag);
	}
	
	public Logger e(String msg){
		Log.e(tag, msg);
		return this ;
	}
	
	public Logger v(String msg){
		Log.v(tag, msg);
		return this ;
	}
}
