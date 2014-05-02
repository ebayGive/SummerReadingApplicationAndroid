package com.sccl.summerreadingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.sccl.summerreadingapp.adapter.ImageAdapter;
import com.sccl.summerreadingapp.clients.GridActivityClient;
import com.sccl.summerreadingapp.helper.MiscUtils;
import com.sccl.summerreadingapp.model.Account;
import com.sccl.summerreadingapp.model.GridActivity;
import com.sccl.summerreadingapp.model.Prize;
import com.sccl.summerreadingapp.model.User;

public class SummerActivityFragment extends Fragment implements GridActivityAsyncListener{
	private static final int TOTAL_ROWS = 5;
	private static final int TOTAL_COLUMNS = 5;
	private static final int TOTAL_PRIZES = 5;
	public ImageAdapter imageAdapter;
	View rootView;
	User user;
	int selectedIndex = -1;
	private Account account;
	private int userIndex = -1;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.fragment_summer_activity, container, false);

        if (user != null) {
            new PrizeImageHandler(getActivity()).setImagesAndAssignClickHandler(rootView, user.getPrizes());
        }
        
        GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
    
        GridActivity[] userGrid = null;
        if (user != null)
        	userGrid = user.getGridActivities();
        imageAdapter = new ImageAdapter(container.getContext(), userGrid);
		gridview.setAdapter(imageAdapter);

        gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				showAlertDialog(position);
			}

        });
        
        return rootView;
    }

	// public void setAccountAndSelectedUserIndex(User user, Account account, int userIndex) {
	public void setAccountAndSelectedUserIndex(Account account, int userIndex) {
    	// this.user = user;
    	this.account = account;
    	this.userIndex = userIndex;
    	
    	if (account != null && userIndex >=0) {
    		User users[] = account.getUsers();
    		this.user = users[userIndex];
    	}
    	
    	
    	if (user != null && imageAdapter != null) {
    		imageAdapter.setGridData(user.getGridActivities());
        	prizeWon(getActivity(), rootView);
    	}
    }

    private void showAlertDialog(int index) {
    	if (user != null)
    	{
	        FragmentManager fm = getActivity().getSupportFragmentManager();
	        ConfirmSummerActivityFragment alertDialog = ConfirmSummerActivityFragment.newInstance("Confirm Activity", user.getGridActivities()[index]);
	        alertDialog.setTargetFragment(this, 1);
	        alertDialog.setCancelable(false);
	        selectedIndex = index;
	        alertDialog.show(fm, "fragment_alert");
    	}
      }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch(requestCode) {
                case 1:
                    if (resultCode == Activity.RESULT_OK) {
                    	prizeWon(getActivity(), rootView);
                    	imageAdapter.notifyDataSetChanged();
                    	if (selectedIndex != -1 && MiscUtils.isNetworkAvailable(getActivity().getApplicationContext())) {

                    		new GridActivityClient(getActivity(), user, selectedIndex).execute();
                    	}
                    	updateSharedPreferences();
                			//new GridActivityClient(getActivity(), SummerActivityFragment.this, user, gridActivities[selectedIndex]).execute();
                        // After Ok code.
                    } else if (resultCode == Activity.RESULT_CANCELED){
                        // After Cancel code.
                    }

                    break;
            }
        }
 
    private void updateSharedPreferences() {
    	Context c = getActivity().getApplicationContext();
		SharedPreferences userDetails = c.getSharedPreferences("Account", c.MODE_PRIVATE);
		Editor edit = userDetails.edit();
		edit.putString("Account", account.toJSON());
		edit.commit(); 
	}

	private void prizeWon(Context context, View rootView)
    {
    	if (user == null)
    		return;

    	GridActivity[] userGrid = user.getGridActivities();
    	if (userGrid.length < TOTAL_ROWS * TOTAL_COLUMNS)
    		return;

        Prize[] prizes = user.getPrizes();
        if (prizes.length < TOTAL_PRIZES)
        	return;
    	
    	int total = countOfCompletedRows(userGrid);
    			
    	total += countOfCompletedColumns(userGrid);
    	
		if (isLeftDiagonalCompleted(userGrid)) {
			total++;
		}

		if (isRightDiagonalCompleted(userGrid)) {
			total++;
		}

        setPrizes(prizes, total);

        new PrizeImageHandler(getActivity()).setImagesAndAssignClickHandler(rootView, prizes);
        
        MiscUtils.displayToastMessage(context, "Prizes="+total);
    }

	private void setPrizes(Prize[] prizes, int total) {
		if (total > 0)
        	prizes[0].setState(1);

        if (total > 1)
        	prizes[1].setState(1);

        if (total > 2)
        	prizes[2].setState(1);

        if (total > 3)
        	prizes[3].setState(1);

        if (total > 11)
        	prizes[4].setState(1);
	}

	private boolean isRightDiagonalCompleted(GridActivity[] userGrid) {
		boolean completed = true;
    	for (int i = TOTAL_COLUMNS - 1, totalIterations = 0; totalIterations < TOTAL_ROWS; i+= (TOTAL_COLUMNS - 1), totalIterations++) {
    		if (userGrid[i].getType() != 1) {
				completed = false;
				break;
    		}
    	}
		return completed;
	}

	private boolean isLeftDiagonalCompleted(GridActivity[] userGrid) {
		boolean completed = true;
    	for (int i = 0; i < TOTAL_ROWS * TOTAL_COLUMNS; i += (TOTAL_COLUMNS + 1)) {
    		if (userGrid[i].getType() != 1) {
    			completed = false;
				break;
    		}
    	}
		return completed;
	}

	private int countOfCompletedColumns(GridActivity[] userGrid) {
		int total = 0;
		for (int i = 0; i < TOTAL_COLUMNS; i++) {
    		boolean good = true;
    		for (int j = i, totalIterations = 0;  totalIterations < TOTAL_ROWS; j += TOTAL_COLUMNS, totalIterations++) {
    			if (userGrid[j].getType() != 1) {
    				good = false;
    				break;
    			}
    		}

    		if (good) {
    			total++;
    		}
    			
    	}
		return total;
	}

	private int countOfCompletedRows(GridActivity[] userGrid) {
		int total = 0;
		for (int i = 0; i < TOTAL_ROWS; i++) {
    		int firstIndex = i * TOTAL_COLUMNS;
    		boolean good = true;
    		for (int j = firstIndex; j < firstIndex + TOTAL_COLUMNS; j++) {
    			if (userGrid[j].getType() != 1) {
    				good = false;
    				break;
    			}
    		}

    		if (good) {
    			total++;
    		}
    			
    	}
		return total;
	}

	@Override
	public void onResult(GridActivity grid) {
		// TODO Auto-generated method stub
		
	}
}