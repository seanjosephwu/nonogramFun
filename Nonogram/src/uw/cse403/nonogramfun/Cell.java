package uw.cse403.nonogramfun;

/**
 * CSE 403 AA
 * Project Nonogram: Frontend
 * @author  Xiaoxia Jian, Huiqi Wang, Renhao Xie, Alan Loh
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

/**
 * The cell is used at PlayGameScreen, it can change color when user press it,
 * and also has an integer state to record weather it is pressed or not
 */
public class Cell extends Button{
	private boolean select;
	private int state;  
	private int origin; 
	private int current;
	
	/**
	 * Constructor, create a new cell, the default color should be white, and the state should be 0
	 * @param context
	 */
	public Cell(Context context) {
		super(context);
		select = false;
		state = 0; 
		current = Color.WHITE;
	}
	
	/**
	 * Change the select
	 */
	public void setSelectVal(){
		if(select)
			select = false;
		else
			select = true;
	}
	
	/**
	 * get the select
	 * @return if this cell is selected or not
	 */
	public boolean getSelectVal(){
		return select;
	}
	
	/**
	 * set the state after a click
	 */
	public void setState(){
		if(state < 2){
    		state++;
		} else {
    		state = 0;
		}
	}
	
	/**
	 * get the state
	 * @return this cell's state
	 */
	public int getState(){
		return state;
	}
	
	/**
	 * get the color
	 * @return this cell's color
	 */
	public int getColor(){
		return current;
	}
	
	/**
	 * store the origin cell color before any action
	 * @param color, the color we want to this cell's origin color would be
	 */
	public void setOriginColor(int color){
		origin = color;
	}
	
	/**
	 * Set the color and text of this cell based its state
	 */
	public void setStateColor(){
		if (state == 0) {
			//0 : set to the original cell color (unmark)
			this.setText("");
			this.setColor(origin);
		} else if(state == 1) {
			//1 : mark the cell black
			this.setText("");
			this.setColor(Color.BLACK);
		} else {
			//2 : leave a question mark on the cell
			this.setColor(origin);
			this.setText("?");
			this.setTextColor(Color.BLUE);
		}
	}
	
	/**
	 * set the color
	 * @param color
	 */
	public void setColor(int color){
		this.setBackgroundColor(color);
		current = color;
	}
}

