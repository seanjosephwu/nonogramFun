/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */


package uw.cse403.nonogramfun.utility;
import java.io.*;
import java.nio.*;
import org.json.*;
import uw.cse403.nonogramfun.enums.*;
import uw.cse403.nonogramfun.nonogram.*;
import com.google.gson.*;


/**
 * NonoUtil class provides utility methods to make networking code cleaner,
 * such as converting object to byte and vice versa, and putting parameters
 * to JSON Object or getting result out of JSON Object.
 */
public class NonoUtil {
	public static final String JSON_HEADER_TAG = "JSON_Header";
	public static final String JSON_ERROR_MSG_TAG = "JSON_Error_Msg";
	public static final String JSON_COLOR_ARRAY_TAG = "JSON_Color_Array";
	public static final String JSON_COLOR_TAG = "JSON_Color";
	public static final String JSON_NONOPUZZLE_TAG = "JSON_NonoPuzzle";
	public static final String JSON_DIFFICULTY_TAG = "JSON_Difficulty";
	public static final String JSON_STRING_TAG = "JSON_String";
	public static final String JSON_INT_TAG = "JSON_Integer";
	public static final String JSON_SCORE_BOARD_TAG = "JSON_ScoreBoard";
	private static final Gson JSON = new Gson();


	// Private constructor
	private NonoUtil() { }

	/**
	 * Converts a given integer to byte, in little endian
	 * @param i An integer to be converted to byte
	 * @return A byte array stores byte representation of given integer
	 */
	public static byte[] intToByte(int i) {
		ByteBuffer b = ByteBuffer.allocate(4);
		b.order(ByteOrder.LITTLE_ENDIAN);
		b.putInt(i);
		byte buf[] = b.array();
		return buf;
	}

	/**
	 * Converts given byte array to integer.
	 * @param buf A byte array to be converted to integer
	 * @return An integer representation of the given byte array.
	 * @throws IllegalArgumentException if the passed buffer is null.
	 * @throws BufferUnderflowException If there are fewer than four bytes in the buf.
	 */
	public static int byteToInt(byte buf[]) {
		ParameterPolice.checkIfNull(buf, "Byte array");

		ByteBuffer b = ByteBuffer.wrap(buf);
		b.order(ByteOrder.LITTLE_ENDIAN);
		return b.getInt();
	}

	/**
	 * Converts the given object into byte array.
	 * @param object An object to be converted to byte array
	 * @return A byte array stores byte represntation of the given object.
	 * @throws IllegalArgumentException if the passed object is null.
	 * @throws IOException if any IO error occurs.
	 */
	public static byte[] objecToByte(Object object) throws IOException {
		ParameterPolice.checkIfNull(object, "Object");

		ByteArrayOutputStream boStream = new ByteArrayOutputStream();
	    ObjectOutputStream ooStream = new ObjectOutputStream(boStream);
	    ooStream.writeObject(object);
	    ooStream.flush();
	    ooStream.close();
	    boStream.close();
	    return boStream.toByteArray();
	}

	/**
	 * Converts a byte array to object.
	 * @param byteArray A byte array to be converted to object.
	 * @return An object representation of given byte array
	 * @throws IllegalArgumentException if the passed object is null.
	 * @throws IOException if any IO error occurs.
	 * @throws ClassNotFoundException if Class of a serialized object cannot be found.
	 */
	public static Object byteToObject(byte[] byteArray) throws IOException, ClassNotFoundException {
		ParameterPolice.checkIfNull(byteArray, "Byte Array");

		ByteArrayInputStream biStream = new ByteArrayInputStream(byteArray);
	    ObjectInputStream oiStream = new ObjectInputStream(biStream);
	    Object object = oiStream.readObject();
	    oiStream.close();
	    return object;
	}
	
	/**
	 * Converts an object into JSON string.
	 * @param object An object to be converted to JSON string
	 * @return A JSON string representation of the given object.
	 * @throws IllegalArgumentException if the passed object is null.
	 */
	public static String objectToJSON(Object object) {
		ParameterPolice.checkIfNull(object, "Object");
		return JSON.toJson(object);
	}

	//--Wrapper functions (Adding parameters into JSON Object)-----------------------------------------------------

	public static void putClientRequest(JSONObject requestJSON, ClientRequest cr) throws JsonSyntaxException, JSONException {
		requestJSON.put(JSON_HEADER_TAG, NonoUtil.objectToJSON(cr));
	}

