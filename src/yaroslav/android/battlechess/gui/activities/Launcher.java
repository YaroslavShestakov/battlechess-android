package yaroslav.android.battlechess.gui.activities;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import yaroslav.android.battlechess.R;
import yaroslav.android.battlechess.helpers.TaggedActivity;

/**
 * Main activity, starts loading, switches views.
 * 
 * @author Yaroslav
 *
 */
public class Launcher extends TaggedActivity {
	
	private int seconds = 2 ;
	private Timer timer = new Timer();
	private Boolean launched = false ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		if (!launched){
			displaySplash();
			launched = true ;
			
			timer.schedule(new TimerTask(){
				public void run(){
					runOnUiThread(new Runnable(){
						public void run(){
							displayMainMenu();
						}
					});
				}
			}, seconds * 1000);
		}
	}
	
	/**
	 * Displays the main menu layout.
	 */
	public void displayMainMenu(){
		setContentView(R.layout.main_menu);
	}
	
	/**
	 * Displays splash when application is started.
	 */
	public void displaySplash(){
		setContentView(R.layout.splash);
	}
	
	/**
	 * Starts a new activity - MatchActivity.
	 * @param v source.
	 */
	public void openMatchmaking(View v){
		Intent intent = new Intent(this, MatchActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Starts a new activity - AboutActivity.
	 * @param v source.
	 */
	public void onAboutButtonPress(View v){
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Starts a new activity - SettingsActivity.
	 * @param v source.
	 */
	public void onSettingsButtonPress(View v){
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
	
	@Override
	protected void onDestroy() {
		this.launched = false ;
		super.onDestroy();
	}
}
