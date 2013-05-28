package uw.cse403.nonogramfun.tests.frontend;

import com.jayway.android.robotium.solo.Solo;

import uw.cse403.nonogramfun.CreateGameMenu;
import uw.cse403.nonogramfun.CreateGameScreen;
import android.R;
import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.TableRow;

import com.jayway.android.robotium.solo.Solo;

public class Test_CreateGameScreen extends ActivityInstrumentationTestCase2<CreateGameScreen> {
	private Activity activity;
	private Solo solo;
	
	public Test_CreateGameScreen() {
		super(CreateGameScreen.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		Intent i = new Intent();
		i.putExtra("size", 5);
		setActivityIntent(i);
		activity = getActivity();
		solo = new Solo(getInstrumentation(),activity);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testViewsCreated(){
		solo.assertCurrentActivity("Not CreateGameScreen", CreateGameScreen.class);
		//TableRow tr1 = (TableRow)activity.findViewById(uw.cse403.nonogramfun.R.id.tableRow1);
		//assertNotNull(tr1);
	}
}
