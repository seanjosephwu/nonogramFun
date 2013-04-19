package uw.cse403.nonogramfun;

import android.os.AsyncTask;
import android.os.Bundle;

import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
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

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    }


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
    
}


