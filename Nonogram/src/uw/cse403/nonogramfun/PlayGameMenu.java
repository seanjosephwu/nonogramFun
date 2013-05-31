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
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class PlayGameMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_game_menu);
		setTitle("Play A Game");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play_game_menu, menu);
		return true;
	}

	public void PlayGameSmallScreen(View view) {
		Intent i = new Intent(this, PlayGameScreen.class);
		i.putExtra("size", 5);
		i.putExtra("test", false);
		startActivity(i);
	}

	public void PlayGameMediumScreen(View view) {
		Intent i = new Intent(this, PlayGameScreen.class);
		i.putExtra("size", 10);
		i.putExtra("test", false);
		startActivity(i);
	}

	public void PlayGameLargeScreen(View view) {
		Intent i = new Intent(this, PlayGameScreen.class);
		i.putExtra("size", 14);
		i.putExtra("test", false);
		startActivity(i);
	}
	
}
