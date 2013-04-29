package uw.cse403.nonogramfun;

import android.os.AsyncTask;
import android.os.Bundle;

import android.content.Intent;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity implements View.OnClickListener {

    public Button b;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = (Button) findViewById(R.id.creategame);
        b.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Intent i = new Intent(MainActivity.this, CreateGame.class);
        		startActivity(i);    		
        	}
        });

    }
//        TableLayout layout = new TableLayout (this);
//        layout.setLayoutParams( new TableLayout.LayoutParams(4,5) );
//
//        layout.setPadding(1,1,1,1);
//
//        for (int f=0; f<=5; f++) {
//            TableRow tr = new TableRow(this);
//            for (int c=0; c<=5; c++) {
//                Button b = new Button (this);
//                b.setText(""+f+c);
//                b.setTextSize(10.0f);
//                b.setTextColor(Color.rgb( 100, 200, 200));
//                b.setOnClickListener(this);
//                tr.addView(b,30,30);
//            }
//            layout.addView(tr);
//        }
//
//        super.setContentView(layout); 
        
//
//    public void onClick(View view) {
//        ((Button) view).setText("*");
//        ((Button) view).setEnabled(false);
//    }


    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    /** Called when the user clicks the Access button 
     * @throws SQLException */
    public void accessServer(View view) throws SQLException {
        
    	new NetworkAccessTask().execute("");
    }
    
    
    class NetworkAccessTask extends AsyncTask<String, Void, String> {
    	
    	@Override
    	protected String doInBackground(String... params) {
    		String result = "";
        	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        	nameValuePairs.add(new BasicNameValuePair("id","message"));
    		InputStream is = null;
    		//http get
    		try{
    		        HttpClient httpclient = new DefaultHttpClient();
    		        HttpGet httpget = new HttpGet("http://uw-cse403-nonogram.co.nf/test.php?id=*");
    		        HttpResponse response = httpclient.execute(httpget);
    		        HttpEntity entity = response.getEntity();
    		        is = entity.getContent();
    		}catch(Exception e){
    		        Log.e("log_tag", "Error in http connection "+e.toString());
    		}
    		//convert response to string
    		try{
    		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
    		        StringBuilder sb = new StringBuilder();
    		        String line = null;
    		        while ((line = reader.readLine()) != null) {
    		                sb.append(line + "\n");
    		        }
    		        is.close();
    		 
    		        result=sb.toString();
    		        System.out.println(result);
    		}catch(Exception e){
    		        Log.e("log_tag", "Error converting result "+e.toString());
    		}
    		 
    		String returnResult = "";
    		
    		//parse json data
    		try{
    			
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
	                JSONObject json_data = jArray.getJSONObject(i);
	                returnResult += json_data.getString("message");
	                returnResult += " ";
		        }
    		}catch(JSONException e){
    		        Log.e("log_tag", "Error parsing data "+e.toString());
    		}
    		
    		return returnResult;
    	}

    	@Override
        protected void onPostExecute(String result) {
              EditText txt = (EditText) findViewById(R.id.editText1);
              txt.setText(result); // txt.setText(result);
        }
    	
    	
    }


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		
	}
	
	
	public void howToPlayScreen() {
		Intent i = new Intent(this, HowToPlay.class);
		startActivity(i);
	}
	
	
	public void createGameScreen() {
		Intent i = new Intent(this, CreateGame.class);
		startActivity(i);
	}
    
	
	public void playGameScreen() {
		Intent i = new Intent(this, PlayGameMenu.class);
		startActivity(i);
	}
	
	
	public void settingsScreen() {
		Intent i = new Intent(this, Settings.class);
		startActivity(i);
	}
}


