package com.iandtop.common.driver.door;

import com.iandtop.common.driver.vo.*;
import com.iandtop.common.utils.BinaryUtil;
import io.vertx.core.Handler;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 包含传统TCP
 */
public class DriverDoor {

    // 读取授权卡信息
    public static MessageVO ReadPsnCardAuth() {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x07});
        messageDataVO.setControlOrder(new byte[]{0x01});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x00});
        messageDataVO.setData(new byte[]{});

        return messageDataVO;
    }

    // 清空所有授权卡
    //1	排序卡区域
    //2	非排序卡区域
    //3	所有区域
    public static MessageVO ClearPsnCardAuth(int areaCode) {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x07});
        messageDataVO.setControlOrder(new byte[]{0x02});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x01});
        byte[] ac = BinaryUtil.intToByteHignInF(areaCode);
        messageDataVO.setData(new byte[]{ac[3]});

        return messageDataVO;
    }

    //读取所有授权卡
    public static MessageVO ReadAllPsnCardAuth(int areaCode) {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x07});
        messageDataVO.setControlOrder(new byte[]{0x03});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x01});
        byte[] ac = BinaryUtil.intToByteHignInF(areaCode);
        messageDataVO.setData(new byte[]{ac[3]});

        return messageDataVO;
    }

    // 读取单个授权卡
    public static MessageVO ReadOnePsnCardAuth(byte[] cardCode) {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x07});
        messageDataVO.setControlOrder(new byte[]{0x03});
        messageDataVO.setControlParam(new byte[]{0x01});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x05});
        if (cardCode.length != 5) {
            return null;
        }
        messageDataVO.setData(cardCode);

        return messageDataVO;
    }

    // 写入非排序卡
    public static MessageVO WritePsnCardAuthUn(List<AuthCardVO> list) {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x07});
        messageDataVO.setControlOrder(new byte[]{0x04});
        messageDataVO.setControlParam(new byte[]{0x00});
        int length = list.size() * AuthCardVO.BLength + 4;
        byte[] lb = BinaryUtil.intToByteHignInF(length);
        messageDataVO.setDataLength(lb);

        byte[] sizeBytes = BinaryUtil.intToByteHignInF(list.size());
        byte[] datas = new byte[]{sizeBytes[0], sizeBytes[1], sizeBytes[2], sizeBytes[3]};
        byte[] listBytes = AuthCardVO.getAuthCardVOBytes(list);

        messageDataVO.setData(BinaryUtil.appendByteArray(datas, listBytes));

        return messageDataVO;
    }

    // 删除非排序卡
    public static MessageVO DeletePsnCardAuthUn(List<AuthCardVO> list) {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x07});
        messageDataVO.setControlOrder(new byte[]{0x05});
        messageDataVO.setControlParam(new byte[]{0x00});
        int length = list.size() * 5 + 4;
        byte[] lb = BinaryUtil.intToByteHignInF(length);
        messageDataVO.setDataLength(lb);

        byte[] sizeBytes = BinaryUtil.intToByteHignInF(list.size());
        byte[] datas = new byte[]{sizeBytes[0], sizeBytes[1], sizeBytes[2], sizeBytes[3]};
        List<byte[]> failCardCodeList = new ArrayList<byte[]>();
        for (int m = 0; m < list.size(); m++) {
//            long cardcode=  Long.parseLong(list.get(m));
//            byte[] ccbs = BinaryUtil.longToByteHignInF(cardcode);
            //byte[] bytes = new byte[]{ccbs[3], ccbs[4], ccbs[5], ccbs[6], ccbs[7]};
            failCardCodeList.add(list.get(m).getCard_code());
        }

        byte[] listBytes = BinaryUtil.bytsArrayListTobyteArray(failCardCodeList);
        messageDataVO.setData(BinaryUtil.appendByteArray(datas, listBytes));
        return messageDataVO;
    }

    // 开始写入排序卡
    public static MessageVO WriteBeginPsnCardAuth() {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x07});
        messageDataVO.setControlOrder(new byte[]{0x07});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x00});
        messageDataVO.setData(new byte[]{});

        return messageDataVO;
    }

    // 写入排序卡
    public static MessageVO WritePsnCardAuth(int beginIndex, List<AuthCardVO> list) {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x07});
        messageDataVO.setControlOrder(new byte[]{0x07});
        messageDataVO.setControlParam(new byte[]{0x01});
        int length = list.size() * AuthCardVO.BLength + 4 + 4;
        byte[] lb = BinaryUtil.intToByteHignInF(length);
        messageDataVO.setDataLength(lb);

        byte[] beginBytes = BinaryUtil.intToByteHignInF(beginIndex);
        byte[] sizeBytes = BinaryUtil.intToByteHignInF(list.size());
        byte[] datas = new byte[]{beginBytes[0], beginBytes[1], beginBytes[2], beginBytes[3]
                , sizeBytes[0], sizeBytes[1], sizeBytes[2], sizeBytes[3]};
        byte[] listByts = AuthCardVO.getAuthCardVOBytes(list);

     /*   for(AuthCardVO a : subList){
            System.out.println(BinaryUtil.bcd2Str(a.getCard_code()));
        }*/

        messageDataVO.setData(BinaryUtil.appendByteArray(datas, listByts));

        return messageDataVO;
    }

    // 终止写入排序卡
    public static MessageVO WriteEndPsnCardAuth() {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x07});
        messageDataVO.setControlOrder(new byte[]{0x07});
        messageDataVO.setControlParam(new byte[]{0x02});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x00});
        messageDataVO.setData(new byte[]{});

        return messageDataVO;
    }

    // 同步时间组
    // 读取时间组
    public static MessageVO ReadTimeGroup() {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x06});
        messageDataVO.setControlOrder(new byte[]{0x02});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x00});
        messageDataVO.setData(new byte[]{});
        return messageDataVO;
    }

    /**
     * 设置时间组
     *
     * @param groupOrder 时间组序号 1-64
     * @param vo
     * @return
     */
    public static MessageVO WriteTimeGroup(int groupOrder, DeviceDataTimeGrpWeekVO vo) {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x06});
        messageDataVO.setControlOrder(new byte[]{0x03});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, (byte) 0xe1});
        byte[] groupOrderbs = BinaryUtil.intToByteHignInF(groupOrder);
        byte[] vobs = vo.getBytes();
        byte[] data = BinaryUtil.appendByteArray(new byte[]{groupOrderbs[3]}, vobs);
        messageDataVO.setData(data);
        return messageDataVO;
    }

    // 获取设备的门信息
    // 获取设备开门时长
    public static MessageVO ReadDoorOpenTime(int doorCode) {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x03});
        messageDataVO.setControlOrder(new byte[]{0x08});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x01});
        byte[] data = BinaryUtil.subArray(BinaryUtil.intToByteHignInF(doorCode), 3, 4);
        messageDataVO.setData(data);
        return messageDataVO;
    }

    // 设置设备开门时长
    public static MessageVO WriteDoorOpenTime(int doorCode, int doorOpenTime) {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x03});
        messageDataVO.setControlOrder(new byte[]{0x08});
        messageDataVO.setControlParam(new byte[]{0x01});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x03});
        byte[] port = BinaryUtil.intToByteHignInF(doorCode);
        byte[] opentime = BinaryUtil.intToByteHignInF(doorOpenTime);
        byte[] data = new byte[]{port[3], opentime[2], opentime[3]};
        messageDataVO.setData(data);
        return messageDataVO;
    }

    // 获取门工作方式
    public static MessageVO DeviceDataWorkMode(int doorCode) {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x03});
        messageDataVO.setControlOrder(new byte[]{0x06});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x01});
        byte[] data = BinaryUtil.subArray(BinaryUtil.intToByteHignInF(doorCode), 3, 4);
        messageDataVO.setData(data);
        return messageDataVO;
    }

    // 远程开门
    public static MessageVO OpenDoor(Boolean d1, Boolean d2, Boolean d3, Boolean d4) {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x03});
        messageDataVO.setControlOrder(new byte[]{0x03});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x04});
        messageDataVO.setData(new byte[]{(byte) (d1 ? 0x01 : 0x00), (byte) (d2 ? 0x01 : 0x00), (byte) (d3 ? 0x01 : 0x00),
                (byte) (d4 ? 0x01 : 0x00)});
        return messageDataVO;
    }

    // 远程关门
    public static MessageVO CloseDoor(Boolean d1, Boolean d2, Boolean d3, Boolean d4) {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x03});
        messageDataVO.setControlOrder(new byte[]{0x03});
        messageDataVO.setControlParam(new byte[]{0x01});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x04});
        messageDataVO.setData(new byte[]{(byte) (d1 ? 0x01 : 0x00), (byte) (d2 ? 0x01 : 0x00), (byte) (d3 ? 0x01 : 0x00),
                (byte) (d4 ? 0x01 : 0x00)});
        return messageDataVO;
    }

    // 读取设备时间
    public static MessageVO ReadDeviceTime() {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x02});
        messageDataVO.setControlOrder(new byte[]{0x01});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x00});
        messageDataVO.setData(new byte[]{});

        return messageDataVO;
    }

    // 设置设备时间
    public static MessageVO WriteDeviceTime(Date date) {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x02});
        messageDataVO.setControlOrder(new byte[]{0x02});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x07});
        byte[] datebytes = new DeviceDataTimeVO().getBytes(date);
        messageDataVO.setData(datebytes);

        return messageDataVO;
    }

    // 获取设备运行信息
    public static MessageVO DeviceInfo() {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x01});
        messageDataVO.setControlOrder(new byte[]{0x09});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x00});
        messageDataVO.setData(new byte[]{});

        return messageDataVO;
    }

    // 获取读卡器的读卡字节数 算法
    public static MessageVO ReadCardReaderInfo() {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x03});
        messageDataVO.setControlOrder(new byte[]{0x01});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x00});
        messageDataVO.setData(new byte[]{});

        return messageDataVO;
    }

    // 写读卡器的读卡字节数 算法
    public static MessageVO WriteCardReaderInfo(int t1, int t2, int t3, int t4) {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x03});
        messageDataVO.setControlOrder(new byte[]{0x01});
        messageDataVO.setControlParam(new byte[]{0x01});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x04});
        DeviceDataCardReaderVO dvo = new DeviceDataCardReaderVO(t1, t2, t3, t4);
        messageDataVO.setData(dvo.getBytes());

        return messageDataVO;
    }

    // 读取设备SN
    public static MessageVO DeviceSN() {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x01});
        messageDataVO.setControlOrder(new byte[]{0x02});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x00});
        messageDataVO.setData(new byte[]{});

        return messageDataVO;
    }

    // 搜索设备
    public static MessageVO SearchDevice() {
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x01});
        messageDataVO.setControlOrder(new byte[]{(byte) 0xfe});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x02});
        messageDataVO.setData(new byte[]{(byte) 0x8e, (byte) 0xbc});// 网络标志

        return messageDataVO;
    }

    //读取设备TCP参数
    public static MessageVO ReadTCPParam(){
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x01});
        messageDataVO.setControlOrder(new byte[]{0x06});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x00});
        messageDataVO.setData(new byte[]{});

        return messageDataVO;
    }

    //开启监控
    public static MessageVO OpenMonitor(){
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x01});
        messageDataVO.setControlOrder(new byte[]{(byte) 0x0b});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x00});
        messageDataVO.setData(new byte[]{});

        return messageDataVO;
    }

    //关闭监控
    public static MessageVO CloseMonitor(){
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x01});
        messageDataVO.setControlOrder(new byte[]{(byte) 0x0b});
        messageDataVO.setControlParam(new byte[]{0x01});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x00});
        messageDataVO.setData(new byte[]{});

        return messageDataVO;
    }

    //读取实时监控状态
    public static MessageVO ReadMonitorState(){
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x01});
        messageDataVO.setControlOrder(new byte[]{(byte) 0x0b});
        messageDataVO.setControlParam(new byte[]{0x02});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x00});
        messageDataVO.setData(new byte[]{});

        return messageDataVO;
    }

    //设置设备TCP参数
    public static MessageVO WriteTCPPara(TCPParaDataVO tcpParaDataVO){
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x01});
        messageDataVO.setControlOrder(new byte[]{(byte) 0x06});
        messageDataVO.setControlParam(new byte[]{0x01});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x10, 0x7F});
        messageDataVO.setData(tcpParaDataVO.getBytes());

        return messageDataVO;
    }

    //读取记录指针信息
    public static MessageVO ReadRecordPoint(){
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x08});
        messageDataVO.setControlOrder(new byte[]{(byte) 0x01});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x00});
        messageDataVO.setData(new byte[]{});

        return messageDataVO;
    }

    //清空所有记录
    public static MessageVO EmptyAllRecord(){
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x08});
        messageDataVO.setControlOrder(new byte[]{(byte) 0x02});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x00});
        messageDataVO.setData(new byte[]{});

        return messageDataVO;
    }

    //清空某个模块的记录
    public static MessageVO EmptyRecord(int recordModuleCode){
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x08});
        messageDataVO.setControlOrder(new byte[]{(byte) 0x02});
        messageDataVO.setControlParam(new byte[]{0x01});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x01});

        byte[] codeBytes = BinaryUtil.subArray(BinaryUtil.intToByteHignInF(recordModuleCode), 3, 4);
        messageDataVO.setData(codeBytes);

        return messageDataVO;
    }

    //更新记录指针  (上传断点）
    public static MessageVO UpdateRecordBreak(int recordModuleCode,int breakPoint){
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x08});
        messageDataVO.setControlOrder(new byte[]{(byte) 0x03});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x06});

        byte[] codeBytes = BinaryUtil.subArray(BinaryUtil.intToByteHignInF(recordModuleCode), 3, 4);
        byte[] breakPointBytes = BinaryUtil.intToByteHignInF(breakPoint);
        byte[] loopModule = new byte[]{0x00};//循环标志，先暂时填默认值
        List<byte[]> bytebytes = new ArrayList<byte[]>();
        bytebytes.add(codeBytes);
        bytebytes.add(breakPointBytes);
        bytebytes.add(loopModule);
        byte[] data = BinaryUtil.bytsArrayListTobyteArray(bytebytes);
        messageDataVO.setData(data);

        return messageDataVO;
    }

    //读取记录
    public static MessageVO ReadRecord(int recordModuleCode,int start,int size){
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(new byte[]{0x08});
        messageDataVO.setControlOrder(new byte[]{(byte) 0x04});
        messageDataVO.setControlParam(new byte[]{0x00});
        messageDataVO.setDataLength(new byte[]{0x00, 0x00, 0x00, 0x09});

        byte[] codeBytes = BinaryUtil.subArray(BinaryUtil.intToByteHignInF(recordModuleCode), 3, 4);
        byte[] startBytes = BinaryUtil.intToByteHignInF(start);
        byte[] sizeBytes = BinaryUtil.intToByteHignInF(size);
        List<byte[]> bytebytes = new ArrayList<byte[]>();
        bytebytes.add(codeBytes);
        bytebytes.add(startBytes);
        bytebytes.add(sizeBytes);
        byte[] data = BinaryUtil.bytsArrayListTobyteArray(bytebytes);

        messageDataVO.setData(data);

        return messageDataVO;
    }

    // 获得设备传递到uap的消息的类型
    public static int getMessageType(MessageVO messageVO) {
        if (messageVO.getControlType()[0] == 0x21 && messageVO.getControlOrder()[0] == 0x01) {
            return MessageVO.Type_OK;
        } else if (messageVO.getControlType()[0] == 0x21 && messageVO.getControlOrder()[0] == 0x02) {
            return MessageVO.Type_PasswordError;
        } else if (messageVO.getControlType()[0] == 0x21 && messageVO.getControlOrder()[0] == 0x03) {
            return MessageVO.Type_checkError;
        } else if (messageVO.getControlType()[0] == 0x21 && messageVO.getControlOrder()[0] == 0x04) {
            return MessageVO.Type_ipError;
        } else {
            return MessageVO.Type_NULL;
        }
    }


    /**
     * 获得所有时间组(64个)
     *
     * @param url
     * @param port
     * @param sn
     * @return
     *//*

    //TODO 此方法目前有问题bug  赵召   2016-07-21
    @Deprecated
    public static DeviceDataTimeGrpVO readTimeGroup(String url, int port, String sn) throws IOException {
        MessageVO messageDataVO = DriverDoor.ReadTimeGroup();
        int mlen = 64 * (DeviceToServerMessageVO.BLengthwithoutData + DeviceDataTimeGrpWeekVO.BLength + 2);// 2:组号，64：64个时间组
        mlen += DeviceToServerMessageVO.BLengthwithoutData + 4;// 传输结束 消息的长度
        byte[] bytes = common(url, port, sn, messageDataVO, mlen);
        DeviceDataTimeGrpVO tvo = new DeviceDataTimeGrpVO(bytes);
        return tvo;
    }

    */
