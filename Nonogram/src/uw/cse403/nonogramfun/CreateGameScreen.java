package uw.cse403.nonogramfun;

/**
 * CSE 403 AA
 * Project Nonogram: Frontend
 * @author  Xiaoxia Jian, Huiqi Wang, Renhao Xie, Alan Loh
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */

import java.io.IOException;
import java.net.UnknownHostException;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;


public class CreateGameScreen extends Activity implements OnClickListener{
	private Button[][] buttons;
	private int dimension;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game_screen);

		Bundle bundle = getIntent().getExtras();
		dimension = bundle.getInt("size");
		
		buttons = new Button[dimension][dimension];

		TableLayout layout = new TableLayout (this);
		layout.setLayoutParams( new TableLayout.LayoutParams(dimension-1,dimension) );
		
		layout.setPadding(50,50,50,50);

		for (int i = 0; i < dimension; i++) {
			TableRow tr = new TableRow(this);
			for (int j = 0; j < dimension; j++) {
	        	buttons[i][j] = new Cell(this);
	        	buttons[i][j].setWidth(10);
	        	buttons[i][j].setHeight(10);
	        	if((i % 2 == j % 2)){
	        		buttons[i][j].setBackgroundColor(Color.LTGRAY);
	        	}
	        	else{
	        		buttons[i][j].setBackgroundColor(Color.WHITE);
	        	}
	        	buttons[i][j].setOnClickListener(this);
	        	tr.addView(buttons[i][j],50,50);
			}
			layout.addView(tr);
		}
		
		Button submitButton = new Button(this);
		submitButton.setText("Submit");
		submitButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				final Integer[][] gameArray = new Integer[dimension][dimension];
				boolean isEmpty = true;

				for (int i = 0; i < dimension; i++) {
					for (int j = 0; j < dimension; j++) {
						if(buttons[i][j].getText().toString().equalsIgnoreCase("x")){
							gameArray[i][j] = Color.WHITE;
							isEmpty = false;
						} else {
							gameArray[i][j] = Color.BLACK;
						}
					}
				}

				if ( isEmpty ){
					// Alert Dialog popup box
					AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
					alertDialog.setTitle("Error");
					alertDialog.setMessage("Please do not submit an empty game");
					// -1 = BUTTON_POSITIVE = a positive button?
					alertDialog.setButton(-1, "OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// here you can add functions
						}
					});
					alertDialog.show();
				} else {

					AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
					alertDialog.setTitle("Submit Success");
					alertDialog.setMessage("Puzzle Created!");
					// -1 = BUTTON_POSITIVE = a positive button?
					alertDialog.setButton(-1, "OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// here you can add functions
						}
					});
					Thread thread = new Thread(new Runnable(){
						@Override
						public void run() {
							try {
								NonoClient.createPuzzle(gameArray, Integer.valueOf(Color.WHITE), "Puzzle 1");
							} catch (UnknownHostException e) {
								//e.printStackTrace();
							} catch (IOException e) {
								//e.printStackTrace();
							} catch (JSONException e) {
								//e.printStackTrace();
							}

						}
					});
					thread.start();
					try {
						thread.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					alertDialog.show();
				}
			}
		}); 
		
		layout.addView(submitButton);
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
		if(((Cell)arg0).getSelectVal()){
			((Cell) arg0).setText("");	
		}
		else{
			((Cell) arg0).setText("X");
			((Cell) arg0).setTextColor(Color.BLUE);
		}
			
		((Cell) arg0).setSelectVal();
	}
    
    class Cell extends Button {
    	private boolean select;
		public Cell(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			select = false;
		}
    	public void setSelectVal(){
    		if(select)
    			select = false;
    		else
    			select = true;
    	}
    	
    	public boolean getSelectVal(){
    		return select;
    	}
    	
    }
    
}