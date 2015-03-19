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

import android.os.AsyncTask;

import com.studyonthegoapp.oop.StudyGroup;
import com.studyonthegoapp.search.SearchStudyGroupsFragment;

/**
 * THIS CLASS IS NOT USED ANY WHERE
 * IN THE PROJECT YET.
 * @author khancode
 *
 */

public class GetAllStudyGroups extends AsyncTask<String, Void, Void>
{	
	
	private StudyGroup[] studyGroups;
	private SearchStudyGroupsFragment searchStudyGroupFragment;
	
	public GetAllStudyGroups(SearchStudyGroupsFragment instance)
	{
		searchStudyGroupFragment = instance;
	}

	@Override
	protected Void doInBackground(String... params) {
		
		String course = params[0];
		String building = params[1];
		
		String url;		
		if (course == null && building == null)
			url = "http://www.studyonthegoapp.com/rest/studygroups/show/all";
		else
		{
			if (course != null) {
				try {
					course = URLEncoder.encode(params[0], "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if (building != null) {
				try {
					building = URLEncoder.encode(params[1], "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if (course != null && building == null)
			{
				url = "http://www.studyonthegoapp.com/rest/studygroups/show?course="+course;
			}
			else if (course == null && building != null)
				url = "http://www.studyonthegoapp.com/rest/studygroups/show?building="+building;
			else
				url = "http://www.studyonthegoapp.com/rest/studygroups/show?course="+course+"&building="+building;
		}
		
//		String url = "http://www.studyonthegoapp.com/rest/studygroups/show";
		
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
//			System.out.println(response.toString());
		
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
		// Return the results to Messaging activity
//		searchStudyGroupFragment.receiveGetStudyGroupsResultFromMySQL(studyGroups);
	}

}
