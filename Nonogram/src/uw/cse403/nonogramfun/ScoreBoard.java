package uw.cse403.nonogramfun;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;

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

public class ScoreBoard extends Activity {
	NonoScoreBoard nonoScoreBoard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scoreboard_screen);
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

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(inflater.inflate(R.layout.scoreboard_layout, null))
	    	   .setTitle("Score Board (Small Puzzle)")
	           .setNeutralButton(R.string.scoreboard_acknowledgement, null);
	    builder.create();

		try {
			getScore();
		    if(nonoScoreBoard != null){
		    	/*
		    	PriorityQueue<NonoScore> queue = new PriorityQueue<NonoScore>(nonoScores.size());
		    	for(int i = 0; i < nonoScores.size(); i++){
		    		queue.add(nonoScores.get(i));
		    	}
		    	
		    	for(int i = 0; i < 3; i++){
		    		//remove the one with the least score(in seconds) 
		    		NonoScore ns = queue.remove();
		    		String viewIdName = "rank_name" + i;
		    		int resID_1 = getResources().getIdentifier(viewIdName, "id", null);
		    		TextView tv1 = (TextView) this.findViewById(resID_1);
		    		tv1.setText(ns.playerName);
		    		
		    		String viewIdScore = "rank_score" + i;
		    		int resID_2 = getResources().getIdentifier(viewIdScore, "id", null);
		    		TextView tv2 = (TextView) this.findViewById(resID_2);
		    		int score = ns.score;
		    		int min = score / 60;
		    		int sec = score % 60;
		    		tv2.setText(min + ";" + sec);
		    	}
		    	*/
		    	Log.i("done", "");
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    builder.show();
	}
	
	public void scoreMedium(View view){
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    // Get the layout inflater
	    LayoutInflater inflater = this.getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(inflater.inflate(R.layout.scoreboard_layout, null))
	    	   .setTitle("Score Board (Medium Puzzle)")
	           .setNeutralButton(R.string.scoreboard_acknowledgement, null);
	    builder.create();
	    //List<NonoScore> nonoScores;
		try {
			nonoScoreBoard = NonoClient.getScoreBoard(Difficulty.MEDIUM);
		    if(nonoScoreBoard != null){
		    /*
		    	PriorityQueue<NonoScore> queue = new PriorityQueue<NonoScore>(nonoScores.size());
		    	for(int i = 0; i < nonoScores.size(); i++){
		    		queue.add(nonoScores.get(i));
		    	}
		    	
		    	for(int i = 0; i < 3; i++){
		    		//remove the one with the least score(in seconds) 
		    		NonoScore ns = queue.remove();
		    		String viewIdName = "rank_name" + i;
		    		int resID_1 = getResources().getIdentifier(viewIdName, "id", null);
		    		TextView tv1 = (TextView) this.findViewById(resID_1);
		    		tv1.setText(ns.playerName);
		    		
		    		String viewIdScore = "rank_score" + i;
		    		int resID_2 = getResources().getIdentifier(viewIdScore, "id", null);
		    		TextView tv2 = (TextView) this.findViewById(resID_2);
		    		int score = ns.score;
		    		int min = score / 60;
		    		int sec = score % 60;
		    		tv2.setText(min + ";" + sec);
		    	}
		    	*/
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	    builder.show();
	}
	
	public void scoreLarge(View view){
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    // Get the layout inflater
	    LayoutInflater inflater = this.getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(inflater.inflate(R.layout.scoreboard_layout, null))
	    	   .setTitle("Score Board (Large Puzzle)")
	           .setNeutralButton(R.string.scoreboard_acknowledgement, null);
	    builder.create();
	    
		try {
			getScore();
		    if(nonoScoreBoard != null){
		    	/*
		    	PriorityQueue<NonoScore> queue = new PriorityQueue<NonoScore>(nonoScores.size());
		    	for(int i = 0; i < nonoScores.size(); i++){
		    		queue.add(nonoScores.get(i));
		    	}
		    	
		    	for(int i = 0; i < 3; i++){
		    		//remove the one with the least score(in seconds) 
		    		NonoScore ns = queue.remove();
		    		String viewIdName = "rank_name" + i;
		    		int resID_1 = getResources().getIdentifier(viewIdName, "id", null);
		    		TextView tv1 = (TextView) this.findViewById(resID_1);
		    		tv1.setText(ns.playerName);
		    		
		    		String viewIdScore = "rank_score" + i;
		    		int resID_2 = getResources().getIdentifier(viewIdScore, "id", null);
		    		TextView tv2 = (TextView) this.findViewById(resID_2);
		    		int score = ns.score;
		    		int min = score / 60;
		    		int sec = score % 60;
		    		tv2.setText(min + ";" + sec);
		    	}
		    	*/
		    }
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	    builder.show();
	}
	
	private void getScore(){
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				
				try {
					Log.i("getScoreBoard", "start");
					nonoScoreBoard = NonoClient.getScoreBoard(Difficulty.EASY);
					Log.i("getScoreBoard", "end");
					Log.i("getScoreBoard", nonoScoreBoard.toString());
					//***printing to console
					Iterator<NonoScore> scoreIter = nonoScoreBoard.getIterator();
					while(scoreIter.hasNext()) {
						NonoScore next = scoreIter.next();
						Log.i("scoreSmall", next.difficulty);
						Log.i("scoreSmall", next.playerName);
						Log.i("scoreSmall", String.valueOf(next.score));
					}
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
}