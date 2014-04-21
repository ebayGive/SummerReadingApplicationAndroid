package com.sccl.summerreadingapp.adapter;

import java.util.Locale;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sccl.summerreadingapp.DailyReadingFragment;
import com.sccl.summerreadingapp.InformationFragment;
import com.sccl.summerreadingapp.R;
import com.sccl.summerreadingapp.SummerActivityFragment;
import com.sccl.summerreadingapp.model.GridActivity;
import com.sccl.summerreadingapp.model.User;


/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    GridActivity[] data = null;
	private User user;
	SummerActivityFragment summer;

    public SectionsPagerAdapter(FragmentManager fm, Context c) {
        super(fm);
        mContext = c;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
        case 0:
        	summer =  new SummerActivityFragment();
        	// fragment.setGridData(data);
        	summer.setUser(user);
        	return summer;
        case 1:
            return new DailyReadingFragment();
        case 2:
            return new InformationFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
//                return "Summer Activity";
                return mContext.getString(R.string.title_summer_activity);
          case 1:
                return mContext.getString(R.string.title_daily_reading);
            case 2:
                return mContext.getString(R.string.title_information);
        }
        return null;
    }
    
    public void setActivityGridData1(FragmentManager fm, GridActivity[] data)
    {
    	this.data = data;
    	// SummerActivityFragment summer = (SummerActivityFragment)getItem(0);
//    	if (summer != null && summer.imageAdapter != null)
//    		summer.imageAdapter.setGridData(data);

    	SummerActivityFragment fragment = 
    			(SummerActivityFragment) fm.findFragmentByTag(
  	                       "android:switcher:"+R.id.pager+":0");
	  	if(fragment != null)  {
	  		fragment.imageAdapter.setGridData(data);
	  	}
    }

	public void setUser(FragmentManager fm, User user) {
    	this.user = user;
//    	SummerActivityFragment summer = (SummerActivityFragment)getItem(0);
//    	if (summer != null && summer.imageAdapter != null)
//    		summer.setUser(user);
    	SummerActivityFragment fragment = 
    			(SummerActivityFragment) fm.findFragmentByTag(
  	                       "android:switcher:"+R.id.pager+":0");
	  	if(fragment != null)  {
	  		fragment.setUser(user);
;	  	}
    	
    }


}

