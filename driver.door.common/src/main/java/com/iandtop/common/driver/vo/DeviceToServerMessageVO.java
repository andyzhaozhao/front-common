package com.iandtop.common.driver.vo;

import com.iandtop.common.utils.BinaryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *门禁发给服务器的信息
 */
public class DeviceToServerMessageVO {

    public static int BLengthwithoutData = 34;//除去data剩下的消息的总长度

    private byte[] startFlag = new byte[]{0x7e};

    private byte[] messageCode = new byte[4];//发送端信息，信息代码

    private byte[] deviceSN = new byte[16];//接收端信息，设备sn

    private byte[] devicePass = new byte[4];//接收端信息，设备密码

    private MessageVO messageDataVO ;

    private byte[] check = new byte[1];//检验码

    private byte[] endFlag = new byte[]{0x7e};


    public DeviceToServerMessageVO(){}
    public DeviceToServerMessageVO(byte[] bytes){
        startFlag = BinaryUtil.subArray(bytes,0,1);
        messageCode = BinaryUtil.subArray(bytes,1,5);
        deviceSN = BinaryUtil.subArray(bytes,5,21);
        devicePass = BinaryUtil.subArray(bytes,21,25);
        messageDataVO = new MessageVO(bytes);
        check = BinaryUtil.subArray(bytes,bytes.length-2,bytes.length-1);
        endFlag = BinaryUtil.subArray(bytes,bytes.length-1,bytes.length);
    }

    public Boolean isEndVO(){
        if(messageDataVO.getControlType()[0] == 0x37
                && messageDataVO.getControlOrder()[0] == 0x03
                && messageDataVO.getControlParam()[0] ==(byte) 0xff
                && messageDataVO.getDataLength()[3] == 0x04){
            return true;
        }
        return false;
    }
    public byte[] getStartFlag() {
        return startFlag;
    }

    public void setStartFlag(byte[] startFlag) {
        this.startFlag = startFlag;
    }

    public byte[] getDeviceSN() {
        return deviceSN;
    }

    public void setDeviceSN(byte[] deviceSN) {
        this.deviceSN = deviceSN;
    }

    public byte[] getDevicePass() {
        return devicePass;
    }

    public void setDevicePass(byte[] devicePass) {
        this.devicePass = devicePass;
    }

    public byte[] getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(byte[] messageCode) {
        this.messageCode = messageCode;
    }

    public MessageVO getMessageDataVO() {
        return messageDataVO;
    }

    public void setMessageDataVO(MessageVO messageDataVO) {
        this.messageDataVO = messageDataVO;
    }

    public byte[] getCheck() {
        return check;
    }

    public void setCheck(byte[] check) {
        this.check = check;
    }

    public byte[] getEndFlag() {
        return endFlag;
    }

    public void setEndFlag(byte[] endFlag) {
        this.endFlag = endFlag;
    }

    public byte[] getBytes() {
        byte[] r = null;
        List<byte[]> bytebytes = new ArrayList<byte[]>();
        bytebytes.add(startFlag);
        bytebytes.add(messageCode);
        bytebytes.add(deviceSN);
        bytebytes.add(devicePass);
        if(messageDataVO!=null){
            bytebytes.add(messageDataVO.getControlType());
            bytebytes.add(messageDataVO.getControlOrder());
            bytebytes.add(messageDataVO.getControlParam());
            bytebytes.add(messageDataVO.getDataLength());
            bytebytes.add(messageDataVO.getData());
        }else{
            throw new RuntimeException("没有控制码和数据");
        }
        bytebytes.add(check);
        bytebytes.add(endFlag);
            r = BinaryUtil.bytsArrayListTobyteArray(bytebytes);

        return r;
    }
}
