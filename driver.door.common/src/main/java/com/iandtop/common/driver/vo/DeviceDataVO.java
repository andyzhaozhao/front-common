package com.iandtop.common.driver.vo;

import com.iandtop.common.utils.BinaryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备运行信息
 */
public class DeviceDataVO {

    public static int BLength = 18;//byte数组长度
    private byte[] day_num = new byte[2];//设备运行天数
    private byte[] format_num = new byte[2];//格式化次数
    private byte[] reset_num = new byte[2];//看门狗复位次数
    private byte[] ups_state = new byte[1];//UPS供电状态 0--表示电源取电；1--表示UPS供电
    private byte[] temperature = new byte[2];//系统温度 第一字节是正或负，0负，1正
    private byte[] electricity_time = new byte[7];//上电时间  时间格式：ssmmHHddMMWWyy，秒分时日月周年
    private byte[] voltage = new byte[2];//TDV12电压；第一字小数点前，第二字节小数点后

    public DeviceDataVO(byte[] deviceToServerMessageBytes){
        day_num = BinaryUtil.subArray(deviceToServerMessageBytes, 32, 34);
        format_num = BinaryUtil.subArray(deviceToServerMessageBytes, 34, 36);
        reset_num = BinaryUtil.subArray(deviceToServerMessageBytes, 36, 38);
        ups_state = BinaryUtil.subArray(deviceToServerMessageBytes, 38, 39);
        temperature = BinaryUtil.subArray(deviceToServerMessageBytes, 39, 41);
        electricity_time = BinaryUtil.subArray(deviceToServerMessageBytes, 41, 48);
        voltage = BinaryUtil.subArray(deviceToServerMessageBytes, 48, 50);
    }

    public DeviceDataVO(MessageVO messageVO) {
        byte[] deviceToServerMessageBytes = messageVO.getData();
        day_num = BinaryUtil.subArray(deviceToServerMessageBytes, 0, 2);
        format_num = BinaryUtil.subArray(deviceToServerMessageBytes, 2, 4);
        reset_num = BinaryUtil.subArray(deviceToServerMessageBytes, 4, 6);
        ups_state = BinaryUtil.subArray(deviceToServerMessageBytes, 6, 7);
        temperature = BinaryUtil.subArray(deviceToServerMessageBytes, 7, 9);
        electricity_time = BinaryUtil.subArray(deviceToServerMessageBytes, 9, 16);
        voltage = BinaryUtil.subArray(deviceToServerMessageBytes, 16, 18);
    }

    public byte[] getDay_num() {
        return day_num;
    }

    public void setDay_num(byte[] day_num) {
        this.day_num = day_num;
    }

    public byte[] getFormat_num() {
        return format_num;
    }

    public void setFormat_num(byte[] format_num) {
        this.format_num = format_num;
    }

    public byte[] getReset_num() {
        return reset_num;
    }

    public void setReset_num(byte[] reset_num) {
        this.reset_num = reset_num;
    }

    public byte[] getUps_state() {
        return ups_state;
    }

    public void setUps_state(byte[] ups_state) {
        this.ups_state = ups_state;
    }

    public byte[] getTemperature() {
        return temperature;
    }

    public void setTemperature(byte[] temperature) {
        this.temperature = temperature;
    }

    public byte[] getElectricity_time() {
        return electricity_time;
    }

    public void setElectricity_time(byte[] electricity_time) {
        this.electricity_time = electricity_time;
    }

    public byte[] getVoltage() {
        return voltage;
    }

    public void setVoltage(byte[] voltage) {
        this.voltage = voltage;
    }

    public byte[] getBytes() {
        //转为byte数组
        byte[] r = null;
        List<byte[]> bytebytes = new ArrayList<byte[]>();
        bytebytes.add(day_num);
        bytebytes.add(format_num);
        bytebytes.add(reset_num);
        bytebytes.add(ups_state);
        bytebytes.add(temperature);
        bytebytes.add(electricity_time);
        bytebytes.add(voltage);

        r = BinaryUtil.bytsArrayListTobyteArray(bytebytes);
        return r;
    }
}
