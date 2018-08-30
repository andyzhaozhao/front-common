package com.iandtop.common.driver.vo;

import com.iandtop.common.utils.BinaryUtil;
/**
 * 授权卡信息内容
 * @author andyzhao
 */
public class AuthVO {

    public static int BLength = 16;//byte数组长度
    private byte[] sortedAreaMax = new byte[4];//排序卡区域授权卡容量
    private byte[] sortedArea = new byte[4];//排序卡区域已存数量
    private byte[] unSortedAreaMax = new byte[4];//非排序卡区域授权卡容量
    private byte[] unSortedArea = new byte[4];//非排序卡区域已存数量

    private int sortedAreaMaxNum;
    private int sortedAreaNum;
    private int unSortedAreaMaxNum;
    private int unSortedAreaNum;

  /*  public AuthVO(Buffer buffer) {
        sortedAreaMax = buffer.getBytes(0, 4);
        sortedArea = buffer.getBytes(4, 8);
        unSortedAreaMax = buffer.getBytes(8, 12);
        unSortedArea = buffer.getBytes(12, 16);
    }*/

    public AuthVO(byte[] datas) {
        sortedAreaMax = BinaryUtil.subArray(datas, 0, 4);
        sortedArea = BinaryUtil.subArray(datas, 4, 8);
        unSortedAreaMax = BinaryUtil.subArray(datas, 8, 12);
        unSortedArea = BinaryUtil.subArray(datas, 12, 16);

        sortedAreaMaxNum = BinaryUtil.byteToIntHignInF(sortedAreaMax);
        sortedAreaNum = BinaryUtil.byteToIntHignInF(sortedArea);
        unSortedAreaMaxNum = BinaryUtil.byteToIntHignInF(unSortedAreaMax);
        unSortedAreaNum = BinaryUtil.byteToIntHignInF(unSortedArea);
    }

    public static int getBLength() {
        return BLength;
    }

    public static void setBLength(int BLength) {
        AuthVO.BLength = BLength;
    }

    public byte[] getSortedAreaMax() {
        return sortedAreaMax;
    }

    public void setSortedAreaMax(byte[] sortedAreaMax) {
        this.sortedAreaMax = sortedAreaMax;
    }

    public byte[] getSortedArea() {
        return sortedArea;
    }

    public void setSortedArea(byte[] sortedArea) {
        this.sortedArea = sortedArea;
    }

    public byte[] getUnSortedAreaMax() {
        return unSortedAreaMax;
    }

    public void setUnSortedAreaMax(byte[] unSortedAreaMax) {
        this.unSortedAreaMax = unSortedAreaMax;
    }

    public byte[] getUnSortedArea() {
        return unSortedArea;
    }

    public void setUnSortedArea(byte[] unSortedArea) {
        this.unSortedArea = unSortedArea;
    }

    public int getSortedAreaMaxNum() {
        return sortedAreaMaxNum;
    }

    public void setSortedAreaMaxNum(int sortedAreaMaxNum) {
        this.sortedAreaMaxNum = sortedAreaMaxNum;
    }

    public int getSortedAreaNum() {
        return sortedAreaNum;
    }

    public void setSortedAreaNum(int sortedAreaNum) {
        this.sortedAreaNum = sortedAreaNum;
    }

    public int getUnSortedAreaMaxNum() {
        return unSortedAreaMaxNum;
    }

    public void setUnSortedAreaMaxNum(int unSortedAreaMaxNum) {
        this.unSortedAreaMaxNum = unSortedAreaMaxNum;
    }

    public int getUnSortedAreaNum() {
        return unSortedAreaNum;
    }

    public void setUnSortedAreaNum(int unSortedAreaNum) {
        this.unSortedAreaNum = unSortedAreaNum;
    }
}
