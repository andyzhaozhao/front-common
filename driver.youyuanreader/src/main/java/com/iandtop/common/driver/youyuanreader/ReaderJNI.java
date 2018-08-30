package com.iandtop.common.driver.youyuanreader;

/**
 * Created by Administrator on 2017-01-05.
 */
public class ReaderJNI {

    public static native int getVersion();
    public static native int openPort(String port);
    public static native int closePort(String port);
    public static native String readEPC(String port);

    public static void main(String[] args){
        System.loadLibrary("youyuanreader");
        System.out.println(ReaderJNI.getVersion());
    }

}
