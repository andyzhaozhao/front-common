package com.iandtop.common.driver.vo;

import com.iandtop.common.utils.BinaryUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 服务器发给门禁的信息
 */
public class ServerToDeviceMessageVO {

    private byte[] startFlag = new byte[]{0x7e};

    private byte[] deviceSN = new byte[16];// 接收端信息，设备sn

    private byte[] devicePass = new byte[4];// 接收端信息，设备密码

    private byte[] messageCode = new byte[4];// 发送端信息，信息代码

    private MessageVO messageDataVO;

    private byte[] check = new byte[1];// 检验码

    private byte[] endFlag = new byte[]{0x7e};

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
        // 获得check值
        check = getCheckBytes();
        // 转为byte数组
        byte[] r = null;
        List<byte[]> bytebytes = new ArrayList<byte[]>();
        bytebytes.add(startFlag);
        bytebytes.add(deviceSN);
        bytebytes.add(devicePass);
        bytebytes.add(messageCode);
        if (messageDataVO != null) {
            bytebytes.add(messageDataVO.getControlType());
            bytebytes.add(messageDataVO.getControlOrder());
            bytebytes.add(messageDataVO.getControlParam());
            bytebytes.add(messageDataVO.getDataLength());
            bytebytes.add(messageDataVO.getData());
        } else {
            throw new RuntimeException("没有控制码和数据");
        }
        bytebytes.add(check);
        bytebytes.add(endFlag);
        r = BinaryUtil.bytsArrayListTobyteArray(bytebytes);

        return r;
    }

    private byte[] getCheckBytes() {
        List<byte[]> bytes = new ArrayList<byte[]>();
        bytes.add(deviceSN);
        bytes.add(devicePass);
        bytes.add(messageCode);
        if (messageDataVO != null) {
            bytes.add(messageDataVO.getControlType());
            bytes.add(messageDataVO.getControlOrder());
            bytes.add(messageDataVO.getControlParam());
            bytes.add(messageDataVO.getDataLength());
            bytes.add(messageDataVO.getData());
        } else {
            throw new RuntimeException("没有控制码和数据");
        }
        byte[] bs = BinaryUtil.bytsArrayListTobyteArray(bytes);
        byte rrr = 0x00;
        for (int i = 0; i < bs.length; i++) {
            rrr += bs[i];
        }
        return new byte[]{rrr};
    }


}
