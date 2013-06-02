package uw.cse403.nonogramfun;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * CSE 403 AA
 * Project Nonogram: Frontend
 * @author  Xiaoxia Jian, Huiqi Wang, Renhao Xie, Alan Loh
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */
public class ScoreBoardScreen extends Activity{
	ArrayList<String> names;
	ArrayList<String> scores;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scoreboard_screen);
		Log.i("ScoreBoardScreen", "now you are in scoreboardScreen");
		
		ArrayList<String> topTenStrings = getIntent().getStringArrayListExtra("topTen");
		
		/*Test Print**/
		for(int i = 0; i < topTenStrings.size(); i++){
			Log.i("ScoreBoardScreen", topTenStrings.get(i));
		}
		
		names = new ArrayList<String>(topTenStrings.size());
		scores = new ArrayList<String>(topTenStrings.size());
		parser(topTenStrings);
		
		//parse topTenStirng by *** to get each NonoScore
	
		for(int i = 0; i < topTenStrings.size(); i++){
			TextView tv1 = new TextView(this);
			tv1.setGravity(Gravity.LEFT);
			tv1.setText(i);
			//tv1.setText(names.get(i));
			TextView tv2 = new TextView(this);
			tv2.setGravity(Gravity.RIGHT);
			//tv2.setText(scores.get(i));
			tv2.setText(i);
		}
		
	}
	

	private void parser(ArrayList<String> list){
		for(int i = 0; i < list.size(); i ++){
			String s = list.get(i);
			/**
			 * [playername**difficulty**score]
			 */
			String[] info = new String[3];
			info = s.split("**");
			names.add(info[0]);
			scores.add(info[2]);
		}

	}
	
	private ArrayList<String> getScores(ArrayList<String> list){
		ArrayList<String> scores = new ArrayList<String>(list.size());
		for(int i = 2; i < list.size(); i += 3){
			scores.add(list.get(i));
		}
		return scores;
	}
	
}
