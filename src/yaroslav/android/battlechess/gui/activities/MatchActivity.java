package yaroslav.android.battlechess.gui.activities;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import yaroslav.android.battlechess.R;
import yaroslav.android.battlechess.engine.Event;
import yaroslav.android.battlechess.engine.EventListener;
import yaroslav.android.battlechess.engine.Match;
import yaroslav.android.battlechess.engine.Player;
import yaroslav.android.battlechess.engine.Location;
import yaroslav.android.battlechess.engine.pieces.Piece;
import yaroslav.android.battlechess.gui.views.BoardView;
import yaroslav.android.battlechess.helpers.JSONReader;
import yaroslav.android.battlechess.helpers.TaggedActivity;
import yaroslav.android.battlechess.storage.Storage;

public class MatchActivity extends TaggedActivity implements EventListener {
	public final String PREFS_NAME 	= "ChessSetup";
	public final String PAUSED_GAME_SETTING   = "ContinueMatch" ;

	BoardView boardview ;
	private Match match ;
	private Button cont ;
	
	private SharedPreferences settings;
	private String DEFAULT_SETUP 	= null;
	private String PAUSED_SETUP  	= null;
	
	private Storage storage ;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		loadSettings();
		setMenuLayout();
		
		boolean canResume = canContinueMatch();
		enableContinueButton(canResume);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle state) {
		saveMatch();
		super.onSaveInstanceState(state);
	}
	
	@Override
	protected void onDestroy() {
		log.v("MatchActivity was  destroyed");
		saveMatch();
		//match.removeEventListener(Event.MOVE_DONE, this);
		//match.removeEventListener(Event.CHECK, this);
		super.onDestroy();
	}
	
	public void onStartNewGameButtonPress(View v){	
		try {
			Match.MatchBuilder builder = getMatchBuilder(DEFAULT_SETUP);
			initializeMatch(builder);
			log.v("New match created. "+ match.getCurrentPlayer().getName() + " is moving.");
		} catch (Exception e) {
			log.e("Could not start a new match.");
			log.e(e.getClass() + ": " + e.getMessage());
		}
	}
	
	public void onResumeGameButtonPress(View v){
		if (canContinueMatch()){
			try {
				Match.MatchBuilder builder = getMatchBuilder(PAUSED_SETUP);
				initializeMatch(builder);
			} catch (Exception e) {
				log.e("Could not resume match from a setup.");
				log.e(e.getClass() + ": " + e.getMessage());
			}
		} else {
			log.e("Cannot continue match");
		}
	}
	
	public void onLoadGameButtonPress(View v){
		AlertDialog.Builder first = new AlertDialog.Builder(
               MatchActivity.this);
        first.setIcon(R.drawable.ic_launcher);
        first.setTitle("Select a save");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                MatchActivity.this,
                android.R.layout.select_dialog_singlechoice);
        
        String[] filenames = storage.getFiles();
        for (String name : filenames){
        	arrayAdapter.add(Storage.removeExt(name));
        }
        
        first.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        first.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        final String name = arrayAdapter.getItem(which);
                       
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                MatchActivity.this);
                        builderInner.setMessage(name);
                        builderInner.setTitle("Loading the game...");
                        final AlertDialog second = builderInner.create();
                        
                        second.show();
                        
                        Handler handler = null;
                        handler = new Handler(); 
                        handler.postDelayed(new Runnable(){ 
                             public void run(){
                                 dialog.cancel();
                                 dialog.dismiss();
                                 
                                 second.cancel();
                                 second.dismiss();
                                 
                                 attemptToLoadGame(name);
                             }
                        }, 1000);
                    }
                });
        first.show();
	}
	
	@SuppressLint("DefaultLocale")
	public void onSaveGameButtonPress(View v){
		if (match != null){

			final EditText input = new EditText(this);
			input.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
			input.setFilters(new InputFilter[]{getInputFilter()}); 
			
			final AlertDialog dialog = new AlertDialog.Builder(this)
		    .setTitle("Saving game state...")
		    .setMessage("Please choose a name for your save")
		    .setView(input)
		    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface first, int whichButton) {
		        	
		        	final String name =	input.getText().toString()
		        			.trim().toLowerCase(Locale.ENGLISH);
		        	
		        	if (storage.exists(name)){
		        		
		    			new AlertDialog.Builder(MatchActivity.this)
		    		    .setTitle("Save already exists")
		    		    .setMessage("Do you wish to overwrite the save: " + name)
		    		    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		    		    	@Override
		    		    	public void onClick(DialogInterface inner, int which) {
		    		    		attemptToSaveGame(name);
		    		    	}
		    		    })
		    		    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    		    	public void onClick(DialogInterface inner, int whichButton) {
		    		    		inner.cancel();
		    		    	}
		    		    }).show();
		    			
		        	} else {
		        		attemptToSaveGame(name);
		        	}
		        }
		    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            // Do nothing.
		        }
		    }).create();
			
		    dialog.show();
		}
	}

	private InputFilter getInputFilter(){
		return new InputFilter() { 
			public CharSequence filter(CharSequence source, int start, int end, 
					Spanned dest, int dstart, int dend) { 
				for (int i = start; i < end; i++) { 
					if (!Character.isLetterOrDigit(source.charAt(i))) { 
						return ""; 
					} 
				} 
				return null; 
			} 
		}; 
	}
	
	private void attemptToSaveGame(String name){
		final String filename = name.concat(".txt");
		final String saveSuccess = "Game saved: " + name ;
		final String noSaveSuccess = "Could not save game" ;
		
		String message ;
		try {
			if (storage.save(filename, match.save())){
				message = saveSuccess ;
			} else {
				message = noSaveSuccess ;
			}
		} catch (Exception e){
			message = noSaveSuccess ;
		}
		showToast(message);
	}
	
	private void attemptToLoadGame(String name){
		if (storage.exists(name)){
			String setup = storage.read(name);
			try {
				Match.MatchBuilder builder = getMatchBuilder(setup);
				initializeMatch(builder);
				log.v("Loaded a saved match. "+ match.getCurrentPlayer().getName() + " is moving.");
			} catch (Exception e) {
				log.e("Could load a saved match.");
				log.e(e.getClass() + ": " + e.getMessage());
			}
		}
	}
	
	private void showToast(String message){
		Toast toast = 
			Toast.makeText(MatchActivity.this, 
				message, 
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	private void setMatchLayout(){
		setContentView(R.layout.match);
		this.boardview = (BoardView) findViewById(R.id.board);
		
		if (this.boardview == null){
			log.v("BoardView is not instantiated");
			return ;
		}
		
		boardview.setMatch(match);
		boardview.update();
	}
	
	private void setMenuLayout(){
		setContentView(R.layout.matchmaking_menu);
		this.cont = (Button) findViewById(R.id.continueGameBtn);
		cont.setEnabled(match != null);
		cont.setClickable(match != null);
	}
	
	private Match.MatchBuilder getMatchBuilder(String setup) throws JSONException {
		Match.MatchBuilder builder = new Match.MatchBuilder();
		builder
			.setPlayers(
				new Player("White", 0),
				new Player("Black", 0))
			.loadSetup(new JSONObject(setup));
		return builder ;
	}
	
	private void initializeMatch(Match.MatchBuilder builder){
		this.match = builder.create();
		this.match.addEventListener(Event.MOVE_DONE, this);
		this.match.addEventListener(Event.CHECK, this);
		setMatchLayout();
		updatePlayer();
	}
	
	private boolean saveMatch(){
		if (match != null){
			boolean result = false ;
			try {
				result = settings
							.edit()
							.putString(PAUSED_GAME_SETTING, match.save())
							.commit();
				return result ;
			} catch (Exception e){
				log.v("Could not save game");
			}
		}
		return false ;
	}
	
	private void updatePlayer(){
		TextView team = (TextView) findViewById(R.id.team);
		
		Player player = match.getCurrentPlayer();
		
		team.setBackgroundColor(Color.GRAY);
		team.setTextColor(player.getColor());
		team.setText(player.getName());
	}
	
	public void onRotatePiecesButtonPress(View v){
		boardview.rotate();
	}
	
	private void loadSettings(){
		storage 	= new Storage(this);
		settings 	= getSharedPreferences(PREFS_NAME, 0);
		DEFAULT_SETUP 	= JSONReader.read(this, R.raw.setup);
		PAUSED_SETUP 	= settings.getString(PAUSED_GAME_SETTING, null);
	}
	
	private boolean canContinueMatch(){
		return (PAUSED_SETUP != null);
	}
	
	private void enableContinueButton(boolean state){
		cont.setEnabled(state);
		cont.setClickable(state);
	}

	@Override
	public void onEvent(Event event) {
		switch (event){
			case MOVE_DONE:
				updatePlayer();
				break;
			case CHECK:
				log.v("Check");
				break;
		}
	}
}
