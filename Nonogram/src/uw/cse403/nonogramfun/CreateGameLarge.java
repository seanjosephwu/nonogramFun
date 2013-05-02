package uw.cse403.nonogramfun;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CreateGameLarge extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game_large);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_game_large, menu);
		return true;
	}

}
