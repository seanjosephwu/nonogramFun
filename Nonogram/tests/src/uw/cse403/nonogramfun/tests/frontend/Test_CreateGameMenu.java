package uw.cse403.nonogramfun.tests.frontend;

import junit.framework.Assert;
import uw.cse403.nonogramfun.*;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
/**
 * Black Box Tests
 * CreateGameMenu class
 * @author Huiqi Wang
 *
 */
public class Test_CreateGameMenu extends ActivityInstrumentationTestCase2<CreateGameMenu> {
	private Solo solo;
	
	public Test_CreateGameMenu() {
		super(CreateGameMenu.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(),getActivity());
	}
	
	public void testViewsCreated(){
		assertEquals(true, solo.searchButton("Small"));
		assertEquals(true, solo.searchButton("Medium"));
		assertEquals(true, solo.searchButton("Large"));
	}
	
	public void testSmallGameButton(){
		solo.assertCurrentActivity("Not CreateGameMenu", CreateGameMenu.class);
		solo.clickOnButton("Small");
		solo.assertCurrentActivity("Not CreateGameScreen", CreateGameScreen.class);
		// verify the game board size
		int dimensions = solo.getCurrentActivity().getIntent().getIntExtra("size", 0);
		Assert.assertEquals(5, dimensions);
	}
	
	public void testMediumGameButton(){
		solo.assertCurrentActivity("Not CreateGameMenu", CreateGameMenu.class);
		solo.clickOnButton("Medium");
		solo.assertCurrentActivity("Not CreateGameScreen", CreateGameScreen.class);
		// verify the game board size
		int dimensions = solo.getCurrentActivity().getIntent().getIntExtra("size", 0);
		Assert.assertEquals(10, dimensions);
	}

	public void testLargeGameButton(){
		solo.assertCurrentActivity("Not CreateGameMenu", CreateGameMenu.class);
		solo.clickOnButton("Large");
		solo.assertCurrentActivity("Not CreateGameScreen", CreateGameScreen.class);
		// verify the game board size
		int dimensions = solo.getCurrentActivity().getIntent().getIntExtra("size", 0);
		Assert.assertEquals(14, dimensions);
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
