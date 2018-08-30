package com.uhf.structures;

public class InventoryData {
	public int nAntennaPortNum;
	public short RSSI;
	public byte[] INV_EPC_Data = new byte[64];
	public int EPC_Length;
	public byte[] INV_TID_Data = new byte[64];
	public int TID_Length;
	public byte[] bip = new byte[4];
	public void setValue(int nAntennaPortNum,short RSSI,byte[] INV_EPC_Data,int EPC_Length,byte[] INV_TID_Data,int TID_Length,byte[] bip)
	{
		this.nAntennaPortNum = nAntennaPortNum;
		this.RSSI = RSSI;
		this.INV_EPC_Data = INV_EPC_Data;
		this.EPC_Length = EPC_Length;
		this.INV_TID_Data = INV_TID_Data;
		this.TID_Length = TID_Length;
		this.bip =bip;
	}
}
