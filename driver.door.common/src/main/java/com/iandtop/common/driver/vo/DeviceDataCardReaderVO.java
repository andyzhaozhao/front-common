package com.iandtop.common.driver.vo;

import com.iandtop.common.utils.BinaryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 读卡器参数
 * 1	韦根26(三字节)
 * 2	韦根34(四字节)
 * 3	韦根26(二字节)
 * 4	禁用
 */
public class DeviceDataCardReaderVO {

    public static int CardReaderType_1wg26 = 1;
    public static int CardReaderType_wg34 = 2;
    public static int CardReaderType_2wg26 = 3;
    public static int CardReaderType_null = 4;

    public static int BLength = 4;//byte数组长度
    private byte[] cardReader1 = new byte[1];//读卡器1
    private byte[] cardReader2 = new byte[1];//读卡器2
    private byte[] cardReader3 = new byte[1];//读卡器3
    private byte[] cardReader4 = new byte[1];//读卡器4

    public DeviceDataCardReaderVO(int t1, int t2, int t3, int t4) {
        cardReader1 = BinaryUtil.subArray(BinaryUtil.intToByteHignInF(t1), 3, 4);
        cardReader2 = BinaryUtil.subArray(BinaryUtil.intToByteHignInF(t2), 3, 4);
        cardReader3 = BinaryUtil.subArray(BinaryUtil.intToByteHignInF(t3), 3, 4);
        cardReader4 = BinaryUtil.subArray(BinaryUtil.intToByteHignInF(t4), 3, 4);
    }

    public DeviceDataCardReaderVO(MessageVO messageVO) {
        byte[] deviceToServerMessageBytes = messageVO.getData();
        cardReader1 = BinaryUtil.subArray(deviceToServerMessageBytes, 0, 1);
        cardReader2 = BinaryUtil.subArray(deviceToServerMessageBytes, 1, 2);
        cardReader3 = BinaryUtil.subArray(deviceToServerMessageBytes, 2, 3);
        cardReader4 = BinaryUtil.subArray(deviceToServerMessageBytes, 3, 4);
    }

    public byte[] getCardReader1() {
        return cardReader1;
    }


    public void setCardReader1(byte[] cardReader1) {
        this.cardReader1 = cardReader1;
    }


    public byte[] getCardReader2() {
        return cardReader2;
    }


    public void setCardReader2(byte[] cardReader2) {
        this.cardReader2 = cardReader2;
    }


    public byte[] getCardReader3() {
        return cardReader3;
    }


    public void setCardReader3(byte[] cardReader3) {
        this.cardReader3 = cardReader3;
    }


    public byte[] getCardReader4() {
        return cardReader4;
    }


    public void setCardReader4(byte[] cardReader4) {
        this.cardReader4 = cardReader4;
    }


    public byte[] getBytes() {
        //转为byte数组
        byte[] r = null;
        List<byte[]> bytebytes = new ArrayList<byte[]>();
        bytebytes.add(cardReader1);
        bytebytes.add(cardReader2);
        bytebytes.add(cardReader3);
        bytebytes.add(cardReader4);

        r = BinaryUtil.bytsArrayListTobyteArray(bytebytes);
        return r;
    }
}
