package com.uhf.structures;

public class Single_Inventory_Time_Config {
	public int iWorkTime;
	public int iRestTime;
	public Single_Inventory_Time_Config(){};
	public Single_Inventory_Time_Config(int iWorkTime, int iRestTime) {
		this.iWorkTime = iWorkTime;
		this.iRestTime = iRestTime;
	}

	public void setValue(int iWorkTime, int iRestTime) {
		this.iWorkTime = iWorkTime;
		this.iRestTime = iRestTime;
	}
}
