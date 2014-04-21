package com.sccl.summerreadingapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sccl.summerreadingapp.R;
import com.sccl.summerreadingapp.model.UserType;

public class ChooseUserAdapter extends BaseAdapter {
    
    private Activity activity;
    private static LayoutInflater inflater =null;
    String users[];
    String userTypes[];
 
    public ChooseUserAdapter(Activity a, String users[], String userTypes[]) {
        activity = a;
        this.users = users; 
        this.userTypes = userTypes; 
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public int getCount() {
        return users.length;
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.choose_user_detail, null);
 
        TextView title = (TextView)vi.findViewById(R.id.user_name); // title
        ImageView imageView=(ImageView)vi.findViewById(R.id.list_image); // thumb image
 
        String name  = users[position];
        title.setText(name);

        String description = UserType.getDescription(userTypes[position]);
        if ("Adult".equalsIgnoreCase(description)) 
        	imageView.setImageResource(R.drawable.adult);
        else if ("Pre-Reader".equalsIgnoreCase(description)) 
        	imageView.setImageResource(R.drawable.prereader);
        else if ("Teen".equalsIgnoreCase(description)) 
        	imageView.setImageResource(R.drawable.teen);
        else // if ("Reader".equalsIgnoreCase(description)) 
        	imageView.setImageResource(R.drawable.reader);


        return vi;
    }
}