package com.iandtop.common.driver.vo;


import com.iandtop.common.utils.BinaryUtil;

/**
 * 控制码\数据vo
 *
 * @author andyzhao
 */
public class MessageVO {

    public static int Type_NULL = -1;//无此消息类型
    public static int Type_OK = 1;//ok
    public static int Type_PasswordError = 2;//密码错误
    public static int Type_checkError = 3;//校验错
    public static int Type_ipError = 4;//IP设置错误


    private byte[] controlType = new byte[1];//控制码，分类

    private byte[] controlOrder = new byte[1];//控制码，命令

    private byte[] controlParam = new byte[1];//控制码，参数

    private byte[] dataLength = new byte[4];//数据码，数据长度

    private byte[] data = null;//数据码，数据

    public MessageVO() {
    }
//    public MessageVO(Buffer buffer){
//        controlType = buffer.getBytes(25,26);
//        controlOrder = buffer.getBytes(26,27);
//        controlParam = buffer.getBytes(27,28);
//        dataLength = buffer.getBytes(28,32);
//        int to = BinaryUtil.byteToIntHignInF(dataLength)+32 ;
//        data =buffer.getBytes(32,to);
//    }

    public MessageVO(byte[] deviceToServerMessageBytes) {
        controlType = BinaryUtil.subArray(deviceToServerMessageBytes, 25, 26);
        controlOrder = BinaryUtil.subArray(deviceToServerMessageBytes, 26, 27);
        controlParam = BinaryUtil.subArray(deviceToServerMessageBytes, 27, 28);
        dataLength = BinaryUtil.subArray(deviceToServerMessageBytes, 28, 32);
        int to = BinaryUtil.byteToIntHignInF(dataLength) + 32;
        data = BinaryUtil.subArray(deviceToServerMessageBytes, 32, to);
    }

    public byte[] getControlType() {
        return controlType;
    }

    public void setControlType(byte[] controlType) {
        this.controlType = controlType;
    }

    public byte[] getControlOrder() {
        return controlOrder;
    }

    public void setControlOrder(byte[] controlOrder) {
        this.controlOrder = controlOrder;
    }

    public byte[] getControlParam() {
        return controlParam;
    }

    public void setControlParam(byte[] controlParam) {
        this.controlParam = controlParam;
    }

    public byte[] getDataLength() {
        return dataLength;
    }

    public void setDataLength(byte[] dataLength) {
        this.dataLength = dataLength;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
