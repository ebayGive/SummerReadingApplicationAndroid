package com.sccl.summerreadingapp.clients;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.sccl.summerreadingapp.LoginActivity;
import com.sccl.summerreadingapp.helper.JSONResultParser;
import com.sccl.summerreadingapp.helper.ServiceInvoker;
import com.sccl.summerreadingapp.model.Account;

/**
 * Async task class to get json by making HTTP call
 * */
public class AccountClient extends AsyncTask<String, Void, Account> {
	
	private LoginActivity parent;
	private ProgressDialog pDialog;
	

	public AccountClient(LoginActivity parent) {
		super();
		this.parent = parent;
	}

	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(parent);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected Account doInBackground(String... arg0) {
        // Creating service handler class instance
        ServiceInvoker sh = new ServiceInvoker();
        String url = "http://hackathon.ebaystratus.com/accounts/2A8F81D9-F040-445C-BFA7-A44C26E9968B";

        // Making a request to url and getting response
       String jsonStr = sh.makeServiceCall(url, ServiceInvoker.GET);
       // String jsonStr = "";
       Account account = null;

        Log.d("Response: ", "> " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                account = JSONResultParser.createAccount(jsonObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }

        return account;
    }

    @Override
    protected void onPostExecute(Account result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();
//        parent.onAccountDetailsResult(result);
    }

}