/**
 * 设置时间组
 *
 * @param url
 * @param port
 * @param sn
 * @param groupOrder 组号： 1 --64
 * @param vo
 * @return
 *//*

    public static Boolean writeTimeGroup(String url, int port, String sn, int groupOrder, DeviceDataTimeGrpWeekVO vo) throws IOException {
        MessageVO messageDataVO = DriverDoor.WriteTimeGroup(groupOrder, vo);
        int mlen = DeviceToServerMessageVO.BLengthwithoutData;
        byte[] ok = common(url, port, sn, messageDataVO, mlen);
        MessageVO messageVO = new MessageVO(ok);
        if (MessageVO.Type_OK == getMessageType(messageVO)) {
            return true;
        }
        return false;
    }

    */
/**
 * 获取门 开门时长
 *
 * @param url
 * @param port
 * @param sn
 * @param doorcode 门号 1 or 2 or 3 or 4
 * @return
 *//*

    public static long readDoorOpenTime(String url, int port, String sn, int doorcode) throws IOException {
        MessageVO messageDataVO = DriverDoor.ReadDoorOpenTime(doorcode);
        int mlen = DeviceToServerMessageVO.BLengthwithoutData + 3;
        byte[] bytes = common(url, port, sn, messageDataVO, mlen);
        MessageVO messageVO = new MessageVO(bytes);
        byte[] data = messageVO.getData();
        int second = BinaryUtil.byteToIntHignInF(new byte[]{0x00, 0x00, data[1], data[2]});

        return second;
    }

    */
