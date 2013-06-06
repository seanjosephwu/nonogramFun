package uw.cse403.nonogramfun;
/**
 * CSE 403 AA
 * Project Nonogram: FrontEnd
 * @author  Xiaoxia Jian
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import uw.cse403.nonogramfun.enums.Difficulty;
import uw.cse403.nonogramfun.network.NonoClient;
import uw.cse403.nonogramfun.nonogram.NonoScore;
import uw.cse403.nonogramfun.nonogram.NonoScoreBoard;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
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
     * Display scoreboard according to different difficulty levels
     * @param view
     */
	public void score(View view){
		boolean smallSB = false;
		boolean mediumSB = false;
		boolean largeSB = false;
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    // Get the layout inflater
	    LayoutInflater inflater = this.getLayoutInflater();

		View layout = inflater.inflate(R.layout.scoreboard_layout, null);
	    LinearLayout scoreboard = (LinearLayout) layout.findViewById(R.id.score_board);
	    
	    Button btn = (Button) view;
	    CharSequence btnName = btn.getText();
	    Log.i("btnName", btnName.toString());
	    if(btnName.equals("Small (5x5)")){
	    	scoreboard = setUpScoreBoard(Difficulty.EASY, scoreboard);
	    	smallSB = true;
	    }
	    else if(btnName.equals("Medium (10x10)")){
	    	scoreboard = setUpScoreBoard(Difficulty.MEDIUM, scoreboard);
	    	mediumSB = true;
	    }
	    else if (btnName.equals("Large (14x14)")){
	    	scoreboard = setUpScoreBoard(Difficulty.HARD, scoreboard);
	    	largeSB = true;
	    }
	    	
	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(scoreboard)
	           .setNeutralButton(R.string.scoreboard_acknowledgement, null);
	    if(smallSB)
	    	builder.setTitle("Score Board (Small)");
	    if(mediumSB)
	    	builder.setTitle("Score Board (Medium)");
	    if(largeSB)
	    	builder.setTitle("Score Board (Large)");
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
	/*
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
	*/
	/**
	 * Set up scoreboard layout before inject the the dialog 
	 * @param d difficulty level 
	 * @param board
	 * @return a setup linearlayout
	 */
	private LinearLayout setUpScoreBoard(Difficulty d, LinearLayout board) {
		getScore(d);
		
		//store nonoScore getting from backend
		ArrayList<NonoScore> ns = new ArrayList<NonoScore>();
		Iterator<NonoScore> itr = nonoScoreBoard.getIterator();
		while(itr.hasNext()){
			ns.add(itr.next());
		}
		
		int size = ns.size();
		int showing;
		if(size < 10)
			showing = size;
		else
			showing = 10;
		
		for(int i = 0; i < showing; i++){	
			LayoutInflater inflater = this.getLayoutInflater();
			View rank = inflater.inflate(R.layout.score_board_rank, null);
			TextView name = (TextView) rank.findViewById(R.id.rank_name);
			TextView score = (TextView) rank.findViewById(R.id.rank_score);
			
			//setting font size for name and score
		    name.setTextSize(16);
		    score.setTextSize(16);    
		    
			//strip the quotation mark from the name getback from backend
			String preName = ns.get(i).playerName;
			String[] preNames = preName.split("\"");	
			name.setText(preNames[1]);
			
			//cover total second from backend to min:sec form
			int min = ns.get(i).score / 60;
			int sec = ns.get(i).score % 60;
			String minDisplay;
			String secDisplay;
			
			//make sure it has uniform format [00:00]
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
			if(i % 2 == 1){
				name.setBackgroundColor(Color.GRAY);
				score.setBackgroundColor(Color.GRAY);
			}
			board.addView(rank);
		}
		return board;
	}
}