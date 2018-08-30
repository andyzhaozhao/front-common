package com.iandtop.common.driver.vo;

import com.iandtop.common.utils.BinaryUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 时间组
 */
public class DeviceDataTimeGrpVO {

	// public static int BLength = 4;//byte数组长度
	private List<DeviceDataTimeGrpWeekVO> timeGrps = new ArrayList<DeviceDataTimeGrpWeekVO>();// 最大长度64
	private List<Integer> groupNums = new ArrayList<Integer>();//对应的序列号， 最大长度64

	public DeviceDataTimeGrpVO() {
	}

	public DeviceDataTimeGrpVO(byte[] deviceToServerMessageBytes) {
		int mlen = DeviceToServerMessageVO.BLengthwithoutData + DeviceDataTimeGrpWeekVO.BLength + 2;// 2:组号
		int emlen = DeviceToServerMessageVO.BLengthwithoutData + 4;// 传输结束 消息的长度
		int tlen = (deviceToServerMessageBytes.length - emlen) / mlen;// 有多少条消息
//
//		List<byte[]> btlist = new ArrayList<byte[]>();
		Boolean isBegin = true;
		byte[] tmp = new byte[1024];
		int pos = 0;
		for (int i = 0; i < deviceToServerMessageBytes.length; i++) {
			byte crtbyte = deviceToServerMessageBytes[i];
			if (crtbyte == 0x7e) {
				if (isBegin) {
					tmp[pos] = crtbyte;
					pos++;
				} else {//是结尾处的0x7e
					tmp[pos] = crtbyte;
					byte[] allm = BinaryUtil.subArray(tmp, 0, pos+1) ;
					byte[] weeks	 = BinaryUtil.subArray(allm, 34, allm.length-2)  ;
					DeviceDataTimeGrpWeekVO weekVO = new DeviceDataTimeGrpWeekVO(weeks);
					timeGrps.add(weekVO);
					byte[] gnb = BinaryUtil.subArray(allm, 32,34)  ;
					int groupnum = BinaryUtil.byteToIntHignInF(new byte[]{0x00,0x00,gnb[0],gnb[1]});
					groupNums.add(groupnum);
					pos = 0 ;
					tmp = new byte[1024];
				}
				isBegin = !isBegin;
			} else {
				tmp[pos] = crtbyte;
				pos++;
			}
		}

//		for (int i = 0; i < timeGrps.size(); i++) {
//			weekVO.getMondy();
//		}

//		for (int i = 0; i < btlist.size(); i++) {
//			int start = i * mlen;
//			int end = (i + 1) * mlen;
//			byte[] msg = BinaryUtil.subArray(deviceToServerMessageBytes, start, end);
//			int length = 64;
//		}

//		int length = 64;
//		for (int i = 0; i < length; i++) {
//			DeviceDataTimeGrpWeekVO weeks = new DeviceDataTimeGrpWeekVO();
//		}

		timeGrps.get(31).getFriday();
	}

	/*
	 * public byte[] getBytes() { //转为byte数组 byte[] r = null; List<byte[]>
	 * bytebytes = new ArrayList<byte[]>(); bytebytes.add(cardReader1);
	 * bytebytes.add(cardReader2); bytebytes.add(cardReader3);
	 * bytebytes.add(cardReader4);
	 * 
	 * r = BinaryUtil.bytsArrayListTobyteArray(bytebytes); return r; }
	 */

	public List<DeviceDataTimeGrpWeekVO> getTimeGrps() {
		return timeGrps;
	}
}
