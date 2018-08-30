package com.iandtop.common.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 一卡通平台统一生成二维码,消费二维码
 */
public class TwoDimensionCodeUtil {

	//生成二维码
	public static long getSmartParkQRCode(){

		int num;
		StringBuilder sb = new StringBuilder();
	    sb.append(1);
		for (int i = 0; i < 9; i++){
			num = (int)(Math.random()*10);
			sb.append(num);
		}

		Long TDcode = Long.parseLong(sb.toString());

		if(!checkTDCode(sb.toString())){
			TDcode = getSmartParkQRCode();
		}
		return TDcode;
	}

	/**
	 * 不能生成14开头的数字
	 * @param TDCode
	 * @return
     */
	private static boolean checkTDCode(String TDCode){
		List<String> checkNums = new ArrayList<String>();
		checkNums.add("14");

		String checkNum = TDCode.substring(0,2);
		if(checkNums.contains(checkNum)){
			return false;
		}
		return true;
	}

	public static void main(String[] args){
		for (int i = 0; i < 100; i++){
			System.out.println(getSmartParkQRCode());
		}
	}

}

