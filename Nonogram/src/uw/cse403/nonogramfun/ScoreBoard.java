/**
 * CSE 403 AA
 * Project Nonogram: FrontEnd
 * @author  Xiaoxia Jian
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */


package uw.cse403.nonogramfun;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import org.json.JSONException;
import uw.cse403.nonogramfun.enums.Difficulty;
import uw.cse403.nonogramfun.network.NonoClient;
import uw.cse403.nonogramfun.nonogram.NonoScore;
import uw.cse403.nonogramfun.nonogram.NonoScoreBoard;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * ScoreBoard Display rankings depends on difficulties that user wants to see.
 * It displays up to 10 rankings. Less time is used in solving the game on the 
 * given level has higher ranking.
 */
public class ScoreBoard extends Activity {
	NonoScoreBoard nonoScoreBoard;
	List<NonoScore> nonoScores = new ArrayList<NonoScore>();
	List<NonoScore> topTen = new ArrayList<NonoScore>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scoreboard_menu);
		setTitle("Score Board");
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /**
     * The score board for small game
     * @param view
     */
	public void scoreSmall(View view){
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    // Get the layout inflater
	    LayoutInflater inflater = this.getLayoutInflater();

		View layout = inflater.inflate(R.layout.scoreboard_layout, null);
	    LinearLayout scoreboard = (LinearLayout) layout.findViewById(R.id.score_board);
	    
	    scoreboard = setUpScoreBoard(Difficulty.EASY, scoreboard);
	    
	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(scoreboard)
	    	   .setTitle("Score Board (Small Puzzle)")
	           .setNeutralButton(R.string.scoreboard_acknowledgement, null);
	    builder.create();
	    builder.show();
	}
	
	/**
	 * The score board for medium game
	 * @param view
	 */
	public void scoreMedium(View view){
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    // Get the layout inflater
	    LayoutInflater inflater = this.getLayoutInflater();

		View layout = inflater.inflate(R.layout.scoreboard_layout, null);
	    LinearLayout scoreboard = (LinearLayout) layout.findViewById(R.id.score_board);
	    
	    scoreboard = setUpScoreBoard(Difficulty.MEDIUM, scoreboard);

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(scoreboard)
	    	   .setTitle("Score Board (Medium Puzzle)")
	           .setNeutralButton(R.string.scoreboard_acknowledgement, null);
	    builder.create();
	    builder.show();
	}
	
	/**
	 * The score board for large game
	 * @param view
	 */
	public void scoreLarge(View view){
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    // Get the layout inflater
	    LayoutInflater inflater = this.getLayoutInflater();

		View layout = inflater.inflate(R.layout.scoreboard_layout, null);
	    LinearLayout scoreboard = (LinearLayout) layout.findViewById(R.id.score_board);
	    
	    scoreboard = setUpScoreBoard(Difficulty.HARD, scoreboard);

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(scoreboard)
	    	   .setTitle("Score Board (Large Puzzle)")
	           .setNeutralButton(R.string.scoreboard_acknowledgement, null);
	    builder.create();  
	    builder.show();
	}
	
	/** 
	 * Get the score from the server
	 * @param d, the difficulty level
	 */
	private void getScore(final Difficulty d){
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				try {			
					nonoScoreBoard = NonoClient.getScoreBoard(d);
				} catch (UnknownHostException e) {					
				} catch (IOException e) {			
				} catch (JSONException e) {
				} catch (Exception e) {
				}
			}
		});
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			
		}
	}
	
	/**
	 * Save the scores in order
	 * @return
	 */
	private PriorityQueue<NonoScore> sortingNonoScores() {
		PriorityQueue<NonoScore> minScoreQue = new PriorityQueue<NonoScore>(11, new minScoreCompare());
		PriorityQueue<NonoScore> maxScoreQue = new PriorityQueue<NonoScore>(11, new maxScoreCompare());
		if(nonoScoreBoard != null){
			Iterator<NonoScore> scoreIter = nonoScoreBoard.getIterator();
			while(scoreIter.hasNext()) {
				NonoScore next = scoreIter.next();
				if (minScoreQue.size() < 10) {
					minScoreQue.add(next);
					maxScoreQue.add(next);
				} else {
					NonoScore most = maxScoreQue.peek();
					if (most.compareTo(next) > 0) {
						maxScoreQue.poll();
						minScoreQue.remove(most);
						minScoreQue.add(next);
						maxScoreQue.add(next);
					}
				}
			}
		}
		return minScoreQue;
	}
	
	/**
	 * Set up scoreboard layout before inject the the dialog 
	 * @param d difficulty level 
	 * @param board
	 * @return a setup linearlayout
	 */
	private LinearLayout setUpScoreBoard(Difficulty d, LinearLayout board) {
		getScore(d);
		PriorityQueue<NonoScore> top = sortingNonoScores();
		int color_stripe = 0;
		while(!top.isEmpty()) {
			color_stripe++;
			NonoScore least = top.remove();
			
			LayoutInflater inflater = this.getLayoutInflater();
			View rank = inflater.inflate(R.layout.score_board_rank, null);
			TextView name = (TextView) rank.findViewById(R.id.rank_name);
			TextView score = (TextView) rank.findViewById(R.id.rank_score);
		    name.setTextSize(14);
		    score.setTextSize(14);    
			
			//strip the quotation mark from the name getback from backend
			String preName = least.playerName;
			String[] preNames = preName.split("\"");	
			name.setText(preNames[1]);
			
			//cover total second from backend to min:sec form
			int min = least.score / 60;
			int sec = least.score % 60;
			String minDisplay;
			String secDisplay;

			if(sec < 10 ) {
				secDisplay = "0" + sec;
			} else{
				secDisplay = "" + sec;
			}
			if(min < 10) {
				minDisplay = "0" + min;
			} else {
				minDisplay = "" + min;
			}
			
			String scoreDisplay = String.format("%2s: %2s", minDisplay, secDisplay);
			score.setText(scoreDisplay);
			
			//color code the ranking 
			if(color_stripe % 2 == 1){
				name.setBackgroundColor(Color.GRAY);
				score.setBackgroundColor(Color.GRAY);
			}
			board.addView(rank);
		}
		
		return board;
	}
	
	/**
	 * Get the minimum score
	 */
	public class minScoreCompare implements  Comparator<NonoScore> {

		public minScoreCompare() {}
		@Override
		public int compare(NonoScore lhs, NonoScore rhs) {
			if (lhs.score < rhs.score) 
				return -1;
			else if (lhs.score > rhs.score)
				return 1;
			return 0;
		}
		
	}
	
	/**
	 * Get the maximum score
	 */
	public class maxScoreCompare implements  Comparator<NonoScore> {
		
		public maxScoreCompare() {}
		@Override
		public int compare(NonoScore lhs, NonoScore rhs) {
			if (lhs.score > rhs.score) 
				return -1;
			else if (lhs.score < rhs.score)
				return 1;
			return 0;
		}
		
	}
}