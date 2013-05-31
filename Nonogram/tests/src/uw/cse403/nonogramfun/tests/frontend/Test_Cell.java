package uw.cse403.nonogramfun.tests.frontend;

import org.junit.Test;

import com.jayway.android.robotium.solo.Solo;

import android.content.Intent;
import android.graphics.Color;
import android.test.ActivityInstrumentationTestCase2;
import uw.cse403.nonogramfun.Cell;
import uw.cse403.nonogramfun.PlayGameScreen;


public class Test_Cell  extends ActivityInstrumentationTestCase2<PlayGameScreen> {
	
	private static Cell c;
	private Solo solo;

	public Test_Cell() {
		super(PlayGameScreen.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		Intent i = new Intent();
		i.putExtra("size", 5);
		setActivityIntent(i);
		solo = new Solo(getInstrumentation(),getActivity());
	}

	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
	
	@Test
	public void test_constructor() {
		c = (Cell) solo.getButton("11");
		assertTrue(c != null);
//		assertTrue(c.getSelectVal() == false);
//		assertTrue(c.getState() == 0);
//		assertTrue(c.getColor() == Color.WHITE);
	}

	@Test
	public void test_setSelectVal() {
		c.setSelectVal();
		assertTrue(c.getSelectVal() == true);
		c.setSelectVal();
		assertTrue(c.getSelectVal() == false);
	}
	
	@Test
	public void test_setState() {
		c.setState();
		assertTrue(c.getState() == 1);
		c.setState();
		assertTrue(c.getState() == 2);
		c.setState();
		assertTrue(c.getState() == 0);
	}

	@Test
	public void test_setStateColor() {
		c.setOriginColor(Color.WHITE);
		c.setStateColor();
		assertTrue(c.getColor() == Color.WHITE);
		c.setState();
		c.setStateColor();
		assertTrue(c.getColor() == Color.BLACK);
		c.setState();
		c.setStateColor();
		assertTrue(c.getColor() == Color.WHITE);
		assertTrue(c.getText().equals("?"));
		assertTrue(c.getTextColors().equals(Color.BLUE));
	}
}
