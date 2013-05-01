package uw.cse403.nonogramfun;

import android.app.Activity;
import android.content.Context;
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

public class CreateGame extends Activity implements OnClickListener{
	Button[][] buttons = new Button[5][5] ;
	//boolean selected = false;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_game_menu);
		
		Canvas ca = new Canvas();
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setStyle(Style.STROKE);
		p.setStrokeWidth(5);
		
		
		TableLayout layout = new TableLayout (this);
		layout.setLayoutParams( new TableLayout.LayoutParams(4,5) );

		  
		  
		layout.setPadding(0,0,50,50);

		for (int f = 0; f < 5; f++) {
			TableRow tr = new TableRow(this);
			for (int c = 0; c < 5; c++) {
	        	buttons[f][c] = new Cell(this);
	        	buttons[f][c].setBackgroundColor(Color.BLACK);	   
	        	buttons[f][c].setOnClickListener(this);
	        	Log.i("button width[" + f + "] [" + c + "] = ", ("" + buttons[f][c].getWidth()));
	        	Log.i("button height[" + f + "] [" + c + "] = ", ("" + buttons[f][c].getHeight()));
	        	//ca.drawLine(startX, startY, stopX, stopY, p);
	        	tr.addView(buttons[f][c],30,30);
			}
			layout.addView(tr);
		}
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
		if(((Cell)arg0).getSelectVal())
			((Cell) arg0).setBackgroundColor(Color.RED);
		else
			((Cell) arg0).setBackgroundColor(Color.YELLOW);
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