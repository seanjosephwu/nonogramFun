package uw.cse403.nonogramfun;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class HowToPlay3 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_how_to_play3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.how_to_play3, menu);
		return true;
	}

	public void next(View view) {
		Intent i = new Intent(this, PlayGameMenu.class);
		startActivity(i);
	}
	
	public void prev(View view) {
		Intent i = new Intent(this, HowToPlay.class);
		startActivity(i);
	}
}
