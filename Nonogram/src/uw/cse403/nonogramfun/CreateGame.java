package uw.cse403.nonogramfun;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;

public class CreateGame extends Activity implements OnClickListener{
	Button[][] buttons = new Button[5][5] ;
	boolean selected = false;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_game_menu);
		Log.i("create a game", "on create");
		
		
		TableLayout layout = new TableLayout (this);
		layout.setLayoutParams( new TableLayout.LayoutParams(4,5) );

		  
		  
		layout.setPadding(20,20,20,20);

		for (int f=0; f<5; f++) {
			TableRow tr = new TableRow(this);
			tr.setPadding(0, 0, 0, 5);
			for (int c=0; c<5; c++) {
	        	buttons[f][c] = new Button (this);
	        	buttons[f][c].setBackgroundColor(Color.BLACK);
	        	buttons[f][c].setOnClickListener(this);
	        	buttons[f][c].setPadding(5, 5, 5, 5);
	        	tr.addView(buttons[f][c],50, 50);
			}
			layout.addView(tr);
		}

		super.setContentView(layout); 
		  
	}
	
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(selected == true){
			selected = false;
			//((Button) arg0).setText("*");
			((Button) arg0).setBackgroundColor(Color.BLACK);
			((Button) arg0).setTextColor(Color.BLACK);
		}
		else{
			selected = true;
			//((Button) arg0).setText("*");
			((Button) arg0).setBackgroundColor(Color.WHITE);
			((Button) arg0).setTextColor(Color.WHITE);
		}
	}
    
    
    
}