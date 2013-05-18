package uw.cse403.nonogramfun.tests.frontend;

import uw.cse403.nonogramfun.CreateGameScreen;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

public class Test_CreateGameScreen extends ActivityInstrumentationTestCase2<CreateGameScreen> {

	private CreateGameScreen mActivity;
	
	public Test_CreateGameScreen() {
		super(CreateGameScreen.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		Intent i = new Intent();
		i.putExtra("size", 5);
		setActivityIntent(i);
		
		mActivity = this.getActivity();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@SmallTest
	public void testFetchPuzzle(){
	
	}
}
