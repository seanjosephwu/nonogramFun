package uw.cse403.nonogramfun.tests.frontend;

import uw.cse403.nonogramfun.*;
import android.test.ActivityInstrumentationTestCase2;
import com.jayway.android.robotium.solo.Solo;
/**
 * Black Box Tests
 * ScoreBoard class
 * @author Huiqi Wang
 *
 */
public class Test_ScoreBoard extends ActivityInstrumentationTestCase2<ScoreBoard> {
	private Solo solo;
	
	public Test_ScoreBoard() {
		super(ScoreBoard.class);
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
		solo.assertCurrentActivity("Not ScoreBoard", ScoreBoard.class);
		solo.clickOnButton("Small");
		solo.assertCurrentActivity("Not ScoreBoard", ScoreBoard.class);
		solo.clickOnButton("OK");
	}
	
	public void testMediumGameButton(){
		solo.assertCurrentActivity("Not ScoreBoard", ScoreBoard.class);
		solo.clickOnButton("Medium");
		solo.assertCurrentActivity("Not ScoreBoard", ScoreBoard.class);
		solo.clickOnButton("OK");
	}

	public void testLargeGameButton(){
		solo.assertCurrentActivity("Not ScoreBoard", ScoreBoard.class);
		solo.clickOnButton("Large");
		solo.assertCurrentActivity("Not ScoreBoard", ScoreBoard.class);
		solo.clickOnButton("OK");
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
