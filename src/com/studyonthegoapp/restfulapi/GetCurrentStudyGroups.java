package com.studyonthegoapp.restfulapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.studyonthegoapp.oop.StudyGroup;
import com.studyonthegoapp.search.SearchStudyGroupsFragment;

import android.os.AsyncTask;

public class GetCurrentStudyGroups extends AsyncTask<String, Void, Void>
{	
	
	private StudyGroup[] studyGroups;
	private SearchStudyGroupsFragment searchStudyGroupsFragment;
	
	public GetCurrentStudyGroups(SearchStudyGroupsFragment instance)
	{
		searchStudyGroupsFragment = instance;
	}

	@Override
	protected Void doInBackground(String... params) {
		
		String courses = params[0];
		String building = params[1];
		
		String url = "http://www.studyonthegoapp.com/rest/studygroups/show/current";
		
		if (courses != null || building != null)
		{
			int paramCount = 0;
			url += "?";
			
			if (courses != null)
			{
				try {
					courses = URLEncoder.encode(params[0], "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				url += "courses="+courses;
				paramCount++;
			}
			
			if (building != null)
			{
				try {
					building = URLEncoder.encode(params[1], "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (paramCount > 0)
					url += "&";
				
				url += "building="+building;
				paramCount++;
			}
			
		}
		
		StringBuffer response = new StringBuffer();
		
		try {
			URL obj = new URL(url);
		
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			// optional default is GET
			con.setRequestMethod("GET");
	 
			//add request header
//			con.setRequestProperty("User-Agent", "USER_AGENT");
	 
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	 
			//print result
			System.out.println(response.toString());
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			JSONArray jArray = new JSONArray(response.toString());
		
			studyGroups = new StudyGroup[jArray.length()];
			
			for(int i = 0; i < jArray.length(); i++)
			{
			   JSONObject jsonObject = jArray.getJSONObject(i);
	
			   studyGroups[i] = new StudyGroup(jsonObject);
			}
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        
        return null;
	}
	
	@Override
	protected void onPostExecute(Void v)
	{
		// Return the results to SearchStudyGroupsFragment
		searchStudyGroupsFragment.receiveGetCurrentStudyGroupsResult(studyGroups);
	}

}