/**
 * 设置门 开门时长
 *
 * @param url
 * @param port
 * @param sn
 * @param doorcode 门号 1 or 2 or 3 or 4
 * @param opentime 时长 单位（秒）
 * @return
 *//*

    public static Boolean writeDoorOpenTime(String url, int port, String sn, int doorcode, int opentime) throws IOException {
        MessageVO messageDataVO = DriverDoor.WriteDoorOpenTime(doorcode, opentime);
        int mlen = DeviceToServerMessageVO.BLengthwithoutData;
        byte[] ok = common(url, port, sn, messageDataVO, mlen);
        MessageVO messageVO = new MessageVO(ok);
        if (MessageVO.Type_OK == getMessageType(messageVO)) {
            return true;
        }
        return false;
    }

    */
/**
 * 获得门工作方式
 *
 * @param url
 * @param port
 * @param sn
 * @param doorcode 门号 1 or 2 or 3 or 4
 * @return
 *//*

    public static DeviceDataWorkModeVO deviceDataWorkMode(String url, int port, String sn, int doorcode) throws IOException {
        MessageVO messageDataVO = DriverDoor.DeviceDataWorkMode(doorcode);
        int mlen = DeviceToServerMessageVO.BLengthwithoutData + DeviceDataWorkModeVO.BLength;
        byte[] bytes = common(url, port, sn, messageDataVO, mlen);
        DeviceDataWorkModeVO deviceDataWorkModeVO = new DeviceDataWorkModeVO(bytes);
        return deviceDataWorkModeVO;
    }

    */
