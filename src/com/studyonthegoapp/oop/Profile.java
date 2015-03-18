package com.studyonthegoapp.oop;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable {
	
	private String username;
	private Course[] courses;
	
	public Profile(String username, Course[] courses)
	{
		this.username = username;
		this.courses = courses;
	}
	
	public Profile(Parcel parcel)
	{
		this.username = parcel.readString();
		this.courses = parcel.createTypedArray(Course.CREATOR);
	}
	
	public String getUsername() { return this.username; }
	public Course[] getCourses() { return this.courses; }
	
	public String toString()
	{
		String str =  "username: " + username +
					  "\ncourses.length: " + courses.length;
		
		for (int i = 0; i < courses.length; i++) {
			Course c = courses[i];
			str += "\n" + c.getId() + "_" + c.getSubject() +
				   "_" + c.getNumber() + "_" + c.getSection();
		}
		
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
		parcel.writeTypedArray(courses, i);
	}
}
