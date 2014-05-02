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
import com.sccl.summerreadingapp.model.GridActivity;

public class ImageAdapter extends BaseAdapter implements Serializable{
    private Context mContext;
    private GridActivity[] gridData;
    int imageSize;

    public ImageAdapter(Context c, GridActivity[] gridData) {
        mContext = c;
        this.gridData = gridData;
        imageSize = (int) mContext.getResources().getDimension(R.dimen.image_size);

    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
    
    public void setGridData(GridActivity[] data)
    {
    	gridData = data;
    	super.notifyDataSetChanged();
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

    	ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(imageSize, imageSize));

            MiscUtils.displayToastMessage(mContext, "size="+imageSize);
            //imageView.setLayoutParams(new GridView.LayoutParams(180, 180));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(2, 2, 2, 2);
        } else {
            imageView = (ImageView) convertView;
        }

        int resId = mThumbIdsEnabled[position];
        
        if (gridData != null && position < gridData.length)
        {
        	GridActivity activity = gridData[position];
        	if (activity != null && activity.getType() == 1)
        	{
        		resId = mThumbIds[position];
        	}
        	
        }
        imageView.setImageResource(resId);
        return imageView;
    
/*
    	ImageView imageView = (ImageView) convertView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(2, 2, 2, 2);
        } else {
            imageView = (ImageView) convertView;
        }
    	Picasso.with(mContext).load("http://i.imgur.com/DvpvklR.png")
    		.placeholder(R.drawable.grid_0_0_d)
    		.error(R.drawable.grid_0_0_e)
    		.resize(175, 175)
    		//.resize(imageView.getMeasuredWidth(), imageView.getMeasuredWidth())
    		.centerCrop().into(imageView);
    	return imageView;
*/    	
    	}

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.grid_0_0_d, R.drawable.grid_0_1_d,
            R.drawable.grid_0_2_d, R.drawable.grid_0_3_d,
            R.drawable.grid_0_4_d, 
            R.drawable.grid_0_0_d, R.drawable.grid_0_1_d,
            R.drawable.grid_0_2_d, R.drawable.grid_0_3_d,
            R.drawable.grid_0_4_d, 
            R.drawable.grid_0_0_d, R.drawable.grid_0_1_d,
            R.drawable.grid_0_2_d, R.drawable.grid_0_3_d,
            R.drawable.grid_0_4_d, 
            R.drawable.grid_0_0_d, R.drawable.grid_0_1_d,
            R.drawable.grid_0_2_d, R.drawable.grid_0_3_d,
            R.drawable.grid_0_4_d, 
            R.drawable.grid_0_0_d, R.drawable.grid_0_1_d,
            R.drawable.grid_0_2_d, R.drawable.grid_0_3_d,
            R.drawable.grid_0_4_d 
    };
    // references to our images
    private Integer[] mThumbIdsEnabled = {
            R.drawable.grid_0_0_e, R.drawable.grid_0_1_e,
            R.drawable.grid_0_2_e, R.drawable.grid_0_3_e,
            R.drawable.grid_0_4_e, 
            R.drawable.grid_0_0_e, R.drawable.grid_0_1_e,
            R.drawable.grid_0_2_e, R.drawable.grid_0_3_e,
            R.drawable.grid_0_4_e, 
            R.drawable.grid_0_0_e, R.drawable.grid_0_1_e,
            R.drawable.grid_0_2_e, R.drawable.grid_0_3_e,
            R.drawable.grid_0_4_e, 
            R.drawable.grid_0_0_e, R.drawable.grid_0_1_e,
            R.drawable.grid_0_2_e, R.drawable.grid_0_3_e,
            R.drawable.grid_0_4_e, 
            R.drawable.grid_0_0_e, R.drawable.grid_0_1_e,
            R.drawable.grid_0_2_e, R.drawable.grid_0_3_e,
            R.drawable.grid_0_4_e 
    };
}