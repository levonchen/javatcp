package face.utils;

import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class LicenseUtil {

	

    public static String GenerateLicense(String clearText){
        try {
            //DESKeySpec keySpec = new DESKeySpec(
             //       MyConstant.PASSWORD_ENC_SECRET.getBytes("UTF-8"));

            //用自己加密自己
            DESKeySpec keySpec = new DESKeySpec(clearText.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            
            byte[] result = cipher.doFinal(clearText.getBytes("UTF-8"));
            
            String strResult = result.toString();
            
            String encrypedPwd = Base64.getEncoder().encodeToString(result);
            
            //在android
            //端给转为base64的字符串增加了一个\n 我们可以刚好利用这个异常来做掩饰，生成不一样的md5值。
            encrypedPwd += "\n";
            //String encrypedPwd = Base64.encodeToString(cipher.doFinal(clearText.getBytes("UTF-8")), Base64.DEFAULT);
            String md5 = getMD5(encrypedPwd,true);
            return md5;
            //return encrypedPwd;
        } catch (Exception e) {
        }
        return clearText;
    }

    /**
     * 对挺特定的 内容进行 md5 加密
     * @param message  加密明文
     * @param upperCase  加密以后的字符串是是大写还是小写  true 大写  false 小写
     * @return
     */
    public static String getMD5(String message, boolean upperCase) {
        String md5str = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] input = message.getBytes();

            byte[] buff = md.digest(input);

            md5str = bytesToHex(buff, upperCase);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5str;
    }


    public static String bytesToHex(byte[] bytes, boolean upperCase) {
        StringBuffer md5str = new StringBuffer();
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        if (upperCase) {
            return md5str.toString().toUpperCase();
        }
        return md5str.toString().toLowerCase();
    }

}
