package com.hailian.util.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by han on 2017/4/14.
 */


public class AES {

    private static final String default_charset = "UTF-8";

    /**
     * 加密
     *
     * @param content
     *            需要加密的内容
     * @param key
     *            加密密码
     * @param md5Key
     *            是否对key进行md5加密
     * @param iv
     *            加密向量
     * @return 加密后的字节数据
     */
    public static byte[] encrypt(byte[] content, byte[] key, boolean md5Key, byte[] iv) {
        try {
            if (md5Key) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                key = md.digest(key);
            }
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding"); // "算法/模式/补码方式"
            IvParameterSpec ivps = new IvParameterSpec(iv);// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivps);
            return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            // HLog.e(ex.getLocalizedMessage());
        }
        return null;
    }

    public static byte[] decrypt(byte[] content, byte[] key, boolean md5Key, byte[] iv) {
        try {
            if (md5Key) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                key = md.digest(key);
            }
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding"); // "算法/模式/补码方式"
            IvParameterSpec ivps = new IvParameterSpec(iv);// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivps);
            return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            // HLog.e(ex.getLocalizedMessage());
        }
        return null;
    }

    /*
     * 加密数据 encrypt
     *
     * @param data 需要加密的源数据
     *
     * @param key 用来加密数据的秘钥
     */
    public static final String encrypt(String data, String key) {
        if (StringUtils.isEmpty(data)) {
            return "";
        }
        if (StringUtils.isEmpty(key)) {
            return "";
        }
        if (key.length() != 16) {
            return "";
        }

        byte[] binary = encrypt(data.getBytes(), key.getBytes(), false, key.getBytes());
        if (binary == null) {
            return "";
        }
        String value = Base64Util.encode(binary);

        // URL解码
        //加密
        value = URLCoder.getURLEncoderString(value);
        return value;
    }

    /*
     * 解密数据 decrypt
     *
     * @param data 需要解密的源数据
     *
     * @param key 用来解密数据的秘钥
     */
    public static final String decrypt(String data, String key) {
        if (StringUtils.isEmpty(data)) {
            return "";
        }
        if (StringUtils.isEmpty(key)) {
            return "";
        }
        if (key.length() != 16) {
            return "";
        }

        //解密
        data = URLCoder.getURLDecoderString(data);

        byte[] binary = Base64Util.decode(data);
        if (binary == null) {
            return "";
        }
        byte[] valueByte = decrypt(binary, key.getBytes(), false, key.getBytes());
        if (valueByte == null) {
            return "";
        }
        String value = "";
        try {
            value = new String(valueByte, default_charset);
        } catch (Exception e) {
            value = "";
        }

        return value;
    }

    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String cKey = "LX00000000001111";

        Map<String,String> map = new HashMap<String,String>();
        /*
        map.put("customId","1");
        map.put("continent","2");
        map.put("countryName","3");
        map.put("reportType","4");
        map.put("orderType","5");
        map.put("reportLanguage","6");
        map.put("company","7");
        map.put("speed","8");
        map.put("referenceNum","9");
        map.put("address","10");
        map.put("telphone","11");
        map.put("fax","12");
        map.put("email","13");
        map.put("contacts","14");
        map.put("remarks","15");
        map.put("onlineId","16");*/
        //客户
        /*map.put("id","406");
        map.put("name","接口");
        map.put("contacts","王先生");
        map.put("contactsShortName","王先生1");
        map.put("telphone","15953295779");
        map.put("email","610754@qq.com");
        map.put("fax","123456");
        map.put("country","中国大陆");
        map.put("accountCount","0");
        map.put("money","100.05");
        map.put("isArrearage","0");
        map.put("isOldCustomer","1");
        map.put("address","青岛崂山区");
        map.put("remarks","这里是备注");*/

        //充值
        String time = System.currentTimeMillis()+"";
        /*map.put("userId","406");
        map.put("money","0");
        map.put("currency","RMB");
        map.put("units","50");
        map.put("updateTime",time);*/

        //扣款
        map.put("userId","406");
        map.put("money","0");
        map.put("currency","RMB");
        map.put("units","50");
        map.put("updateTime",time);

        JSONObject json = JSONObject.fromObject(map);

        // 需要加密的字串
        String cSrc = json.toString();
        System.out.println(cSrc);
        // 加密
        String enString = AES.encrypt(cSrc, cKey);
        System.out.println("加密后的字串是：" + enString);

        // 解密
        //String DeString = AES.decrypt("pGzNOXrAd5XLDp8RjAEHt3DQXb%2BUs4SR4fVFnRCbaDZzN6AqmxFoQFlC6htjSRNcwt13dIYx786nHQ8eArL%2FB9QzGvekFyGb%2B6DESqF36jeBetormSk7xwvm0kRQECtFZA1n4DkeEY1eX8R43bhzGeWZTWWHgEbAWHFlVBDPS9wwj2sCLADSfh4wkNw1aP%2B4UD%2FkRQ67B1UZtzMKP3VDoahcThFhb6imAOCjx%2B%2FVyibKFOtF5vpt6%2BF3POh%2BH711VEM6%2F2hFVYUo%2FuGiZDNp%2BWf4pGq96mGo6HoBYk0ULieCBouR1vxBbirpbZ4D12AUnGaZjcyrkvkLFesQJ5uAgw%3D%3D", cKey);
        String DeString = AES.decrypt(enString, cKey);
        System.out.println("解密后的字串是：" + DeString);
    }

}

