package uw.cse403.nonogramfun.tests.frontend;

import com.jayway.android.robotium.solo.Solo;

import uw.cse403.nonogramfun.CreateGameScreen;
import uw.cse403.nonogramfun.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
/**
 * Test the case of small gameboard of CreateGameScreen
 * @author Huiqi Wang
 *
 */
public class Test_CreateGameScreen_Small extends ActivityInstrumentationTestCase2<CreateGameScreen> {
	private Activity activity;
	private Solo solo;
	final int SIZE_SMALL = 5;
	
	public Test_CreateGameScreen_Small() {
		super(CreateGameScreen.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Intent i = new Intent();
		i.putExtra("size", SIZE_SMALL);
		i.putExtra("test", true);
		setActivityIntent(i);
		activity = getActivity();
		solo = new Solo(getInstrumentation(),activity);
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	/*
	 * White Box Test
	 * Test if the gameboard buttons are all created in the view, and if clicking the
	 * buttons will mark the buttons
	 */
	public void testViewsCreated(){
		solo.assertCurrentActivity("Not CreateGameScreen", CreateGameScreen.class);
		String text;
		for(int i = 0; i < SIZE_SMALL; i++){
			for (int j = 0; j < SIZE_SMALL; j++){
				
				text = Integer.toString(i)+""+Integer.toString(j);
				assertEquals(true, solo.searchButton(text));
				
				Button button = solo.getButton(text);
				solo.clickOnButton(text);
				assertEquals("X", button.getText());
			}
		}
	}
	
	/*
	 * Black Box Test
	 * Test if the submit button exists
	 */
	public void testSubmitButtonView(){
		solo.assertCurrentActivity("Not CreateGameScreen", CreateGameScreen.class);
		assertEquals(true,solo.searchButton("Submit"));	
	}
	
	/*
	 * Black Box Test
	 * Test clicking submit with an empty game gives an error message
	 */
	public void testSubmitButton_EmptyGame(){
		solo.assertCurrentActivity("Not CreateGameScreen", CreateGameScreen.class);
		assertEquals(false, solo.searchButton("X"));	
		solo.clickOnButton("Submit");
		assertEquals(true, solo.searchText("Error"));
		solo.clickOnButton("OK");
		solo.assertCurrentActivity("Not MainActivity", MainActivity.class);
	}
	
	/*
	 * Black Box Test
	 * Test clicking submit with a non-empty game gives a success message
	 */
	public void testSubmitButton_NonEmptyGame(){
		solo.assertCurrentActivity("Not CreateGameScreen", CreateGameScreen.class);
		solo.clickOnButton("11");
		assertEquals(true, solo.searchButton("X"));	
		solo.clickOnButton("Submit");
		assertEquals(true, solo.searchText("Success"));
		solo.clickOnButton("OK");
		solo.assertCurrentActivity("Not MainActivity", MainActivity.class);
	}
}
