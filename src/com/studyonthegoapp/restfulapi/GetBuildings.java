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

import com.studyonthegoapp.active.CreateGroupActivity;
import com.studyonthegoapp.oop.StudyGroup;

import android.os.AsyncTask;

public class GetBuildings extends AsyncTask <String, Void, Void>

{

	private CreateGroupActivity createGroupInstance;
	
	private int responseCode;
	
	private ArrayList<String> buildings = new ArrayList<String>();
	
	public GetBuildings(CreateGroupActivity createGroupActivity)
	{
		createGroupInstance = createGroupActivity;
	}

	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		String url ="http://m.gatech.edu/w/gtplacesm3/content/api/buildings"; 
		
		StringBuffer response = new StringBuffer();
		
		try {			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			//add request header
			con.setRequestMethod("GET");
	 
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
			JSONArray jArray = new JSONArray(response.toString());
			for(int i = 0; i < jArray.length(); i++)
			{
			   JSONObject jsonObject = jArray.getJSONObject(i);
			   String building = jsonObject.getString("name");
			   buildings.add(building);
			}
			
			
		
			
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
		createGroupInstance.receiveGetBuildingsResult(buildings, responseCode);	}
	
}
