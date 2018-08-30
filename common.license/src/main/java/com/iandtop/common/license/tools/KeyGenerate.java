package com.iandtop.common.license.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import sun.misc.BASE64Encoder;

/**
 * 第一步
 * 生成公钥和私钥对
 * @author andyzhao
 */
public class KeyGenerate {

	public static final String PUBLIC_KEY_FILE_PATH = "C://MyKeyStore/iandtop.keystore";

	public static final String PRIVATE_KEY_FILE_PATH = "C://MyKeyStore/iandtopprivatekey.keystore";

	public static final String SIGN_FILE_PATH = "C://MyKeyStore/sign.iandtop";

	public static final String DATE_FILE_PATH = "C://MyKeyStore/"+"iandtop.date";//DATE

	public static void writeStr2File(File file, String content) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(content);
		writer.flush();
		writer.close();
	}

    public static void createKey() throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = new SecureRandom();
        generator.initialize(1024, random);
        KeyPair keyPair = generator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        // 将生成的私钥及公钥base64编码
        String privateKeyStr = new BASE64Encoder().encode(privateKey.getEncoded());
        String publicKeyStr = new BASE64Encoder().encode(publicKey.getEncoded());

        System.out.println("privateKeyStr = " + privateKeyStr);
        System.out.println("publicKeyStr = " + publicKeyStr);

        // 将公钥以及私钥写入文件保存
        File privateFile = new File(PRIVATE_KEY_FILE_PATH);
        writeStr2File(privateFile, privateKeyStr);

        File publicFile = new File(PUBLIC_KEY_FILE_PATH);
        writeStr2File(publicFile, publicKeyStr);
    }
}
