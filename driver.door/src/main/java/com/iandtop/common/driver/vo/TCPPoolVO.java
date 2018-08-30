package com.iandtop.common.driver.vo;

import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

/**
 * 表示一个tcp长链接
 * @author andyzhao
 */
public class TCPPoolVO {
    private  NetClient monitorNetClient  ;
    private  NetSocket monitorNetSocket  ;
    private  Boolean isOnSocketClose = false;//正在关闭tcp长连接

    public NetClient getMonitorNetClient() {
        return monitorNetClient;
    }

    public void setMonitorNetClient(NetClient monitorNetClient) {
        this.monitorNetClient = monitorNetClient;
    }

    public NetSocket getMonitorNetSocket() {
        return monitorNetSocket;
    }

    public void setMonitorNetSocket(NetSocket monitorNetSocket) {
        this.monitorNetSocket = monitorNetSocket;
    }

    public Boolean getOnSocketClose() {
        return isOnSocketClose;
    }

    public void setOnSocketClose(Boolean onSocketClose) {
        isOnSocketClose = onSocketClose;
    }
}


