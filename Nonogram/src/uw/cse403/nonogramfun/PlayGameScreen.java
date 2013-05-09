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

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PlayGameScreen extends Activity implements OnClickListener{
	private int dimension;         //dimension is size for the clickable cells
	private Integer[][] gameArray; //store the gameArray get back from the server
	private View[][] buttons;      
	private String[] rowHint;
	private String[] columnHint;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_game_screen);
		
		Bundle bundle = getIntent().getExtras();
		dimension = bundle.getInt("size");
		gameArray = new Integer[dimension][dimension];
		
		fetchPuzzle();
		//parseGameRow();
		//parseGameColumn();
		
		// dimension + 1 for the number field at the top and left sides
		buttons = new View[dimension + 1][dimension + 1];
		
		TableLayout layout = new TableLayout (this);
		layout.setLayoutParams( new TableLayout.LayoutParams());
		layout.setPadding(50,50,50,50);

		 HorizontalScrollView scrollView = new  HorizontalScrollView(this);
		
		//create the empty game board and the number fields
		for (int i = 0; i < dimension + 1; i++) {
			TableRow tr = new TableRow(this);
			for (int j = 0; j < dimension + 1; j++) {
				
				if (i == 0 || j == 0) 
					buttons[i][j] = new TextView(this);
				else
					buttons[i][j] = new Cell(this);
				
				if(i == 0 && j == 0){
					//buttons[i][j].setBackgroundColor(Color.TRANSPARENT);
					TextView textview = (TextView) buttons[i][j];
					textview.setBackgroundColor(Color.TRANSPARENT);
					tr.addView(buttons[i][j],50,50);
				}
				else if(j == 0){
					// vertical number field
					TextView textview = (TextView) buttons[i][j];
		        	textview.setBackgroundColor(Color.TRANSPARENT);
		        	
		        	//textview.setText(columnHint[i]);
		        	textview.setText("1 2 3 4 5 6 7");
		        	
		        	textview.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		        	tr.addView(buttons[i][j],150,50);
				}else if(i == 0){
					// horizontal number field
					TextView textview = (TextView) buttons[i][j];
					textview.setBackgroundColor(Color.TRANSPARENT);
					
					//textview.setText(rowHint[j]);
					textview.setText("1\n2\n3\n4\n5\n6\n7");
					
					textview.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
					textview.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
					tr.addView(buttons[i][j],50,200);
				}else{
					Cell c = (Cell) buttons[i][j];
		        	if((i % 2 == j % 2)){
		        		c.setBackgroundColor(Color.LTGRAY);
		        		c.setOriginColor(Color.LTGRAY);
		        	}
		        	else{
		        		c.setBackgroundColor(Color.WHITE);
		        		c.setOriginColor(Color.WHITE);
		        	}
		        	c.setOnClickListener(this);
		        	tr.addView(buttons[i][j],50,50);
				}
				
			}
			layout.addView(tr);
		}
		
		Button submitButton = new Button(this);
		layout.addView(submitButton);
		submitButton.setText("Submit");
		scrollView.addView(layout);
		super.setContentView(scrollView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play_game_screen, menu);
		return true;
	}

	//pull a puzzle from the server database
	private void fetchPuzzle(){
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				Difficulty puzzleDifficulty;
				try {
					if (dimension == 5){
						puzzleDifficulty = Difficulty.EASY;
					} else if (dimension == 10) {
						puzzleDifficulty = Difficulty.MEDIUM;
					} else if (dimension == 14) {
						puzzleDifficulty = Difficulty.HARD;
					} else {
						puzzleDifficulty = Difficulty.UNKNOWN;
					}
					
					NonoPuzzle puzzle = NonoClient.getPuzzle(puzzleDifficulty);
					
					for(int i = 0; i < puzzle.getNonoPicColSize(); i++){
						for(int j = 0; j < puzzle.getNonoPicRowSize(); j++){
							gameArray[i][j] = puzzle.getColor(i, j);
						}
					}	
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
			//e.printStackTrace();
		}
	}
	
	private void parseGameRow(){
		for(int x = 0; x < dimension; x++){
			rowHint = new String[dimension];
			boolean emptyCell = false, start = true;
			rowHint[x] = "";
			int count = 0;
			for(int y = 0; y < dimension; y++){
				// If the game cell is filled in...
				if (gameArray[x][y] == Color.BLACK){
					emptyCell = false;
					count++;
					start = false;
					//if already at the last cell of the row store the count anyway
					if (y == dimension - 1){
						rowHint[x] += count;
					}
					
				// If the game cell is not filled in...
				} else {
					// If we reached the end of a set of filled cells and 
					// it's not the first cell in the row...
					if (emptyCell == false && start == false){
						//rowHint[x] += count + " ";
						rowHint[x] += count + "\n";
						count = 0;
						emptyCell = true;
					}
					// Otherwise we do nothing
				}
			}
		}
	}
	
	private void parseGameColumn(){
		for(int y = 0; y < dimension; y++){
			columnHint = new String[dimension];
			boolean emptyCell = false, start = true;
			columnHint[y] = "";
			int count = 0;
			for(int x = 0; x < dimension; x++){
				// If the game cell is filled in...
				if (gameArray[x][y] == Color.BLACK){
					emptyCell = false;
					count++;
					start = false;
					//if already at the last cell of the row store the count anyway
					if (x == dimension - 1){
						columnHint[y] += count;
					}
				// If the game cell is not filled in...
				} else {
					// If we reached the end of a set of filled cells and 
					// it's not the first cell in the row...
					if (emptyCell == false && start == false){
						columnHint[x] += count + " ";
						count = 0;
						emptyCell = true;
					}
					// Otherwise we do nothing
				}
			}
		}
	}
	
	//inner class for cell, which has state that change based on each 
	//click.
	class Cell extends Button {
		//0 : unmark
		//1 : mark
		//2 : question mark
		public int state;  
		public int origin; 
		
		public Cell(Context context) {
			super(context);
			state = 0; 
		}
		
		//set the state if there is a click
		public void setState(){
			if(state < 2)
	    		state++;
	    	else
	    		state = 0;
		}
		
		public int getState(){
			return state;
		}
		
		//store the origin cell color before any action
		public void setOriginColor(int color){
			origin = color;
		}
		
		public void setColor(int state){
			if(state == 0){
				this.setText("");
				this.setBackgroundColor(origin);
			}else if(state == 1){
				this.setText("");
				this.setBackgroundColor(Color.BLACK);
			}else{
				this.setBackgroundColor(origin);
				this.setText("?");
				this.setTextColor(Color.BLUE);
			}
		}
    }
	
	@Override
	public void onClick(View view) {
		Cell cell = (Cell)view;
		cell.setState();
		cell.setColor(cell.getState());
	}
}
