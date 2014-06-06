package com.sccl.summerreadingapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sccl.summerreadingapp.R;
import com.sccl.summerreadingapp.SummerReadingApplication;
import com.sccl.summerreadingapp.model.Config;

public class ChooseUserAdapter extends BaseAdapter {
    
    private Activity activity;
    private static LayoutInflater inflater =null;
    String users[];
    String userTypes[];
    int mCurrSelected = -1;
 
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

        RelativeLayout layout= (RelativeLayout) vi.findViewById(R.id.user_detail_layout);

        SummerReadingApplication summerReadingApplication = (SummerReadingApplication) activity.getApplicationContext();
		Config config = summerReadingApplication.getConfig();

        // String description = UserType.getDescription(userTypes[position]);
        String description = config.getUserTypeById(userTypes[position]).getDescription();
        int bgColor = vi.getResources().getColor(R.color.LibraryRed);
		if ("Adult".equalsIgnoreCase(description)) { 
        	imageView.setImageResource(R.drawable.adult);
        	bgColor = vi.getResources().getColor(R.color.LibraryGreen);
        }
        else if ("Pre-Reader".equalsIgnoreCase(description)) {
        	imageView.setImageResource(R.drawable.prereader);
        	bgColor = vi.getResources().getColor(R.color.LibraryOrange);
    	}
        else if ("Teen".equalsIgnoreCase(description)) {
        	imageView.setImageResource(R.drawable.teen);
        	bgColor = vi.getResources().getColor(R.color.LibraryBlue);
        }
        else if ("Staff".equalsIgnoreCase(description)) {
        	imageView.setImageResource(R.drawable.staff);
        	bgColor = vi.getResources().getColor(R.color.LibraryGray);
        }
        else { // if ("Reader".equalsIgnoreCase(description)) 
        	imageView.setImageResource(R.drawable.reader);
        }

        if (position == mCurrSelected) {
        	vi.setBackgroundColor(activity.getResources().getColor(R.color.LightSkyBlue));
        }
        else {
        	vi.setBackgroundColor(bgColor);
        }

        return vi;
    }

    public void setSelectedPosition(int position) {
    	mCurrSelected = position;
    	notifyDataSetChanged();
    }
 
    public int getSelectedPosition() {
    	return mCurrSelected;
    }
 
}