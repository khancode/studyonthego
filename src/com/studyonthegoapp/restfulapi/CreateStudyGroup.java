package com.studyonthegoapp.restfulapi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.studyonthegoapp.active.CreateGroupActivity;

import android.os.AsyncTask;

public class CreateStudyGroup extends AsyncTask<String, Void, Void>
{	
	private boolean groupNameExists;
	private boolean insertError;
	private CreateGroupActivity createGroupActivity;
	
	public CreateStudyGroup(CreateGroupActivity instance)
	{
		createGroupActivity = instance;
	}

	@Override
	protected Void doInBackground(String... params) {
		
		String admin = null;
		String groupName = null;
		String description = null;
		String building = null;
		String location = null;
		try {
			admin = URLEncoder.encode(params[0], "UTF-8");		
			groupName = URLEncoder.encode(params[1], "UTF-8");
			description = URLEncoder.encode(params[3], "UTF-8");
			building = URLEncoder.encode(params[4], "UTF-8");
			location = URLEncoder.encode(params[5], "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String course = "1"; //params[2];
		String startDate = params[6];
		String endDate = params[7];
		String startTime = params[8];
		String endTime = params[9];
		String membersLimit = params[10];		
		
		String url = "http://www.studyonthegoapp.com/rest/studygroups/create";
		String urlParameters = "admin="+admin+"&groupName="+groupName+"&courseID="+course+
							   "&description="+description+"&building="+building+
							   "&location="+location+"&startDate="+startDate+"&endDate="+endDate+
							   "&startTime="+startTime+"&endTime="+endTime+"&membersLimit="+membersLimit;
		
		StringBuffer response = new StringBuffer();
		
		try {			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			//add request header
			con.setRequestMethod("POST");
//			con.setRequestProperty("User-Agent", USER_AGENT);
//			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	 
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
	 
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
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
			JSONObject jsonObj = new JSONObject(response.toString());
		
			groupNameExists = jsonObj.getBoolean("groupNameExists");
			insertError = jsonObj.getBoolean("insertError");
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
		createGroupActivity.receiveCreateStudyGroupResultFromMySQL(groupNameExists, insertError);
	}

}