/**
 * 开门
 *
 * @param url
 * @param port
 * @param sn
 * @param d1   门1
 * @param d2   门2
 * @param d3   门3
 * @param d4   门4
 * @return
 *//*

    public static Boolean openDoor(String url, int port, String sn, Boolean d1, Boolean d2, Boolean d3, Boolean d4) throws IOException {
        MessageVO messageDataVO = DriverDoor.OpenDoor(d1, d2, d3, d4);
        int mlen = DeviceToServerMessageVO.BLengthwithoutData;
        byte[] ok = common(url, port, sn, messageDataVO, mlen);
        MessageVO messageVO = new MessageVO(ok);
        if (MessageVO.Type_OK == getMessageType(messageVO)) {
            return true;
        }
        return false;
    }

    */
/**
 * 关门
 *
 * @param url
 * @param port
 * @param sn
 * @param d1   门1
 * @param d2   门2
 * @param d3   门3
 * @param d4   门4
 * @return
 *//*

    public static Boolean closeDoor(String url, int port, String sn, Boolean d1, Boolean d2, Boolean d3, Boolean d4) throws IOException {
        MessageVO messageDataVO = DriverDoor.CloseDoor(d1, d2, d3, d4);
        int mlen = DeviceToServerMessageVO.BLengthwithoutData + 8;
        byte[] bytes = common(url, port, sn, messageDataVO, mlen);
        byte[] jilu = BinaryUtil.subArray(bytes, 32, 40);
        if (jilu[7] == 0x01 || jilu[7] == 0x02 || jilu[7] == 0x03 || jilu[7] == 0x04 || jilu[7] == 0x05
                || jilu[7] == 0x06 || jilu[7] == 0x07) {
            return true;
        }

//		MessageVO messageVO = new MessageVO(ok);
//		if (MessageVO.Type_OK == getMessageType(messageVO)) {
//			return true;
//		}
        return false;
    }

    */
