package com.sccl.summerreadingapp;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.sccl.summerreadingapp.adapter.ImageAdapter;
import com.sccl.summerreadingapp.clients.GridActivityClient;
import com.sccl.summerreadingapp.helper.MiscUtils;
import com.sccl.summerreadingapp.model.GridActivity;
import com.sccl.summerreadingapp.model.Prize;
import com.sccl.summerreadingapp.model.User;

public class SummerActivityFragment extends Fragment implements GridActivityAsyncListener{
	private static final int TOTAL_ROWS = 5;
	private static final int TOTAL_COLUMNS = 5;
	private static final int TOTAL_PRIZES = 5;
	public ImageAdapter imageAdapter;
	// GridActivity[] gridActivities = null;
	View rootView;
	User user;
	int selectedIndex = -1;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.fragment_summer_activity, container, false);

        if (user != null) {
            setPrizeImages(rootView, user.getPrizes());
            handlePrizeImageClick(user.getPrizes());
        }
        
        GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
    
        GridActivity[] userGrid = null;
        if (user != null)
        	userGrid = user.getGridActivities();
        // imageAdapter = new ImageAdapter(container.getContext(), this.data);
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

	public void handlePrizeImageClick(Prize[] prizes) {
		int[] resourceIds = {R.id.prize1, R.id.prize2, R.id.prize3, R.id.prize4, R.id.prize5};
		
		for (int i = 0; i < resourceIds.length; i++) {
			setPrizeImageClickListener((ImageView) rootView.findViewById(resourceIds[i]), 
				getPrizeTitle(prizes[i]), getPrizeMessage(prizes[i]));
		}

	}

	public String getPrizeTitle(Prize prize) {
		String title = prize.getState() == 1 ? "Great Job" : "Good Luck";
		return title;
	}

	public String getPrizeMessage(Prize prize) {
    	String message = prize.getState() == 1 ? 
        		"Congratulations on earning the prize. You can collect the prize by visiting library!" : "You need to finish one row/column/diagonal activities to earn prize. ";
		return message;
	}

	public void setPrizeImageClickListener(ImageView prizeView, String title, String message) {
    	final String finalTitle = title;
		final String finalMessage = message;
		prizeView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
				MiscUtils.showAlertDialog(getActivity(), finalTitle, finalMessage);
            }
        });
	}

	public void setPrizeImages(View rootView, Prize[] prizes) {
		if (prizes != null && prizes.length > 3) {
			setPrizeImageBasedOnState(rootView, prizes[0], R.id.prize1);
			setPrizeImageBasedOnState(rootView, prizes[1], R.id.prize2);
			setPrizeImageBasedOnState(rootView, prizes[2], R.id.prize3);
			setPrizeImageBasedOnState(rootView, prizes[3], R.id.prize4);
			setPrizeImageBasedOnState(rootView, prizes[4], R.id.prize5);
		}
	}

	public void setPrizeImageBasedOnState(View rootView, Prize prize, int prizeResourceId) {
		if (prize.getState() == 1) {
			ImageView prizeView = (ImageView) rootView.findViewById(prizeResourceId);
			prizeView.setImageResource(R.drawable.prize_ready_to_claim);

		}
	}

	public void setUser(User user) {
    	this.user = user;
    	if (user != null && imageAdapter != null) {
    		imageAdapter.setGridData(user.getGridActivities());
        	prizeWon(getActivity(), rootView);
    	}
    }

	private void setGridData1(GridActivity[] data) {
    	//this.gridActivities = data;
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
		String loginJSONResponse = userDetails.getString("LoginJSONResponse", "");
		try {
			JSONObject jsonLoginObject = new JSONObject(loginJSONResponse);
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
    	
		if (isLeftDiagonalCompleted(userGrid, total)) {
			total++;
		}

		if (isRightDiagonalCompleted(userGrid)) {
			total++;
		}

        setPrizes(prizes, total);

        setPrizeImages(rootView, prizes);
        
        handlePrizeImageClick(prizes);

        
        MiscUtils.displayToastMessage(context, "Prizes="+total);
    }

	public void setPrizes(Prize[] prizes, int total) {
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

	public boolean isRightDiagonalCompleted(GridActivity[] userGrid) {
		boolean completed = true;
    	for (int i = TOTAL_COLUMNS - 1, totalIterations = 0; totalIterations < TOTAL_ROWS; i+= (TOTAL_COLUMNS - 1), totalIterations++) {
    		if (userGrid[i].getType() != 1) {
				completed = false;
				break;
    		}
    	}
		return completed;
	}

	public boolean isLeftDiagonalCompleted(GridActivity[] userGrid, int totalPrizes) {
		boolean completed = true;
    	for (int i = 0; i < TOTAL_ROWS * TOTAL_COLUMNS; i += (TOTAL_COLUMNS + 1)) {
    		if (userGrid[i].getType() != 1) {
    			completed = false;
				break;
    		}
    	}
		return completed;
	}

	public int countOfCompletedColumns(GridActivity[] userGrid) {
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

	public int countOfCompletedRows(GridActivity[] userGrid) {
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