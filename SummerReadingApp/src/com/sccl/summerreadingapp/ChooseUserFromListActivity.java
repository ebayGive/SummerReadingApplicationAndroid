package com.sccl.summerreadingapp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sccl.summerreadingapp.adapter.ChooseUserAdapter;
	 
public class ChooseUserFromListActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_user_list);
        
        final String users[] = (String[])getIntent().getSerializableExtra("user");
        final String usersTypes[] = (String[])getIntent().getSerializableExtra("userType");
         
        ListView lv = (ListView) findViewById(R.id.user_list);
         
        // Adding items to listview
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.choose_user_detail, R.id.user_name, users);
        ChooseUserAdapter c = new ChooseUserAdapter(this, users, usersTypes);        
        lv.setAdapter(c);       
         
        // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             // selected item 
             String userName = users[position];
               
			 Intent returnIntent = new Intent();
			 returnIntent.putExtra("user", userName);
			 returnIntent.putExtra("index", position);
			 setResult(RESULT_OK,returnIntent);     
			 finish();			
			 }
        });
        
        
    }
     
}