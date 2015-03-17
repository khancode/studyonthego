package com.studyonthegoapp.fragment;

import com.studyonthegoapp.activity.AppCoreActivity;
import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.id;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ActiveGroupFragment extends Fragment implements OnClickListener {
	
	private Button actionButton;
	
	private String username;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_active_group, container, false);
		
		actionButton = (Button) view.findViewById(id.actionButton);
		actionButton.setOnClickListener(this);
		
		username = ((AppCoreActivity) view.getContext()).getUsername();
		
		return view;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}	

}
