/*
 * ColorDrawActivity
 * 
 * This Activity will display the colors on the screen parsed from the tweets.
 */

package com.qualcomm.qtweetcolor;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

public class ColorDrawActivity extends Activity {

	// The DrawView is what will be our canvas
	DrawView drawView;

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
		super.onCreate(savedInstanceState);

		/*
		 * Instead of using an xml for the layout like we have with the other
		 * Activities, we will use our custom DrawView. If your application
		 * requires buttons and such in addition to the DrawView, you can
		 * always define the placement of the DrawView in the xml just like a
		 * button, textview, etc.
		 */
		drawView = new DrawView(this);
		drawView.setBackgroundColor(Color.BLACK);
		setContentView(drawView);

		/* 
		 * If savedInstanceState is not null, the screen was likely rotated and
		 * the Activity restarted. Thus we will grab our list of colors saved
		 * from last time. Otherwise the Activity was likely started by the
		 * MainActivity, thus we should grab the colors passed by the Activity. 
		 */
		if (savedInstanceState != null) {
			int[] oldColors = savedInstanceState.getIntArray("colors");
			for (int i = 0; i < oldColors.length; i++) {
				drawView.addColor(oldColors[i]);
			}
			drawView.setPos(savedInstanceState.getInt("pos"));
		} else {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				ArrayList<Integer> oldColors = extras.getBundle("bundle").getIntegerArrayList("colors");
				for(int i=0; i < oldColors.size(); i++){
					drawView.addColor(oldColors.get(i));
				}
			}
		}
		
		// This will let us listen for messages containing new colors
		LocalBroadcastManager.getInstance(this).registerReceiver(
				mMessageReceiver, new IntentFilter("send-tweet-color"));
	}

	// This will grab the messages and add the color to the view.
	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Get the color. Select black if something goes wrong
			int color = intent.getIntExtra("color", Color.BLACK);
			
			// Put the color on the screen.
			drawView.addColor(color);
		}
	};

	/*
	 * This is called when the activity is about to be destroyed. This could be
	 * the Activity being closed or the device being rotated. If the device is
	 * rotated, we want all of our data to persist, so we will save the list of
	 * colors just in case.
	 */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putIntArray("colors", drawView.getColorArray());
		savedInstanceState.putInt("pos", drawView.getPos());

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
