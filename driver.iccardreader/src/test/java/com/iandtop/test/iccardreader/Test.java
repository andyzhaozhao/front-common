package com.iandtop.test.iccardreader;

import com.iandtop.common.driver.iccardreader.ReaderJNI;

public class Test {
    public static void main(String[] args) {
        test();
    }

    //测试时候需要把两个dll文件拷贝到项目根目录
    public static void test() {
        System.loadLibrary("SmartParkDriver");
        int a = ReaderJNI.openReader();
        switch (a) {
            case -1://失败
                System.out.println("失败");
                break;
            default:
                System.out.println(a);
                break;
        }
        ReaderJNI.hidBeep(a, 100, 1);

        byte[] rr = ReaderJNI.M1ReadData(a, (byte) 0x02, 64, new byte[]{});
        String rrr = rr.toString();
        System.out.println(rrr);

        byte[] data = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x10
                , 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x10};
        int aaa = ReaderJNI.M1WriteData(a, (byte) 0x02, data, new byte[]{});

        System.out.println(aaa);
        ReaderJNI.closeReader(a);
    }

    public static void download() {
        try {
            ReaderJNI.getDriversFileContent("C:\\ufsoft\\nchome\\modules\\smartparkpub\\lib\\driver.iccardreader.jar");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