/**
 * 获取设备时间
 *
 * @param url
 * @param port
 * @param sn
 * @return
 *//*

    public static DeviceDataTimeVO readDeviceTime(String url, int port, String sn) throws IOException {
        MessageVO messageDataVO = DriverDoor.ReadDeviceTime();
        int mlen = DeviceToServerMessageVO.BLengthwithoutData + DeviceDataTimeVO.BLength;
        byte[] bytes = common(url, port, sn, messageDataVO, mlen);
        DeviceDataTimeVO deviceDataTimeVO = new DeviceDataTimeVO(bytes);
        return deviceDataTimeVO;
    }

    */
/**
 * 设置设备时间
 *
 * @param url
 * @param port
 * @param sn
 * @param date java.util.Date
 * @return
 *//*

    public static Boolean writeDeviceTime(String url, int port, String sn, Date date) throws IOException {
        MessageVO messageDataVO = DriverDoor.WriteDeviceTime(date);
        int mlen = DeviceToServerMessageVO.BLengthwithoutData;
        byte[] ok = common(url, port, sn, messageDataVO, mlen);
        MessageVO result = new MessageVO(ok);
        if (MessageVO.Type_OK == getMessageType(result)) {
            return true;
        }
        return false;
    }

    */
/**
 * 获得设备的sn
 *
 * @param url
 * @param port
 * @return
 *//*

    public static String getSn(String url, int port) throws IOException {
        String sn = "CA-3240T46040235";
        MessageVO messageDataVO = DriverDoor.DeviceSN();
        int mlen = DeviceToServerMessageVO.BLengthwithoutData + 16;
        byte[] bytes = common(url, port, sn, messageDataVO, mlen);
        byte[] subbytes = BinaryUtil.subArray(bytes, 32, 48);
        //System.out.println(new String(subbytes));
        return new String(subbytes);
    }

    */
