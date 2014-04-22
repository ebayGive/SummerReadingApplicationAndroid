package com.sccl.summerreadingapp;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.sccl.summerreadingapp.helper.MiscUtils;
import com.sccl.summerreadingapp.model.Prize;
 
public class PrizeImageHandler {
    private Activity activity;
	
	public void handlePrizeImageClick(View rootView, Prize[] prizes) {
		int[] resourceIds = {R.id.prize1, R.id.prize2, R.id.prize3, R.id.prize4, R.id.prize5};
		
		for (int i = 0; i < resourceIds.length; i++) {
			setPrizeImageClickListener((ImageView) rootView.findViewById(resourceIds[i]), 
				getPrizeTitle(prizes[i]), getPrizeMessage(prizes[i]));
		}

	}

	public String getPrizeTitle(Prize prize) {
		String title = prize.getState() == 1 ? "Great Job" : "Good Luck";
		return title;
	}

	public String getPrizeMessage(Prize prize) {
    	String message = prize.getState() == 1 ? 
        		"Congratulations on earning the prize. You can collect the prize by visiting library!" : "You need to finish one row/column/diagonal activities to earn prize. ";
		return message;
	}

	public void setPrizeImageClickListener(ImageView prizeView, String title, String message) {
    	final String finalTitle = title;
		final String finalMessage = message;
		prizeView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MiscUtils.showAlertDialog(activity, finalTitle, finalMessage);
            }
        });
	}

	public void setPrizeImages(View rootView, Prize[] prizes) {
		if (prizes != null && prizes.length > 3) {
			setPrizeImageBasedOnState(rootView, prizes[0], R.id.prize1);
			setPrizeImageBasedOnState(rootView, prizes[1], R.id.prize2);
			setPrizeImageBasedOnState(rootView, prizes[2], R.id.prize3);
			setPrizeImageBasedOnState(rootView, prizes[3], R.id.prize4);
			setPrizeImageBasedOnState(rootView, prizes[4], R.id.prize5);
		}
	}

	public void setPrizeImageBasedOnState(View rootView, Prize prize, int prizeResourceId) {
		int imageId = R.drawable.prize_not_ready_to_claim;
		if (prize.getState() == 1) {
			imageId = R.drawable.prize_ready_to_claim;
		}
		else if (prize.getState() == 2) {
			imageId = R.drawable.prize_claimed;
		}
		ImageView prizeView = (ImageView) rootView.findViewById(prizeResourceId);
		prizeView.setImageResource(imageId);
	}

}
