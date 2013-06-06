package uw.cse403.nonogramfun.tests.frontend;

import com.jayway.android.robotium.solo.Solo;
import uw.cse403.nonogramfun.*;
import android.test.ActivityInstrumentationTestCase2;
/**
 * Black Box Tests
 * HowToPlay2 class
 * @author Huiqi Wang
 *
 */
public class Test_HowToPlay2 extends ActivityInstrumentationTestCase2<HowToPlay2> {
	private Solo solo;
	
	public Test_HowToPlay2() {
		super(HowToPlay2.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(),getActivity());
	}

	public void testViewsCreated(){
		assertEquals(true, solo.searchButton("next"));
		assertEquals(true, solo.searchButton("skip"));
		assertEquals(true, solo.searchButton("prev"));
	}
	
	public void testPrevButton(){
		solo.assertCurrentActivity("Not HowToPlay2", HowToPlay2.class);
		solo.clickOnButton("prev");
		solo.assertCurrentActivity("Not HowToPlay", HowToPlay.class);
	}
	
	public void testNextButton(){
		solo.assertCurrentActivity("Not HowToPlay2", HowToPlay2.class);
		solo.clickOnButton("next");
		solo.assertCurrentActivity("Not HowToPlay3", HowToPlay3.class);
	}
	
	public void testSkipButton(){
		solo.assertCurrentActivity("Not HowToPlay2", HowToPlay2.class);
		solo.clickOnButton("skip");
		solo.assertCurrentActivity("Not MainAcitivity", MainActivity.class);
	}
	
	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
