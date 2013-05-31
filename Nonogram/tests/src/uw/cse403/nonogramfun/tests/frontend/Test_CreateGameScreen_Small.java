package uw.cse403.nonogramfun.tests.frontend;

import com.jayway.android.robotium.solo.Solo;

import uw.cse403.nonogramfun.CreateGameScreen;
import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

public class Test_CreateGameScreen_Small extends ActivityInstrumentationTestCase2<CreateGameScreen> {
	private Activity activity;
	private Solo solo;
	final int SIZE_SMALL = 5;
	
	public Test_CreateGameScreen_Small() {
		super(CreateGameScreen.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		Intent i = new Intent();
		i.putExtra("size", SIZE_SMALL);
		i.putExtra("test", true);
		setActivityIntent(i);
		activity = getActivity();
		solo = new Solo(getInstrumentation(),activity);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * White Box Tests
	 * Test if the gameboard buttons are all created in the view, and if clicking the
	 * buttons will mark the buttons
	 */
	public void testViewsCreated(){
		solo.assertCurrentActivity("Not CreateGameScreen", CreateGameScreen.class);
		String text;
		for(int i = 0; i < SIZE_SMALL; i++){
			for (int j = 0; j < SIZE_SMALL; j++){
				
				text = Integer.toString(i)+Integer.toString(j);
				assertEquals(true, solo.searchButton(text));
				
				Button button = solo.getButton(text);
				solo.clickOnButton(text);
				assertEquals("X", button.getText());
			}
		}
	}
	
	public void testSubmitButton(){
		//test alert box
	}
}
