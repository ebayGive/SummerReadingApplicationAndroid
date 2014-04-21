package com.sccl.summerreadingapp.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sccl.summerreadingapp.model.Account;
import com.sccl.summerreadingapp.model.GridActivity;
import com.sccl.summerreadingapp.model.Login;
import com.sccl.summerreadingapp.model.Prize;
import com.sccl.summerreadingapp.model.User;

public class JSONResultParser {
    private static final String ACCOUNT = "account";
    private static final String USERS = "users";
    private static final String USER_GRIDS = "activityGrid";
    private static final String USER_PRIZES = "prizes";
	private static String AUTH_TOKEN = "authToken"; 
	

	static public Login createLogin(JSONObject jsonLoginObject) throws JSONException {
        String authToken = jsonLoginObject.getString(AUTH_TOKEN);
        JSONObject jsonAccountObj = jsonLoginObject.getJSONObject(ACCOUNT);
        Account account = createAccount(jsonAccountObj);
        
        return new Login(authToken, account);
	}

	static public Account createAccount(JSONObject jsonAccountObject) throws JSONException {
		Account account = Account.createAccount(jsonAccountObject);
		
		// Getting JSON User Array node
		JSONArray jsonUserArray = jsonAccountObject.getJSONArray(USERS);
		if (jsonUserArray.length() > 0)
			account.setUsers(createUserArray(jsonUserArray));
		return account;
	}

	static private User[] createUserArray(JSONArray jsonUserArray)
			throws JSONException {
		User users[] = new User[jsonUserArray.length()];

		// looping through All Users
		for (int i = 0; i < jsonUserArray.length(); i++) {
		    JSONObject userJson = jsonUserArray.getJSONObject(i);
		    users[i] = createUser(userJson);
		}
		return users;
	}

	static public User createUser(JSONObject jsonObj) throws JSONException {
		User user = User.createUser(jsonObj);
		
	    JSONArray activityArray = jsonObj.getJSONArray(USER_GRIDS);
	    if (activityArray.length() > 0)
	    	user.setGridActivities(createGridActivityArray(activityArray));

	    JSONArray prizesArray = jsonObj.getJSONArray(USER_PRIZES);
	    if (prizesArray.length() > 0)
	    	user.setPrizes(createPrizeArray(prizesArray));
	    
	    return user;
	}

	static private GridActivity[] createGridActivityArray(JSONArray activityArray)
			throws JSONException {
		GridActivity activity[] = new GridActivity[activityArray.length()];
	    // looping through All Grids
	    for (int j = 0; j < activityArray.length(); j++) {
	        JSONObject activityJson = activityArray.getJSONObject(j);
	        activity[j] = GridActivity.createGridActivity(activityJson);
	    }
		return activity;
	}

	static private Prize[] createPrizeArray(JSONArray prizeArray)
			throws JSONException {
		Prize prize[] = new Prize[prizeArray.length()];
	    // looping through All Grids
	    for (int j = 0; j < prizeArray.length(); j++) {
	        JSONObject prizeJson = prizeArray.getJSONObject(j);
	        prize[j] = Prize.createPrize(prizeJson);
	    }
		return prize;
	}
}