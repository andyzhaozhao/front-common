package com.uhf.demo;
import com.uhf.constants.Constants.*;
import com.uhf.linkage.Linkage;
import com.uhf.structures.*;

class MyThread_OpenRadioThread extends Thread {
	public void run() {
		test.linkage.Radio_Inventory(test.handle,0);
	}
}

class MyThread_InventoryThread extends Thread {
	public void run() {
		int j=0;
		while (true) {
			j++;
			int num;
			InventoryData[] args1= new InventoryData[200];
			num = test.linkage.Radio_GetInventoryData(test.handle, args1);
			if ((num > 0)&& (args1 != null)) {					
				String strEPCTemp = "";					
				for (int i = 0; i < num; i++) {
					if (args1[i].EPC_Length > 0 && args1[i].EPC_Length < 66) {
						strEPCTemp = test.linkage.b2hexs(args1[i].INV_EPC_Data,args1[i].EPC_Length);
						System.out.println("epc:"+strEPCTemp);
					}	
				}
			}
			try {
				sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(j>2)break;
		}
		int result = test.linkage.Radio_CancelOperation(test.handle);
		if(result == 0)
		{
			System.out.println("Radio_CancelOperation success");
		}
		else
		{
			System.out.println("Radio_CancelOperation failure");
		}
	}
}

public class test {
	public static Linkage linkage = new Linkage();
	public static int handle;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyThread_InventoryThread mythread_Inventory = null;	
		MyThread_OpenRadioThread mythread_OpenRadio = null;
		do
		{
			int result = Result.STATUS_OK.getValue();
			result = linkage.Radio_DisConnect(0);
			linkage.FreeAPI();
			System.out.println("FreeAPI");
			linkage.InitAPI();
			System.out.println("InitAPI");
			Rfid_Value rv =  new Rfid_Value();
			RadioInfo[] radioInfo = linkage.Radio_RetrieveAttache(rv);
			if(rv.value == 0)
			{
				if(radioInfo.length >0)
				{
					System.out.println("Radio_RetrieveAttache success");
					RadioInfo info = radioInfo[0];
					System.out.println("radioNum"+info.radioNum);
					System.out.println("idLength"+info.idLength);
					System.out.println("pUniqueId"+info.pUniqueId);
					System.out.println("driverVersion"+info.driverVersion);
				}
				else
				{
					System.out.println("Radio_RetrieveAttache no radio");
					break;
				}
			}
			else
			{
				System.out.println("Radio_RetrieveAttache"+rv.value);//19999代表没有搜索到
				break;
			}

			
			
			
			result = linkage.Radio_ConnectTo(0);
			if(result == 0)
			{
				System.out.println("Radio_ConnectTo success");

			}
			else
			{
				System.out.println("Radio_ConnectTo failure"+result);
				break;		

			}
			
			
			
			
			result = linkage.Radio_DisConnect(0);
			if(result == 0)
			{
				System.out.println("Radio_DisConnect success");

			}
			else
			{
				System.out.println("Radio_DisConnect success"+result);
				break;	

			}
			
			
			
			
			result = linkage.Radio_ConnectTo(0);
			if(result == 0)
			{
				System.out.println("Radio_DisConnect success");

			}
			else
			{
				System.out.println("Radio_DisConnect success"+result);
				break;	

			}
			
			handle=linkage.Radio_GetOpenRadioHandle(0);
			if(handle <= 0)
			{
				System.out.println("Radio_GetOpenRadioHandle failure"+handle);
				break;			
			}
			else
			{
				System.out.println("Radio_GetOpenRadioHandle success"+handle);
			}
			
			
			
			AntennaInfo ata_info= new AntennaInfo();
			result = linkage.Radio_GetAntennaInfo(handle,ata_info,0);
			if(result != 0)
			{
				System.out.println("Radio_GetAntennaInfo failure"+result);
				break;
			}
			else
			{
				System.out.println("Radio_GetAntennaInfo success");
				System.out.println("antennaNum"+ata_info.antennaNum);
				System.out.println("state"+ata_info.state);
				System.out.println("powerLevel"+ata_info.powerLevel);
				System.out.println("d_SWR"+ata_info.d_SWR);
				System.out.println("length"+ata_info.length);
				System.out.println("physicalRxPort"+ata_info.physicalRxPort);
				System.out.println("physicalRxPort"+ata_info.physicalRxPort);
			}	
			
			
			
			
			result = linkage.Radio_SetAntennaInfo(handle,ata_info);
			if(result != 0)
			{
				System.out.println("Radio_SetAntennaInfo failure"+result);
				break;
			}
			else
			{
				System.out.println("Radio_SetAntennaInfo success");
			}

			
			
			
			/*ReadParms parm = new ReadParms();
			result =linkage.Radio_ReadTag(handle, 2, 2, 1, 0,parm,0);
			if(result == 0)
			{
				char[] ReadData = parm.ReadData;
				char[] EpcData = parm.EpcData;
				String readvalue = linkage.c2hexs(ReadData, ReadData.length);
				String epcvalue = linkage.c2hexs(EpcData, EpcData.length);
				System.out.println("Radio_ReadTag"+readvalue);
			}
			else
			{
				System.out.println("Radio_ReadTag failure" +result);
			}

			
			
			String value = "5678";
			value = linkage.toHexString(value);
			char [] WriteData = linkage.s2char(value);
			result = linkage.Radio_WriteTag(handle, 2, 1, 1,WriteData, 0, 0);
			if(result != 0)
			{
				System.out.println("Radio_WriteTag faliure"+result);
				break;
			}
			else
			{
				System.out.println("Radio_WriteTag success");
			}*/

			
			
			
			mythread_OpenRadio = new MyThread_OpenRadioThread();
			mythread_OpenRadio.start();	

			mythread_Inventory = new MyThread_InventoryThread();
			mythread_Inventory.start();

			try {
				Thread.currentThread();
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}while(false);
		linkage.FreeAPI();
	}	
}

