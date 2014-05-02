package com.sccl.summerreadingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.sccl.summerreadingapp.adapter.DailyReadingImageAdapter;
import com.sccl.summerreadingapp.model.Account;
import com.sccl.summerreadingapp.model.User;

public class DailyReadingFragment extends Fragment {
	private Account account;
	private User user;
	private DailyReadingImageAdapter imageAdapter;
	private int userIndex;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_daily_reading, container, false);
         
        GridView gridview = (GridView) rootView.findViewById(R.id.gridviewDailyReading);
        imageAdapter = new DailyReadingImageAdapter(container.getContext(), account, user);
		gridview.setAdapter(imageAdapter);

        gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				imageAdapter.addTwenty();
			}

        });

        return rootView;
    }
    
	public void setAccountAndSelectedUserIndex(Account account, int userIndex) {
    	// this.user = user;
    	this.account = account;
    	this.userIndex = userIndex;
    	
    	if (account != null && userIndex >=0) {
    		User users[] = account.getUsers();
    		this.user = users[userIndex];
    	}
    	
    	
    	if (user != null && imageAdapter != null) {
    		imageAdapter.setAccountAndUser(this.account, this.user);
    	}
    }

    
}