package com.studyonthegoapp.fragment;

import java.util.ArrayList;

import com.studyonthegoapp.codebase.R;
import com.studyonthegoapp.oop.StudyGroup;
import com.studyonthegoapp.restfulapi.GetStudyGroups;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchStudyGroupsFragment extends Fragment {
	
	private ArrayList<StudyGroup> studyGroups;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_search_study_groups, container, false);
		
		studyGroups = null;
		
		GetStudyGroups asyncTask = new GetStudyGroups(this);
		asyncTask.execute();
		
		return view;
	}
	
	public void receiveGetStudyGroupsResultFromMySQL(ArrayList<StudyGroup> studyGroups)
	{	
		this.studyGroups = studyGroups;
		
		for (int i = 0; i < this.studyGroups.size(); i++)
		{
			StudyGroup group = this.studyGroups.get(i);
			
			Log.d("receiveGetStudyGroupsResultFromMySQL()", group.toString() + "\n");
		}
	}
}
