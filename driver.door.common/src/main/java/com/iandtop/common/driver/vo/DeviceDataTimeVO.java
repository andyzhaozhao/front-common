package com.iandtop.common.driver.vo;

import com.iandtop.common.utils.BinaryUtil;
import com.iandtop.common.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 平台时间格式：yyyy-MM-dd HH:mm:ss
 * 设备时间格式：ssmmHHddMMWWyy，秒分时日月周年
 * 星期表示：1表示星期一；2表示星期二。。。。6表示星期六；7表示星期日；
 */
public class DeviceDataTimeVO {

	public static int BLength = 7;//byte数组长度
	private byte[] ss = new byte[1];//秒
	private byte[] mm = new byte[1];//分
	private byte[] HH = new byte[1];//时
	private byte[] dd = new byte[1];//日
	private byte[] MM = new byte[1];//月
	private byte[] WW = new byte[1];//周
	private byte[] yy = new byte[1];//年

	private String timeStr = "";//字符串数据
	private String yearStr = "";
	private String monthStr = "";
	private String dayStr = "";
	private String hourStr = "";
	private String minuteStr = "";
	private String secondStr = "";

	public DeviceDataTimeVO(){}

	public DeviceDataTimeVO(byte[] deviceToServerMessageBytes){
		ss = BinaryUtil.subArray(deviceToServerMessageBytes, 32, 33);
		mm = BinaryUtil.subArray(deviceToServerMessageBytes, 33, 34);
		HH = BinaryUtil.subArray(deviceToServerMessageBytes, 34, 35);
		dd = BinaryUtil.subArray(deviceToServerMessageBytes, 35, 36);
		MM = BinaryUtil.subArray(deviceToServerMessageBytes, 36, 37);
		WW = BinaryUtil.subArray(deviceToServerMessageBytes, 37, 38);
		yy = BinaryUtil.subArray(deviceToServerMessageBytes, 38, 39);

		timeStr = BinaryUtil.bcd2Str(BinaryUtil.subArray(deviceToServerMessageBytes, 32, 39));
		yearStr = DateUtils.currentDatetime().substring(0,2) + timeStr.substring(12, 14);
		monthStr = timeStr.substring(8, 10);
		dayStr = timeStr.substring(6, 8);
		hourStr = timeStr.substring(4, 6);
		minuteStr = timeStr.substring(2, 4);
		secondStr = timeStr.substring(0, 2);
	}

	public DeviceDataTimeVO(MessageVO messageVO) {
		byte[] deviceToServerMessageBytes = messageVO.getData();
		ss = BinaryUtil.subArray(deviceToServerMessageBytes, 0, 1);
		mm = BinaryUtil.subArray(deviceToServerMessageBytes, 1, 2);
		HH = BinaryUtil.subArray(deviceToServerMessageBytes, 2, 3);
		dd = BinaryUtil.subArray(deviceToServerMessageBytes, 3, 4);
		MM = BinaryUtil.subArray(deviceToServerMessageBytes, 4, 5);
		WW = BinaryUtil.subArray(deviceToServerMessageBytes, 5, 6);
		yy = BinaryUtil.subArray(deviceToServerMessageBytes, 6, 7);

		timeStr = BinaryUtil.bcd2Str(BinaryUtil.subArray(deviceToServerMessageBytes, 0, 7));
		yearStr = DateUtils.currentDatetime().substring(0,2) + timeStr.substring(12, 14);
		monthStr = timeStr.substring(8, 10);
		dayStr = timeStr.substring(6, 8);
		hourStr = timeStr.substring(4, 6);
		minuteStr = timeStr.substring(2, 4);
		secondStr = timeStr.substring(0, 2);
	}

	public byte[] getBytes(Date date) {
		String ds = DateUtils.formatDatetime(date);//2016-07-20 14:36:04
		yy = BinaryUtil.str2Bcd(ds.substring(2,4));
		int uapWeek =  DateUtils.dayOfWeek();
		String weekStr = "0"+(uapWeek==1?"7":(uapWeek-1)+"");

		WW = BinaryUtil.str2Bcd(weekStr);
		MM = BinaryUtil.str2Bcd(ds.substring(5,7));
		dd = BinaryUtil.str2Bcd(ds.substring(8,10));
		HH = BinaryUtil.str2Bcd(ds.substring(11,13));
		mm = BinaryUtil.str2Bcd(ds.substring(14,16));
		ss = BinaryUtil.str2Bcd(ds.substring(17,19));

		//转为byte数组
		byte[] r = null;
		List<byte[]> bytebytes = new ArrayList<byte[]>();
		bytebytes.add(ss);
		bytebytes.add(mm);
		bytebytes.add(HH);
		bytebytes.add(dd);
		bytebytes.add(MM);
		bytebytes.add(WW);
		bytebytes.add(yy);

		r = BinaryUtil.bytsArrayListTobyteArray(bytebytes);
		return r;
	}

	public String getTimeStr() {
		return timeStr;
	}


	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}


	public String getYearStr() {
		return yearStr;
	}


	public void setYearStr(String yearStr) {
		this.yearStr = yearStr;
	}


	public String getMonthStr() {
		return monthStr;
	}


	public void setMonthStr(String monthStr) {
		this.monthStr = monthStr;
	}


	public String getDayStr() {
		return dayStr;
	}


	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}


	public String getHourStr() {
		return hourStr;
	}


	public void setHourStr(String hourStr) {
		this.hourStr = hourStr;
	}


	public String getMinuteStr() {
		return minuteStr;
	}


	public void setMinuteStr(String minuteStr) {
		this.minuteStr = minuteStr;
	}


	public String getSecondStr() {
		return secondStr;
	}


	public void setSecondStr(String secondStr) {
		this.secondStr = secondStr;
	}


	public byte[] getSs() {
		return ss;
	}


	public void setSs(byte[] ss) {
		this.ss = ss;
	}


	public byte[] getMm() {
		return mm;
	}


	public void setMm(byte[] mm) {
		this.mm = mm;
	}


	public byte[] getHH() {
		return HH;
	}


	public void setHH(byte[] hH) {
		HH = hH;
	}


	public byte[] getDd() {
		return dd;
	}


	public void setDd(byte[] dd) {
		this.dd = dd;
	}


	public byte[] getMM() {
		return MM;
	}


	public void setMM(byte[] mM) {
		MM = mM;
	}


	public byte[] getWW() {
		return WW;
	}


	public void setWW(byte[] wW) {
		WW = wW;
	}


	public byte[] getYy() {
		return yy;
	}


	public void setYy(byte[] yy) {
		this.yy = yy;
	}
}
