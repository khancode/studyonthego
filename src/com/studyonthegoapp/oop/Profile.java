package com.studyonthegoapp.oop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable {
	
	private String username;
	private String firstName;
	private String lastName;
	private Course[] courses;
	private String major;
	private String year;
	private String skills;
	
	/* Constructor for inserting dummy data */
	public Profile(String username, String firstName, String lastName, Course[] courses,
				   String major, String year, String skills)
	{
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.courses = courses;
		this.major = major;
		this.year = year;
		this.skills = skills;
	}
	
	public Profile(JSONObject jObject)
	{
		try {
			this.username = jObject.getString("Username");
			this.firstName = jObject.getString("FirstName");
			this.lastName = jObject.getString("LastName");
			this.major = jObject.getString("Major");
			this.year = jObject.getString("Year");
			this.skills = jObject.getString("Skills");
			
			JSONArray coursesArr = jObject.getJSONArray("Courses");
			this.courses = new Course[coursesArr.length()];
			for (int i = 0; i < coursesArr.length(); i++)
			{
				JSONObject course = coursesArr.getJSONObject(i);
				this.courses[i] = new Course(course);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Profile(Parcel parcel)
	{
		this.username = parcel.readString();
		this.firstName = parcel.readString();
		this.lastName = parcel.readString();
		this.courses = parcel.createTypedArray(Course.CREATOR);
		this.major = parcel.readString();
		this.year = parcel.readString();
		this.skills = parcel.readString();		
	}
	
	public String getUsername() { return this.username; }
	public Course[] getCourses() { return this.courses; }
	
	@Override
	public String toString()
	{
		String str =  "username: " + username +
					  "\nfirstName: " + firstName +
					  "\nlastName: " + lastName +
					  "\ncourses.length: " + courses.length;
		
		for (int i = 0; i < courses.length; i++) {
			Course c = courses[i];
			str += "\n" + c.getId() + "_" + c.getSubject() +
				   "_" + c.getNumber() + "_" + c.getSection();
		}
		
		str += "\nmajor: " + major +
			   "\nyear: " + year +
			   "\nskills: " + skills;
		
		return str;
	}

	public static final Parcelable.Creator<Profile> CREATOR = new Creator<Profile>() {  
		public Profile createFromParcel(Parcel source) {  
		    return new Profile(source);
		}

		@Override
		public Profile[] newArray(int size) {
			return new Profile[size];
		}
	};
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(username);
		parcel.writeString(firstName);
		parcel.writeString(lastName);
		parcel.writeTypedArray(courses, i);
		parcel.writeString(major);
		parcel.writeString(year);
		parcel.writeString(skills);
	}
}
