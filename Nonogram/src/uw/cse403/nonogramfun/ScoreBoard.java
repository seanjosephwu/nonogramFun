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
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * CSE 403 AA
 * Project Nonogram: FrontEnd
 * @author  Xiaoxia Jian
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
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
	
	private void getScore(final Difficulty d){
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					Log.i("get score", "before");
					nonoScoreBoard = NonoClient.getScoreBoard(d);
					Log.i("get score", "after");

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
	
	private LinearLayout setUpScoreBoard(Difficulty d, LinearLayout board) {
		getScore(d);
		PriorityQueue<NonoScore> top = sortingNonoScores();
		while(!top.isEmpty()) {
			NonoScore least = top.remove();
			
			LayoutInflater inflater = this.getLayoutInflater();
			View rank = inflater.inflate(R.layout.score_board_rank, null);
			TextView name = (TextView) rank.findViewById(R.id.rank_name);
			TextView score = (TextView) rank.findViewById(R.id.rank_score);
			String preName = least.playerName;
			String[] preNames = preName.split("\"");		
			//Log.i("rank", least.playerName + " : " + least.score);
			name.setText(preNames[1]);
			int hour = least.score / 3600;
			int min = least.score % 3600/ 60;
			int sec = least.score % 3600 % 60;
			String hourDisplay;
			String minDisplay;
			String secDisplay;
			if(hour < 10 ) {
				hourDisplay = "0" + hour;
			} else{
				hourDisplay = "" + hour;
			}
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
			String scoreDisplay = String.format("%2s: %2s: %2s", hourDisplay, minDisplay, secDisplay);
			score.setText(scoreDisplay);
			board.addView(rank);
		}
		
		return board;
	}
	
	public class minScoreCompare implements  Comparator<NonoScore> {

		public minScoreCompare() {}
		@Override
		public int compare(NonoScore lhs, NonoScore rhs) {
			if (lhs.score > rhs.score) 
				return -1;
			else if (lhs.score < rhs.score)
				return 1;
			return 0;
		}
		
	}
	
	public class maxScoreCompare implements  Comparator<NonoScore> {
		
		public maxScoreCompare() {}
		@Override
		public int compare(NonoScore lhs, NonoScore rhs) {
			if (lhs.score < rhs.score) 
				return -1;
			else if (lhs.score > rhs.score)
				return 1;
			return 0;
		}
		
	}
}