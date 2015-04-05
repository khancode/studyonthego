package com.studyonthegoapp.oop;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class RequestsToJoin implements Parcelable {
	
	private ArrayList<User> requests;
	
	public RequestsToJoin()
	{
		this.requests = new ArrayList<User>();
	}
	
	public RequestsToJoin(JSONArray jArray)
	{
		requests = new ArrayList<User>();
		
		for (int i = 0; i < jArray.length(); i++)
		{
			JSONObject profileObject = null;
			try {
				profileObject = jArray.getJSONObject(i);
			}
			catch (JSONException e) {
				Log.e("RequestsToJoin()", "This should not happen");
			}
			
			requests.add(new User(profileObject));
		}
	}
	
	public RequestsToJoin(Parcel parcel)
	{
		this.requests = parcel.createTypedArrayList(User.CREATOR);
	}
	
	public void addRequest(User user)
	{
		requests.add(user);
	}
	
	public void removeRequest(User user)
	{		
		for (int i = 0; i < this.requests.size(); i++)
		{
			if (this.requests.get(i).getUsername().equals(user.getUsername()))
			{
				this.requests.remove(i);
				break;
			}
		}
	}
	
	public int length() { return this.requests.size(); }
	
	public ArrayList<User> getRequests() { return this.requests; }
	
	@Override
	public String toString()
	{
		String str = "";
		for (int i = 0; i < requests.size(); i++)
			str += "\n" + i + ": " + requests.get(i);
		
		return str;
	}
	
	public static final Parcelable.Creator<RequestsToJoin> CREATOR = new Creator<RequestsToJoin>() {  
		public RequestsToJoin createFromParcel(Parcel source) {  
		    return new RequestsToJoin(source);
		}

		@Override
		public RequestsToJoin[] newArray(int size) {
			return new RequestsToJoin[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeTypedList(requests);
	}
}