	public static void putServerResponse(JSONObject requestJSON, ServerResponse sr) throws JsonSyntaxException, JSONException {
		requestJSON.put(JSON_HEADER_TAG, NonoUtil.objectToJSON(sr));
	}

	public static void putColorArray(JSONObject requestJSON, Integer[][] cArray) throws JsonSyntaxException, JSONException {
		requestJSON.put(JSON_COLOR_ARRAY_TAG, NonoUtil.objectToJSON(cArray));
	}

	public static void putColor(JSONObject requestJSON, Integer c) throws JsonSyntaxException, JSONException {
		requestJSON.put(JSON_COLOR_TAG, NonoUtil.objectToJSON(c));
	}

	public static void putNonoPuzzle(JSONObject requestJSON, NonoPuzzle puzzle) throws JsonSyntaxException, JSONException {
		requestJSON.put(JSON_NONOPUZZLE_TAG, NonoUtil.objectToJSON(puzzle));
	}

	public static void putDifficulty(JSONObject requestJSON, Difficulty d) throws JsonSyntaxException, JSONException {
		requestJSON.put(JSON_DIFFICULTY_TAG, NonoUtil.objectToJSON(d));
	}

	public static void putString(JSONObject requestJSON, String s) throws JsonSyntaxException, JSONException {
		requestJSON.put(JSON_STRING_TAG, NonoUtil.objectToJSON(s));
	}

	public static void putScore(JSONObject requestJSON, int score) throws JsonSyntaxException, JSONException {
		requestJSON.put(JSON_INT_TAG, score);
	}
	
	public static void putScoreBoard(JSONObject requestJSON, NonoScoreBoard scoreBoard) throws JsonSyntaxException, JSONException {
		requestJSON.put(JSON_SCORE_BOARD_TAG, NonoUtil.objectToJSON(scoreBoard));
	}
	
	public static void putObject(JSONObject requestJSON, String key, Object val) throws JSONException {
		requestJSON.put(key, NonoUtil.objectToJSON(val));
	}
	
	
	//--Wrapper functions (Getting result from JSON Object)-----------------------------------------------------
	
	public static ClientRequest getClientRequest(JSONObject responseJSON) throws JsonSyntaxException, JSONException {
		return JSON.fromJson(responseJSON.getString(JSON_HEADER_TAG), ClientRequest.class);
	}
	
	public static ServerResponse getServerResponse(JSONObject responseJSON) throws JsonSyntaxException, JSONException {
		return JSON.fromJson(responseJSON.getString(JSON_HEADER_TAG), ServerResponse.class);
	}
	
	public static Integer[][] getColorArray(JSONObject responseJSON) throws JsonSyntaxException, JSONException {
		return JSON.fromJson(responseJSON.getString(JSON_COLOR_ARRAY_TAG), Integer[][].class);
	}

	public static Integer getColor(JSONObject responseJSON) throws JsonSyntaxException, JSONException {
		return JSON.fromJson(responseJSON.getString(JSON_COLOR_TAG), Integer.class);
	}

	public static NonoPuzzle getNonoPuzzle(JSONObject responseJSON) throws JsonSyntaxException, JSONException {
		return JSON.fromJson(responseJSON.getString(JSON_NONOPUZZLE_TAG), NonoPuzzle.class);
	}

	public static Difficulty getDifficulty(JSONObject responseJSON) throws JsonSyntaxException, JSONException {
		return JSON.fromJson(responseJSON.getString(JSON_DIFFICULTY_TAG), Difficulty.class);
	}

	public static String getString(JSONObject responseJSON) throws JsonSyntaxException, JSONException {
		return responseJSON.getString(JSON_STRING_TAG);
	}

	public static int getScore(JSONObject responseJSON) throws JsonSyntaxException, JSONException {
		return responseJSON.getInt(JSON_INT_TAG);
	}
	
	public static NonoScoreBoard getScoreBoard(JSONObject responseJSON) throws JsonSyntaxException, JSONException {
		return JSON.fromJson(responseJSON.getString(JSON_SCORE_BOARD_TAG), NonoScoreBoard.class);
	}
	
	public static StackTraceElement[] getErrorMsg(JSONObject responseJSON) throws JsonSyntaxException, JSONException {
		return JSON.fromJson(responseJSON.getString(JSON_ERROR_MSG_TAG), StackTraceElement[].class);
	}
}

