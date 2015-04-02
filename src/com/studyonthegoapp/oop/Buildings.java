package com.studyonthegoapp.oop;

import java.util.ArrayList;

public class Buildings {

	private ArrayList<String> buildings;
	
	public Buildings()
	{
		this.buildings = new ArrayList<String>();
	}
	
	/*public Buildings()
	{

	}*/
	
	public int length()	{ return this.buildings.size(); }
	
	public ArrayList<String> getBuildings() { return this.buildings; }
	
}
