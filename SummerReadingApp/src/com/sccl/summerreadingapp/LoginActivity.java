package com.sccl.summerreadingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sccl.summerreadingapp.clients.LoginClient;
import com.sccl.summerreadingapp.helper.MiscUtils;
import com.sccl.summerreadingapp.model.Login;
 
public class LoginActivity extends Activity implements LoginAsyncListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.login);
 
        Button loginButton = (Button) findViewById(R.id.btnLogin);
 		loginButton.setOnClickListener(new View.OnClickListener() {
 			@Override
			public void onClick(View v) {
				if (MiscUtils.isNetworkAvailable(getApplicationContext())) {
				    EditText userNameEdit = (EditText) findViewById(R.id.username);
				    String userName = userNameEdit.getText().toString();
				    
				    EditText passwordEdit = (EditText) findViewById(R.id.password);
				    String password = passwordEdit.getText().toString();

				    new LoginClient(LoginActivity.this, LoginActivity.this).execute(userName, password);
				}
				else {
					MiscUtils.showAlertDialog(LoginActivity.this, "Network Error", "User not logged in. You need to enable network to login.");
				}
			}
 
		});
 
        TextView registerButton = (TextView) findViewById(R.id.link_to_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(i);
            }
        });
        
    }
    
    public void onResult(Login login) {
    	if (login != null) {
			 Intent returnIntent = new Intent();
			 returnIntent.putExtra("user", login);
			 setResult(RESULT_OK,returnIntent);     
			 finish();
    	}
    	else {
    		MiscUtils.showAlertDialog(this, "Login Error", "Unable to login. Check the credentials");
    	}
    }
}