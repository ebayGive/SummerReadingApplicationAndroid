package com.sccl.summerreadingapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.sccl.summerreadingapp.adapter.ImageAdapter;
import com.sccl.summerreadingapp.model.GridActivity;

public class ConfirmSummerActivityFragment  extends DialogFragment {
	GridActivity grid;
	ImageAdapter imageAdapter;
	
    public ConfirmSummerActivityFragment() {
          // Empty constructor required for DialogFragment
    }

    public static ConfirmSummerActivityFragment newInstance(String title, GridActivity grid) {
    	ConfirmSummerActivityFragment frag = new ConfirmSummerActivityFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putSerializable("grid", grid);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, 0);        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm_summer_activity, container);
        String title = getArguments().getString("title");
        this.grid = (GridActivity)getArguments().getSerializable("grid");
        this.imageAdapter = (ImageAdapter)getArguments().getSerializable("adapter");
        
        getDialog().setTitle(title);
        // Show soft keyboard automatically
        final EditText noteText = (EditText) view.findViewById(R.id.txt_name);
        if (grid != null)
        	noteText.setText(grid.getNotes());
        noteText.requestFocus();

        Button okbutton = (Button) view.findViewById(R.id.btn_ok);
        Button cancelbutton = (Button) view.findViewById(R.id.btn_cancel);
                  
        getDialog().getWindow().setSoftInputMode(
        		WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (grid != null) {
	            	grid.setNotes(noteText.getText().toString());
	                grid.setType(1);
                }
//                if (imageAdapter != null)
  //              	imageAdapter.notifyDataSetChanged();
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent());
        
            	dismiss();
            }
           });

        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	dismiss();
            }
           });

      return view;
    }
    
/*    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
         String title = getArguments().getString("title");
         AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
         alertDialogBuilder.setTitle(title);
         alertDialogBuilder.setMessage("Are you sure?");
         alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                   // on success
             }
         });
         alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();
             }
         });

         return alertDialogBuilder.create();
    }
*/    
}