package com.sccl.summerreadingapp;
import android.app.Application;
import android.content.Context;

import com.sccl.summerreadingapp.model.Account;
import com.sccl.summerreadingapp.model.User;
 
public class SummerReadingApplication extends Application {
 
    /**
     * Keeps a reference of the application context
     */
    private static Context sContext;
    
    private Account account;
    private User user;
 
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
 
    }
 
    /**
     * Returns the application context
     *
     * @return application context
     */
    public static Context getContext() {
        return sContext;
    }
 
}
