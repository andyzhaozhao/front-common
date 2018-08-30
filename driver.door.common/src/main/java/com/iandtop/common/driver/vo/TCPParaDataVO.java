package com.iandtop.common.driver.vo;

import com.iandtop.common.utils.BinaryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-08-24.
 * TCP参数
 */
public class TCPParaDataVO {

    public static int BLength = 137;

    private byte[] mac = new byte[6];                   //MAC地址
    private byte[] ip = new byte[4];                    //IP地址
    private byte[] subnet_mask = new byte[4];           //子网掩码
    private byte[] gateway_ip = new byte[4];            //网关IP
    private byte[] dns = new byte[4];                   //DNS
    private byte[] reserve_dns = new byte[4];           //备用DNS
    private byte[] tcp_work_mode = new byte[1];         //TCP工作模式
    private byte[] local_tcp_port = new byte[2];        //本地TCP监听端口
    private byte[] local_udp_port = new byte[2];        //本地UDP监听端口
    private byte[] target_port = new byte[2];           //目标端口
    private byte[] target_ip = new byte[4];             //目标IP
    private byte[] automatically_get_ip = new byte[1];  //自动获取IP
    private byte[] target_domain_name = new byte[99];   //目标域名

    public TCPParaDataVO(){}

    public TCPParaDataVO(byte[] bytes){
        mac = BinaryUtil.subArray(bytes, 32, 38);
        ip = BinaryUtil.subArray(bytes, 38, 42);
        subnet_mask = BinaryUtil.subArray(bytes, 42, 46);
        gateway_ip = BinaryUtil.subArray(bytes, 46, 50);
        dns = BinaryUtil.subArray(bytes, 50, 54);
        reserve_dns = BinaryUtil.subArray(bytes, 54, 58);
        tcp_work_mode = BinaryUtil.subArray(bytes, 58, 59);
        local_tcp_port = BinaryUtil.subArray(bytes, 59, 61);
        local_udp_port = BinaryUtil.subArray(bytes, 61, 63);
        target_port = BinaryUtil.subArray(bytes, 63, 65);
        target_ip = BinaryUtil.subArray(bytes, 65, 69);
        automatically_get_ip = BinaryUtil.subArray(bytes, 69, 70);
        target_domain_name = BinaryUtil.subArray(bytes, 70, 169);
    }

    public TCPParaDataVO(MessageVO messageVO) {
        byte[] bytes = messageVO.getData();
        mac = BinaryUtil.subArray(bytes, 0, 6);
        ip = BinaryUtil.subArray(bytes, 6, 10);
        subnet_mask = BinaryUtil.subArray(bytes, 10, 14);
        gateway_ip = BinaryUtil.subArray(bytes, 14, 18);
        dns = BinaryUtil.subArray(bytes, 18, 22);
        reserve_dns = BinaryUtil.subArray(bytes, 22, 26);
        tcp_work_mode = BinaryUtil.subArray(bytes, 26, 27);
        local_tcp_port = BinaryUtil.subArray(bytes, 27, 29);
        local_udp_port = BinaryUtil.subArray(bytes, 29, 31);
        target_port = BinaryUtil.subArray(bytes, 31, 33);
        target_ip = BinaryUtil.subArray(bytes, 33, 37);
        automatically_get_ip = BinaryUtil.subArray(bytes, 37, 38);
        target_domain_name = BinaryUtil.subArray(bytes, 38, 137);
    }

    public TCPParaDataVO(TCPParaVO tcpParaVO){
        if(tcpParaVO.getMac() != null){
            mac = BinaryUtil.str2Bcd(tcpParaVO.getMac().replaceAll(" ",""));
        }
        setIpTypeData(ip,tcpParaVO.getIp());
        setIpTypeData(subnet_mask,tcpParaVO.getSubnet_mask());
        setIpTypeData(gateway_ip,tcpParaVO.getGateway_ip());
        setIpTypeData(dns,tcpParaVO.getDns());
        setIpTypeData(reserve_dns,tcpParaVO.getReserve_dns());

        tcp_work_mode = BinaryUtil.str2Bcd(tcpParaVO.getTcp_work_mode());

        setPortTypeData(local_tcp_port,tcpParaVO.getLocal_tcp_port());
        setPortTypeData(local_udp_port,tcpParaVO.getLocal_udp_port());
        setPortTypeData(target_port,tcpParaVO.getTarget_port());
        setIpTypeData(target_ip,tcpParaVO.getTarget_ip());

        automatically_get_ip = BinaryUtil.str2Bcd(tcpParaVO.getAutomatically_get_ip());
        target_domain_name = BinaryUtil.stringToAscII(tcpParaVO.getTarget_domain_name());
    }

