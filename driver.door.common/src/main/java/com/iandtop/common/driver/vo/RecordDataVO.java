package com.iandtop.common.driver.vo;

import com.iandtop.common.utils.BinaryUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备运行信息
 */
public class RecordDataVO {

    public static Map<String,String> stateMap = new HashMap<String, String>();
    static{
        stateMap.put("1","合法开门");
        stateMap.put("2","密码开门------------卡号为密码");
        stateMap.put("3","卡加密码");
        stateMap.put("4","手动输入卡加密码");
        stateMap.put("5","首卡开门");
        stateMap.put("6","门常开   ---  常开工作方式中，刷卡进入常开状态");
        stateMap.put("7","多卡开门  --  门未开，等待继续读卡");
        stateMap.put("8","重复读卡");
        stateMap.put("9","有效期过期");
        stateMap.put("10","开门时段过期");
        stateMap.put("11","节假日无效");
        stateMap.put("12","非法卡");
        stateMap.put("13","巡更卡  --  不开门");
        stateMap.put("14","探测锁定");
        stateMap.put("15","无有效次数");
        stateMap.put("16","防潜回");
        stateMap.put("17","密码错误------------卡号为错误密码");
        stateMap.put("18","密码加卡模式密码错误----卡号为卡号");
        stateMap.put("19","锁定时(读卡)或(读卡加密码)开门");
        stateMap.put("20","锁定时(密码开门)");
        stateMap.put("21","首卡未开门");
        stateMap.put("22","挂失卡");
        stateMap.put("23","黑名单卡");
        stateMap.put("24","门内上限已满，禁止入门。");
        stateMap.put("25","开启防盗主机(设置卡)");
        stateMap.put("26","关闭防盗主机(设置卡)");
        stateMap.put("27","开启防盗主机(密码)");
        stateMap.put("28","关闭防盗主机(密码)");
        stateMap.put("29","互锁时(读卡)或(读卡加密码)开门");
        stateMap.put("30","互锁时(密码开门)");
        stateMap.put("31","全卡开门");
        stateMap.put("32","多卡开门--等待下张卡");
        stateMap.put("33","多卡开门--组合错误");
        stateMap.put("34","非首卡时段刷卡无效");
        stateMap.put("35","非首卡时段密码无效");
        stateMap.put("36","禁止刷卡开门");
        stateMap.put("37","禁止密码开门");
    }

    private byte[] card_code = new byte[5];//卡号
    private byte[] time =new byte[6];//时间
    private byte[] reader_no = new byte[1];//读卡器号
    private byte[] state = new byte[1];//状态

    private String card_codeStr;
    private String read_timeStr ;
    private String reader_noStr ;
    private String stateStr;

    public RecordDataVO(MessageVO messageVO) {
        byte[] message = messageVO.getData();
        init(message);
    }

    public RecordDataVO(byte[] recordBytes) {
        init(recordBytes);
    }

    private void init(byte[] message){
        card_code = BinaryUtil.subArray(message,0,5);
        time = BinaryUtil.subArray(message,5,11);
        reader_no = BinaryUtil.subArray(message,11,12);
        state = BinaryUtil.subArray(message,12,13);

        card_codeStr = new BigInteger(BinaryUtil.bcd2Str(card_code),16).toString();
        String ts = BinaryUtil.bcd2Str(time);
        read_timeStr = "20" + ts.substring(0,2) +
                "-" + ts.substring(2,4) +
                "-" + ts.substring(4,6) +
                " " + ts.substring(6,8) +
                ":" + ts.substring(8,10) +
                ":" + ts.substring(10,12);
        reader_noStr = BinaryUtil.bcd2Str(reader_no);
        stateStr = new BigInteger(BinaryUtil.bcd2Str(state),16).toString();
    }

    public byte[] getCard_code() {
        return card_code;
    }

    public void setCard_code(byte[] card_code) {
        this.card_code = card_code;
    }

    public byte[] getState() {
        return state;
    }

    public void setState(byte[] state) {
        this.state = state;
    }

    public byte[] getReader_no() {
        return reader_no;
    }

    public void setReader_no(byte[] reader_no) {
        this.reader_no = reader_no;
    }

    public byte[] getTime() {
        return time;
    }

    public void setTime(byte[] time) {
        this.time = time;
    }

    public String getCard_codeStr() {
        return card_codeStr;
    }

    public void setCard_codeStr(String card_codeStr) {
        this.card_codeStr = card_codeStr;
    }

    public String getRead_timeStr() {
        return read_timeStr;
    }

    public void setRead_timeStr(String read_timeStr) {
        this.read_timeStr = read_timeStr;
    }

    public String getReader_noStr() {
        return reader_noStr;
    }

    public void setReader_noStr(String reader_noStr) {
        this.reader_noStr = reader_noStr;
    }

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public byte[] getBytes() {
        //转为byte数组
        byte[] r = null;
        List<byte[]> bytebytes = new ArrayList<byte[]>();
        bytebytes.add(card_code);
        bytebytes.add(time);
        bytebytes.add(reader_no);
        bytebytes.add(state);

        r = BinaryUtil.bytsArrayListTobyteArray(bytebytes);
        return r;
    }
}
