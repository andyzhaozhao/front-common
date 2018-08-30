package com.uhf.structures;

public class Frequency_Region {
	public int iLowFrequency; 
	public int iHeighFrequency;
	public int zone; // 0 china,1 usa,2 open_area
	public Frequency_Region(){		
	}
	public Frequency_Region(int ilow,int iheigh,int zone)
	{
		iLowFrequency = ilow;
		iHeighFrequency = iheigh;
		this.zone = zone;
	}	
	public void setValue(int ilow,int iheigh,int zone)
	{
		iLowFrequency = ilow;
		iHeighFrequency = iheigh;
		this.zone = zone;
	}
}
