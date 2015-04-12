package yaroslav.android.battlechess.helpers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import android.app.Activity;

/**
 * Class used to get a String data from a raw resource such as 
 * <b>.txt</b> file.
 * @author Yaroslav Shestakov
 *
 */
public class JSONReader {
	/**
	 * @param activity Current activity
	 * @param res Resource from <b>R</b> class
	 * @return Content of the file
	 */
	public static String read(Activity activity, int res){
		InputStream is = activity.getResources().openRawResource(res);
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		try {
		    Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		    int n;
		    while ((n = reader.read(buffer)) != -1) {
		        writer.write(buffer, 0, n);
		    }
		} catch(Exception e){
			
		} finally {
		    try {
		    	is.close();
		    } catch (Exception e1){
		    	
		    }
		}
		String jsonString = writer.toString();
		return jsonString ;
	}
}
