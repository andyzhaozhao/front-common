package com.iandtop.common.driver.vo;

import java.util.ArrayList;
import java.util.List;

public class TCPResultVO {
    private int currentLength ;
    private List<DeviceToServerMessageVO> vos = new ArrayList<DeviceToServerMessageVO>();
    private List<AuthCardVO> authCardVOs = new ArrayList<AuthCardVO>();

    public byte[] tmpBytes = new byte[]{};//存储临时的bytes，用来缓存从硬件读取的bytes
    public byte[] tmpVoBytes = new byte[]{};//存储临时的bytes，用来拼vo

    public int getCurrentLength() {
        return currentLength;
    }

    public void setCurrentLength(int currentLength) {
        this.currentLength = currentLength;
    }

    public List<DeviceToServerMessageVO> getVos() {
        return vos;
    }

    public void setVos(List<DeviceToServerMessageVO> vos) {
        this.vos = vos;
    }

    public List<AuthCardVO> getAuthCardVOs() {
        return authCardVOs;
    }

    public void setAuthCardVOs(List<AuthCardVO> authCardVOs) {
        this.authCardVOs = authCardVOs;
    }
}
