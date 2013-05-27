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
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HowToPlay extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.how_to_play);
		Log.i("create a game", "on create");
		setTitle("How To Play");
	}
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        	// Inflate the menu; this adds items to the action bar if it is present.
        	getMenuInflater().inflate(R.menu.main, menu);
        	return true;
	}
	
	public void next(View view) {
		Intent i = new Intent(this, HowTwoPlay2.class);
		startActivity(i);
	}
	
	public void skip(View view) {
		Intent i = new Intent(this, PlayGameMenu.class);
		startActivity(i);
	}
}
