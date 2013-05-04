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

public class CreateGameSmall extends Activity implements OnClickListener{
	Button[][] buttons = new Button[5][5] ;
	//boolean selected = false;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game_small);

//		Canvas ca = new Canvas();
//		Paint p = new Paint();
//		p.setAntiAlias(true);
//		p.setStyle(Style.STROKE);
//		p.setStrokeWidth(5);


//	    TestCanvas tcanvas=new TestCanvas(this);
//	    Canvas canvas = new Canvas();
//		tcanvas.draw(canvas);

		TableLayout layout = new TableLayout (this);
		layout.setLayoutParams( new TableLayout.LayoutParams(4,5) );

		layout.setPadding(50,50,50,50);
//		layout.addView((View)tcanvas);

		for (int i = 0; i < 5; i++) {
			TableRow tr = new TableRow(this);
			for (int j = 0; j < 5; j++) {
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
	        	Log.i("button width[" + i + "] [" + j + "] = " + "" + buttons[i][j].getWidth(), ("" + buttons[i][j].getWidth()));
	        	Log.i("button height[" + i + "] [" + j + "] = " + "" + buttons[i][j].getWidth(), ("" + buttons[i][j].getHeight()));
	        	tr.addView(buttons[i][j],50,50);
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
    
    /*
    public class TestCanvas extends View {
        public TestCanvas(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);

    	for (int i = 0; i < 5; i++) {
    		
			canvas.drawLine(i * 10, 0, i * 10, 50, paint);
		}

		for (int j = 0; j < 5; j++) {
			canvas.drawLine(0, j * 10, 50, j * 10, paint);
		}
            canvas.save();
        }
    }
    */
}