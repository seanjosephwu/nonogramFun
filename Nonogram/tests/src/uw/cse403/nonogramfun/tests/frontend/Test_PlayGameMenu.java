package uw.cse403.nonogramfun.tests.frontend;

import junit.framework.Assert;
import uw.cse403.nonogramfun.*;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
/**
 * Black Box Tests
 * PlayGameMenu class
 * @author Huiqi Wang
 *
 */
public class Test_PlayGameMenu extends ActivityInstrumentationTestCase2<PlayGameMenu> {
	private Solo solo;
	
	public Test_PlayGameMenu() {
		super(PlayGameMenu.class);
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
		solo.assertCurrentActivity("Not PlayGameMenu", PlayGameMenu.class);
		solo.clickOnButton("Small");
		solo.assertCurrentActivity("Not PlayGameScreen", PlayGameScreen.class);
		// verify the game board size
		int dimensions = solo.getCurrentActivity().getIntent().getIntExtra("size", 0);
		Assert.assertEquals(5, dimensions);
	}
	
	public void testMediumGameButton(){
		solo.assertCurrentActivity("Not PlayGameMenu", PlayGameMenu.class);
		solo.clickOnButton("Medium");
		solo.assertCurrentActivity("Not PlayGameScreen", PlayGameScreen.class);
		// verify the game board size
		int dimensions = solo.getCurrentActivity().getIntent().getIntExtra("size", 0);
		Assert.assertEquals(10, dimensions);
	}

	public void testLargeGameButton(){
		solo.assertCurrentActivity("Not PlayGameMenu", PlayGameMenu.class);
		solo.clickOnButton("Large");
		solo.assertCurrentActivity("Not PlayGameScreen", PlayGameScreen.class);
		// verify the game board size
		int dimensions = solo.getCurrentActivity().getIntent().getIntExtra("size", 0);
		Assert.assertEquals(14, dimensions);
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
