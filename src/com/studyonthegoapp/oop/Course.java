package com.studyonthegoapp.oop;

import android.os.Parcel;
import android.os.Parcelable;

public class Course implements Parcelable {
	
	private int id;
	private String subject;
	private int number;
	private String section;
	
	public Course(int id, String subject, int number, String section)
	{
		this.id = id;
		this.subject = subject;
		this.number = number;
		this.section = section;
	}
	
	public Course(Parcel parcel)
	{
		this.id = parcel.readInt();
		this.subject = parcel.readString();
		this.number = parcel.readInt();
		this.section = parcel.readString();
	}

	public int getId() { return this.id; }
	public String getSubject() { return this.subject; }
	public int getNumber() { return this.number; }
	public String getSection() { return this.section; }

	public static final Parcelable.Creator<Course> CREATOR = new Creator<Course>() {  
		public Course createFromParcel(Parcel source) {  
		    return new Course(source);
		}

		@Override
		public Course[] newArray(int size) {
			return new Course[size];
		}
	};
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(id);
		parcel.writeString(subject);
		parcel.writeInt(number);
		parcel.writeString(section);
	}
}
