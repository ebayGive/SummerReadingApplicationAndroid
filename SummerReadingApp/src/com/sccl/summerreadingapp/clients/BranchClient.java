package com.sccl.summerreadingapp.clients;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.sccl.summerreadingapp.SummerReadingApplication;
import com.sccl.summerreadingapp.helper.ServiceInvoker;
import com.sccl.summerreadingapp.model.Branch;
import com.sccl.summerreadingapp.model.GridCell;

/**
 * Async task class to get json by making HTTP call
 * */
public class BranchClient extends AsyncTask<Void, Void, Void> {
	
	private static final String BRANCH_REQUEST = "http://hackathon.ebaystratus.com/branches.json";
	private static final String USER_TYPE_REQUEST = "http://hackathon.ebaystratus.com/userTypes.json";
	private static final String GRID_CELL_REQUEST = "http://hackathon.ebaystratus.com/grids.json";
	private Activity parent;
	private ProgressDialog pDialog;
	
	// JSON Node names

	public BranchClient(Activity parent) {
		super();
		this.parent = parent;
	}

	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(parent);
        pDialog.setMessage("Setting Grid result...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected Void doInBackground(Void... arg0) {
    	SummerReadingApplication summerReadingApplication = (SummerReadingApplication) parent.getApplicationContext();
    	ServiceInvoker serviceInvoker = new ServiceInvoker();
    	getBranchFromUrl(summerReadingApplication, serviceInvoker);
    	getGridCellFromUrl(summerReadingApplication, serviceInvoker);

    	return null;
    }

	private void getBranchFromUrl(
			SummerReadingApplication summerReadingApplication,
			ServiceInvoker serviceInvoker) {
		String jsonStr = serviceInvoker.invoke(BRANCH_REQUEST, ServiceInvoker.GET);
    	Branch branch = null;

    	Log.d("Response: ", "> " + jsonStr);

    	if (jsonStr != null) {
    		try {
    			JSONObject jsonObj = new JSONObject(jsonStr);
    			branch = Branch.createBranch(jsonObj);
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}
    	} else {
    		Log.e("ServiceHandler", "Couldn't get any data from the url");
    	}

    	if (branch != null) {
    		summerReadingApplication.setBranch(branch);
    	}
	}

	private void getGridCellFromUrl(
			SummerReadingApplication summerReadingApplication,
			ServiceInvoker serviceInvoker) {
		String jsonStr = serviceInvoker.invoke(GRID_CELL_REQUEST, ServiceInvoker.GET);
    	GridCell gridCell = null;

    	Log.d("Response: ", "> " + jsonStr);

    	if (jsonStr != null) {
    		try {
    			JSONObject jsonObj = new JSONObject(jsonStr);
    			gridCell = GridCell.createGridCell(jsonObj);
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}
    	} else {
    		Log.e("ServiceHandler", "Couldn't get any data from the url");
    	}

    	if (gridCell != null) {
    		summerReadingApplication.setGridCell(gridCell);
    	}
	}

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();
//        listener.onResult(result);
    }

}
