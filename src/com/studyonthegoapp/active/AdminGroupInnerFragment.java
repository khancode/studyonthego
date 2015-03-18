package com.studyonthegoapp.active;

import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.codebase.R.id;
import com.studyonthegoapp.oop.StudyGroup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdminGroupInnerFragment extends Fragment {
	
	private TextView groupNameTV;
	
	private StudyGroup group;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.inner_fragment_admin_group, container, false);
		
		Bundle bundle = getArguments();
		group = bundle.getParcelable("studyGroup");
		
		groupNameTV = (TextView) view.findViewById(id.groupNameTextView);
		groupNameTV.setText(group.getGroupName());
		
		return view;
	}

}