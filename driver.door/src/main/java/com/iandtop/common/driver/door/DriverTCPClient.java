package com.iandtop.common.driver.door;

import com.iandtop.common.driver.vo.*;
import com.iandtop.common.utils.BinaryUtil;
import com.iandtop.common.utils.FormatUtils;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 门禁控制板tcp驱动
 *
 * @author andyzhao
 *         每个门禁控制板对应一个DriverTCPClient
 *         <p/>
 *         门禁控制板同时只能有一个tcp链接，如果开启监控等长连接，
 *         那么进行其他操作时要先关闭监控长连接，然后执行完操作后再开启
 */
public class DriverTCPClient {

    private Vertx vertx = null;
    private String deviceUrl = null;//门禁控制板url
    private int devicePort = -1;//
    private String deviceSn = null;//门禁控制板sn
    private TCPPoolVO monitorClient = null;//监控连接池
    private NetClient netClient = null;//普通链接

    private static int ReconnectAttempt = 1;//重连次数
    private static int ReconnectInterval = 500;//重连间隔
    private static int ConnectTimeout = 2000;//超时时间

    private Handler<RecordDataVO> recordHandler = null;//接收监控信息

    public DriverTCPClient(Vertx _vertx, String _deviceUrl, int _devicePort, String _deviceSn) {
        vertx = _vertx;
        deviceUrl = _deviceUrl;
        devicePort = _devicePort;
        deviceSn = _deviceSn;
    }

    public void setRecordHandler(Handler<RecordDataVO> recordHandler) {
        this.recordHandler = recordHandler;
    }

