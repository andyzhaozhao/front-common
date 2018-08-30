package com.iandtop.common.driver.vo;

import com.iandtop.common.utils.BinaryUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 每个星期的八个时间段 时间组每个星期八个时间段
 */
public class DeviceDataTimeGrpSegmentVO {

	public static int BLength = 32;// byte数组长度
	private byte[] segment1 = new byte[4];// 段1
	private byte[] segment2 = new byte[4];// 段2
	private byte[] segment3 = new byte[4];// 段3
	private byte[] segment4 = new byte[4];// 段4
	private byte[] segment5 = new byte[4];// 段5
	private byte[] segment6 = new byte[4];// 段6
	private byte[] segment7 = new byte[4];// 段7
	private byte[] segment8 = new byte[4];// 段8

	public DeviceDataTimeGrpSegmentVO() {
	}

	public static void main(String args [] ){
		String aaa = "12:10:45-13:20:45";
		String d = aaa.substring(0,2)+aaa.substring(3, 5)
				+aaa.substring(9, 11)+aaa.substring(12, 14);
		byte[] asdf = BinaryUtil.str2Bcd(d);
		System.out.println(asdf);
	}

	//时间格式:HH:mm:ss-HH:mm:ss
	public DeviceDataTimeGrpSegmentVO(String time1, String time2, String time3, String time4,
									  String time5, String time6, String time7, String time8) {
		segment1 = BinaryUtil.str2Bcd(time1.substring(0,2)+time1.substring(3, 5)+time1.substring(9, 11)+time1.substring(12, 14));
		segment2 = BinaryUtil.str2Bcd(time2.substring(0,2)+time2.substring(3, 5)+time2.substring(9, 11)+time2.substring(12, 14));
		segment3 = BinaryUtil.str2Bcd(time3.substring(0,2)+time3.substring(3, 5)+time3.substring(9, 11)+time3.substring(12, 14));
		segment4 = BinaryUtil.str2Bcd(time4.substring(0,2)+time4.substring(3, 5)+time4.substring(9, 11)+time4.substring(12, 14));
		segment5 = BinaryUtil.str2Bcd(time5.substring(0,2)+time5.substring(3, 5)+time5.substring(9, 11)+time5.substring(12, 14));
		segment6 = BinaryUtil.str2Bcd(time6.substring(0,2)+time6.substring(3, 5)+time6.substring(9, 11)+time6.substring(12, 14));
		segment7 = BinaryUtil.str2Bcd(time7.substring(0,2)+time7.substring(3, 5)+time7.substring(9, 11)+time7.substring(12, 14));
		segment8 = BinaryUtil.str2Bcd(time8.substring(0,2)+time8.substring(3, 5)+time8.substring(9, 11)+time8.substring(12, 14));
	}

	public DeviceDataTimeGrpSegmentVO(byte[] bytes) {
		if (bytes.length != 32) {

		} else {
			segment1 = BinaryUtil.subArray(bytes, 0, 4);
			segment2 = BinaryUtil.subArray(bytes, 4, 8);
			segment3 = BinaryUtil.subArray(bytes, 8, 12);
			segment4 = BinaryUtil.subArray(bytes, 12, 16);
			segment5 = BinaryUtil.subArray(bytes, 16, 20);
			segment6 = BinaryUtil.subArray(bytes, 20, 24);
			segment7 = BinaryUtil.subArray(bytes, 24, 28);
			segment8 = BinaryUtil.subArray(bytes, 28, 32);
		}
	}

	public byte[] getBytes() {
		// 转为byte数组
		byte[] r = null;
		List<byte[]> bytebytes = new ArrayList<byte[]>();
		bytebytes.add(segment1);
		bytebytes.add(segment2);
		bytebytes.add(segment3);
		bytebytes.add(segment4);
		bytebytes.add(segment5);
		bytebytes.add(segment6);
		bytebytes.add(segment7);
		bytebytes.add(segment8);

		r = BinaryUtil.bytsArrayListTobyteArray(bytebytes);
		return r;
	}

	public byte[] getSegment1() {
		return segment1;
	}

	public void setSegment1(byte[] segment1) {
		this.segment1 = segment1;
	}

	public byte[] getSegment2() {
		return segment2;
	}

	public void setSegment2(byte[] segment2) {
		this.segment2 = segment2;
	}

	public byte[] getSegment3() {
		return segment3;
	}

	public void setSegment3(byte[] segment3) {
		this.segment3 = segment3;
	}

	public byte[] getSegment4() {
		return segment4;
	}

	public void setSegment4(byte[] segment4) {
		this.segment4 = segment4;
	}

	public byte[] getSegment5() {
		return segment5;
	}

	public void setSegment5(byte[] segment5) {
		this.segment5 = segment5;
	}

	public byte[] getSegment6() {
		return segment6;
	}

	public void setSegment6(byte[] segment6) {
		this.segment6 = segment6;
	}

	public byte[] getSegment7() {
		return segment7;
	}

	public void setSegment7(byte[] segment7) {
		this.segment7 = segment7;
	}

	public byte[] getSegment8() {
		return segment8;
	}

	public void setSegment8(byte[] segment8) {
		this.segment8 = segment8;
	}

}
