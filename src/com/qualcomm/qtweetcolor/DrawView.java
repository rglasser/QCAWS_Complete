/*
 * DrawView
 * 
 * This is our custom DrawView class. It will handle drawing the colors from
 * the tweets to the screen.
 */

package com.qualcomm.qtweetcolor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/*
 * A View is the base of the objects placed on the screen in Android. For
 * example, a TextView is a subclass of a View, and a Button is a subclass
 * of a TextView.
 */
public class DrawView extends View {
    Paint paint = new Paint();
    int[] colors = new int[25];
    int pos = 0;

    public DrawView(Context context) {
        super(context);
        // Initialize everything to black
        for(int i=0; i<colors.length; i++){
        	colors[i] = Color.BLACK;
        }
    }

    /*
     * onDraw function
     * 
     * This is the method that is always called when the view needs to be
     * refreshed. We force the View to be refreshed in the addColor function
     * by calling invalidate().
     */
    @Override
    public void onDraw(Canvas canvas) {
    	int w = canvas.getWidth();
    	int h = canvas.getHeight();
    	
    	// Draw all the colors
    	for(int i=0; i<5; i++){
    		for(int j=0; j<5; j++){
    			paint.setColor(colors[i+j*5]);
    			canvas.drawRect(i*w/5, j*h/5, (i+1)*w/5, (j+1)*h/5, paint);
    		}
    	}
    }
    
    /*
     * addColor function
     * 
     * Adds a color to be displayed and displays it
     */
    public void addColor(int color){
    	colors[pos%25] = color;
    	pos++;
    	
    	// This forces a redraw of the screen
    	this.invalidate();
    }
    
    /*
     * setPos function
     * 
     * Setter method for the position variable. Needed for restoring state.
     */
    public void setPos(int pos){
    	this.pos = pos;
    }
    
    /*
     * getPos function
     * 
     * Getter method for the position variable. Needed for saving state.
     */
    public int getPos(){
    	return this.pos;
    }
    
    /*
     * getColorArray function
     * 
     * Getter method for the color array. Needed for saving state.
     */
    public int[] getColorArray() {
    	return colors;
    }
}