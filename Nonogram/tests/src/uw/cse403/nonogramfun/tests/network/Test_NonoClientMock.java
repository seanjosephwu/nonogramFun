/**
 * CSE 403 AA
 * Project Nonogram: Backend Test
 * @author  Sean Wu
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 * Tests NonoClient: Black Box
 * Mock Test
 */

package uw.cse403.nonogramfun.tests.network;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expectLastCall;
import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uw.cse403.nonogramfun.enums.ClientRequest;
import uw.cse403.nonogramfun.enums.Difficulty;
import uw.cse403.nonogramfun.enums.ServerResponse;
import uw.cse403.nonogramfun.network.NonoClient;
import uw.cse403.nonogramfun.network.NonoNetwork;
import uw.cse403.nonogramfun.nonogram.NonoPuzzle;
import uw.cse403.nonogramfun.utility.NonoUtil;


public class Test_NonoClientMock extends TestCase {
	private static final Difficulty EASY = Difficulty.EASY;
	private static final Integer BLACK = -16777216;
	private static final Integer WHITE = -1;
	private static final Integer[][] EXP_ARR_1 = {{BLACK, BLACK, WHITE, BLACK, WHITE},
												  {BLACK, WHITE, BLACK, WHITE, BLACK},
												  {WHITE, BLACK, WHITE, BLACK, WHITE},
												  {WHITE, BLACK, WHITE, BLACK, WHITE},
												  {WHITE, WHITE, BLACK, WHITE, WHITE}};
	private static final Integer EXP_BG_COLOR_1 = WHITE;
	private static final String EXP_NAME_1 = "Ice Cream";
	private static final NonoPuzzle PUZZLE_1 = NonoPuzzle.createNonoPuzzle(EXP_ARR_1, EXP_BG_COLOR_1, EXP_NAME_1);
	private NonoNetwork mockNetwork;
	
	@Before
	public void setUp() {
		mockNetwork = createNiceMock(NonoNetwork.class);
		NonoClient.setNetwork(mockNetwork);
	}
	
	@After
	public void cleanUp() {
		NonoClient.setNetwork(null);
	}
	
	@Test
	public void test_createPuzzle() {
		JSONObject requestJSON = new JSONObject();
		JSONObject requestJSON2 = new JSONObject();
		try {
			NonoUtil.putClientRequest(requestJSON, ClientRequest.CREATE_PUZZLE);
			NonoUtil.putColorArray(requestJSON, EXP_ARR_1);
			NonoUtil.putColor(requestJSON, EXP_BG_COLOR_1);
			NonoUtil.putString(requestJSON, EXP_NAME_1);
			NonoUtil.putServerResponse(requestJSON2, ServerResponse.SUCCESS);
			
			
			mockNetwork.sendMessage(requestJSON);
			expectLastCall().once();
			mockNetwork.readMessageJSON();
			expectLastCall().andReturn(requestJSON2);
			expectLastCall().once();
		} catch (Exception e) {
			fail(e.getLocalizedMessage());
		}
		mockNetwork.close();
		expectLastCall().once();
		EasyMock.replay(mockNetwork);
		
		
		try {
			NonoClient.createPuzzle(EXP_ARR_1, EXP_BG_COLOR_1, EXP_NAME_1);
		} catch (Exception e) {
			fail(e.getLocalizedMessage());
		}
	}
	
	//--Test getPuzzle------------------------------------------------------------------------
	
	@Test
	public void test_getPuzzle() {
		JSONObject requestJSON = new JSONObject();
		JSONObject requestJSON2 = new JSONObject();
		try {
			NonoUtil.putClientRequest(requestJSON, ClientRequest.GET_PUZZLE);
			NonoUtil.putDifficulty(requestJSON, EASY);
			
			NonoUtil.putServerResponse(requestJSON2, ServerResponse.SUCCESS);
			NonoUtil.putNonoPuzzle(requestJSON2, PUZZLE_1);
			
			mockNetwork.sendMessage(requestJSON);
			expectLastCall().once();
			mockNetwork.readMessageJSON();
			expectLastCall().andStubReturn(requestJSON2);
		} catch (Exception e) {
			fail (e.getLocalizedMessage());
		}

		mockNetwork.close();
		expectLastCall().once();
		
		EasyMock.replay(mockNetwork);
		
		try {
			NonoPuzzle puzzle = NonoClient.getPuzzle(EASY);
			assert(puzzle != null);
			assert(puzzle.getDifficulty().equals(EASY));
			assert(puzzle.equals(PUZZLE_1));
		} catch (Exception e) {
			fail(e.getLocalizedMessage());
		}
	}
}
