package com.sccl.summerreadingapp;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.sccl.summerreadingapp.helper.JSONResultParser;
import com.sccl.summerreadingapp.helper.MiscUtils;
import com.sccl.summerreadingapp.helper.SharedPreferenceHelper;
import com.sccl.summerreadingapp.model.Account;
import com.sccl.summerreadingapp.model.Config;
import com.sccl.summerreadingapp.model.User;
 
public class SummerReadingApplication extends Application {
 
    /**
     * Keeps a reference of the application context
     */
    private static Context sContext;
    
    private Account account;
    private User user;

	private Config config;
 
    public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
		// SharedPreferences userDetails = sContext.getSharedPreferences("Account", MODE_PRIVATE);
		// Account account = getAccountFromSharedPreferences(userDetails);
		Account account = SharedPreferenceHelper.getAccountFromSharedPreferences(sContext);
		if (account != null) {
			this.setAccount(account);
		}
		Config config = SharedPreferenceHelper.getConfigFromSharedPreferences(sContext);
		if (config != null) {
			this.setConfig(config);
		}
   }
 
    /**
     * Returns the application context
     *
     * @return application context
     */
    public static Context getContext() {
        return sContext;
    }
 
	private Account getAccountFromSharedPreferences(SharedPreferences userDetails) {
		Account account = null;
		try {
            String accountStr = userDetails.getString("Account", "");
            if (!MiscUtils.empty(accountStr)) {
            	account = JSONResultParser.createAccount(new JSONObject(accountStr));
            }
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return account;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public Config getConfig() {
		return this.config;
	}

}
