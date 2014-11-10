/*
 * MainActivity
 * 
 * This is the first activity that will appear when the app is launched. This is
 * determined by the intent-filter in AndroidManifest.xml
 * 
 * This Activity contains two Buttons. One will launch the activity showing the
 * colors in the tweets and the other will show a list of tweets received. 
 */

package com.qualcomm.qtweetcolor;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

// By convention, any class that is an activity should contain "Activity" in the class name.
public class MainActivity extends Activity {

	public static final String TAG = "";

	// See https://apps.twitter.com to create your KEYS
        public static final String TAG = "";
	private static final String CONSUMER_KEY = "";
	private static final String CONSUMER_SECRET = "";
	private static final String ACCESS_TOKEN = "";
	private static final String ACCESS_TOKEN_SECRET = "";

	private static TwitterStream twitterStream;

	// We want to keep track of the colors as we receive them
	private ArrayList<Integer> colors;

	// We want to keep track of the tweets as we receive them
	private ArrayList<String> tweets;

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
		setContentView(R.layout.activity_main);

		colors = new ArrayList<Integer>();
		tweets = new ArrayList<String>();

		// Gets a reference to the Button defined in activity_main.xml
		Button colorButton = (Button) findViewById(R.id.showColors);

		/*
		 * In order for the button to do anything, we need to add an
		 * OnClickListener in order to "listen" for when a user clicks (touches)
		 * the button on the screen. There are other ways to implement an
		 * OnClickListener, such as having the activity implement the
		 * OnClickListener class, which is useful if your activity has a large
		 * number of buttons.
		 */
		colorButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Intents allow us to launch a new activity
				Intent intent = new Intent(getBaseContext(),
						ColorDrawActivity.class);
				if (colors.size() > 0) {
					// We want to add our current colors to the intent
					Bundle b = new Bundle();
					b.putIntegerArrayList("colors", colors);
					intent.putExtra("bundle", b);
				}
				// Launch the new activity
				startActivity(intent);
			}
		});

		// Another button. Same idea as above
		Button listButton = (Button) findViewById(R.id.showList);
		listButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Do something in response to button click
				Intent intent = new Intent(getBaseContext(),
						TweetListActivity.class);
				if (tweets.size() > 0) {
					Bundle b = new Bundle();
					b.putStringArrayList("tweets", tweets);
					intent.putExtra("bundle", b);
				}
				startActivity(intent);
			}
		});

		// Configure Twitter Stream API
		ConfigurationBuilder confbuilder = new ConfigurationBuilder();
		Configuration conf = confbuilder.setOAuthConsumerKey(CONSUMER_KEY)
				.setOAuthConsumerSecret(CONSUMER_SECRET)
				.setOAuthAccessToken(ACCESS_TOKEN)
				.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET).build();
		twitterStream = new TwitterStreamFactory(conf).getInstance();

		// Start streaming (twitter)
		startStreaming();

		Log.i(TAG, "Now streaming...");
	}

	/*
	 * sendMessage(int) function
	 * 
	 * This allows us to send a message containing the color received in a
	 * tweet. If the ColorDrawActivity is already active this will enable us to
	 * make sure it receives the color.
	 */
	private void sendMessage(int color) {
		Intent intent = new Intent("send-tweet-color");
		intent.putExtra("color", color);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

	/*
	 * sendMessage(String) function
	 * 
	 * This allows us to send a message containing the tweet received. If the
	 * TweetListActivity is already active this will enable us to make sure it
	 * receives the tweet to display immediately.
	 */
	private void sendMessage(String tweet) {
		Intent intent = new Intent("send-tweet");
		intent.putExtra("tweet", tweet);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

	/*
	 * This block of code can mostly be ignored for the purposes of the demo.
	 * What's important is that it is activated when a tweet is received via
	 * twitter, and the onStatus method is called.
	 */
	public void startStreaming() {
		UserStreamListener listener = new UserStreamListener() {

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
				Log.i(TAG, "onDeletionNotice");
			}

			@Override
			public void onScrubGeo(long arg0, long arg1) {
				Log.i(TAG, "onScrubGeo");
			}

			/*
			 * onStatus function
			 * 
			 * This is called when a tweet is sent to @QC_Purdue_Works. We want
			 * to find the color (if it exists) and display it.
			 */
			@Override
			public void onStatus(Status status) {
				String tweet = "@" + status.getUser().getScreenName() + " : "
						+ status.getText() + "\n";
				int color = ColorParser.tweetToColor(status.getText());
				if (color != 0) {
					// Only want to display a color if we find one
					colors.add(color);
					sendMessage(color);
				}
				/*
				 * We will display the tweet in the tweet list even if it does
				 * not contain a color.
				 */
				sendMessage(tweet);
				tweets.add(0, tweet);
				Log.i(TAG, "onStatus: " + tweet);
			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
				Log.i(TAG, "onTrackLimitationNotice");
			}

			@Override
			public void onException(Exception arg0) {
				Log.i(TAG, "onException: " + arg0.toString());
			}

			@Override
			public void onBlock(User arg0, User arg1) {
				Log.i(TAG, "onBlock");
			}

			@Override
			public void onDeletionNotice(long arg0, long arg1) {
				Log.i(TAG, "onDeletionNotice");
			}

			@Override
			public void onDirectMessage(DirectMessage arg0) {
				Log.i(TAG, "onDirectMessage");
			}

			@Override
			public void onFavorite(User arg0, User arg1, Status arg2) {
				Log.i(TAG, "onFavorite");
			}

			@Override
			public void onFollow(User arg0, User arg1) {
				Log.i(TAG, "onFollow");
			}

			@Override
			public void onFriendList(long[] arg0) {
				Log.i(TAG, "onFriendList");
			}

			@Override
			public void onUnblock(User arg0, User arg1) {
				Log.i(TAG, "onUnblock");
			}

			@Override
			public void onUnfavorite(User arg0, User arg1, Status arg2) {
				Log.i(TAG, "onUnfavorite");
			}

			@Override
			public void onUserListCreation(User arg0, UserList arg1) {
				Log.i(TAG, "onUserListCreation");
			}

			@Override
			public void onUserListDeletion(User arg0, UserList arg1) {
				Log.i(TAG, "onUserListDeletion");
			}

			@Override
			public void onUserListMemberAddition(User arg0, User arg1,
					UserList arg2) {
				Log.i(TAG, "onUserListMemberAddition");
			}

			@Override
			public void onUserListMemberDeletion(User arg0, User arg1,
					UserList arg2) {
				Log.i(TAG, "onUserListMemberDeletion");
			}

			@Override
			public void onUserListSubscription(User arg0, User arg1,
					UserList arg2) {
				Log.i(TAG, "onUserListSubscription");
			}

			@Override
			public void onUserListUnsubscription(User arg0, User arg1,
					UserList arg2) {
				Log.i(TAG, "onUserListUnsubscription");
			}

			@Override
			public void onUserListUpdate(User arg0, UserList arg1) {
				Log.i(TAG, "onUserListUpdate");
			}

			@Override
			public void onUserProfileUpdate(User arg0) {
				Log.i(TAG, "onUserProfileUpdate");
			}

			@Override
			public void onStallWarning(StallWarning arg0) {
				Log.i(TAG, "onStallWarning");
			}

			@Override
			public void onUnfollow(User arg0, User arg1) {
				Log.i(TAG, "onUnfollow");
			}
		};

		twitterStream.addListener(listener);
		twitterStream.user();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * This is called immediately before the Activity is killed. Implement this
	 * if you don't want anything sticking around (such as BroadcastReceivers or
	 * threads).
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		twitterStream.cleanUp();
		twitterStream.shutdown();
	}
}