    /**
     * 获得所有时间组(64个)
     */
    //TODO 此方法目前有问题bug  赵召   2016-07-21
    @Deprecated
    public void readTimeGroup(Handler<DeviceDataTimeGrpVO> handler) throws IOException {
        MessageVO messageDataVO = DriverDoor.ReadTimeGroup();
        int mlen = 64 * (DeviceToServerMessageVO.BLengthwithoutData + DeviceDataTimeGrpWeekVO.BLength + 2);// 2:组号，64：64个时间组
        mlen += DeviceToServerMessageVO.BLengthwithoutData + 4;// 传输结束 消息的长度

        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            DeviceDataTimeGrpVO tvo = new DeviceDataTimeGrpVO(bytes);
            if (MessageVO.Type_OK == DriverDoor.getMessageType(result)) {
                handler.handle(tvo);
            } else {
                handler.handle(null);
            }
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(null);
        });
    }

    /**
     * 设置时间组
     *
     * @param groupOrder 组号： 1 --64
     * @param vo
     */
    public void writeTimeGroup(int groupOrder, DeviceDataTimeGrpWeekVO vo, Handler<Boolean> handler) throws IOException {
        MessageVO messageDataVO = DriverDoor.WriteTimeGroup(groupOrder, vo);
        int mlen = DeviceToServerMessageVO.BLengthwithoutData;
        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            if (MessageVO.Type_OK == DriverDoor.getMessageType(result)) {
                handler.handle(true);
            } else {
                handler.handle(false);
            }
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(false);
        });
    }

    /**
     * 获取门 开门时长
     *
     * @param doorcode 门号 1 or 2 or 3 or 4
     */
    public void readDoorOpenTime(int doorcode, Handler<Long> handler) throws IOException {
        MessageVO messageDataVO = DriverDoor.ReadDoorOpenTime(doorcode);
        int mlen = DeviceToServerMessageVO.BLengthwithoutData + 3;
        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            byte[] data = result.getData();
            int second = BinaryUtil.byteToIntHignInF(new byte[]{0x00, 0x00, data[1], data[2]});
            handler.handle((long) second);
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(null);
        });
    }

    /**
     * 设置门 开门时长
     *
     * @param doorcode 门号 1 or 2 or 3 or 4
     * @param opentime 时长 单位（秒）
     */
    public void writeDoorOpenTime(int doorcode, int opentime, Handler<Boolean> handler) throws IOException {
        MessageVO messageDataVO = DriverDoor.WriteDoorOpenTime(doorcode, opentime);
        int mlen = DeviceToServerMessageVO.BLengthwithoutData;

        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            if (MessageVO.Type_OK == DriverDoor.getMessageType(result)) {
                handler.handle(true);
            } else {
                handler.handle(false);
            }
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(false);
        });
    }

    /**
     * 获得门工作方式
     *
     * @param doorcode 门号 1 or 2 or 3 or 4
     */
    public void readDeviceDataWorkMode(int doorcode, Handler<DeviceDataWorkModeVO> handler) throws IOException {
        MessageVO messageDataVO = DriverDoor.DeviceDataWorkMode(doorcode);
        int mlen = DeviceToServerMessageVO.BLengthwithoutData + DeviceDataWorkModeVO.BLength;

        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            DeviceDataWorkModeVO deviceDataWorkModeVO = new DeviceDataWorkModeVO(result);
            handler.handle(deviceDataWorkModeVO);
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(null);
        });
    }

    /**
     * 开门
     *
     * @param d1 门1
     * @param d2 门2
     * @param d3 门3
     * @param d4 门4
     */
    public void openDoor(Boolean d1, Boolean d2, Boolean d3, Boolean d4, Handler<Boolean> handler) throws IOException {
        MessageVO messageDataVO = DriverDoor.OpenDoor(d1, d2, d3, d4);
        int mlen = DeviceToServerMessageVO.BLengthwithoutData;
        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            if (MessageVO.Type_OK == DriverDoor.getMessageType(result)) {
                handler.handle(true);
            } else {
                handler.handle(false);
            }
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(false);
        });
    }

    /**
     * 关门
     *
     * @param d1 门1
     * @param d2 门2
     * @param d3 门3
     * @param d4 门4
     */
    public void closeDoor(Boolean d1, Boolean d2, Boolean d3, Boolean d4, Handler<Boolean> handler) throws IOException {
        MessageVO messageDataVO = DriverDoor.CloseDoor(d1, d2, d3, d4);
        int mlen = DeviceToServerMessageVO.BLengthwithoutData + 8;

        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            if (MessageVO.Type_OK == DriverDoor.getMessageType(result)) {
                byte[] jilu = BinaryUtil.subArray(bytes, 32, 40);
                if (jilu[7] == 0x01 || jilu[7] == 0x02 || jilu[7] == 0x03 || jilu[7] == 0x04 || jilu[7] == 0x05
                        || jilu[7] == 0x06 || jilu[7] == 0x07) {
                    handler.handle(true);
                } else {
                    handler.handle(false);
                }
            } else {
                handler.handle(false);
            }
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(false);
        });
    }

    /**
     * 获取设备时间
     */
    public void readDeviceTime(Handler<DeviceDataTimeVO> handler) throws IOException {
        MessageVO messageDataVO = DriverDoor.ReadDeviceTime();
        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            DeviceDataTimeVO deviceDataTimeVO = new DeviceDataTimeVO(result);
            handler.handle(deviceDataTimeVO);
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(null);
        });
    }

    /**
     * 设置设备时间
     *
     * @param date java.util.Date
     */
    public void writeDeviceTime(Date date, Handler<Boolean> handler) throws IOException {
        MessageVO messageDataVO = DriverDoor.WriteDeviceTime(date);
        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            if (MessageVO.Type_OK == DriverDoor.getMessageType(result)) {
                handler.handle(true);
            } else {
                handler.handle(false);
            }
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(false);
        });
    }

    /**
     * 获得设备的sn
     */
    public void readSN(Handler<String> handler) throws IOException {
        MessageVO messageDataVO = DriverDoor.DeviceSN();

        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            byte[] subbytes = result.getData();
            String sn = new String(subbytes);
            handler.handle(sn);
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(null);
        });
    }

    /**
     * 读取读卡器信息
     */
    public void readCardReaderInfo(Handler<DeviceDataCardReaderVO> handler) throws IOException {
        MessageVO messageDataVO = DriverDoor.ReadCardReaderInfo();
        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            DeviceDataCardReaderVO deviceDataCardReaderVO = new DeviceDataCardReaderVO(result);
            handler.handle(deviceDataCardReaderVO);
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(null);
        });
    }

    /**
     * 设置读卡器信息,读卡器参数
     *
     * @param t1 1	韦根26(三字节)
     * @param t2 2	韦根34(四字节)
     * @param t3 3	韦根26(二字节)
     * @param t4 4	禁用
     */
    public void writeCardReaderInfo(int t1, int t2, int t3, int t4, Handler<Boolean> handler) throws IOException {
        MessageVO messageDataVO = DriverDoor.WriteCardReaderInfo(t1, t2, t3, t4);
        int mlen = DeviceToServerMessageVO.BLengthwithoutData;

        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            if (MessageVO.Type_OK == DriverDoor.getMessageType(result)) {
                handler.handle(true);
            } else {
                handler.handle(false);
            }
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(false);
        });
    }

    /**
     * 设备运行信息
     */
    public void readDeviceInfo(Handler<DeviceDataVO> handler) throws IOException {
        MessageVO messageDataVO = DriverDoor.DeviceInfo();

        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            DeviceDataVO deviceDataVO = new DeviceDataVO(result);
            handler.handle(deviceDataVO);
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(null);
        });
    }

    /*
   * 是否开启了实时监控
   */
    public Boolean isOnMonitor() {
        if (monitorClient == null) {
            return false;
        }
        return true;
    }

    /*
     * 开启实时监控
     */
    public void openMonitor() {
        MessageVO messageVO = DriverDoor.OpenMonitor();
        Boolean r = TCPConnectMonitorOpen(messageVO);
    }

    /*
     * 关闭实时监控
     */
    public void closeMonitor() {
        MessageVO messageVO = DriverDoor.CloseMonitor();
        Boolean r = TCPConnectMonitorClose(messageVO);
    }

    /**
     * 读取设备TCP参数
     *
     * @param handler
     * @throws IOException
     */
    public void readTCPParam(Handler<TCPParaDataVO> handler) throws IOException {
        MessageVO messageDataVO = DriverDoor.ReadTCPParam();

        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            TCPParaDataVO tcpParaDataVO = new TCPParaDataVO(result);
            handler.handle(tcpParaDataVO);
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(null);
        });
    }

    //TODO 写入设备TCP参数
    public void writeTCPParam(TCPParaDataVO tcpParaDataVO, Handler<Boolean> handler) throws IOException {
        MessageVO messageVO = DriverDoor.WriteTCPPara(tcpParaDataVO);
        int mlen = DeviceToServerMessageVO.BLengthwithoutData + TCPParaDataVO.BLength;

        TCPConnect(messageVO, bytes -> {
            netClient = null;
            MessageVO result = new MessageVO(bytes);
            if (MessageVO.Type_OK == DriverDoor.getMessageType(result)) {
                handler.handle(true);
            } else {
                handler.handle(false);
            }
        }, error -> {
            netClient = null;
            handler.handle(false);
        });

    }

    /**
     * 读取记录指针信息
     *
     * @param handler
     * @throws IOException
     */
    public void readRecordPoint(Handler<RecordPointDataVO> handler) throws IOException {
        MessageVO messageDataVO = DriverDoor.ReadRecordPoint();

        TCPConnect(messageDataVO, bytes -> {
            netClient = null;
            MessageVO result = new MessageVO(bytes);
            RecordPointDataVO recordPointDataVO = new RecordPointDataVO(result);
            handler.handle(recordPointDataVO);
        }, error -> {
            netClient = null;
            handler.handle(null);
        });
    }

    /**
     * 更新记录指针
     *
     * @param breakPoint
     * @param handler
     * @throws IOException
     */
    public void updateRecordBreak(int breakPoint, Handler<Boolean> handler) throws IOException {
        MessageVO messageDataVO = DriverDoor.UpdateRecordBreak(1, breakPoint);

        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            if (MessageVO.Type_OK == DriverDoor.getMessageType(result)) {
                handler.handle(true);
            } else {
                handler.handle(false);
            }
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(false);
        });
    }

    /**
     * 获取记录
     * <p/>
     * 记录是分批次获取的，
     *
     * @param start
     * @param size
     * @param partHandler 收到记录片段的处理方法
     * @param endHandler  收到结束信息的处理方法
     * @throws IOException
     */
    public void readRecord(int start, int size, Handler<RecordVO> partHandler, Handler<Integer> endHandler) throws IOException {
        MessageVO messageDataVO = DriverDoor.ReadRecord(1, start, size);

        TCPConnectNotClosed(messageDataVO, bytes -> {
            MessageVO messageVO = new MessageVO(bytes);
            if (messageVO.getControlType()[0] == (byte) 0x38 && messageVO.getControlOrder()[0] == (byte) 0x04) {
                if (messageVO.getControlParam()[0] == (byte) 0x00) {//信息
                    RecordVO recordVO = new RecordVO(messageVO);
                    partHandler.handle(recordVO);
                } else if (messageVO.getControlParam()[0] == (byte) 0xff) {//传输结束
                    byte[] lbytes = BinaryUtil.subArray(messageVO.getData(), 0, 4);
                    int dataLength = BinaryUtil.byteToIntHignInF(lbytes);
                    netClient.close();
                    netClient = null;
                    endHandler.handle(dataLength);
                    if (isOnMonitor()) {//如果之前监控是出于打开状态，那么执行完成后继续打开
                        openMonitor();
                    }
                } else {
                    //TODO
                    System.out.println("asjdfiojasdpiofjpaosidfjas");
                }
            } else {
                //TODO
                System.out.println("asjdfiojasdpiofjpaosidfjas");
            }
        }, error -> {
            netClient = null;
            endHandler.handle(null);
        });
    }

    /**
     * 获取记录，所有的新记录
     */
    public void readRecordLast(Handler<RecordVO> partHandler, Handler<Boolean> endHandler) throws IOException {
        /*readRecordPoint(recordPointData -> {
            int lastnum = Integer.parseInt(recordPointData.getRecord1_last_num_str());//记录尾号
            int breaknum = Integer.parseInt(recordPointData.getRecord1_break_str());//上传断点
            int size = lastnum - breaknum;
            try {
                readRecord(breaknum + 1, size, recordVO -> {//+1从新位置开始
                    partHandler.handle(recordVO);
                }, length -> {
                    //传送完成，更新记录指针
                    try {
                        updateRecordBreak(lastnum, endHandler);
                    } catch (IOException e) {
                        e.printStackTrace();
                        endHandler.handle(false);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                endHandler.handle(false);
            }
        });*/
        readRecordLastWithoutUpdate(partHandler, lastnum -> {
            if (lastnum != null) {
                //传送完成，更新记录指针
                try {
                    updateRecordBreak(lastnum, endHandler);
                } catch (IOException e) {
                    e.printStackTrace();
                    endHandler.handle(false);
                }
            } else {
                endHandler.handle(false);
            }
        });
    }

    /**
     * 获取记录，所有的新记录,但是不自动更新记录指针
     */
    public void readRecordLastWithoutUpdate(Handler<RecordVO> partHandler, Handler<Integer> endHandler) throws IOException {
        readRecordPoint(recordPointData -> {
            if (recordPointData != null) {
                int lastnum = Integer.parseInt(recordPointData.getRecord1_last_num_str());//记录尾号
                int breaknum = Integer.parseInt(recordPointData.getRecord1_break_str());//上传断点
                int size = lastnum - breaknum;
                try {
                    readRecord(breaknum + 1, size, recordVO -> {//+1从新位置开始
                        partHandler.handle(recordVO);
                    }, length -> {
                        endHandler.handle(lastnum);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    endHandler.handle(null);
                }
            } else {
                endHandler.handle(null);
            }
        });
    }

    /**
     * 读取设备实时监控状态
     *
     * @throws IOException
     */
    public void readMonitorState(Handler<String> handler) throws IOException {
        MessageVO messageVO = DriverDoor.ReadMonitorState();

        TCPConnect(messageVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            String s = BinaryUtil.bcd2Str(result.getData());
            handler.handle(s);
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(null);
        });
    }


    //设置设备TCP参数
    public void writeDevicePara(TCPParaVO tcpParaVOs, Handler<Boolean> handler) {

        TCPParaVO tcpParaVO = new TCPParaVO();
        tcpParaVO.setMac("00180610CF50");
        tcpParaVO.setIp("192.168.000.151");
        tcpParaVO.setAutomatically_get_ip("0");
        tcpParaVO.setSubnet_mask("255.255.255.000");
        tcpParaVO.setGateway_ip("192.168.000.001");
        tcpParaVO.setDns("000.000.000.000");
        tcpParaVO.setReserve_dns("000.000.000.000");
        tcpParaVO.setTcp_work_mode(TCPParaVO.TcpWorkMode_TCPBlend);
        tcpParaVO.setLocal_tcp_port("8000");
        tcpParaVO.setLocal_udp_port("8101");
        tcpParaVO.setTarget_port("8022");
        tcpParaVO.setTarget_ip("192.168.000.016");
        tcpParaVO.setTarget_domain_name("www.1234567890.com");

        TCPParaDataVO tcpParaDataVO = new TCPParaDataVO(tcpParaVO);
        MessageVO messageVO = DriverDoor.WriteTCPPara(tcpParaDataVO);
        TCPConnect(messageVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            if (MessageVO.Type_OK == DriverDoor.getMessageType(result)) {
                handler.handle(true);
            } else {
                handler.handle(false);
            }
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(false);
        });

    }

    //读取授权卡
    public void readPsnCardAuth(Handler<AuthVO> handler) {
        MessageVO messageDataVO = DriverDoor.ReadPsnCardAuth();
        TCPConnect(messageDataVO, bytes -> {
            MessageVO msg = new MessageVO(bytes);
            AuthVO authVO = new AuthVO(msg.getData());
            handler.handle(authVO);
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(null);
        });
    }

    //清空所有授权卡
    public void clearPsnCardAuth(int areaCode, Handler<Boolean> handler) {
        MessageVO messageDataVO = DriverDoor.ClearPsnCardAuth(areaCode);
        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            if (MessageVO.Type_OK == DriverDoor.getMessageType(result)) {
                handler.handle(true);
            } else {
                handler.handle(false);
            }
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(false);
        });
    }

    //读取所有授权卡
    public void readAllPsnCardAuth(int areaCode, Handler<List<AuthCardVO>> handler) {

        MessageVO messageDataVO = DriverDoor.ReadAllPsnCardAuth(areaCode);
        readPsnCardAuth(authVO -> {
            int length = authVO.getSortedAreaNum();
            List<AuthCardVO> authCardVOs = new ArrayList<AuthCardVO>();
            TCPConnectReadBigData(messageDataVO, tcpResultVO -> {
                List<DeviceToServerMessageVO> mvos = tcpResultVO.getVos();
                for (DeviceToServerMessageVO vo : mvos) {
                    List<AuthCardVO> ts = AuthCardVO.getAuthCardVOs(vo.getMessageDataVO());
                    // tcpResultVO.getAuthCardVOs().addAll(ts);
                    authCardVOs.addAll(ts);
                }
                System.out.println("接收数据完成");
                handler.handle(authCardVOs);
                netClient = null;
            }, process -> {
                System.out.println("第" + process[0] + "次接收设备传来的数据,接收数据量" + process[1] + "byte");
            }, error -> {
                netClient = null;
                handler.handle(null);
            });
        });
    }

    //读取单个授权卡
    public void readOnePsnCardAuth(byte[] cardCode, Handler<AuthCardVO> handler) {
        MessageVO messageDataVO = DriverDoor.ReadOnePsnCardAuth(cardCode);
        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            AuthCardVO vo = new AuthCardVO(result.getData());
            handler.handle(vo);
            netClient = null;
        }, error -> {
            netClient = null;
            handler.handle(null);
        });
    }

