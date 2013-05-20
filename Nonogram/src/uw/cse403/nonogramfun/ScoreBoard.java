package uw.cse403.nonogramfun;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

public class ScoreBoard extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scoreboard_screen);
		setTitle("Score Board");
		Log.i("scoreboard.java", "on create");
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
	public void scoreSmall(View view){
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    // Get the layout inflater
	    LayoutInflater inflater = this.getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(inflater.inflate(R.layout.scoreboard_layout, null))
	    	   .setTitle("Score Board (Small Puzzle)")
	           .setNeutralButton(R.string.scoreboard_acknowledgement, null);
	    builder.create();
	    builder.show();
	}
	
	public void scoreMedium(View view){
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    // Get the layout inflater
	    LayoutInflater inflater = this.getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(inflater.inflate(R.layout.scoreboard_layout, null))
	    	   .setTitle("Score Board (Medium Puzzle)")
	           .setNeutralButton(R.string.scoreboard_acknowledgement, null);
	    builder.create();
	    builder.show();
	}
	
	public void scoreLarge(View view){
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    // Get the layout inflater
	    LayoutInflater inflater = this.getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(inflater.inflate(R.layout.scoreboard_layout, null))
	    	   .setTitle("Score Board (Large Puzzle)")
	           .setNeutralButton(R.string.scoreboard_acknowledgement, null);
	    builder.create();
	    builder.show();
	}
}