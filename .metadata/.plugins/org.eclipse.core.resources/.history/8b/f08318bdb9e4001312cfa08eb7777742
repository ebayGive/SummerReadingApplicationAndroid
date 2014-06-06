package com.sccl.summerreadingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InformationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_information, container, false);
        TextView tvFeedback = (TextView) rootView.findViewById(R.id.textView4);
		tvFeedback.setText(Html.fromHtml(getString(R.string.feedback_label)));
		return rootView;
    }
}