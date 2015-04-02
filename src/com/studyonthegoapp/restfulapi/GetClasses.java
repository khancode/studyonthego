package com.studyonthegoapp.restfulapi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.studyonthegoapp.activity.MainActivity;

import android.os.AsyncTask;

public class GetClasses extends AsyncTask <String, Void, Void>
{
	
	private MainActivity mainActivity;
	
	private int responseCode;
	
	public GetClasses(MainActivity instance)
	{
		mainActivity = instance;
	}

	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		//String url = "http://m.gatech.edu/w/schedule/content/api/myschedule";
		
		String url ="http://m.gatech.edu/w/gtplacesm3/content/api/buildings"; 
		
		StringBuffer response = new StringBuffer();
		
		try {			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			//add request header
			con.setRequestMethod("GET");
//			con.setRequestProperty("User-Agent", USER_AGENT);
//			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	 
			// Send post request
/*			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			//wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
	*/ 
			responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			//System.out.println("Post parameters : " + urlParameters);
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
			System.out.println("maformed url exception");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error in reading input");
		}
		
		try
		{
			
			JSONObject jsonObj = new JSONObject(response.toString());
		
			//groupNameExists = jsonObj.getBoolean("groupNameExists");
			//insertStudyGroupError = jsonObj.getBoolean("insertStudyGroupError");
			//insertMemberError = jsonObj.getBoolean("insertMemberError");
			//groupId = jsonObj.getInt("GroupID");
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error in reading response");
		}

		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void v)
	{
		// Return the results to Messaging activity
		//mainActivity.receiveGetClassesResult(responseCode);	
		}

}
