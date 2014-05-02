package com.sccl.summerreadingapp.model;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {
	private static String ID = "id"; 
	private static String FIRST_NAME = "firstName"; 
	private static String LAST_NAME = "lastName"; 
	private static String USER_TYPE_ID = "userType"; 
	private static String READING_LOG = "readingLog"; 
	
	private String id;
	private String firstName;
	private String lastName;
	private String userType;
	private GridActivity[] activities;
	private Prize[] prizes;
	private int readingLog;
	
	static public User createUser (JSONObject jsonObj)
	{
		try {
			String id = jsonObj.getString(ID);
			String firstName = jsonObj.getString(FIRST_NAME);
			String lastName = jsonObj.getString(LAST_NAME);
			String userType = jsonObj.getString(USER_TYPE_ID);
			int readingLog = jsonObj.getInt(READING_LOG);
	
			return new User(id, firstName, lastName, userType, readingLog);
		} catch (JSONException e) {
			return null;
		}
	}
	
	public User (String id, String firstName, String lastName, String userType, int readingLog)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = userType;
		this.readingLog = readingLog;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public GridActivity[] getGridActivities() {
		return this.activities;
	}

	public void setGridActivities(GridActivity[] activity) {
		this.activities = activity;
	}

	public Prize[] getPrizes() {
		return this.prizes;
	}

	public void setPrizes(Prize[] prizes) {
		this.prizes = prizes;
	}
	
	public int getReadingLog() {
		return readingLog;
	}

	public void setReadingLog(int readingLog) {
		this.readingLog = readingLog;
	}

	public void addTwentyToReadingLog() {
		this.readingLog++;
	}

	public String toJSON(){

	    JSONObject jsonObject = this.toJSONObject(true);
	    return jsonObject.toString();
	}	

	public JSONObject toJSONObject(boolean addChildren){
	    JSONObject jsonObject = new JSONObject();
	    try {
	        jsonObject.put(ID, getId());
	        jsonObject.put(FIRST_NAME, getFirstName());
	        jsonObject.put(LAST_NAME, getLastName());
	        jsonObject.put(USER_TYPE_ID, getUserType());

	        if (addChildren) {
		        JSONArray jsonGridArray = new JSONArray();
		        GridActivity[] grid = getGridActivities();
		        for (int i = 0; grid != null && i < grid.length; i++){
		        	jsonGridArray.put(grid[i].toJSONObject());
		        }
		        jsonObject.put("activityGrid", jsonGridArray);
	
		        JSONArray jsonPrizeArray = new JSONArray();
		        Prize[] prizes = getPrizes();
		        for (int i = 0; prizes != null && i < prizes.length; i++){
		        	jsonPrizeArray.put(prizes[i].toJSONObject());
		        }
		        jsonObject.put("prizes", jsonPrizeArray);

		        jsonObject.put(READING_LOG, getReadingLog());
	        }
	    } catch (JSONException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        return null;
	    }
	    return jsonObject;	    
	}	
}