/**
 * 读取读卡器信息
 *
 * @param url
 * @param port
 * @param sn
 * @return
 *//*

    public static DeviceDataCardReaderVO readCardReaderInfo(String url, int port, String sn) throws IOException {
        MessageVO messageDataVO = DriverDoor.ReadCardReaderInfo();
        int mlen = DeviceToServerMessageVO.BLengthwithoutData + DeviceDataCardReaderVO.BLength;
        byte[] bytes = common(url, port, sn, messageDataVO, mlen);
        DeviceDataCardReaderVO deviceDataCardReaderVO = new DeviceDataCardReaderVO(bytes);
        return deviceDataCardReaderVO;
    }

    */
/**
 * 设置读卡器信息
 *
 * @param url
 * @param port
 * @param sn
 * @param t1   1	韦根26(三字节)
 * @param t2   2	韦根34(四字节)
 * @param t3   3	韦根26(二字节)
 * @param t4   4	禁用
 * @return
 *//*

    public static Boolean writeCardReaderInfo(String url, int port, String sn, int t1, int t2, int t3, int t4) throws IOException {
        MessageVO messageDataVO = DriverDoor.WriteCardReaderInfo(t1, t2, t3, t4);
        int mlen = DeviceToServerMessageVO.BLengthwithoutData;
        byte[] ok = common(url, port, sn, messageDataVO, mlen);
        MessageVO result = new MessageVO(ok);
        if (MessageVO.Type_OK == getMessageType(result)) {
            return true;
        }
        return false;
    }

    */
