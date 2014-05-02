package com.sccl.summerreadingapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.sccl.summerreadingapp.clients.AddUserClient;
import com.sccl.summerreadingapp.helper.MiscUtils;
import com.sccl.summerreadingapp.model.Config;
import com.sccl.summerreadingapp.model.User;
import com.sccl.summerreadingapp.model.UserType;
 
public class AddUserActivity extends Activity implements AddUserAsyncListener {
	String accountId;
	ArrayList<User> userList = new ArrayList<User>();
	private ArrayList<UserType> readerTypeList;
	Config config;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);
        accountId = (String)getIntent().getStringExtra("accountId");
        
		SummerReadingApplication summerReadingApplication = (SummerReadingApplication) getApplicationContext();
		config = summerReadingApplication.getConfig();
		
		readerTypeList = config.getUserTypes();
        
		populateUserTypes();
    }

	private void populateUserTypes() {
		 
		Spinner readerTypeSpinner = (Spinner) findViewById(R.id.reader_type);
		List<String> readerTypeNames = new ArrayList<String>();
		for (UserType userType:readerTypeList) {
			readerTypeNames.add(userType.getName());
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, readerTypeNames);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		readerTypeSpinner.setAdapter(dataAdapter);
	  }
	
	
    public void onButtonClickHandler(View v) {
        if( v.getId() == R.id.btnAdd )
          handleAdd();
        else // if( v.getId() == R.id.btnDone )
            handleDone();
      }
    
	private void handleAdd() {
	    EditText firstNameEdit = (EditText) findViewById(R.id.add_fname);
	    String firstName = firstNameEdit.getText().toString();
	    
	    EditText lastNameEdit = (EditText) findViewById(R.id.add_lname);
	    String lastName = lastNameEdit.getText().toString();
	    
	    Spinner readerTypeSpinner = (Spinner) findViewById(R.id.reader_type);
	    String readerType = String.valueOf(readerTypeSpinner.getSelectedItem());
	    
	    UserType userType = config.getUserType(readerType);
	    String userTypeId = userType.getId();

//	    String id = UserType.getId(readerType);
	    
	    if (MiscUtils.isNetworkAvailable(getApplicationContext())) {
		    new AddUserClient(AddUserActivity.this, AddUserActivity.this).execute(firstName, lastName, userTypeId, accountId);
		}
		else {
			MiscUtils.showAlertDialog(AddUserActivity.this, "Network Error", "User not added. You need to enable network to login.");
		}
	}

    private void handleDone() {
		 Intent returnIntent = new Intent();
		 returnIntent.putExtra("user", userList);
		 setResult(RESULT_OK,returnIntent);     
		 finish();			
	}

	@Override
	public void onResult(User user) {
		// TODO Auto-generated method stub
		userList.add(user);
	}
}