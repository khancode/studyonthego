package com.studyonthegoapp.active;

import com.studyonthegoapp.codebase.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AdminGroupInnerFragment extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.inner_fragment_admin_group, container, false);
		
		return view;
	}

}