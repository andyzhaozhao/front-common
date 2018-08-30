package com.iandtop.common.license.tools;

import com.iandtop.common.utils.DriverUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * 第二步
 * 使用私钥加密，生成数字签名
 * @author andyzhao
 */
public class SignatureData {

    /**
     * 私钥加密
     *
     * @throws Exception
     */
    public static void SignatureByPrivate() throws Exception {

        // base64后的私钥串,这个串实际上应该从上一步的私钥文件privatekey.keystore中读取
        File privateFile = new File(KeyGenerate.PRIVATE_KEY_FILE_PATH);
        byte[] bytess = DriverUtil.readFileContent(privateFile).getFileContent();
        String privateKeyStr = new String(bytess);
        PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(privateKeyStr));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(encodedKeySpec);
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initSign(privateKey);

        File signDateFile = new File(KeyGenerate.DATE_FILE_PATH);//主要存储日期数据和宿主机器mac地址
        byte[] signDateFileBytes = DriverUtil.readFileContent(signDateFile).getFileContent();
        String ss = new String(signDateFileBytes);
        String toBeSignedData = new String(signDateFileBytes);// 需要生产签名的元数据

        signature.update(toBeSignedData.getBytes("utf-8"));
        byte[] signedData = signature.sign();
        String sign = new BASE64Encoder().encode(signedData);

        File signFile = new File(KeyGenerate.SIGN_FILE_PATH);
        KeyGenerate.writeStr2File(signFile, sign);
        System.out.println("元数据生成的签名: " + sign);
    }

    public static void main(String[] args) throws Exception {
        SignatureByPrivate();
    }

}