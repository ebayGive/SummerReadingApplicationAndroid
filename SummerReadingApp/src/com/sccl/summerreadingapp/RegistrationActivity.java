package com.sccl.summerreadingapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sccl.summerreadingapp.clients.RegistrationClient;
import com.sccl.summerreadingapp.helper.MiscUtils;
import com.sccl.summerreadingapp.model.Account;
 
public class RegistrationActivity extends Activity implements RegistrationAsyncListener{
    private static final int REQUEST_CODE_REGISTER = 1;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);
 
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        Button button = (Button) findViewById(R.id.btnRegister);
        
		button.setOnClickListener(new View.OnClickListener() {
 
			@Override
			public void onClick(View v) {
			    EditText userNameEdit = (EditText) findViewById(R.id.reg_username);
			    String userName = userNameEdit.getText().toString();
			    
			    EditText passwordEdit = (EditText) findViewById(R.id.reg_password);
			    String password = passwordEdit.getText().toString();

			    EditText emailEdit = (EditText) findViewById(R.id.reg_email);
			    String email = emailEdit.getText().toString();

			    EditText fullNameEdit = (EditText) findViewById(R.id.reg_fullname);
			    String fullName = fullNameEdit.getText().toString();
			    String branch = "C36F2906-1173-459D-B5BC-73AD058673A3";
			    
			    if (MiscUtils.isNetworkAvailable(getApplicationContext())) {
				    new RegistrationClient(RegistrationActivity.this, RegistrationActivity.this).execute(userName, password, email, fullName, branch);
				}
				else {
					MiscUtils.showAlertDialog(RegistrationActivity.this, "Network Error", "User not registered. You need to enable network to login.");
				}
			}
 
		});
 
        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                // startActivity(i);
        		startActivityForResult(i, REQUEST_CODE_REGISTER);
            }
        });
    }

	@Override
	public void onResult(Account account) {
		 Intent returnIntent = new Intent();
		 returnIntent.putExtra("account", account);
		 setResult(RESULT_OK,returnIntent);     
		 finish();			
		
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK){
			if (requestCode == REQUEST_CODE_REGISTER)
				return;
		}
	}//onActivityResult
}