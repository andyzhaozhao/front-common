package com.iandtop.common.driver.vo;

import com.iandtop.common.utils.BinaryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 星期 每个时间段是星期一到星期日
 */
public class DeviceDataTimeGrpWeekVO {

	public static int BLength = 7 * 8 * 4;// byte数组长度

	// private byte[] group = new byte[2];//组号
	private DeviceDataTimeGrpSegmentVO mondy = new DeviceDataTimeGrpSegmentVO();// 星期1
	private DeviceDataTimeGrpSegmentVO tuesday = new DeviceDataTimeGrpSegmentVO();// 星期2
	private DeviceDataTimeGrpSegmentVO wednesday = new DeviceDataTimeGrpSegmentVO();// 星期3
	private DeviceDataTimeGrpSegmentVO thursday = new DeviceDataTimeGrpSegmentVO();// 星期4
	private DeviceDataTimeGrpSegmentVO friday = new DeviceDataTimeGrpSegmentVO();// 星期5
	private DeviceDataTimeGrpSegmentVO saturday = new DeviceDataTimeGrpSegmentVO();// 星期6
	private DeviceDataTimeGrpSegmentVO sunday = new DeviceDataTimeGrpSegmentVO();// 星期日

	public DeviceDataTimeGrpWeekVO() {
	}

	public DeviceDataTimeGrpWeekVO(DeviceDataTimeGrpSegmentVO _monday, DeviceDataTimeGrpSegmentVO _tuesday,
								   DeviceDataTimeGrpSegmentVO _wednesday, DeviceDataTimeGrpSegmentVO _thursday, DeviceDataTimeGrpSegmentVO _friday,
								   DeviceDataTimeGrpSegmentVO _saturday, DeviceDataTimeGrpSegmentVO _sunday) {
		this.mondy = _monday;
		this.tuesday = _tuesday;
		this.wednesday = _wednesday;
		this.thursday = _thursday;
		this.friday = _friday;
		this.saturday = _saturday;
		this.sunday = _sunday;
	}

	public DeviceDataTimeGrpWeekVO(byte[] timegroupBytes) {
		mondy = new DeviceDataTimeGrpSegmentVO(BinaryUtil.subArray(timegroupBytes, 0, 32));
		tuesday = new DeviceDataTimeGrpSegmentVO(BinaryUtil.subArray(timegroupBytes, 32, 64));
		wednesday = new DeviceDataTimeGrpSegmentVO(BinaryUtil.subArray(timegroupBytes, 64, 96));
		thursday = new DeviceDataTimeGrpSegmentVO(BinaryUtil.subArray(timegroupBytes, 96, 128));
		friday = new DeviceDataTimeGrpSegmentVO(BinaryUtil.subArray(timegroupBytes, 128, 156));
		saturday = new DeviceDataTimeGrpSegmentVO(BinaryUtil.subArray(timegroupBytes, 156, 192));
		sunday = new DeviceDataTimeGrpSegmentVO(BinaryUtil.subArray(timegroupBytes, 192, 224));
	}

	public byte[] getBytes() {
		// 转为byte数组
		byte[] r = null;
		List<byte[]> bytebytes = new ArrayList<byte[]>();
		bytebytes.add(mondy.getBytes());
		bytebytes.add(tuesday.getBytes());
		bytebytes.add(wednesday.getBytes());
		bytebytes.add(thursday.getBytes());
		bytebytes.add(friday.getBytes());
		bytebytes.add(saturday.getBytes());
		bytebytes.add(sunday.getBytes());

		r = BinaryUtil.bytsArrayListTobyteArray(bytebytes);
		return r;
	}

	public DeviceDataTimeGrpSegmentVO getMondy() {
		return mondy;
	}

	public void setMondy(DeviceDataTimeGrpSegmentVO mondy) {
		this.mondy = mondy;
	}

	public DeviceDataTimeGrpSegmentVO getTuesday() {
		return tuesday;
	}

	public void setTuesday(DeviceDataTimeGrpSegmentVO tuesday) {
		this.tuesday = tuesday;
	}

	public DeviceDataTimeGrpSegmentVO getWednesday() {
		return wednesday;
	}

	public void setWednesday(DeviceDataTimeGrpSegmentVO wednesday) {
		this.wednesday = wednesday;
	}

	public DeviceDataTimeGrpSegmentVO getThursday() {
		return thursday;
	}

	public void setThursday(DeviceDataTimeGrpSegmentVO thursday) {
		this.thursday = thursday;
	}

	public DeviceDataTimeGrpSegmentVO getFriday() {
		return friday;
	}

	public void setFriday(DeviceDataTimeGrpSegmentVO friday) {
		this.friday = friday;
	}

	public DeviceDataTimeGrpSegmentVO getSaturday() {
		return saturday;
	}

	public void setSaturday(DeviceDataTimeGrpSegmentVO saturday) {
		this.saturday = saturday;
	}

	public DeviceDataTimeGrpSegmentVO getSunday() {
		return sunday;
	}

	public void setSunday(DeviceDataTimeGrpSegmentVO sunday) {
		this.sunday = sunday;
	}

}
