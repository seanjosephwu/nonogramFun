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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * The screen of main menu
 */
public class MainActivity extends Activity {

    public Button b;
    private MediaPlayer mPlayer;
    private boolean musicOn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addMuteMusicButton();
        startMusicPlayer();
        musicOn = true;
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
	 * Set the button to mute music background
	 */
	private void addMuteMusicButton(){
		final ImageView muteButton = (ImageView) findViewById(R.id.music);
		muteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if(musicOn){
					musicOn = false;
					mPlayer.pause();
					muteButton.setImageResource(R.drawable.mute);
				}else{
					musicOn = true;
					mPlayer.start();
					muteButton.setImageResource(R.drawable.on);
				}
				
			}
		});
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
