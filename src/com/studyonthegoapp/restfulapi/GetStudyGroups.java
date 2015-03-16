package com.studyonthegoapp.restfulapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.studyonthegoapp.fragment.SearchStudyGroupsFragment;
import com.studyonthegoapp.oop.StudyGroup;

import android.os.AsyncTask;

public class GetStudyGroups extends AsyncTask<String, Void, Void>
{	
	
	private ArrayList<StudyGroup> studyGroups;
	private SearchStudyGroupsFragment searchStudyGroupFragment;
	
	public GetStudyGroups(SearchStudyGroupsFragment instance)
	{
		searchStudyGroupFragment = instance;
	}

	@Override
	protected Void doInBackground(String... params) {
		
//		String course = params[0];
//		String building = params[1];
		
		String url = "http://www.studyonthegoapp.com/rest/studygroups/show";
		
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
		
			studyGroups = new ArrayList<StudyGroup>();
			
			for(int i = 0; i < jArray.length(); i++)
			{
			   JSONObject jsonObject = jArray.getJSONObject(i);
	
			   studyGroups.add(new StudyGroup(jsonObject));
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
		searchStudyGroupFragment.receiveGetStudyGroupsResultFromMySQL(studyGroups);
	}

}
