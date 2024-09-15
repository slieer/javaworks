package com.security.coder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DESUtil {
    private static final String ALGORITHM = "DESede";
    private static final String HEX_STRING = "0123456789ABCDEF";
    private static final int BUFFER_SIZE = 2 * 1024 * 1024;

    public static void encrypt(String key, File srcfile, File outFile)
            throws Exception {
        // 根据给定的字节数组和算法构造一个密钥
        byte[] temp = build3DesKey(key);
        
        SecretKey deskey = new SecretKeySpec(temp, ALGORITHM);
        // 加密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, deskey);
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(
                srcfile));
        FileOutputStream out = new FileOutputStream(outFile);
        CipherInputStream cis = new CipherInputStream(is, cipher);
        
        byte[] buffer = new byte[srcfile.length() > BUFFER_SIZE ? BUFFER_SIZE : (int)srcfile.length()];
        int r;
        while ((r = cis.read(buffer)) > 0) {
            out.write(buffer, 0, r);
        }
        cis.close();
        is.close();
        out.close();
    }

    public static void decrypt(String key, File srcfile, File destFile)
            throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        byte[] temp = build3DesKey(key);
        SecretKey deskey = new SecretKeySpec(temp, ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, deskey);
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(
                srcfile));
        BufferedOutputStream out = new BufferedOutputStream(
                new FileOutputStream(destFile));

        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        
        byte[] buffer = new byte[srcfile.length() > BUFFER_SIZE ? BUFFER_SIZE : (int)srcfile.length()];
        int r;
        while ((r = is.read(buffer)) >= 0) {
            cos.write(buffer, 0, r);
        }
        cos.close();
        out.close();
        is.close();

    }

    public static String encode(String str) {
        // 根据默认编码获取字节数组
        byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(HEX_STRING.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(HEX_STRING.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }

    public static String decode(String bytes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(
                bytes.length() / 2);
        // 将每2位16进制整数组装成一个字节
        for (int i = 0; i < bytes.length(); i += 2)
            baos.write((HEX_STRING.indexOf(bytes.charAt(i)) << 4 | HEX_STRING
                    .indexOf(bytes.charAt(i + 1))));
        return new String(baos.toByteArray());
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + ":";
        }
        return hs.toUpperCase();
    }

    private static byte[] build3DesKey(String keyStr) throws Exception {
        byte[] key = new byte[24]; // 声明一个24位的字节数组，默认里面都是0
        byte[] temp = keyStr.getBytes("UTF-8"); // 将字符串转成字节数组
        /*
         * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
         */
        if (key.length > temp.length) {
            // 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, temp.length);
            //System.out.println(temp.length);
        } else {
            // 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, key.length);
            //System.out.println(key.length);
        }
        for (byte k : key) {
            System.out.println(k);
        }
        return key;
    }

    public static void main(String[] args){
        //KeyGenerator keyGenerator = KeyGenerator.getInstance(Algorithm);
                   //server:usybm+scr%2Ft*%2Fye%2Fp
         String deviceId = "usybm%2Bscr%252Ft*%252Fye%252Fp";

        String in = "C:/Users/slieer/Downloads/KankeTV_DiyoMate4.0.apz";
        String out = "C:/Users/slieer/Downloads/KankeTV_DiyoMate4.0.de.apz";

        try {
            decrypt(deviceId, new File(in), new File(out));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    public static void main_(String[] args) throws Exception {
        String deviceId = "usybm+scr%2Ft*%2Fye%2Fp";
        
        String in = "E:/BorqsWorkspace/172.16.1.203_thunk/android/ott/Codes/AppStore/AppStore_DY/KankeTV_DiyoMate4.0.apz";
        String outEn = "E:/BorqsWorkspace/172.16.1.203_thunk/android/ott/Codes/AppStore/AppStore_DY/KankeTV_DiyoMate4.0.en.apz";
        
        String outDe = "E:/BorqsWorkspace/172.16.1.203_thunk/android/ott/Codes/AppStore/AppStore_DY/KankeTV_DiyoMate4.0.de.apz";
        
        File inFile = new File(in);
        File outEnFile = new File(outEn);
        File outDeFile = new File(outDe);
        
        if(!outEnFile.exists()){
            outEnFile.createNewFile();
        }
        
        if(!outDeFile.exists()){
            outDeFile.createNewFile();
        }
        //System.out.println(outFile.getAbsoluteFile());
        
        encrypt(deviceId, inFile, outEnFile);
        
        decrypt(deviceId, outEnFile, outDeFile);
    }

}
