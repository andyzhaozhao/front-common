package com.iandtop.common.driver.vo;

import com.iandtop.common.utils.BinaryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 授权卡信息内容
 */
public class AuthCardVO {

    public final static int BLength = 33;//byte数组长度
    private byte[] card_code = new byte[5];//卡号
    private byte[] password = new byte[4];//密码
    private byte[] card_ineffectived_ts = new byte[5];//有效期
    private byte[] open_time = new byte[4];//开门时段
    private byte[] effective_count = new byte[2];//有效次数
    private byte[] authAndAuth = new byte[1];//权限和特权，各占四位
    private byte[] card_state = new byte[1];//状态 0：正常状态；1：挂失；2：黑名单；
    private byte[] holiday = new byte[4];//节假日
    private byte[] outin_flag = new byte[1];//出入标志
    private byte[] readCard_time = new byte[6];//最近读卡时间

    public AuthCardVO() {
    }

    public AuthCardVO(byte[] datas) {
        init(datas);
    }

    public AuthCardVO(MessageVO messageVO) {
        byte[] dataAll = messageVO.getData();
        int size = BinaryUtil.byteToIntHignInF(BinaryUtil.subArray(dataAll, 0, 4));
        byte[] datas = BinaryUtil.subArray(dataAll, 4, dataAll.length);
        init(datas);
    }

    private void init(byte[] datas) {
        card_code = BinaryUtil.subArray(datas, 0, 5);
        password = BinaryUtil.subArray(datas, 5, 9);
        card_ineffectived_ts = BinaryUtil.subArray(datas, 9, 14);
        open_time = BinaryUtil.subArray(datas, 14, 18);
        effective_count = BinaryUtil.subArray(datas, 18, 20);
        authAndAuth = BinaryUtil.subArray(datas, 20, 21);
        card_state = BinaryUtil.subArray(datas, 21, 22);
        holiday = BinaryUtil.subArray(datas, 22, 26);
        outin_flag = BinaryUtil.subArray(datas, 26, 27);
        readCard_time = BinaryUtil.subArray(datas, 27, 33);
    }

    public byte[] getBytes() {
        //转为byte数组
        byte[] r = null;
        List<byte[]> bytebytes = new ArrayList<byte[]>();
        bytebytes.add(card_code);
        bytebytes.add(password);
        bytebytes.add(card_ineffectived_ts);
        bytebytes.add(open_time);
        bytebytes.add(effective_count);
        bytebytes.add(authAndAuth);
        bytebytes.add(card_state);
        bytebytes.add(holiday);
        bytebytes.add(outin_flag);
        bytebytes.add(readCard_time);

        r = BinaryUtil.bytsArrayListTobyteArray(bytebytes);
        return r;
    }

    public static List<AuthCardVO> getAuthCardVOs(MessageVO messageVO) {
        byte[] dataAll = messageVO.getData();
        int size = BinaryUtil.byteToIntHignInF(BinaryUtil.subArray(dataAll, 0, 4));

        byte[] ds = BinaryUtil.subArray(dataAll, 4, dataAll.length);
        List<AuthCardVO> result = new ArrayList<AuthCardVO>();
        for (int i = 0; i < size; i++) {
            byte[] datas = BinaryUtil.subArray(ds, i * AuthCardVO.BLength, (i + 1) * AuthCardVO.BLength);
            AuthCardVO aa = new AuthCardVO(datas);
            result.add(aa);
        }

        return result;
    }

    public static byte[] getAuthCardVOBytes(List<AuthCardVO> vos) {
        byte[] result = new byte[]{};
        for (int i = 0; i < vos.size(); i++) {
            byte[] cvoBytes = vos.get(i).getBytes();
            result = BinaryUtil.appendByteArray(result, cvoBytes);
        }

        return result;
    }

    public byte[] getCard_code() {
        return card_code;
    }

    public void setCard_code(byte[] card_code) {
        this.card_code = card_code;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public byte[] getCard_ineffectived_ts() {
        return card_ineffectived_ts;
    }

    public void setCard_ineffectived_ts(byte[] card_ineffectived_ts) {
        this.card_ineffectived_ts = card_ineffectived_ts;
    }

    public byte[] getOpen_time() {
        return open_time;
    }

    public void setOpen_time(byte[] open_time) {
        this.open_time = open_time;
    }

    public byte[] getEffective_count() {
        return effective_count;
    }

    public void setEffective_count(byte[] effective_count) {
        this.effective_count = effective_count;
    }

    public byte[] getAuthAndAuth() {
        return authAndAuth;
    }

    public void setAuthAndAuth(byte[] authAndAuth) {
        this.authAndAuth = authAndAuth;
    }

    public byte[] getCard_state() {
        return card_state;
    }

    public void setCard_state(byte[] card_state) {
        this.card_state = card_state;
    }

    public byte[] getHoliday() {
        return holiday;
    }

    public void setHoliday(byte[] holiday) {
        this.holiday = holiday;
    }

    public byte[] getOutin_flag() {
        return outin_flag;
    }

    public void setOutin_flag(byte[] outin_flag) {
        this.outin_flag = outin_flag;
    }

    public byte[] getReadCard_time() {
        return readCard_time;
    }

    public void setReadCard_time(byte[] readCard_time) {
        this.readCard_time = readCard_time;
    }
}
