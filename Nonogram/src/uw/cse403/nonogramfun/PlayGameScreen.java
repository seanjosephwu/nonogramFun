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
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class PlayGameScreen extends Activity implements OnClickListener{
	private int dimension;
	private Integer[][] gameArray;
	private Button[][] buttons;
	private String[] rowHint;
	private String[] columnHint;
	private Difficulty puzzleDifficulty;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_game_screen);
		
		Bundle bundle = getIntent().getExtras();
		dimension = bundle.getInt("size");
		gameArray = new Integer[dimension][dimension];
		
		fetchPuzzle();
		
		// dimension + 1 for the number field at the top and left sides
		buttons = new Button[dimension + 1][dimension + 1];

		TableLayout layout = new TableLayout (this);
		layout.setLayoutParams( new TableLayout.LayoutParams(dimension-1,dimension) );
		layout.setPadding(50,50,50,50);

		//create the empty game board and the number fields
		for (int i = 0; i < dimension + 1; i++) {
			TableRow tr = new TableRow(this);
			for (int j = 0; j < dimension + 1; j++) {
				buttons[i][j] = new Cell(this);
				if(i == 0 && j == 0){
					buttons[i][j].setBackgroundColor(Color.TRANSPARENT);
				}
				else if(j == 0){
					// vertical number field
		        	buttons[i][j].setBackgroundColor(Color.TRANSPARENT);
					//buttons[i][j].setText("1 2");
					buttons[i][j].setTextSize(8);
				}else if(i == 0){
					// horizontal number field
		        	buttons[i][j].setBackgroundColor(Color.TRANSPARENT);
					//buttons[i][j].setText("1\n2");
					buttons[i][j].setTextSize(8);
				}else{
		        	if((i % 2 == j % 2)){
		        		buttons[i][j].setBackgroundColor(Color.LTGRAY);
		        	}
		        	else{
		        		buttons[i][j].setBackgroundColor(Color.WHITE);
		        	}
		        	buttons[i][j].setOnClickListener(this);
				}
				
				tr.addView(buttons[i][j],50,50);
			}
			layout.addView(tr);
		}
		
		Button submitButton = new Button(this);
		layout.addView(submitButton);
		submitButton.setText("Submit");
		super.setContentView(layout);
		
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
					Log.i("row " , Integer.toString(puzzle.getNonoPicRowSize()));
					Log.i("col ", Integer.toString(puzzle.getNonoPicColSize()));
					for(int i = 0; i < puzzle.getNonoPicRowSize(); i++){
						for(int j = 0; j < puzzle.getNonoPicColSize(); j++){
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
			e.printStackTrace();
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
					//if already at the last cell of the row print out the 
					//count anyway
					if (y == dimension - 1){
						rowHint[x] += count;
					}
				// If the game cell is not filled in...
				} else {
					// If we reached the end of a set of filled cells and 
					// it's not the first cell in the row...
					if (emptyCell == false && start == false){
						rowHint[x] += count + " ";
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
					//if already at the last cell of the row print out the 
					//count anyway
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
	
	//inner class for cell
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
}
