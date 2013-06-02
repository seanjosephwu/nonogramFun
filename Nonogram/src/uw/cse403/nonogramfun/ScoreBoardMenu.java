package uw.cse403.nonogramfun;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import org.json.JSONException;

import uw.cse403.nonogramfun.enums.Difficulty;
import uw.cse403.nonogramfun.network.NonoClient;
import uw.cse403.nonogramfun.nonogram.NonoScore;
import uw.cse403.nonogramfun.nonogram.NonoScoreBoard;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * CSE 403 AA
 * Project Nonogram: Frontend
 * @author  Xiaoxia Jian
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */

public class ScoreBoardMenu extends Activity implements OnClickListener {
	NonoScoreBoard nonoScoreBoard;
	List<NonoScore> listToSort = new ArrayList<NonoScore>();
	private static final int small = 101; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scoreboard_menu);
		setTitle("Score Board Menu");
		
		//grab the view 
		Button b1 = (Button)findViewById(R.id.scoreboard_screen_button1);
		Button b2 = (Button)findViewById(R.id.scoreboard_screen_button2);
		Button b3 = (Button)findViewById(R.id.scoreboard_screen_button3);
	    b1.setOnClickListener(this);
	    b2.setOnClickListener(this);
	    b3.setOnClickListener(this);
	}
	
    @Override
    public void onClick(View v) {
    	switch (v.getId()) {
    	    case R.id.scoreboard_screen_button1:
    	    	
    	    	getScore(small);
    	    	
    	        List<NonoScore> topTen = sortingNonoScores();
    	       
    	    	Intent intent = new Intent(this, ScoreBoardScreen.class);

    	    	ArrayList<String> list = new ArrayList<String>(topTen.size());
    	    	for(int j = 0; j < topTen.size(); j++){
    	    		list.add(topTen.get(j).toString());
    	    	}
    	    	intent.putStringArrayListExtra("topTen", list);
    	    	startActivity(intent);
    	        break;
    	    case R.id.scoreboard_screen_button2:
    	    	getScore(small);
    	    	sortingNonoScores();
    	       break;
    	    case R.id.scoreboard_screen_button3:
    	    	getScore(small);
    	    	sortingNonoScores();
    	        break;
    	    }   
    }
	
	/**
	 * sorting by score, store top 10 (based on least score)
	 * @return
	 */
	private List<NonoScore> sortingNonoScores(){
		if(nonoScoreBoard != null){
			Iterator<NonoScore> scoreIter = nonoScoreBoard.getIterator();
			List<NonoScore> topTen = new ArrayList<NonoScore>();
			while(scoreIter.hasNext()) {
				NonoScore next = scoreIter.next();
				listToSort.add(next);
				
			}
			assert(listToSort != null);
			
			PriorityQueue<NonoScore> queue = new PriorityQueue<NonoScore>(listToSort.size());
	    	for(int i = 0; i < listToSort.size(); i++){
	    		queue.add(listToSort.get(i));
	    	}
	    	
	    	for(int i = 0; i < 10; i++){
	    		topTen.add(queue.remove());
	    	}
	    	return topTen;
		}
		return null;
	}
	
	/**
	 * given the difficulty retrieve all NonoScore for that difficulty level
	 * @param type
	 */
	private void getScore(final int type){
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				
				try {
					
					if (type == small)
						nonoScoreBoard = NonoClient.getScoreBoard(Difficulty.EASY);
					
					if (nonoScoreBoard == null) {
						Log.e("error","returns null");
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
