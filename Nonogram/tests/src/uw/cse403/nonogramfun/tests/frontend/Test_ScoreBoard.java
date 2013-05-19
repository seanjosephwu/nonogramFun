package uw.cse403.nonogramfun.tests.frontend;

import junit.framework.Assert;
import uw.cse403.nonogramfun.*;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

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

	public void testSmallGameButton(){
		solo.assertCurrentActivity("Not ScoreBoard", ScoreBoard.class);
		solo.clickOnButton("Small");
		solo.assertCurrentActivity("Not ScoreBoard", ScoreBoard.class);
		// verify the game board size
		
	}
	
	public void testMediumGameButton(){
		solo.assertCurrentActivity("Not ScoreBoard", ScoreBoard.class);
		solo.clickOnButton("Medium");
		solo.assertCurrentActivity("Not ScoreBoard", ScoreBoard.class);
	}

	public void testLargeGameButton(){
		solo.assertCurrentActivity("Not ScoreBoard", ScoreBoard.class);
		solo.clickOnButton("Large");
		solo.assertCurrentActivity("Not ScoreBoard", ScoreBoard.class);
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
