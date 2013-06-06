package uw.cse403.nonogramfun.tests.frontend;

import com.jayway.android.robotium.solo.Solo;
import uw.cse403.nonogramfun.*;
import android.test.ActivityInstrumentationTestCase2;
/**
 * Black Box Tests
 * HowToPlay class
 * @author Huiqi Wang
 *
 */
public class Test_HowToPlay extends ActivityInstrumentationTestCase2<HowToPlay> {
	private Solo solo;
	
	public Test_HowToPlay() {
		super(HowToPlay.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(),getActivity());
	}

	public void testViewsCreated(){
		assertEquals(true, solo.searchButton("next"));
		assertEquals(true, solo.searchButton("skip"));
	}
	
	public void testNextButton(){
		solo.assertCurrentActivity("Not HowToPlay", HowToPlay.class);
		solo.clickOnButton("next");
		solo.assertCurrentActivity("Not HowToPlay2", HowToPlay2.class);
		
		// test going back to HowToPlay from HowToPlay2
		assertEquals(true, solo.searchButton("prev"));
		solo.clickOnButton("prev");
		solo.assertCurrentActivity("Not HowToPlay", HowToPlay.class);
	}
	
	public void testSkipButton(){
		solo.assertCurrentActivity("Not HowToPlay", HowToPlay.class);
		solo.clickOnButton("skip");
		solo.assertCurrentActivity("Not MainAcitivity", MainActivity.class);
	}
	
	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
