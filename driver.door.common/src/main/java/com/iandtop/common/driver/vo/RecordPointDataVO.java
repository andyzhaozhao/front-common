package com.iandtop.common.driver.vo;

import com.iandtop.common.utils.BinaryUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 记录指针信息
 */
public class RecordPointDataVO {

    private byte[] record1 = new byte[13];//读卡记录
    private byte[] record2 = new byte[13];//出门开关
    private byte[] record3 = new byte[13];//门磁
    private byte[] record4 = new byte[13];//远程开门
    private byte[] record5 = new byte[13];//报警
    private byte[] record6 = new byte[13];//系统记录

    private byte[] record1_volume = new byte[4];//读卡记录记录容量
    private byte[] record1_last_num = new byte[4];//读卡记录记录尾号
    private byte[] record1_break = new byte[4];//读卡记录上传断点
    private byte[] record1_rount_std = new byte[1];//读卡记录循环标志

    private String record1_volume_str;
    private String record1_last_num_str ;
    private String record1_break_str ;
    private String record1_rount_std_str;

    public RecordPointDataVO(MessageVO messageVO) {
        byte[] message = messageVO.getData();
        record1 = BinaryUtil.subArray(message,0,13);
        record2 = BinaryUtil.subArray(message,13,26);
        record3 = BinaryUtil.subArray(message,26,39);
        record4 = BinaryUtil.subArray(message,39,52);
        record5 = BinaryUtil.subArray(message,52,65);
        record6 = BinaryUtil.subArray(message,65,78);

        record1_volume = BinaryUtil.subArray(message,0,4);
        record1_last_num = BinaryUtil.subArray(message,4,8);
        record1_break = BinaryUtil.subArray(message,8,12);
        record1_rount_std = BinaryUtil.subArray(message,12,13);

        record1_volume_str =  new BigInteger(BinaryUtil.bcd2Str(record1_volume),16).toString();
        record1_last_num_str =  new BigInteger(BinaryUtil.bcd2Str(record1_last_num),16).toString();
        record1_break_str =  new BigInteger(BinaryUtil.bcd2Str(record1_break),16).toString();
        record1_rount_std_str =  new BigInteger(BinaryUtil.bcd2Str(record1_rount_std),16).toString();
    }

    public byte[] getRecord1() {
        return record1;
    }

    public void setRecord1(byte[] record1) {
        this.record1 = record1;
    }

    public byte[] getRecord2() {
        return record2;
    }

    public void setRecord2(byte[] record2) {
        this.record2 = record2;
    }

    public byte[] getRecord3() {
        return record3;
    }

    public void setRecord3(byte[] record3) {
        this.record3 = record3;
    }

    public byte[] getRecord4() {
        return record4;
    }

    public void setRecord4(byte[] record4) {
        this.record4 = record4;
    }

    public byte[] getRecord5() {
        return record5;
    }

    public void setRecord5(byte[] record5) {
        this.record5 = record5;
    }

    public byte[] getRecord6() {
        return record6;
    }

    public void setRecord6(byte[] record6) {
        this.record6 = record6;
    }

    public byte[] getRecord1_volume() {
        return record1_volume;
    }

    public void setRecord1_volume(byte[] record1_volume) {
        this.record1_volume = record1_volume;
    }

    public byte[] getRecord1_last_num() {
        return record1_last_num;
    }

    public void setRecord1_last_num(byte[] record1_last_num) {
        this.record1_last_num = record1_last_num;
    }

    public byte[] getRecord1_break() {
        return record1_break;
    }

    public void setRecord1_break(byte[] record1_break) {
        this.record1_break = record1_break;
    }

    public byte[] getRecord1_rount_std() {
        return record1_rount_std;
    }

    public void setRecord1_rount_std(byte[] record1_rount_std) {
        this.record1_rount_std = record1_rount_std;
    }

    public String getRecord1_volume_str() {
        return record1_volume_str;
    }

    public void setRecord1_volume_str(String record1_volume_str) {
        this.record1_volume_str = record1_volume_str;
    }

    public String getRecord1_last_num_str() {
        return record1_last_num_str;
    }

    public void setRecord1_last_num_str(String record1_last_num_str) {
        this.record1_last_num_str = record1_last_num_str;
    }

    public String getRecord1_break_str() {
        return record1_break_str;
    }

    public void setRecord1_break_str(String record1_break_str) {
        this.record1_break_str = record1_break_str;
    }

    public String getRecord1_rount_std_str() {
        return record1_rount_std_str;
    }

    public void setRecord1_rount_std_str(String record1_rount_std_str) {
        this.record1_rount_std_str = record1_rount_std_str;
    }

}
