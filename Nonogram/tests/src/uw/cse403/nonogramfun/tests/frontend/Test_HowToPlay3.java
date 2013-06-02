package uw.cse403.nonogramfun.tests.frontend;

import com.jayway.android.robotium.solo.Solo;
import uw.cse403.nonogramfun.*;
import android.test.ActivityInstrumentationTestCase2;
/**
 * Black Box Tests
 * HowToPlay3 class
 * @author Huiqi Wang
 *
 */
public class Test_HowToPlay3 extends ActivityInstrumentationTestCase2<HowToPlay3> {
	private Solo solo;
	
	public Test_HowToPlay3() {
		super(HowToPlay3.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(),getActivity());
	}

	public void testViewsCreated(){
		assertEquals(true, solo.searchButton("done"));
		assertEquals(true, solo.searchButton("prev"));
	}
	
	public void testPrevButton(){
		solo.assertCurrentActivity("Not HowToPlay3", HowToPlay3.class);
		solo.clickOnButton("prev");
		solo.assertCurrentActivity("Not HowToPlay2", HowToPlay2.class);
	}
	
	public void testDoneButton(){
		solo.assertCurrentActivity("Not HowToPlay3", HowToPlay3.class);
		solo.clickOnButton("done");
		solo.assertCurrentActivity("Not MainAcitivity", MainActivity.class);
	}
	
	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