/**
 * 设备运行信息
 *
 * @param url
 * @param port
 * @param sn
 * @return
 *//*

    public static DeviceDataVO deviceInfo(String url, int port, String sn) throws IOException {
        MessageVO messageDataVO = DriverDoor.DeviceInfo();
        int mlen = DeviceToServerMessageVO.BLengthwithoutData + DeviceDataVO.BLength;
        byte[] bytes = common(url, port, sn, messageDataVO, mlen);
        DeviceDataVO deviceDataVO = new DeviceDataVO(bytes);
        return deviceDataVO;
    }

    */
/*
     * 开启实时监控
     *//*

    public static Boolean openMonitor(String url, int port, String sn,Handler handler) throws IOException{
        MessageVO messageVO = OpenMonitor();
        int mlen = DeviceToServerMessageVO.BLengthwithoutData;
        byte[] ok = common(url,port,sn,messageVO,mlen);
        MessageVO result = new MessageVO(ok);
        handler.handle(1);
        if (MessageVO.Type_OK == getMessageType(result)) {
            return true;
        }
        return false;
    }

    */
/*
     * 关闭实时监控
     *//*

    public static Boolean closeMonitor(String url, int port, String sn) throws IOException{
        MessageVO messageVO = CloseMonitor();
        int mlen = DeviceToServerMessageVO.BLengthwithoutData;
        byte[] ok = common(url,port,sn,messageVO,mlen);

        MessageVO result = new MessageVO(ok);

        if (MessageVO.Type_OK == getMessageType(result)) {
            return true;
        }
        return false;
    }

    //读取设备TCP参数
    public static TCPParaDataVO readTCPParam(String url, int port, String sn) throws IOException {
        MessageVO messageDataVO = ReadTCPParam();
        int mlen = DeviceToServerMessageVO.BLengthwithoutData + TCPParaDataVO.BLength;
        byte[] bytes = common(url, port, sn, messageDataVO, mlen);
        TCPParaDataVO tcpParaDataVO = new TCPParaDataVO(bytes);
        return tcpParaDataVO;
    }

    //TODO 写入设备TCP参数
    public static Boolean writeTCPParam(String url, int port, String sn, TCPParaDataVO tcpParaDataVO) throws IOException {
        MessageVO messageVO = WriteTCPPara(tcpParaDataVO);
        int mlen = DeviceToServerMessageVO.BLengthwithoutData + TCPParaDataVO.BLength;
        byte[] bytes = common(url, port, sn, messageVO, mlen);
        MessageVO result = new MessageVO(bytes);
        if (MessageVO.Type_OK == getMessageType(result)) {
            return true;
        }
        return false;
    }

    */
