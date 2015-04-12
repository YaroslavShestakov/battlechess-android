package yaroslav.android.battlechess.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import android.content.Context;

public class Storage {
	
	private Context context;

	public Storage(Context context){
		this.context = context ;
	}
	
	public boolean save(String name, String data) {
		String filename = addExt(name);
		FileOutputStream fos;
		try {
			fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
			fos.write(data.getBytes());
			fos.close();
			return true ;
		} catch (Exception e){
			return false ;
		}
	}
	
	public boolean exists(String name){
	    File file = context.getFileStreamPath(addExt(name));
	    return file.exists();
	}
	
	public String[] getFiles(){
		File mydir = context.getFilesDir();            
		File lister = mydir.getAbsoluteFile();

		return lister.list();
	}
	
	public static String removeExt(String name){
		int pos = name.lastIndexOf(".");
		if (pos > 0) {
		    name = name.substring(0, pos);
		}
		return name ;
	}
	
	public static String addExt(String name){
		return name.concat(".txt");
	}

	public String read(String name) {
        try {
            FileInputStream fis = context.openFileInput(addExt(name));
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
	}
}
