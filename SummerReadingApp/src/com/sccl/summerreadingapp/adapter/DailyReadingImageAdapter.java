package com.sccl.summerreadingapp.adapter;

import java.io.Serializable;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.sccl.summerreadingapp.R;
import com.sccl.summerreadingapp.helper.MiscUtils;
import com.sccl.summerreadingapp.model.Account;
import com.sccl.summerreadingapp.model.User;

public class DailyReadingImageAdapter extends BaseAdapter implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6698923475338075042L;
	
	private Context mContext;
    int batteryWidth, batteryHeight;
	Account account;
	User user;


    public DailyReadingImageAdapter(Context c, Account account, User user) {
        mContext = c;
        this.account = account;
        this.user = user;
        // imageSize = (int) mContext.getResources().getDimension(R.dimen.image_size);
        batteryWidth = (int) mContext.getResources().getDimension(R.dimen.battery_image_width);
        batteryHeight = (int) mContext.getResources().getDimension(R.dimen.battery_image_height);

    }

    public int getCount() {
        return 45; //mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
    
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

    	ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            // imageView.setLayoutParams(new GridView.LayoutParams(370, 100));
            // MiscUtils.displayToastMessage(mContext, "w="+batteryWidth + "h="+batteryHeight);
            imageView.setLayoutParams(new GridView.LayoutParams(batteryWidth, batteryHeight));

            // imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            imageView = (ImageView) convertView;
        }
        int id = R.drawable.robottestoff01 + position;
        
        int twentyAdded = user != null ? user.getReadingLog() : 0;
        if (position < twentyAdded) {
        	id = R.drawable.robotactivated01 + position;
        }
        imageView.setImageResource(id);
        // int id = R.drawable.robotactivated01;
        // imageView.setImageResource(position + id);
        return imageView;
    	}

	public void addTwenty() {
		if (user != null)
			user.addTwentyToReadingLog();
    	this.notifyDataSetChanged();
	}

	public void setAccountAndUser(Account account, User user) {
		// TODO Auto-generated method stub
		this.account = account;
		this.user = user;
    	this.notifyDataSetChanged();
	}
}