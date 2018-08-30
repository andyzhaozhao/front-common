package com.iandtop.common.driver.vo;

import com.iandtop.common.utils.BinaryUtil;

/**
 * 2016-08-24
 */
public class TCPParaVO {

    public static final String TcpWorkMode_TCPClient = "1";
    public static final String TcpWorkMode_TCPServer = "2";
    public static final String TcpWorkMode_TCPBlend = "3";

    private String mac;
    private String ip;
    private String subnet_mask;
    private String gateway_ip;
    private String dns;
    private String reserve_dns;
    private String tcp_work_mode;
    private String local_tcp_port;
    private String local_udp_port;
    private String target_port;
    private String target_ip;
    private String automatically_get_ip;
    private String target_domain_name;

    public TCPParaVO(){}

    public TCPParaVO(TCPParaDataVO tcpParaDataVO){
        if(tcpParaDataVO.getMac() != null){
            mac = BinaryUtil.bcd2Str(tcpParaDataVO.getMac());
        }
        if(tcpParaDataVO.getIp() != null){
            ip = BinaryUtil.bcd2Str(tcpParaDataVO.getIp());
        }
        if(tcpParaDataVO.getSubnet_mask() != null){
            subnet_mask = BinaryUtil.bcd2Str(tcpParaDataVO.getSubnet_mask());
        }
        if(tcpParaDataVO.getGateway_ip() != null){
            gateway_ip = BinaryUtil.bcd2Str(tcpParaDataVO.getGateway_ip());
        }
        if(tcpParaDataVO.getDns() != null){
            dns = BinaryUtil.bcd2Str(tcpParaDataVO.getDns());
        }
        if(tcpParaDataVO.getReserve_dns() != null){
            reserve_dns = BinaryUtil.bcd2Str(tcpParaDataVO.getReserve_dns());
        }
        if(tcpParaDataVO.getTcp_work_mode() != null){
            tcp_work_mode = BinaryUtil.bcd2Str(tcpParaDataVO.getTcp_work_mode());
        }
        if(tcpParaDataVO.getLocal_tcp_port() != null){
            local_tcp_port = BinaryUtil.bcd2Str(tcpParaDataVO.getLocal_tcp_port());
        }
        if(tcpParaDataVO.getLocal_udp_port() != null){
            local_udp_port = BinaryUtil.bcd2Str(tcpParaDataVO.getLocal_udp_port());
        }
        if(tcpParaDataVO.getTarget_port() != null){
            target_port = BinaryUtil.bcd2Str(tcpParaDataVO.getTarget_port());
        }
        if(tcpParaDataVO.getTarget_ip() != null){
            target_ip = BinaryUtil.bcd2Str(tcpParaDataVO.getTarget_ip());
        }
        if(tcpParaDataVO.getAutomatically_get_ip() != null){
            automatically_get_ip = BinaryUtil.bcd2Str(tcpParaDataVO.getAutomatically_get_ip());
        }
        if(tcpParaDataVO.getTarget_domain_name() != null){
            target_domain_name = BinaryUtil.bcd2Str(tcpParaDataVO.getTarget_domain_name());
        }
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSubnet_mask() {
        return subnet_mask;
    }

    public void setSubnet_mask(String subnet_mask) {
        this.subnet_mask = subnet_mask;
    }

    public String getGateway_ip() {
        return gateway_ip;
    }

    public void setGateway_ip(String gateway_ip) {
        this.gateway_ip = gateway_ip;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public String getReserve_dns() {
        return reserve_dns;
    }

    public void setReserve_dns(String reserve_dns) {
        this.reserve_dns = reserve_dns;
    }

    public String getTcp_work_mode() {
        return tcp_work_mode;
    }

    public void setTcp_work_mode(String tcp_work_mode) {
        this.tcp_work_mode = tcp_work_mode;
    }

    public String getLocal_tcp_port() {
        return local_tcp_port;
    }

    public void setLocal_tcp_port(String local_tcp_port) {
        this.local_tcp_port = local_tcp_port;
    }

    public String getLocal_udp_port() {
        return local_udp_port;
    }

    public void setLocal_udp_port(String local_udp_port) {
        this.local_udp_port = local_udp_port;
    }

    public String getTarget_port() {
        return target_port;
    }

    public void setTarget_port(String target_port) {
        this.target_port = target_port;
    }

    public String getTarget_ip() {
        return target_ip;
    }

    public void setTarget_ip(String target_ip) {
        this.target_ip = target_ip;
    }

    public String getAutomatically_get_ip() {
        return automatically_get_ip;
    }

    public void setAutomatically_get_ip(String automatically_get_ip) {
        this.automatically_get_ip = automatically_get_ip;
    }

    public String getTarget_domain_name() {
        return target_domain_name;
    }

    public void setTarget_domain_name(String target_domain_name) {
        this.target_domain_name = target_domain_name;
    }

    @Override
    public String toString() {
        return "TCPParaVO{" +
                "mac='" + mac + '\'' +
                ", ip='" + ip + '\'' +
                ", subnet_mask='" + subnet_mask + '\'' +
                ", gateway_ip='" + gateway_ip + '\'' +
                ", dns='" + dns + '\'' +
                ", reserve_dns='" + reserve_dns + '\'' +
                ", tcp_work_mode='" + tcp_work_mode + '\'' +
                ", local_tcp_port='" + local_tcp_port + '\'' +
                ", local_udp_port='" + local_udp_port + '\'' +
                ", target_port='" + target_port + '\'' +
                ", target_ip='" + target_ip + '\'' +
                ", automatically_get_ip='" + automatically_get_ip + '\'' +
                ", target_domain_name='" + target_domain_name + '\'' +
                '}';
    }
}
