package com.uhf.structures;

public class AntennaInfo {
	public int antennaNum;//天线号
	public int state;//状态
	public double d_SWR;//驻波比
	public int length;//长度
	public int powerLevel;//功率
	public int dwellTime;//驻留时间
	public int numberInventoryCycles;//盘讯周期
	public int physicalRxPort;
	public int physicalTxPort;
	public int antennaSenseThreshold;
	public AntennaInfo(){};
	public AntennaInfo(int ant,int sta,double swr,int len,int pow,int dwe,int num,int phyR,int phyT,int ants)
	{
		this.antennaNum = ant;
		this.state = sta;
		this.d_SWR = swr;
		this.length = len;
		this.powerLevel = pow;
		this.dwellTime = dwe;
		this.numberInventoryCycles = num;
		this.physicalRxPort = phyR;
		this.physicalTxPort = phyT;
		this.antennaSenseThreshold = ants;

	}
	public void setValue(int ant,int sta,double swr,int len,int pow,int dwe,int num,int phyR,int phyT,int ants)
	{
		this.antennaNum = ant;
		this.state = sta;
		this.d_SWR = swr;
		this.length = len;
		this.powerLevel = pow;
		this.dwellTime = dwe;
		this.numberInventoryCycles = num;
		this.physicalRxPort = phyR;
		this.physicalTxPort = phyT;
		this.antennaSenseThreshold = ants;

	}
}
