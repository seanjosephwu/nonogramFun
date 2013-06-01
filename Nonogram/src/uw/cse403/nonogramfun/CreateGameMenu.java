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

public class CreateGameMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game_menu);
		setTitle("Create A Game");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_game_menu, menu);
		return true;
	}

	
	public void CreateGameSmallScreen(View view) {
		Intent i = new Intent(this, CreateGameScreen.class);
		i.putExtra("size", 5);
		i.putExtra("test", false);
		startActivity(i);
	}

	public void CreateGameMediumScreen(View view) {
		Intent i = new Intent(this, CreateGameScreen.class);
		i.putExtra("size", 10);
		i.putExtra("test", false);
		startActivity(i);
	}

	public void CreateGameLargeScreen(View view) {
		Intent i = new Intent(this, CreateGameScreen.class);
		i.putExtra("size", 14);
		i.putExtra("test", false);
		startActivity(i);
	}

}
