package com.iandtop.common.license;

import com.iandtop.common.license.tools.KeyGenerate;
import com.iandtop.common.license.tools.MacUap;
import com.iandtop.common.license.tools.SignatureData;
import com.iandtop.common.license.tools.VerifyData;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016-12-21.
 */
public class Starter {
    /**
     * @param args
     * @throws UnknownHostException
     * @throws SocketException
     */
    public static void main(String[] args) throws Exception {
        String goal = args[0];
        if(goal.equals("mmaacc")){//获得本机mac地址
            MacUap.getLocalMac();
        }else if(goal.equals("keyyyy")) {//生成私钥和公钥
            KeyGenerate.createKey();
        }else if(goal.equals("sigggg")) {//签名
            SignatureData.SignatureByPrivate();
        }else if(goal.equals("verifyyyy")) {//验证签名是否正确
            VerifyData.VerifyData();
        }
    }
}
