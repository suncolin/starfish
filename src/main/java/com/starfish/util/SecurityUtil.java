package com.starfish.util;

import cn.hutool.core.util.HexUtil;
import com.starfish.enumeration.ResultEnum;
import com.starfish.exception.CustomException;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * SecurityUtil
 * 编码和加解密工具类
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-01-06
 */
@SuppressWarnings("unused")
public final class SecurityUtil {

    public static final String ALGORITHM_MD5 = "MD5";

    public static final String ALGORITHM_SHA1 = "SHA-1";

    public static final String ALGORITHM_SHA256 = "SHA-256";

    public static final String ALGORITHM_SHA512 = "SHA-512";

    public static final String ALGORITHM_DES = "DES";

    public static final String ALGORITHM_AES = "AES";

    public static final String ALGORITHM_RSA = "RSA";

    private SecurityUtil() {

    }

    /**
     * encode base64,generally,the result may be convert to String
     *
     * @param data data data
     * @return return
     */
    public static String encodeBase64(byte[] data) {
        try {
            return Base64Utils.encodeToString(data);
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "encode base64 exception");
        }
    }

    /**
     * decode base64 string
     *
     * @param data data data
     * @return return
     */
    public static byte[] decodeBase64(String data) {
        try {
            return Base64Utils.decodeFromString(data);
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "decode base64 exception");
        }
    }

    /**
     * encode hex,generally,the result may be convert to String
     *
     * @param data data
     * @return return
     */
    public static String encodeHex(byte[] data) {
        return HexUtil.encodeHexStr(data);
    }

    /**
     * decode hex
     *
     * @param data data
     * @return return
     */
    public static byte[] decodeHex(String data) {
        try {
            return HexUtil.decodeHex(data);
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "decode hex exception");
        }
    }

    /**
     * md5 encode,generally,the result may be convert to String
     *
     * @param data data
     * @return return
     */
    public static String encodeMd5(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM_MD5);
            return encodeHex(digest.digest(data.getBytes()));
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "encode md5 exception");
        }
    }

    /**
     * 消息摘要算法的一种，被广泛认可的MD5算法的继任者.
     * SHA算法家族除了其代表SHA-1算法以外，还有SHA-224、SHA-256、SHA-384
     * 和SHA-512四种SHA算法的变体，以其摘要信息字节长度不同而命名
     * ，通常将这组算法并称为SHA-2算法。摘要信息字节长度的差异是SHA-2和SHA-1算法的最大差异。
     *
     * @param data data
     * @return return
     */
    public static String encodeSha1(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM_SHA1);
            return encodeHex(digest.digest(data.getBytes()));
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "encode sha1 exception");
        }
    }

    public static String encodeSha256(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM_SHA256);
            return encodeHex(digest.digest(data.getBytes()));
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "encode sha256 exception");
        }
    }

    public static String encodeSha512(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM_SHA512);
            return encodeHex(digest.digest(data.getBytes()));
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "encode sha512 exception");
        }
    }

    /**
     * Data Encryption Standard,数据加密标准，速度较快，适用于加密大量数据的场合,加密后转出16进制
     *
     * @param data data
     * @param key  key length must greater than 8
     * @return return
     */
    public static String encodeDes(String data, String key) {
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM_DES);
            SecretKey secureKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            cipher.init(Cipher.ENCRYPT_MODE, secureKey, sr);
            return encodeBase64((cipher.doFinal(data.getBytes())));
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "encode des exception");
        }
    }

    /**
     * DES
     *
     * @param data data
     * @param key  key
     * @return return
     */
    public static String decodeDes(String data, String key) {
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM_DES);
            SecretKey secureKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            cipher.init(Cipher.DECRYPT_MODE, secureKey, sr);
            return new String(cipher.doFinal(decodeBase64(data)));
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "decode des exception");
        }
    }

    /**
     * Advanced Encryption Standard,下一代的加密算法标准,加密后转出16进制
     *
     * @param data data
     * @param key  key
     * @return return
     */
    public static String encodeAes(String data, String key) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM_AES);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());
            keyGenerator.init(128, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, ALGORITHM_AES);
            Cipher cipher = Cipher.getInstance(ALGORITHM_AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return encodeBase64(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "encode aes exception");
        }
    }

    /**
     * decode aes
     *
     * @param data data
     * @param key  key
     * @return return
     */
    public static String decodeAes(String data, String key) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM_AES);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());
            keyGenerator.init(128, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, ALGORITHM_AES);
            Cipher cipher = Cipher.getInstance(ALGORITHM_AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return new String(cipher.doFinal(decodeBase64(data)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "decode aes exception,message is " + e.getMessage());
        }
    }

    /**
     * 生成rsa非对称密钥的公钥和私钥，据说不能在ios中使用。可以使用openssl替换。
     *
     * @return 公钥和私钥
     */
    public static Map<String, String> createRsaKeyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_RSA);
            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            HashMap<String, String> result = new HashMap<>(20);
            result.put("PublicKey", encodeBase64(publicKey.getEncoded()));
            result.put("PrivateKey", encodeBase64(privateKey.getEncoded()));
            return result;
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "create rsa key pair exception");
        }
    }

    /**
     * encode rsa
     *
     * @param data data
     * @param key  key
     * @return return
     */
    public static String encodeRsa(String data, String key) {
        try {
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(decodeBase64(key));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            Key publicKey = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return encodeBase64(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "encode rsa exception");
        }
    }

    /**
     * decode rsa
     *
     * @param data data
     * @param key  key
     * @return return
     */
    public static String decodeRsa(String data, String key) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodeBase64(key));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            Key privateKey = keyFactory.generatePrivate(keySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(decodeBase64(data)));
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "decode rsa exception");
        }
    }

    protected static int toDigit(final char ch, final int index) {
        final int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }

}