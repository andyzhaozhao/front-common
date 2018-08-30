package com.iandtop.common.driver.iccardreader;

import com.iandtop.common.utils.DriverFileContent;
import com.iandtop.common.utils.DriverUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * IC读卡器驱动
 * 生层c++头文件：javah -classpath D:\项目\common\common-smartpark\driver.iccardreader\target\classes -d D:\项目\common\common-smartpark\driver.iccardreader\target\classes -jni com.iandtop.common.driver.iccardreader.ReaderJNI
 */
public class ReaderJNI {

    /**
     * 十六进制为FFFFFFFFCCCCCCCC,没有读到卡时读卡器的返回值
     */
    public static final long NOCARD = 3435973836l;
    /**
     * 发卡器没有插入
     */
    public static final long NODEVICE = -1l;;

    //版本号，此dll要下载到用户本地，根据版本号替换本地就的dll
    //每次更新注意修改此版本号：加1
    public static native String getVersion();

    public static native int openReader();

    public static native int closeReader(int devHandler);

    public static native int requestCard(int devHandler);

    //led灯闪
    public static native int hidLed(int devHandler,int ms ,int count);

    //蜂鸣器响
    public static native int hidBeep(int devHandler,int ms ,int count);

    public static native int[] requestCardEx(int devHandler);

    /**
     * 读数据
     * @param devHandler
     * @param blk M1卡块号
     * @param len 读取长度
     * @param key 秘钥
     * @return
     */
    public static native byte[] M1ReadData(int devHandler,byte blk,int len,byte[] key);

    /**
     * 写数据
     * @param devHandler
     * @param blk M1卡块号
     * @param data 数据
     * @param key  秘钥
     * @return
     */
    public static native int M1WriteData(int devHandler,byte blk,byte[] data,byte[] key);
    /**
     * 获取dll文件的FileContent,调用方需要将jar包中的dll驱动文件保存到本地
     * @param jarFullPath
     * @return
     * @throws Exception
     */
    public static List<DriverFileContent> getDriversFileContent(String jarFullPath) throws Exception {
        List<DriverFileContent> result= new ArrayList<DriverFileContent>();
        JarFile file = new JarFile(jarFullPath);
        JarEntry e1 = file.getJarEntry("AxCardDeal.dll");
        InputStream input = file.getInputStream(e1);
        DriverFileContent tmpcontent = DriverUtil.readFileContent(input,e1.getName());
        result.add(tmpcontent);

        JarEntry e2 = file.getJarEntry("SmartParkDriver.dll");
        InputStream input2 = file.getInputStream(e2);
        DriverFileContent tmpcontent2 = DriverUtil.readFileContent(input2,e2.getName());
        result.add(tmpcontent2);

        input.close();
        input2.close();
        file.close();
        return result;
    }

    /**
     * 获取读到的卡号
     *
     * @return -1 没有插入发卡器
     * NOCARD（十六进制为FFFFFFFFCCCCCCCC）没有读到卡
     */
    public static Long getCardCode() {
        long cardCode = -1l;
        int nDev = ReaderJNI.openReader();
        if (nDev >= 0) {
            cardCode = ReaderJNI.requestCard(nDev);
            if (cardCode < 0) {
                String hexStringValue = Long.toHexString(cardCode);
                cardCode = Long.parseLong(hexStringValue.substring(8), 16);
            }
            ReaderJNI.hidBeep(nDev,100,1);
            ReaderJNI.hidLed(nDev,100,1);
            ReaderJNI.closeReader(nDev);
        }
        return cardCode;
    }
}