/**
 * 读取设备实时监控状态
 *
 *//*

    public static String readMonitorState(String url, int port, String sn) throws IOException {
        MessageVO messageVO = ReadMonitorState();
        int mlen =  DeviceToServerMessageVO.BLengthwithoutData;
        byte[] state = common(url, port, sn, messageVO, mlen);
        MessageVO ressult = new MessageVO(state);
        String s = BinaryUtil.bcd2Str(ressult.getData());

        return s;
    }

*/





  /*  public static byte[] common(String url, int port, String sn, MessageVO messageDataVO, int resultBytesLength) throws IOException {
        return Connect(url, port, sn, messageDataVO, resultBytesLength);
    }*/

  /*  *//**
     * tcp客户端模式 链接 短连接，每次请求完成及关闭
     *//*
   public static byte[] Connect(String url, int port, String sn, MessageVO messageDataVO, int resultBytesLength) throws IOException {
        ServerToDeviceMessageVO stdMsg = new ServerToDeviceMessageVO();
        stdMsg.setDeviceSN(BinaryUtil.stringToAscII(sn));
        stdMsg.setDevicePass(new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});// 默认0xFFFFFFFF
        stdMsg.setMessageCode(new byte[]{0x00, (byte) 0x00, 0x00, (byte) 0x00});
        stdMsg.setMessageDataVO(messageDataVO);
        byte[] buf = stdMsg.getBytes();

        Socket socket;
        InputStream is;
        OutputStream out;

        socket = new Socket();
        InetSocketAddress endpoint = new InetSocketAddress(url, port);
        socket.connect(endpoint, 2000);
        // 获取socket流中的输出流。 向服务器输出消息，即发消息到服务器
        out = socket.getOutputStream();
        // 使用输出流将指定的数据写出去。
        out.write(buf);

        // 读去服务端返回信息
        is = socket.getInputStream();
        DataInputStream ds = new DataInputStream(is);
        byte[] readBytes = new byte[resultBytesLength];
        ds.readFully(readBytes);

        // 关闭资源
        out.close();
        is.close();
        ds.close();
        socket.close();

        return readBytes;

    }*/

 /*   // 读取每一条信息
    // 要保证消息的开头和结尾都是126
    public static ReadDataResult readData(InputStream is) throws IOException {
        byte[] head = new byte[32];// 每条消息的头部是32位
        byte[] body = new byte[]{};
        int m = 0;
        int bodyLength = 0;

        m = is.read(head);

        bodyLength = BinaryUtil.byteToIntHignInF(BinaryUtil.subArray(head, 28, 32)) + 1 + 1;// 数据长+检测位+结束标志
        // if(bodyLength>10000){
        // System.out.println(bodyLength);
        // for(int i = 0 ;i<32;i++){
        // System.out.println(head[i]);
        // }
        // }
        body = new byte[bodyLength];// 数据和后面部分
        m += is.read(body);

        byte[] target = new byte[32 + bodyLength];
        System.arraycopy(head, 0, target, 0, 32);
        System.arraycopy(body, 0, target, 32, bodyLength);

        ReadDataResult r = new ReadDataResult();
        r.setResultNum(m);
        r.setResult(target);
        return r;
    }

    static class ReadDataResult {
        int resultNum;
        byte[] result;

        public int getResultNum() {
            return resultNum;
        }

        public void setResultNum(int resultNum) {
            this.resultNum = resultNum;
        }

        public byte[] getResult() {
            return result;
        }

        public void setResult(byte[] result) {
            this.result = result;
        }
    }*/



/*    private void TCPConnect(MessageVO messageDataVO, Handler<byte[]> handler) {
        NetClientOptions options = new NetClientOptions().setConnectTimeout(10000)
                .setReconnectAttempts(10).//重连次数
                setReconnectInterval(500);//重连间隔

        NetClient client = vertx.createNetClient(options);
        client.connect(devicePort, deviceUrl, res -> {
            if (res.succeeded()) {
                NetSocket socket = res.result();
                socket.handler(buffer -> {
                    byte[] bytes = buffer.getBytes();
                    socket.close();
                    client.close();
                    handler.handle(handleTranslation(bytes));
                });
                socket.endHandler(eh -> {
//                    socket.close();
//                    client.close();
                });
                socket.drainHandler(dh -> {
//                    socket.close();
//                    client.close();
                });
                socket.closeHandler(ch -> {
//                    socket.close();
//                   client.close();
                });

                TCPSend(socket, messageDataVO);
            } else {
                System.out.println("tcp链接失败" + res.cause());
            }
        });
    }*/
}
