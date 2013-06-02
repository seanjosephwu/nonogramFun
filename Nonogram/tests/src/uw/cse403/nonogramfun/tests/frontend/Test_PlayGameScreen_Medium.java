package uw.cse403.nonogramfun.tests.frontend;

import com.jayway.android.robotium.solo.Solo;

import uw.cse403.nonogramfun.PlayGameScreen;
import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
/**
 * Test the case of medium gameboard of PlayGameScreen
 * @author Huiqi Wang
 *
 */
public class Test_PlayGameScreen_Medium extends ActivityInstrumentationTestCase2<PlayGameScreen> {
	private Activity activity;
	private Solo solo;
	final int SIZE_MEDIUM = 10;
	
	public Test_PlayGameScreen_Medium() {
		super(PlayGameScreen.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		Intent i = new Intent();
		i.putExtra("size", SIZE_MEDIUM);
		i.putExtra("test", true);
		setActivityIntent(i);
		activity = getActivity();
		solo = new Solo(getInstrumentation(),activity);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * White Box Test
	 * Test if the gameboard buttons are all created in the view, and if clicking the
	 * buttons will mark the buttons
	 */
	public void testViewsCreated(){
		solo.assertCurrentActivity("Not CreateGameScreen", PlayGameScreen.class);
		String text;
		for(int i = 0; i < SIZE_MEDIUM; i++){
			for (int j = 0; j < SIZE_MEDIUM; j++){
				
				text = Integer.toString(j)+""+Integer.toString(i);
				assertEquals(true, solo.searchButton(text));
				
				Button button = solo.getButton(text);
				solo.clickOnButton(text);
				assertEquals("X", button.getText());
			}
		}
	}
	
	/*
	 * Black Box Test
	 * Test if the submit button exists and functions properly
	 */
	public void testSubmitButton(){
		
	}
}
