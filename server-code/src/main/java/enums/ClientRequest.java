/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */

package uw.cse403.nonogramfun.enums;

/**
 * A simple enum that represent types of client requests.
 */
public enum ClientRequest {
	CREATE_PUZZLE, GET_PUZZLE, SAVE_SCORE,
	GET_SCORE_BOARD, TEST;
}
