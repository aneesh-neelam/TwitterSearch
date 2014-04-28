/**
 * 
 */
package hashtag.twittersearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author Aneesh Neelam
 *
 */
public class Tweets extends Activity 
{	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);    
		setContentView(R.layout.activity_tweets);
		
		Intent intent = getIntent();
		String tweets[] = intent.getStringArrayExtra("tweets");
		
		ListView listview1 = (ListView)findViewById(R.id.tweetList);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tweets);
		listview1.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.Tweets, menu);
		return true;
	}
}