/*
    public void writePsnCardAuth(List<AuthCardVO> authCardVOs, Handler<Boolean> handler) {
        writeBeginPsnCardAuth(isSuccess -> {
            int step = 5;//步长，每次上传多少数据
            int size = authCardVOs.size();
            int times = size / step + ((size % step) > 0 ? 1 : 0);//总共需要上传多少次,如果有余数，说明剩下的小于step长度的数据仍要上传一次
            int[] beginIndex = new int[]{0};//[0]:开始位置
            int[] successCount = new int[]{0};//已经上传成功的个数

            while (beginIndex[0] < size) {
                int cbIndex = beginIndex[0];
                int to = (cbIndex + step) < size ? cbIndex + step : size;
                List<AuthCardVO> subList = authCardVOs.subList(cbIndex, to);//当前要上传的list

                writingPsnCardAuth(cbIndex, subList, upLoadSuccess -> {
                    System.out.println("第" + cbIndex / step + "次上传的数据成功,当前beginIndex：" + cbIndex);
                    successCount[0]++;
                    if (successCount[0] >= times) {//上传次数达到总次数，说明本次上传所有权限数据成功
                        writeEndPsnCardAuth(endSuccess -> {
                            System.out.println("上传成功");
                            handler.handle(endSuccess);
                        });
                    }
                }, failCardCodeList -> {
                    writeEndPsnCardAuth(endSuccess -> {
                        System.out.println("上传失败,当前beginIndex：" + cbIndex + "失败的卡号:");
                        for (byte[] bs : failCardCodeList) {
                            System.out.println(new String(bs));
                        }
                        return;//终止循环写入
                    });
                });
                beginIndex[0] += step;
            }
        });
    }
*/

    //批量写入非排序卡,使用一个tcp链接
    public void writePsnCardAuthUnBatch(List<AuthCardVO> authCardVOs, Handler<Boolean> successHandler) {
        List<MessageVO> msgs = getBatchMSGs(authCardVOs,1);
        TCPConnectWriteBigData(msgs, isSusscess -> {
            successHandler.handle(isSusscess);
            netClient = null;
        }, failListCardCodes -> {
            System.out.println("向设备上传失败,失败的卡号:");
            for (byte[] bs : failListCardCodes) {
                System.out.println(new String(bs));
            }
            netClient = null;
            successHandler.handle(false);
        }, process -> {
        }, error -> {
            netClient = null;
            successHandler.handle(false);
        });
    }

    //批量删除非排序卡,使用一个tcp链接
    public void deletePsnCardAuthUnBatch(List<AuthCardVO> authCardVOs, Handler<Boolean> successHandler) {
        List<MessageVO> msgs = getBatchMSGs(authCardVOs,2);
        TCPConnectWriteBigData(msgs, isSusscess -> {
            successHandler.handle(isSusscess);
            netClient = null;
        }, failListCardCodes -> {
            System.out.println("删除失败,失败的卡号:");
            for (byte[] bs : failListCardCodes) {
                System.out.println(new String(bs));
            }
            netClient = null;
            successHandler.handle(false);
        }, process -> {

        }, error -> {
            netClient = null;
            successHandler.handle(false);
        });
    }

    //批量写入排序卡,使用一个tcp链接
    public void writePsnCardAuthBatch(List<AuthCardVO> authCardVOs, Handler<Boolean> successHandler) {
        writeBeginPsnCardAuth(isSuccess -> {
            if (isSuccess) {
                List<MessageVO> msgs = getBatchMSGs(authCardVOs,3);
                netClient = null;
                TCPConnectWriteBigData(msgs, isSusscess -> {
                    if (isSusscess) {
                        netClient = null;
                        writeEndPsnCardAuth(endSuccess -> {
                            System.out.println("向设备上传成功");
                            successHandler.handle(endSuccess);
                            netClient = null;
                        });
                    } else {

                    }
                }, failListCardCodes -> {
                    netClient = null;
                    writeEndPsnCardAuth(endSuccess -> {
                        System.out.println("向设备上传失败,失败的卡号:");
                        for (byte[] bs : failListCardCodes) {
                            System.out.println(new String(bs));
                        }
                        netClient = null;
                    });
                }, process -> {
                    int a = 0;
                }, error -> {
                    netClient = null;
                    successHandler.handle(null);
                });
            } else {
                successHandler.handle(false);
            }
        });
    }

    private List<MessageVO> getBatchMSGs(List<AuthCardVO> authCardVOs,int type){
        int step = 5;//步长，每次上传多少数据
        List blocks = FormatUtils.splitList(authCardVOs,step);
        if(blocks!=null && blocks.size()>0){
            List<MessageVO> msgs = new ArrayList<MessageVO>();
            for(int i = 0 ;i<blocks.size();i++){
                MessageVO messageDataVO = null;
                List<AuthCardVO> blockList = (List<AuthCardVO>) blocks.get(i);
                switch(type){
                    case 1://批量写入非排序卡
                        messageDataVO = DriverDoor.WritePsnCardAuthUn(blockList);
                        break;
                    case 2://批量删除非排序卡
                        messageDataVO = DriverDoor.DeletePsnCardAuthUn(blockList);
                        break;
                    case 3://批量写入排序卡
                        messageDataVO = DriverDoor.WritePsnCardAuth(i*step+1, blockList);
                        break;
                }
                msgs.add(messageDataVO);
            }
            return msgs;
        }else{
            return null;
        }

//        int step = 5;//步长，每次上传多少数据
//        int size = authCardVOs.size();
//        List<MessageVO> msgs = new ArrayList<MessageVO>();
//        if (size == 1) {
//            MessageVO messageDataVO = null;
//            switch(type){
//                case 1://批量写入非排序卡
//                    messageDataVO = DriverDoor.WritePsnCardAuthUn(authCardVOs);
//                    break;
//                case 2://批量删除非排序卡
//                    messageDataVO = DriverDoor.DeletePsnCardAuthUn(authCardVOs);
//                    break;
//                case 3://批量写入排序卡
//                    messageDataVO = DriverDoor.WritePsnCardAuth(1, authCardVOs);
//                    break;
//            }
//            msgs.add(messageDataVO);
//        } else if (size > 1) {
//            int[] beginIndex = new int[]{1};//[0]:开始位置，起始序号是1
//            while (beginIndex[0] < size) {
//                int to = ( beginIndex[0] - 1 + step) < size ?  beginIndex[0] - 1 + step : size;
//                List<AuthCardVO> subList = authCardVOs.subList( beginIndex[0] - 1, to);//当前要上传的list
//                MessageVO messageDataVO = null;
//                switch(type){
//                    case 1://批量写入非排序卡
//                        messageDataVO = DriverDoor.WritePsnCardAuthUn(subList);
//                        break;
//                    case 2://批量删除非排序卡
//                        messageDataVO = DriverDoor.DeletePsnCardAuthUn(subList);
//                        break;
//                    case 3://批量写入排序卡
//                        messageDataVO = DriverDoor.WritePsnCardAuth(beginIndex[0], subList);
//                        break;
//                }
//                msgs.add(messageDataVO);
//                beginIndex[0] += step;
//            }
//        }
//
//        return msgs;
    }

    //开始写入排序卡
    private void writeBeginPsnCardAuth(Handler<Boolean> handler) {
        MessageVO messageDataVO = DriverDoor.WriteBeginPsnCardAuth();
        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            if (MessageVO.Type_OK == DriverDoor.getMessageType(result)) {
                handler.handle(true);
            } else {
                handler.handle(false);
            }
        }, error -> {
            netClient = null;
            handler.handle(false);
        });
    }

    //写入排序卡
    private void writingPsnCardAuth(int beginIndex, List<AuthCardVO> list, Handler<Boolean> handler, Handler<List<byte[]>> failCardCode) {
        MessageVO messageDataVO = DriverDoor.WritePsnCardAuth(beginIndex, list);
        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            if (MessageVO.Type_OK == DriverDoor.getMessageType(result)) {
                handler.handle(true);
            } else {
                //获取失败卡号
                byte[] data = result.getData();
                int size = BinaryUtil.byteToIntHignInF(BinaryUtil.subArray(data, 0, 4));
                byte[] ccData = BinaryUtil.subArray(data, 4, data.length);
                List<byte[]> failCardCodeList = new ArrayList<byte[]>();
                for (int m = 0; m < size; m++) {
                    byte[] card_code = BinaryUtil.subArray(ccData, m * 4, (m + 1) * 4);
                    failCardCodeList.add(card_code);
                }
                failCardCode.handle(failCardCodeList);
            }
        }, error -> {
            netClient = null;
            handler.handle(false);
        });
    }

    //终止写入排序卡
    private void writeEndPsnCardAuth(Handler<Boolean> handler) {
        MessageVO messageDataVO = DriverDoor.WriteEndPsnCardAuth();
        TCPConnect(messageDataVO, bytes -> {
            MessageVO result = new MessageVO(bytes);
            if (MessageVO.Type_OK == DriverDoor.getMessageType(result)) {
                handler.handle(true);
            } else {
                handler.handle(false);
            }
        }, error -> {
            netClient = null;
            handler.handle(false);
        });
    }

    /**
     * 写入大数据量链接方式
     *
     * @param msgs
     * @param successHandler 成功后回调
     * @param failHandler    失败后回调
     * @param processHandler
     */
    private void TCPConnectWriteBigData(List<MessageVO> msgs, Handler<Boolean> successHandler,
                                        Handler<List<byte[]>> failHandler, Handler<Integer> processHandler
            , Handler<Throwable> exceptionHandler) {
        if (netClient == null) {
            NetClientOptions options = new NetClientOptions().setConnectTimeout(ConnectTimeout)
                    .setReconnectAttempts(ConnectTimeout).//重连次数
                    setReconnectInterval(ReconnectInterval);//重连间隔

            Boolean tmpMonitor = false;
            if (isOnMonitor()) {
                tmpMonitor = true;
                closeMonitor();
            }

            netClient = vertx.createNetClient(options);
            final Boolean finalTmpMonitor = tmpMonitor;
            netClient.connect(devicePort, deviceUrl, res -> {
                if (res.succeeded()) {
                    NetSocket socket = res.result();
                    int[] successCount = new int[]{0};//已经上传成功的个数

                    socket.handler(buffer -> {
                        byte[] bytes = buffer.getBytes();
                        MessageVO result = new MessageVO(bytes);
                        if (MessageVO.Type_OK == DriverDoor.getMessageType(result)) {
                            System.out.println("第" + successCount[0] + "次向设备"+deviceUrl+":"+devicePort+"上传数据成功。");
                            processHandler.handle(successCount[0]);
                            successCount[0]++;
                            if (successCount[0] >= msgs.size()) {
                                if (socket != null) {
                                    socket.close();
                                }
                                if (netClient != null) {
                                    netClient.close();
                                }
                                successHandler.handle(true);
                                if (finalTmpMonitor) {//如果之前监控是出于打开状态，那么执行完成后继续打开
                                    openMonitor();
                                }
                            } else {
                                TCPSend(socket, msgs.get(successCount[0]));
                            }
                        } else {
                            //获取失败卡号
                            byte[] data = result.getData();
                            int size = BinaryUtil.byteToIntHignInF(BinaryUtil.subArray(data, 0, 4));
                            byte[] ccData = BinaryUtil.subArray(data, 4, data.length);
                            List<byte[]> failCardCodeList = new ArrayList<byte[]>();
                            for (int m = 0; m < size; m++) {
                                byte[] card_code = BinaryUtil.subArray(ccData, m * 4, (m + 1) * 4);
                                failCardCodeList.add(card_code);
                            }
                            socket.close();
                            netClient.close();
                            failHandler.handle(failCardCodeList);
                            if (finalTmpMonitor) {//如果之前监控是出于打开状态，那么执行完成后继续打开
                                openMonitor();
                            }
                        }
                    });

                    TCPSend(socket, msgs.get(0));
                } else {

                    exceptionHandler.handle(res.cause());
                    System.out.println("tcp链接失败" + res.cause());
                }
            });
        } else {//仍然有链接没有释放
            successHandler.handle(false);
        }
    }

    /**
     * 获取大数据量
     * 算法：依次读取，直到遇到结束vo。遇到结束vo，调用处理handler，并关闭链接。
     *
     * @param messageDataVO
     * @param handler       处理传输结果
     * @param process       处理进度信息
     */
    private void TCPConnectReadBigData(MessageVO messageDataVO, Handler<TCPResultVO> handler,
                                       Handler<int[]> process, Handler<Throwable> exceptionHandler) {
        if (netClient == null) {
            NetClientOptions options = new NetClientOptions().setConnectTimeout(ConnectTimeout)
                    .setReconnectAttempts(ReconnectAttempt).//重连次数
                    setReconnectInterval(ReconnectInterval);//重连间隔

            TCPResultVO resultVO = new TCPResultVO();
            netClient = vertx.createNetClient(options);

            Boolean tmpMonitor = false;
            if (isOnMonitor()) {
                tmpMonitor = true;
                closeMonitor();
            }

            //边读边解析成DeviceToServerMessageVO对象,遇到结束DeviceToServerMessageVO则说明传输完毕
            final Boolean finalTmpMonitor = tmpMonitor;
            netClient.connect(devicePort, deviceUrl, res -> {
                if (res.succeeded()) {
                    NetSocket socket = res.result();
                    int[] processNum = new int[2];
                    socket.handler(buffer -> {
                        processNum[0]++;
                        processNum[1] = buffer.length();
                        process.handle(processNum);
                        resultVO.tmpBytes = BinaryUtil.appendByteArray(resultVO.tmpBytes, buffer.getBytes());
            /*        for (int i = 0; i < resultVO.tmpBytes.length; i++) {
                        byte tmp = resultVO.tmpBytes[i];
                        if (tmp == 0x7e || tmp == 0x7f) {
                            System.out.println(tmp);
                        }
                    }
*/

                        int lpos = 0;//上次遇到0x7e的位置
                        for (int i = 0; i < resultVO.tmpBytes.length; i++) {
                            byte tmp = resultVO.tmpBytes[i];
                            if (tmp == 0x7e) {
                                if (resultVO.tmpVoBytes.length == 0) {//没有数据，说明是开始标志
                                    byte[] ts = BinaryUtil.subArray(resultVO.tmpBytes, lpos, i + 1);//获得bytes
                                    resultVO.tmpVoBytes = BinaryUtil.appendByteArray(resultVO.tmpVoBytes, ts);//追加到vobytes
                                } else if (resultVO.tmpVoBytes.length > 0) {//有数据，说明是结束标志
                                    byte[] ts = BinaryUtil.subArray(resultVO.tmpBytes, lpos, i + 1);//获得bytes
                                    resultVO.tmpVoBytes = BinaryUtil.appendByteArray(resultVO.tmpVoBytes, ts);//追加到vobytes
                                    //进行DeviceToServerMessageVO的解析
                                    resultVO.tmpVoBytes = handleTranslation(resultVO.tmpVoBytes);//转译处理
                                    DeviceToServerMessageVO d = new DeviceToServerMessageVO(resultVO.tmpVoBytes);
                                    resultVO.tmpVoBytes = new byte[]{};//vobytes初始化
                                    if (d.isEndVO()) {//如果是结束标志
                                        socket.close();
                                        netClient.close();
                                        handler.handle(resultVO);
                                        if (finalTmpMonitor) {//如果之前监控是出于打开状态，那么执行完成后继续打开
                                            openMonitor();
                                        }
                                    } else {//如果还没有结束
                                        resultVO.getVos().add(d);
                                    }
                                }
                                lpos = i + 1;
                            }
                        }

                        if (lpos >= resultVO.tmpBytes.length) {
                            resultVO.tmpBytes = new byte[]{};//vobytes初始化
                        } else {
                            resultVO.tmpBytes = BinaryUtil.subArray(resultVO.tmpBytes, lpos, resultVO.tmpBytes.length);
                        }
                    });

                    TCPSend(socket, messageDataVO);
                } else {
                    exceptionHandler.handle(res.cause());
                    System.out.println("tcp链接失败" + res.cause());
                }
            });
        } else {
            //未处理
        }

    }

    private void TCPConnect(MessageVO messageDataVO, Handler<byte[]> handler, Handler<Throwable> exceptionHandler) {
        if (netClient == null) {
            NetClientOptions options = new NetClientOptions().setConnectTimeout(ConnectTimeout)
                    .setReconnectAttempts(ReconnectAttempt).//重连次数
                    setReconnectInterval(ReconnectInterval);//重连间隔

            TCPResultVO resultVO = new TCPResultVO();
            netClient = vertx.createNetClient(options);

            Boolean tmpMonitor = false;
            if (isOnMonitor()) {
                tmpMonitor = true;
                closeMonitor();
            }

            //边读边解析成DeviceToServerMessageVO对象,遇到结束DeviceToServerMessageVO则说明传输完毕
            final Boolean finalTmpMonitor = tmpMonitor;
            netClient.connect(devicePort, deviceUrl, res -> {
                if (res.succeeded()) {
                    NetSocket socket = res.result();
                    socket.handler(buffer -> {
                        resultVO.tmpBytes = BinaryUtil.appendByteArray(resultVO.tmpBytes, buffer.getBytes());

                        int lpos = 0;//上次遇到0x7e的位置
                        for (int i = 0; i < resultVO.tmpBytes.length; i++) {
                            byte tmp = resultVO.tmpBytes[i];
                            if (tmp == 0x7e) {
                                if (resultVO.tmpVoBytes.length == 0) {//没有数据，说明是开始标志
                                    byte[] ts = BinaryUtil.subArray(resultVO.tmpBytes, lpos, i + 1);//获得bytes
                                    resultVO.tmpVoBytes = BinaryUtil.appendByteArray(resultVO.tmpVoBytes, ts);//追加到vobytes
                                } else if (resultVO.tmpVoBytes.length > 0) {//有数据，说明是结束标志
                                    byte[] ts = BinaryUtil.subArray(resultVO.tmpBytes, lpos, i + 1);//获得bytes
                                    resultVO.tmpVoBytes = BinaryUtil.appendByteArray(resultVO.tmpVoBytes, ts);//追加到vobytes
                                    //进行DeviceToServerMessageVO的解析
                                    resultVO.tmpVoBytes = handleTranslation(resultVO.tmpVoBytes);//转译处理
                                    socket.close();
                                    netClient.close();
                                    handler.handle(resultVO.tmpVoBytes);
                                    if (finalTmpMonitor) {//如果之前监控是出于打开状态，那么执行完成后继续打开
                                        openMonitor();
                                    }
                                }
                                lpos = i + 1;
                            }
                        }
                        if (lpos >= resultVO.tmpBytes.length) {
                            resultVO.tmpBytes = new byte[]{};//vobytes初始化
                        } else {
                            resultVO.tmpBytes = BinaryUtil.subArray(resultVO.tmpBytes, lpos, resultVO.tmpBytes.length);
                        }
                    });
                    TCPSend(socket, messageDataVO);
                } else {
                    exceptionHandler.handle(res.cause());
                    System.out.println("tcp链接失败" + res.cause());
                }
            });
        } else {

        }
    }

    private void TCPConnectNotClosed(MessageVO messageDataVO, Handler<byte[]> handler, Handler<Throwable> exceptionHandler) {
        if (netClient == null) {
            NetClientOptions options = new NetClientOptions().setConnectTimeout(ConnectTimeout)
                    .setReconnectAttempts(ReconnectAttempt).//重连次数
                    setReconnectInterval(ReconnectInterval);//重连间隔

            TCPResultVO resultVO = new TCPResultVO();
            netClient = vertx.createNetClient(options);

            Boolean tmpMonitor = false;
            if (isOnMonitor()) {
                tmpMonitor = true;
                closeMonitor();
            }

            //边读边解析成DeviceToServerMessageVO对象,遇到结束DeviceToServerMessageVO则说明传输完毕
            final Boolean finalTmpMonitor = tmpMonitor;
            netClient.connect(devicePort, deviceUrl, res -> {
                if (res.succeeded()) {
                    NetSocket socket = res.result();
                    socket.handler(buffer -> {
                        resultVO.tmpBytes = BinaryUtil.appendByteArray(resultVO.tmpBytes, buffer.getBytes());

                        int lpos = 0;//上次遇到0x7e的位置
                        for (int i = 0; i < resultVO.tmpBytes.length; i++) {
                            byte tmp = resultVO.tmpBytes[i];
                            if (tmp == 0x7e) {
                                if (resultVO.tmpVoBytes.length == 0) {//没有数据，说明是开始标志
                                    byte[] ts = BinaryUtil.subArray(resultVO.tmpBytes, lpos, i + 1);//获得bytes
                                    resultVO.tmpVoBytes = BinaryUtil.appendByteArray(resultVO.tmpVoBytes, ts);//追加到vobytes
                                } else if (resultVO.tmpVoBytes.length > 0) {//有数据，说明是结束标志
                                    byte[] ts = BinaryUtil.subArray(resultVO.tmpBytes, lpos, i + 1);//获得bytes
                                    resultVO.tmpVoBytes = BinaryUtil.appendByteArray(resultVO.tmpVoBytes, ts);//追加到vobytes
                                    //进行DeviceToServerMessageVO的解析
                                    resultVO.tmpVoBytes = handleTranslation(resultVO.tmpVoBytes);//转译处理
                                    handler.handle(resultVO.tmpVoBytes);
                                    resultVO.tmpVoBytes = new byte[]{};
                                }
                                lpos = i + 1;
                            }
                        }
                        if (lpos >= resultVO.tmpBytes.length) {
                            resultVO.tmpBytes = new byte[]{};//vobytes初始化
                        } else {
                            resultVO.tmpBytes = BinaryUtil.subArray(resultVO.tmpBytes, lpos, resultVO.tmpBytes.length);
                        }
                    });

                    TCPSend(socket, messageDataVO);
                } else {
                    exceptionHandler.handle(res.cause());
                    System.out.println("tcp链接失败" + res.cause());
                }
            });
        } else {

        }
    }


    /**
     * 实时监控的链接，此链接是不会自动关闭的
     *
     * @param messageDataVO
     */
    private Boolean TCPConnectMonitorOpen(MessageVO messageDataVO) {
        NetClientOptions options = new NetClientOptions().setConnectTimeout(ConnectTimeout)
                .setReconnectAttempts(ReconnectAttempt).//重连次数
                setReconnectInterval(ReconnectInterval);//重连间隔

        if (monitorClient == null) {//如果监控tcp客户端为null，说明之前没有开启过此ip对应的监控
            TCPPoolVO tcpPoolVO = new TCPPoolVO();
            tcpPoolVO.setMonitorNetClient(vertx.createNetClient(options));
            monitorClient = tcpPoolVO;

            monitorClient.getMonitorNetClient().connect(devicePort, deviceUrl, res -> {
                if (res.succeeded()) {
                    monitorClient.setMonitorNetSocket(res.result());
                    monitorClient.getMonitorNetSocket().handler(buffer -> {
                        if (monitorClient.getOnSocketClose()) {//如果是关闭成功的消息
                            monitorClient.setOnSocketClose(false);
                            monitorClient.getMonitorNetSocket().close();
                            monitorClient.getMonitorNetClient().close();
                        } else {
                            byte[] bytes = buffer.getBytes();
                            MessageVO result = new MessageVO(bytes);
                            if (MessageVO.Type_OK == DriverDoor.getMessageType(result)) {

                            } else if (MessageVO.Type_PasswordError == DriverDoor.getMessageType(result)) {

                            } else if (MessageVO.Type_checkError == DriverDoor.getMessageType(result)) {

                            } else if (MessageVO.Type_ipError == DriverDoor.getMessageType(result)) {

                            } else {
                                RecordDataVO recordDataVO = new RecordDataVO(result);
                                if (recordHandler != null) {
                                    recordHandler.handle(recordDataVO);
                                }
                            }
                        }
                    });

                    monitorClient.getMonitorNetSocket().endHandler(eh -> {

                    });
                    monitorClient.getMonitorNetSocket().drainHandler(dh -> {

                    });
                    monitorClient.getMonitorNetSocket().closeHandler(ch -> {
                        monitorClient.setMonitorNetSocket(null);
                        monitorClient.setMonitorNetClient(null);
                        monitorClient = null;
                    });

                    TCPSend(monitorClient.getMonitorNetSocket(), messageDataVO);
                } else {
                    System.out.println("tcp链接失败");
                }
            });

            return true;//开启监控长连接成功
        }

        return false;//已经开启了监控长连接
    }

    /**
     * 关闭monitor
     */
    private Boolean TCPConnectMonitorClose(MessageVO messageDataVO) {
        if (monitorClient == null) {//如果监控tcp客户端为null，说明之前没有开启过此ip对应的监控
            return false;//长连接关闭失败:之前没有开启监控
        } else {
            monitorClient.setOnSocketClose(true);
            TCPSend(monitorClient.getMonitorNetSocket(), messageDataVO);
            return true;//长连接关闭失败:之前没有开启监控
        }
    }

    private void TCPSend(NetSocket socket, MessageVO messageVO) {
        byte[] r = GetSendBytes(messageVO);
        //System.out.println(BinaryUtil.bytesToHexString(r));
        Buffer buffer2 = Buffer.buffer().appendBytes(r);
        socket.write(buffer2);
    }

    private byte[] GetSendBytes(MessageVO messageVO) {
        ServerToDeviceMessageVO stdMsg = new ServerToDeviceMessageVO();
        byte[] snbytes = BinaryUtil.stringToAscII(deviceSn);
        stdMsg.setDeviceSN(snbytes);
        stdMsg.setDevicePass(new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff});//默认0xFFFFFFFF
        stdMsg.setMessageCode(new byte[]{0x00, 0x00, 0x00, 0x00});
        stdMsg.setMessageDataVO(messageVO);
        byte[] r = stdMsg.getBytes();
        //转译码处理
        for (int i = 0; i < r.length; i++) {
            if (r[i] == 0x7f) {//转译
                r = BinaryUtil.insertByteArray(r, i + 1, new byte[]{0x02});
                i++;//插入一条数据，遍历位置要加1
            }
            if (r[i] == 0x7e) {
                if (i != 0 && i != r.length - 1) {//不是命令开头和结尾
                    r[i] = 0x7f;
                    r = BinaryUtil.insertByteArray(r, i + 1, new byte[]{0x01});
                    i++;//插入一条数据，遍历位置要加1
                }
            }
        }
        return r;
    }

    //处理转译码
    private byte[] handleTranslation(byte[] readBytes) {
        //转译码处理
        for (int i = 0; i < readBytes.length; i++) {
            if (readBytes[i] == 0x7f) {//转译
                if (readBytes[i + 1] == 0x01) {//后一位为01
                    readBytes[i] = 0x7e;
                    readBytes = BinaryUtil.deleteByteArray(readBytes, i + 1);
                } else if (readBytes[i + 1] == 0x02) {//后一位为02
                    readBytes = BinaryUtil.deleteByteArray(readBytes, i + 1);
                }
            }
        }

        return readBytes;
    }


    public String getDeviceUrl() {
        return deviceUrl;
    }

    public void setDeviceUrl(String deviceUrl) {
        this.deviceUrl = deviceUrl;
    }

    public int getDevicePort() {
        return devicePort;
    }

    public void setDevicePort(int devicePort) {
        this.devicePort = devicePort;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }
}


