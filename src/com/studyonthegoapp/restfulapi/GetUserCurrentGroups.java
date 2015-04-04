package com.studyonthegoapp.restfulapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.studyonthegoapp.active.ActiveGroupFragment;
import com.studyonthegoapp.oop.StudyGroup;

import android.os.AsyncTask;
import android.util.Log;

public class GetUserCurrentGroups extends AsyncTask<String, Void, Void>
{	
	private StudyGroup[] studyGroups;
	
	private ActiveGroupFragment activeGroupFragment;
	
	public GetUserCurrentGroups(ActiveGroupFragment instance)
	{
		activeGroupFragment = instance;
	}

	@Override
	protected Void doInBackground(String... params) {
		
		String username = params[0];
		
		String url = "http://www.studyonthegoapp.com/rest/studygroups/show/current/user?username="+username;
		
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
//			JSONObject jObject = new JSONObject(response.toString());
//				
//			studyGroup = new StudyGroup(jObject);
			
			JSONArray jArray = new JSONArray(response.toString());
			
			studyGroups = new StudyGroup[jArray.length()];
			
			for(int i = 0; i < jArray.length(); i++)
			{
			   JSONObject jsonObject = jArray.getJSONObject(i);
	
			   studyGroups[i] = new StudyGroup(jsonObject);
			}
			
		}
		catch (JSONException e) {
			if (response.toString().equals("null"))
				Log.d("GetUserActiveGroup", "JSONObject is null");
			else
				e.printStackTrace();
			
			studyGroups = null;
		}
        
        return null;
	}
	
	@Override
	protected void onPostExecute(Void v)
	{
		// Return the results to ActiveGroupFragment		
		activeGroupFragment.receiveUserCurrentGroupsResult(studyGroups);
	}

}

