package com.iandtop.common.driver.door;

import com.iandtop.common.driver.vo.DeviceToServerMessageVO;
import com.iandtop.common.driver.vo.MessageVO;
import com.iandtop.common.driver.vo.ServerToDeviceMessageVO;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.core.datagram.DatagramSocketOptions;

public class DriverUDPClient {

    public void searchDoorDevice(Vertx vertx,int port ,Handler<DeviceToServerMessageVO> handler){
        MessageVO messageDataVO = DriverDoor.SearchDevice();
        udpConnect(vertx,port,messageDataVO,buffer -> {
            DeviceToServerMessageVO dtsMsgVO =  SearchDevice(buffer);
            handler.handle(dtsMsgVO);
        });
    }

    private void udpConnect(Vertx vertx, int port , MessageVO messageDataVO, Handler<Buffer> handler){
        DatagramSocket socket = vertx.createDatagramSocket(new DatagramSocketOptions().setBroadcast(true));

        ServerToDeviceMessageVO stdMsg = new ServerToDeviceMessageVO();
        stdMsg.setDeviceSN(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 , 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        stdMsg.setDevicePass(new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});//默认0xFFFFFFFF
        stdMsg.setMessageCode(new byte[]{0x00, 0x00, 0x00, 0x00});
        stdMsg.setMessageDataVO(messageDataVO);
        byte[] bytes = stdMsg.getBytes();

        Buffer sendbuffer = Buffer.buffer().appendBytes(bytes);
        //发布UDP广播消息，搜索门禁控制器设备
        socket.send(sendbuffer, port, "255.255.255.255", asyncResult -> {
            if(asyncResult.succeeded()){
                socket.handler(packet -> {
                    Buffer buffer = packet.data();
                    handler.handle(buffer);
                });
            }else{
                System.out.println("接收消息失败: " + asyncResult.result());
            }
        });
    }

    //服务器搜索设备后，设备返回数据,包括门禁设备的sn，设备的ip信息
    public static DeviceToServerMessageVO SearchDevice(Buffer buffer) {
        DeviceToServerMessageVO vo = new DeviceToServerMessageVO();
        vo.setStartFlag(buffer.getBytes(0, 1));
        vo.setMessageCode(buffer.getBytes(1, 5));
        vo.setDeviceSN(buffer.getBytes(5, 21));
        vo.setDevicePass(buffer.getBytes(21, 25));
        MessageVO messageDataVO = new MessageVO();
        messageDataVO.setControlType(buffer.getBytes(25, 26));
        messageDataVO.setControlOrder(buffer.getBytes(26, 27));
        messageDataVO.setControlParam(buffer.getBytes(27, 28));
        messageDataVO.setDataLength(buffer.getBytes(28, 32));
        messageDataVO.setData(buffer.getBytes(28, 169));
        vo.setMessageDataVO(messageDataVO);
        vo.setCheck(buffer.getBytes(169,170));
        vo.setEndFlag(buffer.getBytes(170,171));
        return vo;

    }

    public static void main(String arg[]) {
       // new DriverUDPClient().searchDoorDevice(Vertx.vertx(),8101,null);
        byte[] aaa = new byte[]{0x00,0x01,0x02,0x03,0x04,0x05,0x06};
        byte[] bbb = new byte[4];
        System.arraycopy(aaa, 2, bbb, 0, 4);
        System.out.println(bbb);
    }

}
