package uw.cse403.nonogramfun;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class CreateGameScreen extends Activity implements OnClickListener{
	private Button[][] buttons;
	private int dimension;
	private boolean submit = false;;
	//boolean selected = false;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game_screen);

		Bundle bundle = getIntent().getExtras();
		dimension = bundle.getInt("size");
		
		buttons = new Button[dimension][dimension];

		TableLayout layout = new TableLayout (this);
		layout.setLayoutParams( new TableLayout.LayoutParams(dimension-1,dimension) );
		
		layout.setPadding(50,50,50,50);
//		layout.addView((View)tcanvas);

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
				submit = true;

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

				int[][] gameArray = new int[dimension][dimension];
				for (int i = 0; i < dimension; i++) {
					for (int j = 0; j < dimension; j++) {
			        	if(buttons[i][j].getText().toString().equalsIgnoreCase("x")){
			        		gameArray[i][j] = Color.WHITE;
			        	} else {
			        		gameArray[i][j] = Color.BLACK;
			        	}
					}
				}
			}
			
		}); 
		layout.addView(submitButton);
		
		Button b = new Button(this);
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
			//((Cell) arg0).setBackgroundColor(Color.RED);
			((Cell) arg0).setText("");	
		}
		else{
			//((Cell) arg0).setBackgroundColor(Color.YELLOW);
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