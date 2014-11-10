/*
 * TweetListActivity
 * 
 * This is an example of a listview seen in many mobile apps. As tweets are
 * received, the list will dynamically update. Tapping the tweets will show
 * what color was extracted from the tweet.
 */

package com.qualcomm.qtweetcolor;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TweetListActivity extends ListActivity {

	ListView listView;

	// This will hold the tweets we receive.
	ArrayList<String> tweets = new ArrayList<String>();

	// The ArrayAdapter will translate our ArrayList to the ListView
	ArrayAdapter<String> adapter;

	/*
	 * onCreate function
	 * 
	 * This function is the first one called when the activity is created. The
	 * argument savedInstanceState can contain information from a previous
	 * instance of this activity. For example, rotating the screen will cause
	 * the activity to be recreated.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Always call the super function
		super.onCreate(savedInstanceState);
		
		// Sets the layout to what's defined in the xml contained in res\layout\
		setContentView(R.layout.activity_list);

		/* 
		 * If savedInstanceState is not null, the screen was likely rotated and
		 * the Activity restarted. Thus we will grab our list of tweets saved
		 * from last time. Otherwise the Activity was likely started by the
		 * MainActivity, thus we should grab the tweets passed by the Activity. 
		 */
		if (savedInstanceState != null) {
			tweets = savedInstanceState.getStringArrayList("tweets");
		} else {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				tweets = extras.getBundle("bundle").getStringArrayList("tweets");
			}
		}

		// Get ListView object from xml
		listView = (ListView) findViewById(android.R.id.list);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, tweets);

		// Assign adapter to ListView
		listView.setAdapter(adapter);

		// ListView Item Click Listener
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// ListView Clicked item value
				String itemValue = (String) listView
						.getItemAtPosition(position);

				Toast.makeText(getApplicationContext(),
						ColorParser.extractColor(itemValue), Toast.LENGTH_LONG)
						.show();
			}

		});
		
		// This will let us listen for messages containing new tweets
		LocalBroadcastManager.getInstance(this).registerReceiver(
				mMessageReceiver, new IntentFilter("send-tweet"));
	}

	// This will grab the messages and add them to the view 
	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Get the tweet
			String tweet = intent.getStringExtra("tweet");
			
			// Add the tweet to the start of the list
			tweets.add(0, tweet);
			
			// Refresh the view
			adapter.notifyDataSetChanged();
		}
	};

	/*
	 * This is called when the activity is about to be destroyed. This could be
	 * the Activity being closed or the device being rotated. If the device is
	 * rotated, we want all of our data to persist, so we will save the list of
	 * tweets just in case.
	 */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putStringArrayList("tweets", tweets);

		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);
	}

	/* 
	 * This is called immediately before the Activity is killed. Implement this
	 * if you don't want anything sticking around (such as BroadcastReceivers
	 * or threads).
	 */
	@Override
	protected void onDestroy() {
		// Unregister since the activity is about to be closed.
		LocalBroadcastManager.getInstance(this).unregisterReceiver(
				mMessageReceiver);
		super.onDestroy();
	}
}
