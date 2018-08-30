package com.iandtop.common.license.tools;

import com.iandtop.common.utils.DriverUtil;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 * 测试：第三步
 * 使用公钥进行解密
 * @author andyzhao
 */
public class VerifyData {
    /**
     * 公钥解密
     *
     * @throws Exception
     */
    public static void VerifyData() throws Exception {
        // base64后的公钥串,这个串实际上应该从上一步的公钥文件publickey.keystore中读取
        /*String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCprcOZYBGNAUWBLPVTbqFyA40aP0+U6lqzSWO7"
                + "SIjm1bYEtPnU8dpGmSC9k7jnvp2g0ss5VZamJczsdFKSA/bA9z5q/DUectF8bviAzGKNBztB9l5e"
				+ "xPRS8jlAi9Lrqy4DcdeBZfYgwbuCVmONodHhLmFbBrjsnt2DgXTJWgirgwIDAQAB";*/

        File publicFile = new File(KeyGenerate.PUBLIC_KEY_FILE_PATH);
        byte[] bytess = DriverUtil.readFileContent(publicFile).getFileContent();
        String publicKeyStr = new String(bytess);

        X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(new BASE64Decoder().decodeBuffer(publicKeyStr));
        KeyFactory keFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keFactory.generatePublic(encodedKeySpec);
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initVerify(publicKey);// 初始化公钥

        File signDateFile = new File(KeyGenerate.DATE_FILE_PATH);//主要存储日期数据
        byte[] signDateFileBytes = DriverUtil.readFileContent(signDateFile).getFileContent();
        String toBeVerifyData = new String(signDateFileBytes);// 待验签的数据

        signature.update(toBeVerifyData.getBytes("utf-8"));
	/*	String sign = "Maz4PvoJtJBf/jWWoROy56Tc9zz+ok/2BoMDY+yBtL2EclEsWp3DsW9rCUHoAevrI/zF2nNt/XfA"
				+ "x1hTvm63c401Wh/gvL4/bNXQAfyxT/WvRMIMprmlRY3Opbq/0TBT+eCdggZwok3VMOje4cgUV1sr" + "ixFT63xCfSmDc0OrN2M=";*/
        //	String sign = "CP2GQzKhg3cyJ1DldK59ioDosOzYZ8YC4pkbiWiNjbabWLaMXc6qw8hT5oVcIi3K27+r9BqSNWDMEqD7SQ4N5LuQaAQVuqWLQmREzDK8jv/A2QWfKuEFtmVg8tHn/FdaahVtVT6vPyvyvnwHv0bwXhXfMIPNi6AwPYruWjiHmH0=";

        File signFile = new File(KeyGenerate.SIGN_FILE_PATH);
        byte[] signBytes = DriverUtil.readFileContent(signFile).getFileContent();
        String sign = new String(signBytes);

        boolean verify = signature.verify(new BASE64Decoder().decodeBuffer(sign)); // 用签名来验证待验证数据的合法性，如果待验签的数据被修改过则会验证失败
        System.out.println(verify);

    }
}



