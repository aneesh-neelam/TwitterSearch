package hashtag.twittersearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity 
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		final EditText ht = (EditText)findViewById(R.id.hashtagBox1);
		final Button button = (Button) findViewById(R.id.searchButton);
        button.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            {
            	final String hashtag = ht.getText().toString();
                new NetworkTask().execute(hashtag);
                
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	private class NetworkTask extends AsyncTask<String, Void, String> 
	{
	    @Override
	    protected String doInBackground(String... params) 
	    {
	    	String ht = params[0];
	    	String link = "http://gp13twitterapp.appspot.com/hash/"+ht;
	    	HttpGet request = new HttpGet(link);
	        AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
	        try 
	        {
	        	HttpResponse result = client.execute(request);
	        	HttpEntity entity = result.getEntity();
	        	InputStream is = entity.getContent();
	        	BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) 
                {
                    sb.append(line + "\n");
                }
                is.close();
                String json = sb.toString();
	        	return json;
	        }
	        catch (IOException e) 
	        {
	        	Log.d("IO","IO Error");
	            e.printStackTrace();
	            return null;
	        }
	        finally 
	        {
	        	client.close();
	        }
	    }

	    @Override
	    protected void onPostExecute(String result) 
	    {
	        //Do something with result
	        if (result != null)
	        {
	        	ArrayList<String> tweets = new ArrayList<String>();
	        	try{
	        			JSONObject ob = new JSONObject(result);
						JSONArray statuses = ob.getJSONArray("statuses");
						for(int i=0;i<statuses.length();i++)
						{
							JSONObject status = statuses.getJSONObject(i);
							String tweet = status.getString("text");
							tweets.add(tweet);
						}
						String[] finalTweets = new String[tweets.size()];
						finalTweets = tweets.toArray(finalTweets);
						Bundle b = new Bundle();
						b.putStringArray("tweets",finalTweets);
		                Intent startNewActivityOpen = new Intent(Login.this, Tweets.class);
		                startNewActivityOpen.putExtras(b);
		                startActivityForResult(startNewActivityOpen, 0);
					}	
				catch(JSONException je)
				{
					Log.d("JSON","JSON Parsing Error");
				}
	        	catch (ParseException e) 
	        	{
	        		Log.d("Parsing","Parsing Error");
					e.printStackTrace();
				}
	        }
	    }
	}
}
