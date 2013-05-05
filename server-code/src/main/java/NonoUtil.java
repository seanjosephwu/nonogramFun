/**
 * CSE 403 AA
 * Project Nonogram: Backend
 * @author  HyeIn Kim, Sean Wu
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */

import android.graphics.Color;
import java.io.*;
import java.nio.*;
import org.json.*;
import enums.*;
import com.google.gson.*;

/**
 * 
 *
 */
public class NonoUtil {
	public static final String JSON_HEADER_TAG = "JSON_Header";
	public static final String JSON_ERROR_MSG_TAG = "JSON_Error_Msg";
	public static final String JSON_COLOR_ARRAY_TAG = "JSON_Color_Array";
	public static final String JSON_COLOR_TAG = "JSON_Color";
	public static final String JSON_NONOPUZZLE_TAG = "JSON_NonoPuzzle";
	public static final String JSON_DIFFICULTY_TAG = "JSON_Difficulty";
	public static final String JSON_STRING_TAG = "JSON_String";
	private static final Gson JSON = new Gson();
	
	
	/**
	 * Converts a given integer to byte, in little endian
	 */
	public static byte[] intToByte(int i) {
		ByteBuffer b = ByteBuffer.allocate(4);
		b.order(ByteOrder.LITTLE_ENDIAN);
		b.putInt(i);
		byte buf[] = b.array();
		return buf;
	}
	
	/**
	 * Converts given byte array to integer
	 */
	public static int byteToInt(byte buf[]) {
		ByteBuffer b = ByteBuffer.wrap(buf);
		b.order(ByteOrder.LITTLE_ENDIAN);
		return b.getInt();
	}
	
	public static byte[] objecToByte(Object object) throws IOException {
		ByteArrayOutputStream boStream = new ByteArrayOutputStream();
	    ObjectOutputStream ooStream = new ObjectOutputStream(boStream);
	    ooStream.writeObject(object);
	    ooStream.flush();
	    ooStream.close();
	    boStream.close();
	    return boStream.toByteArray();
	}
	
	public static Object byteToObject(byte[] byteArray) throws IOException, ClassNotFoundException {
		ByteArrayInputStream biStream = new ByteArrayInputStream(byteArray);
        ObjectInputStream oiStream = new ObjectInputStream(biStream);
        Object object = oiStream.readObject();
        oiStream.close();
        return object;
	}
	
	public static String objectToJSON(Object object) {
		return JSON.toJson(object);
	}
	
	
	
	
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
	
	public static void putObject(JSONObject requestJSON, String key, Object val) throws JSONException {
		requestJSON.put(key, NonoUtil.objectToJSON(val));
	}
	
	
	
	
	
	
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

}
