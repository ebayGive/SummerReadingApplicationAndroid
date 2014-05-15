package com.sccl.summerreadingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.sccl.summerreadingapp.adapter.DailyReadingImageAdapter;
import com.sccl.summerreadingapp.helper.MiscUtils;
import com.sccl.summerreadingapp.helper.SharedPreferenceHelper;
import com.sccl.summerreadingapp.model.Account;
import com.sccl.summerreadingapp.model.User;

public class DailyReadingFragment extends Fragment {
	private Account account;
	private User user;
	private DailyReadingImageAdapter imageAdapter;
	private int userIndex;
	LinearLayout batteryLayout, robotImageLayout;
	Button addTwentyButton;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_daily_reading, container, false);
         
        batteryLayout= (LinearLayout) rootView.findViewById(R.id.batteryLayout);
        robotImageLayout= (LinearLayout) rootView.findViewById(R.id.robotFullImageLayout);
        addTwentyButton = (Button) rootView.findViewById(R.id.btn_ok);

        GridView gridview = (GridView) rootView.findViewById(R.id.gridviewDailyReading);
        imageAdapter = new DailyReadingImageAdapter(container.getContext(), account, user);
		gridview.setAdapter(imageAdapter);

		if (user!= null && user.getReadingLog() >= 45 * 20) {
			hideBatteryAndShowRobot(true);
			return rootView;
		}
        
		
/*        gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				handleAddTwenty();
			}

        });
*/
        addTwentyButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				handleAddTwenty();
			}
		});

        return rootView;
    }
    
	private void handleAddTwenty() {
		imageAdapter.addTwenty();
		if (MiscUtils.isNetworkAvailable(getActivity().getApplicationContext())) {
			// new GridActivityClient(getActivity(), user, selectedIndex).execute();
		}
    	updateAccountInSharedPreferences();
    	displayProgressMessage(getActivity(), user.getReadingLog() / 20);
	}

	private void displayProgressMessage(FragmentActivity activity, int readingLogDividedBy20) {
		if (readingLogDividedBy20 == 45) {
			hideBatteryAndShowRobot(true);
		}
		else if (readingLogDividedBy20 % 9 == 0) {
			int byNine = readingLogDividedBy20 / 9;
			String message = "You have read " + byNine;
			
			if (byNine == 1)
				message += (" hour!");
			else
				message += (" hours!");
				
			MiscUtils.showAlertDialog(getActivity(), "Great Job!", message);
		}
	}

	private void hideBatteryAndShowRobot(boolean readingFinished) {
		batteryLayout.setVisibility(readingFinished ? View.INVISIBLE : View.VISIBLE);
		robotImageLayout.setVisibility(readingFinished ? View.VISIBLE : View.INVISIBLE);
		addTwentyButton.setVisibility(readingFinished ? View.INVISIBLE : View.VISIBLE);
	}

	private void updateAccountInSharedPreferences() {
    	Context c = getActivity().getApplicationContext();
    	SharedPreferenceHelper.storeAccountDataIntoSharedPreferences(c, null, account);
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
    		hideBatteryAndShowRobot(user.getReadingLog() >= 45 * 20);
    	}
    }

    
}