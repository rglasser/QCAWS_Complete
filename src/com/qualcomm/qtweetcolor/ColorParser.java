/*
 * ColorParser
 * 
 * Provides static methods for parsing the color.
 */

package com.qualcomm.qtweetcolor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Color;
import android.util.Log;

public class ColorParser {
	public ColorParser() {}
	
	public static int tweetToColor(String tweet) {
		if (tweet.toLowerCase().contains("black")) {
			return Color.BLACK;
		}
		else if (tweet.toLowerCase().contains("blue")) {
			return Color.BLUE;
		}
		else if (tweet.toLowerCase().contains("brown")) {
			return Color.rgb(165, 42, 42);
		}
		else if (tweet.toLowerCase().contains("cyan")) {
			return Color.CYAN;
		}
		else if (tweet.toLowerCase().contains("dkgray")) {
			return Color.DKGRAY;
		}
		else if (tweet.toLowerCase().contains("dark gray")) {
			return Color.DKGRAY;
		}
		else if (tweet.toLowerCase().contains("ltgray")) {
			return Color.LTGRAY;
		}
		else if (tweet.toLowerCase().contains("light gray")) {
			return Color.LTGRAY;
		}
		else if (tweet.toLowerCase().contains("gold")) {
			return Color.rgb(207, 181, 59);
		}
		else if (tweet.toLowerCase().contains("gray")) {
			return Color.GRAY;
		}
		else if (tweet.toLowerCase().contains("green")) {
			return Color.GREEN;
		}
		else if (tweet.toLowerCase().contains("magenta")) {
			return Color.MAGENTA;
		}
		else if (tweet.toLowerCase().contains("orange")) {
			return Color.rgb(255, 128, 0);
		}
		else if (tweet.toLowerCase().contains("pink")) {
			return Color.rgb(255, 192, 203);
		}
		else if (tweet.toLowerCase().contains("purple")) {
			return Color.rgb(128, 0, 128);
		}
		else if (tweet.toLowerCase().contains("red")) {
			return Color.RED;
		}
		else if (tweet.toLowerCase().contains("white")) {
			return Color.WHITE;
		}
		else if (tweet.toLowerCase().contains("yellow")) {
			return Color.YELLOW;
		}
		else if (tweet.toLowerCase().contains("random")) {
			int r = (int)(Math.random() * 256);
			int g = (int)(Math.random() * 256);
			int b = (int)(Math.random() * 256);
			return Color.rgb(r, g, b);
		}
		/*
		Pattern pattern = Pattern.compile("[0-9A-F]{6}");
		Matcher matcher = pattern.matcher(tweet.toUpperCase());
		Log.i("TweetColor", "Testing regex");
		if (matcher.find())
		{
			Log.i("TweetColor", matcher.group(1));
		    return Color.parseColor("#" + matcher.group(1));
		}*/
		Log.i("TweetColor", "No color found");
		return 0;
	}
	
	public static String extractColor(String tweet) {
		if (tweet.toLowerCase().contains("black")) {
			return "black";
		}
		else if (tweet.toLowerCase().contains("blue")) {
			return "blue";
		}
		else if (tweet.toLowerCase().contains("brown")) {
			return "brown";
		}
		else if (tweet.toLowerCase().contains("cyan")) {
			return "cyan";
		}
		else if (tweet.toLowerCase().contains("dkgray")) {
			return "dark gray";
		}
		else if (tweet.toLowerCase().contains("dark gray")) {
			return "dark gray";
		}
		else if (tweet.toLowerCase().contains("ltgray")) {
			return "light gray";
		}
		else if (tweet.toLowerCase().contains("light gray")) {
			return "light gray";
		}
		else if (tweet.toLowerCase().contains("gold")) {
			return "gold";
		}
		else if (tweet.toLowerCase().contains("gray")) {
			return "gray";
		}
		else if (tweet.toLowerCase().contains("green")) {
			return "green";
		}
		else if (tweet.toLowerCase().contains("magenta")) {
			return "magenta";
		}
		else if (tweet.toLowerCase().contains("orange")) {
			return "orange";
		}
		else if (tweet.toLowerCase().contains("pink")) {
			return "pink";
		}
		else if (tweet.toLowerCase().contains("purple")) {
			return "purple";
		}
		else if (tweet.toLowerCase().contains("red")) {
			return "red";
		}
		else if (tweet.toLowerCase().contains("white")) {
			return "white";
		}
		else if (tweet.toLowerCase().contains("yellow")) {
			return "yellow";
		}
		else if (tweet.toLowerCase().contains("random")) {
			return "random";
		}
		/*
		Pattern pattern = Pattern.compile("[0-9A-F]{6}");
		Matcher matcher = pattern.matcher(tweet.toUpperCase());
		Log.i("TweetColor", "Testing regex");
		if (matcher.find())
		{
			Log.i("TweetColor", matcher.group(1));
		    return "#" + matcher.group(1);
		}*/
		Log.i("TweetColor", "No color found");
		return "no color found";
	}
}
