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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ScoreBoardMenu extends Activity implements OnClickListener {
	NonoScoreBoard nonoScoreBoard;
	//List<NonoScore> nonoScoresSmall = new ArrayList<NonoScore>();
	//List<NonoScore> nonoScoresMedium = new ArrayList<NonoScore>();
	//List<NonoScore> nonoScoresLarge = new ArrayList<NonoScore>();
	List<NonoScore> listToSort = new ArrayList<NonoScore>();
	private static final int small = 101; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scoreboard_menu);
		setTitle("Score Board Menu");
		Log.i("scoreboard.java", "on create");
		
		//grab the view 
		Button b1 = (Button)findViewById(R.id.scoreboard_screen_button1);
		Log.i("Created buttons", "button 1 created");
		Button b2 = (Button)findViewById(R.id.scoreboard_screen_button2);
		Log.i("Created buttons", "button 2 created");
		Button b3 = (Button)findViewById(R.id.scoreboard_screen_button3);
		Log.i("Created buttons", "button 3 created");
		Log.i("Created buttons", "buttons created");
	    b1.setOnClickListener(this);
	    Log.i("Created buttons", "button 1 listener created");
	    b2.setOnClickListener(this);
	    Log.i("Created buttons", "button 2 listener created");
	    b3.setOnClickListener(this);
	    Log.i("Created buttons", "button 3 listener created");
	    Log.i("Listeners set", "button listeners set");

	}
	
    @Override
    public void onClick(View v) {
    	switch (v.getId()) {
    	    case R.id.scoreboard_screen_button1:
    	    	Log.i("Getting easy", "STart");
    	    	getScore(small);
    	    	Log.i("Getting easy", "Got scoreBoard");
    	        List<NonoScore> topTen = sortingNonoScores();
    	        Log.i("Getting easy", "Sorted the NonoScores");
    	    	Intent intent = new Intent(this, ScoreBoardScreen.class);
    	    	//Bundle bundle = new Bundle();
    	    	//intent.putParcelableArrayListExtra("topTen", topTen);
    	    	//Intent intent = getIntent();
    	    	//get NonoScores toString form and pass as intent
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
	
	//sorting by score, store top 10 (based on least score)
	private List<NonoScore> sortingNonoScores(){
		if(nonoScoreBoard != null){
			Iterator<NonoScore> scoreIter = nonoScoreBoard.getIterator();
			List<NonoScore> topTen = new ArrayList<NonoScore>();
			while(scoreIter.hasNext()) {
				NonoScore next = scoreIter.next();
				listToSort.add(next);
				Log.i("Next item", next.toString());
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
	
	//given the difficulty retrieve all NonoScore for that difficulty level
	private void getScore(final int type){
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				Log.i("In thread", "Starting");
				try {
					Log.i("Hi", "Why don't you work?");
					if (type == small)
						nonoScoreBoard = NonoClient.getScoreBoard(Difficulty.EASY);
					Log.i("Yay!", "You worked!");
					if (nonoScoreBoard == null) {
						Log.e("error","returns null");
					}
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {		
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			Log.i("MMmk", "???");
		}
	}
}