    public void setIpTypeData(byte[] bytes,String ipTypeStr){

        String[] tmp = ipTypeStr.split("\\.");
        bytes[0] = BinaryUtil.intToByteHignInF(Integer.parseInt(tmp[0]))[3];
        bytes[1] = BinaryUtil.intToByteHignInF(Integer.parseInt(tmp[1]))[3];
        bytes[2] = BinaryUtil.intToByteHignInF(Integer.parseInt(tmp[2]))[3];
        bytes[3] = BinaryUtil.intToByteHignInF(Integer.parseInt(tmp[3]))[3];

    }

    public void setPortTypeData(byte[] bytes,String portTypeStr){

        portTypeStr = portTypeStr.replaceAll(" ","");
        int portNum = Integer.parseInt(portTypeStr);
        byte[] portBytes = BinaryUtil.intToByteHignInF(portNum);
        bytes[0] = portBytes[2];
        bytes[1] = portBytes[3];

    }

    public byte[] getBytes(){
        //转为byte数组
        byte[] r = null;
        List<byte[]> bytebytes = new ArrayList<byte[]>();
        bytebytes.add(mac);
        bytebytes.add(ip);
        bytebytes.add(subnet_mask);
        bytebytes.add(gateway_ip);
        bytebytes.add(dns);
        bytebytes.add(reserve_dns);
        bytebytes.add(tcp_work_mode);
        bytebytes.add(local_tcp_port);
        bytebytes.add(local_udp_port);
        bytebytes.add(target_port);
        bytebytes.add(target_ip);
        bytebytes.add(automatically_get_ip);
        bytebytes.add(target_domain_name);

        r = BinaryUtil.bytsArrayListTobyteArray(bytebytes);
        return r;
    }

    public static int getBLength() {
        return BLength;
    }

    public static void setBLength(int BLength) {
        TCPParaDataVO.BLength = BLength;
    }

    public byte[] getMac() {
        return mac;
    }

    public void setMac(byte[] mac) {
        this.mac = mac;
    }

    public byte[] getIp() {
        return ip;
    }

    public void setIp(byte[] ip) {
        this.ip = ip;
    }

    public byte[] getSubnet_mask() {
        return subnet_mask;
    }

    public void setSubnet_mask(byte[] subnet_mask) {
        this.subnet_mask = subnet_mask;
    }

    public byte[] getGateway_ip() {
        return gateway_ip;
    }

    public void setGateway_ip(byte[] gateway_ip) {
        this.gateway_ip = gateway_ip;
    }

    public byte[] getDns() {
        return dns;
    }

    public void setDns(byte[] dns) {
        this.dns = dns;
    }

    public byte[] getReserve_dns() {
        return reserve_dns;
    }

    public void setReserve_dns(byte[] reserve_dns) {
        this.reserve_dns = reserve_dns;
    }

    public byte[] getTcp_work_mode() {
        return tcp_work_mode;
    }

    public void setTcp_work_mode(byte[] tcp_work_mode) {
        this.tcp_work_mode = tcp_work_mode;
    }

    public byte[] getLocal_tcp_port() {
        return local_tcp_port;
    }

    public void setLocal_tcp_port(byte[] local_tcp_port) {
        this.local_tcp_port = local_tcp_port;
    }

    public byte[] getLocal_udp_port() {
        return local_udp_port;
    }

    public void setLocal_udp_port(byte[] local_udp_port) {
        this.local_udp_port = local_udp_port;
    }

    public byte[] getTarget_port() {
        return target_port;
    }

    public void setTarget_port(byte[] target_port) {
        this.target_port = target_port;
    }

    public byte[] getTarget_ip() {
        return target_ip;
    }

    public void setTarget_ip(byte[] target_ip) {
        this.target_ip = target_ip;
    }

    public byte[] getAutomatically_get_ip() {
        return automatically_get_ip;
    }

    public void setAutomatically_get_ip(byte[] automatically_get_ip) {
        this.automatically_get_ip = automatically_get_ip;
    }

    public byte[] getTarget_domain_name() {
        return target_domain_name;
    }

    public void setTarget_domain_name(byte[] target_domain_name) {
        this.target_domain_name = target_domain_name;
    }
}
