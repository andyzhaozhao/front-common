package com.iandtop.common.driver.vo;

import com.iandtop.common.utils.BinaryUtil;

/**
 * 门工作方式
 */
public class DeviceDataWorkModeVO {

	public static final int DoorOpenType_normal= 1;//普通
	public static final int DoorOpenType_multCard =2;//多卡
	public static final int DoorOpenType_firstCard= 3;//首卡（时段）
	public static final int DoorOpenType_longOpen= 4;//常开（时段）

	public static final int DoorLongOpenType_1= 1;//合法卡在时段内即可常开
	public static final int DoorLongOpenType_2= 2;//授权中标记为常开卡的在指定时段内刷卡即可常开
	public static final int DoorLongOpenType_3= 3;//自动开关，到时间自动开关门。

	public static int BLength = 229;//byte数组长度
	private byte[] door_code = new byte[1];//端口
	private byte[] isUsing= new byte[1];//启用
	private byte[] door_opentype = new byte[1];//开门方式
	private byte[] door_long_opentype = new byte[1];//常开触发模式
	private byte[] def = new byte[1];//保留字段
	private byte[] timegroup = new byte[7*8*4];//时间段  7个星期*每天八段*每段起始结束时间4

	public DeviceDataWorkModeVO(byte[] deviceToServerMessageBytes){
		door_code = BinaryUtil.subArray(deviceToServerMessageBytes, 32, 33);
		isUsing = BinaryUtil.subArray(deviceToServerMessageBytes, 33, 34);
		door_opentype = BinaryUtil.subArray(deviceToServerMessageBytes, 34, 35);
		door_long_opentype = BinaryUtil.subArray(deviceToServerMessageBytes, 35, 36);
		def = BinaryUtil.subArray(deviceToServerMessageBytes, 36, 37);
		timegroup = BinaryUtil.subArray(deviceToServerMessageBytes, 37, 261);
	}

	public DeviceDataWorkModeVO(MessageVO messageVO) {
		byte[] deviceToServerMessageBytes = messageVO.getData();
		door_code = BinaryUtil.subArray(deviceToServerMessageBytes, 0, 1);
		isUsing = BinaryUtil.subArray(deviceToServerMessageBytes, 1, 2);
		door_opentype = BinaryUtil.subArray(deviceToServerMessageBytes, 2, 3);
		door_long_opentype = BinaryUtil.subArray(deviceToServerMessageBytes, 3, 4);
		def = BinaryUtil.subArray(deviceToServerMessageBytes, 4, 5);
		timegroup = BinaryUtil.subArray(deviceToServerMessageBytes, 5, 229);
	}

	public byte[] getDoor_code() {
		return door_code;
	}

	public void setDoor_code(byte[] door_code) {
		this.door_code = door_code;
	}

	public byte[] getIsUsing() {
		return isUsing;
	}

	public void setIsUsing(byte[] isUsing) {
		this.isUsing = isUsing;
	}

	public byte[] getDoor_opentype() {
		return door_opentype;
	}

	public void setDoor_opentype(byte[] door_opentype) {
		this.door_opentype = door_opentype;
	}

	public byte[] getDoor_long_opentype() {
		return door_long_opentype;
	}

	public void setDoor_long_opentype(byte[] door_long_opentype) {
		this.door_long_opentype = door_long_opentype;
	}

	public byte[] getDef() {
		return def;
	}

	public void setDef(byte[] def) {
		this.def = def;
	}

	public byte[] getTimegroup() {
		return timegroup;
	}

	public void setTimegroup(byte[] timegroup) {
		this.timegroup = timegroup;
	}

}
