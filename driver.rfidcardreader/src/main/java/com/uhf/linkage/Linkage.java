package com.uhf.linkage;
import com.uhf.structures.*;
import com.uhf.constants.Constants.*;

public class Linkage {

	public native int InitAPI();

	public native int FreeAPI();

	public native RadioInfo[] Radio_RetrieveAttache(Rfid_Value rv);

	public native int Radio_ConnectTo(int iSerialNum);

	public native int Radio_DisConnect(int iSerialNum);

	public native int Radio_GetAntennaInfo(int handle,AntennaInfo ata_info,int ui_AntenaPortNum);

	public native int Radio_SetAntennaInfo(int handle,AntennaInfo ata_info);

	public native int Radio_WriteTag(int handle,
			int offset, 
			int count, 
			int bank,
			char[] strShowText,
			int pwd,
			int flag);

	public native int Radio_ReadTag(int handle,
			int offset, 
			int count, 
			int bank,
			int pwd,
			ReadParms parms,
			int flag);

	public native int Radio_LockTag(int handle,
			int pwd,
			int accpwd,
			int killpwd,
			int epc,
			int tid,
			int user,
			int flag);

	public native int Radio_KillTag(int handle,int accpwd, int killpwd,int flag);

	public native int Radio_GetCurrentLinkProfile(int handle,Rfid_Value rv);

	public native int Radio_SetCurrentLinkProfile(int handle,int currentProfile);

	public native int Radio_GetErrorCode(int handle,Rfid_Value error);

	public native int Radio_GetInventoryData(int handle,InventoryData[] args);

	public native int Radio_Inventory(int handle,int flag);

	public native int Radio_CancelOperation(int handle);

	public native int Radio_SetSingleFrequency(int handle,double iSingleFrequency,int Country);

	public native int Radio_GetSingleFrequency(int handle,Rfid_dValue rv);

	public native int Radio_GetAntennaPower(int handle,Rfid_Value rv);

	public native int Radio_SetAntennaPower(int handle,int powerLevel);

	public native int Radio_MacSetRegion(int handle,int region);

	public native int Radio_MacGetRegion(int handle,Rfid_Value rv);

	public native int Radio_GetOpenRadioHandle(int iNum);

	public native boolean  Radio_GetRadioStatus(int iNum);

	public native int Radio_GetSingulationAlgorithmDyParameters(int handle,DynamicQParms rsdp);

	public native int Radio_GetSingulationAlgorithmFiParameters(int handle,FixedQParms rsfp);

	public native int Radio_SetSingulationAlgorithmDyParameters(int handle,DynamicQParms rsdp);

	public native int Radio_SetSingulationAlgorithmFiParameters(int handle,FixedQParms rsfp);

	public native int Radio_SetCurrentSingulationAlgorithm(int handle,int algorithm);

	public native int Radio_GetCurrentsingulationAlgorithm(int handle,Rfid_Value rv);

	public native int Radio_GetQueryTagGroup(int handle, TagGroup group);

	public native int Radio_SetQueryTagGroup(int handle,TagGroup group);

	public native int Radio_GetAntennaCount(int handle,Rfid_Value rv);

	public native int Radio_SetConnectMode(int handle,int mode);

	public native int Radio_SetResponseMode(int handle,int responseType,int responseMode);

	public native int Radio_GetResponseMode(int handle,int responseType,Rfid_Value rv);

	public String b2hexs(byte[] b, int length) {
		String ret = "";
		for (int i = 0; i < length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}

	public String c2hexs(char[] data, int length) {
		byte[] bytes = new byte[2 * length];
		int i;
		for (i = 0; i < length; i++) {
			bytes[2 * i] = (byte) (data[i] >> 8);
			bytes[2 * i + 1] = (byte) (data[i]);
		}
		return b2hexs(bytes, 2 * length);
	}

	public String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}

	public char[] s2char(String value) {
		char[] WriteText = new char[(value.length() / 4)];
		byte[] btemp = new byte[(value.length())];

		for (int i = 0; i < btemp.length; i++) {
			btemp[i] = Byte.parseByte(value.substring(i, i + 1), 16);
		}
		for (int i = 0; i < WriteText.length; i++) {
			WriteText[i] = (char) (((btemp[i * 4 + 0] & 0x0f) << 12)
					| ((btemp[i * 4 + 1] & 0x0f) << 8)
					| ((btemp[i * 4 + 2] & 0x0f) << 4) | (btemp[i * 4 + 3] & 0x0f));
		}
		return WriteText;
	}

	public String b2sip(byte[] ip)
	{
		String str="";
		for(int i=0;i<ip.length-1;i++)
		{
			str += String.valueOf((int)ip[i]&0xff);
			str +=".";
		}
		str+=String.valueOf((int)ip[ip.length-1]&0xff);
		return str;
	}

	static {
//		System.load("uhf.dll");
//		System.load("JNILink.dll");
		System.loadLibrary("dll/uhf");
		System.loadLibrary("dll/JNILink");
	}
}
