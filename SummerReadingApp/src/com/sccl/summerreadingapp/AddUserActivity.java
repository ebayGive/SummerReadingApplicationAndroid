package com.sccl.summerreadingapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.sccl.summerreadingapp.clients.AddUserClient;
import com.sccl.summerreadingapp.helper.MiscUtils;
import com.sccl.summerreadingapp.model.User;
import com.sccl.summerreadingapp.model.UserType;
 
public class AddUserActivity extends Activity implements AddUserAsyncListener {
	String accountId;
	ArrayList<User> userList = new ArrayList<User>();;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);
        accountId = (String)getIntent().getStringExtra("accountId");
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

	    String id = UserType.getId(readerType);
	    
	    if (MiscUtils.isNetworkAvailable(getApplicationContext())) {
		    new AddUserClient(AddUserActivity.this, AddUserActivity.this).execute(firstName, lastName, id, accountId);
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