package com.sccl.summerreadingapp.model;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.sccl.summerreadingapp.helper.MiscUtils;

public class GridActivity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5574204293047294079L;
	private static String TYPE = "activity"; 
	private static String NOTES = "notes"; 
	private static String UPDATED_AT = "updatedAt"; 
	
	private int type;
	private String notes;
	private Date lastUpdated;
	
	
	static public GridActivity createGridActivity (JSONObject jsonObj)
	{
		try {
			int type = jsonObj.getInt(TYPE);
			String notes = jsonObj.getString(NOTES);
			String dateString = ""; // jsonObj.getString(UPDATED_AT);
			Date d = MiscUtils.parseDateString(dateString);
			// Date d = null;
			if (dateString != null)
			{
//				d = new Date(dateString);
			}
	
			return new GridActivity(type, notes, d);
		} catch (JSONException e) {
			return null;
		}
	}
	
	public GridActivity (int type, String notes, Date lastUpdated)
	{
		this.type = type;
		this.notes = notes;
		//this.lastUpdated = lastUpdated;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String toJSON(){

	    JSONObject jsonObject = this.toJSONObject();
	    return jsonObject.toString();
	}	

	public JSONObject toJSONObject(){

	    JSONObject jsonObject= new JSONObject();
	    try {
	        jsonObject.put(TYPE, getType());
	        jsonObject.put(NOTES, getNotes());
//	        jsonObject.put(UPDATED_AT, getLastUpdated());
	    } catch (JSONException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
        return jsonObject;
	}	
}
