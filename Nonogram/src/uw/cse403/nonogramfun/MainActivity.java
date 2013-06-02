package uw.cse403.nonogramfun;

/**
 * CSE 403 AA
 * Project Nonogram: Frontend
 * @author  Xiaoxia Jian, Huiqi Wang, Renhao Xie, Alan Loh
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * The screen of main menu
 */
public class MainActivity extends Activity {

    public Button b;
    private MediaPlayer mPlayer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startMusicPlayer();
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
    /**
     * Set the background music
     */
	private void startMusicPlayer(){
		mPlayer = MediaPlayer.create(this, R.raw.humoresque);
        mPlayer.setLooping(true);
    	mPlayer.setVolume(100,100);
        mPlayer.start();
	}
	
	/**
	 * Go to the howToPlay screen
	 * @param view
	 */
	public void howToPlayScreen(View view) {
		Intent i = new Intent(this, HowToPlay.class);
		startActivity(i);
	}
	
	/**
	 * Go to the createGame menu
	 * @param view
	 */
	public void createGameScreen(View view) {
		Intent i = new Intent(this, CreateGameMenu.class);
		startActivity(i);
	}
    
	/**
	 * Go to the playGame menu
	 * @param view
	 */
	public void playGameScreen(View view) {
		Intent i = new Intent(this, PlayGameMenu.class);
		startActivity(i);
	}
	
	/**
	 * Go to the scoreboard menu
	 * @param view
	 */
	public void scoreBoardScreen(View view) {
		Intent i = new Intent(this, ScoreBoard.class);
		startActivity(i);
	}
	
	/**
	 * Stop the music when exit
	 */
	@Override
	public void onDestroy(){
		super.onDestroy();
		mPlayer.stop();
		mPlayer.release();
		mPlayer = null;
	}
}


