package uw.cse403.nonogramfun;

import java.io.IOException;
import java.net.UnknownHostException;

import org.json.JSONException;

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
	//boolean selected = false;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game_screen);

		Bundle bundle = getIntent().getExtras();
		dimension = bundle.getInt("size");
		
		buttons = new Button[dimension][dimension];
		
//		Canvas ca = new Canvas();
//		Paint p = new Paint();
//		p.setAntiAlias(true);
//		p.setStyle(Style.STROKE);
//		p.setStrokeWidth(5);


//	    TestCanvas tcanvas=new TestCanvas(this);
//	    Canvas canvas = new Canvas();
//		tcanvas.draw(canvas);

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
	        	Log.i("button width[" + i + "] [" + j + "] = " + "" + buttons[i][j].getWidth(), ("" + buttons[i][j].getWidth()));
	        	Log.i("button height[" + i + "] [" + j + "] = " + "" + buttons[i][j].getWidth(), ("" + buttons[i][j].getHeight()));
	        	tr.addView(buttons[i][j],50,50);
			}
			layout.addView(tr);
		}
		
		Button submitButton = new Button(this);
		submitButton.setText("Submit");
		submitButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				final int[][] gameArray = new int[dimension][dimension];
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
				}
				
				Thread thread = new Thread(new Runnable(){
				    @Override
				    public void run() {
						try {
							NonoClient.createPuzzle(gameArray, Integer.valueOf(Color.WHITE), "Puzzle 1");
							System.out.println("Puzzle Created!");
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("Puzzle Created!");
						
				    }
				});
				thread.start();
				try {
					thread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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