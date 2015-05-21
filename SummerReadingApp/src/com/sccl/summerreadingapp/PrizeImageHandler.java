package com.sccl.summerreadingapp;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.sccl.summerreadingapp.helper.MiscUtils;
import com.sccl.summerreadingapp.model.Prize;
 
public class PrizeImageHandler {
    private Activity activity;
	
	public PrizeImageHandler(Activity activity) {
		super();
		this.activity = activity;
	}

	public void setImagesAndAssignClickHandler(View rootView, Prize[] prizes) {
        setPrizeImages(rootView, prizes);
        handlePrizeImageClick(rootView, prizes);
	}

	
	public void handlePrizeImageClick(View rootView, Prize[] prizes) {
		// int[] resourceIds = {R.id.prize1, R.id.prize2, R.id.prize3, R.id.prize4, R.id.prize5};
		int[] resourceIds = {R.id.prize1, R.id.prize2, R.id.prize3};
		
		for (int i = 0; i < resourceIds.length; i++) {
			setPrizeImageClickListener((ImageView) rootView.findViewById(resourceIds[i]), 
				getPrizeTitle(prizes[i]), getPrizeMessage(prizes[i]));
		}

	}

	public void setPrizeImages(View rootView, Prize[] prizes) {
		// if (prizes != null && prizes.length > 3) {
		if (prizes != null && prizes.length > 2) {
			setPrizeImageBasedOnState(rootView, prizes[0], R.id.prize1);
			setPrizeImageBasedOnState(rootView, prizes[1], R.id.prize2);
			setPrizeImageBasedOnState(rootView, prizes[2], R.id.prize3);
/*			setPrizeImageBasedOnState(rootView, prizes[3], R.id.prize4);
			setPrizeImageBasedOnState(rootView, prizes[4], R.id.prize5);
*/		}
	}

	private String getPrizeTitle(Prize prize) {
		String title = prize.getState() == 1 ? "Great Job" : "Good Luck";
		return title;
	}

	private String getPrizeMessage(Prize prize) {
    	String message = prize.getState() == 1 ? 
        	"Congratulations! You have won a prize. Visit your local San Jos� Public Library to pick up prizes from June 2 to July 31." : 
        	"Complete 5 squares in a row (vertical, horizontal, or diagonal) to win a prize. Visit your local library to pick up prizes from June 2 to July 31.";
/*    	String message = "Complete 5 squares in a row (vertical, horizontal, or diagonal) to win a prize. Visit your local library to pick up prizes from June 2 to July 31.";
    	if (prize.getState() == 1)
    		message = "Congratulations! You have won a prize. Visit your local San Jos� Public Library to pick up prizes from June 2 to July 31.";
*/    	return message;
	}

	private void setPrizeImageClickListener(ImageView prizeView, String title, String message) {
    	final String finalTitle = title;
		final String finalMessage = message;
		prizeView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MiscUtils.showAlertDialog(activity, finalTitle, finalMessage);
            }
        });
	}

	private void setPrizeImageBasedOnState(View rootView, Prize prize, int prizeResourceId) {
		int imageId = R.drawable.prize_not_ready;
		if (prize.getState() == 1) {
			imageId = R.drawable.prize_1_ready + prizeResourceId - R.id.prize1;
		}
		else if (prize.getState() == 2) {
			imageId = R.drawable.prize_claimed_badge;
		}
		ImageView prizeView = (ImageView) rootView.findViewById(prizeResourceId);
		prizeView.setImageResource(imageId);
	}

}
