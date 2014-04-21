package com.sccl.summerreadingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sccl.summerreadingapp.clients.RegistrationClient;
import com.sccl.summerreadingapp.helper.MiscUtils;
import com.sccl.summerreadingapp.model.Login;
 
public class RegistrationActivity extends Activity implements RegistrationAsyncListener{
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
			    EditText userNameEdit = (EditText) findViewById(R.id.username);
			    String userName = userNameEdit.getText().toString();
			    
			    EditText passwordEdit = (EditText) findViewById(R.id.password);
			    String password = passwordEdit.getText().toString();

				if (MiscUtils.isNetworkAvailable(getApplicationContext()))
				{
				    new RegistrationClient(RegistrationActivity.this, RegistrationActivity.this).execute(userName, password);
				}
				else
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
					builder.setTitle("Network Error");
					builder.setMessage("No user logged in. You need to enable network to login.");
					builder.setCancelable(false);
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                //do things
				       		dialog.dismiss();
				           }
				       });
					AlertDialog alertDialog = builder.create();
					alertDialog.show();
				}
			}
 
		});
 
        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }

	@Override
	public void onResult(Login login) {
		// TODO Auto-generated method stub
		
	}
}