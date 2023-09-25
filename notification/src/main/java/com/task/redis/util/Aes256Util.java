package com.task.redis.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class Aes256Util {
  public static String alg = "AES/CBC/PKCS5Padding";
  private static final String KEY = "ZERWDAOBASEISZEROBASEKEY";
  private static final String IV = KEY.substring(0, 16);

  public static String encrypt(String text) {
    try {
      Cipher cipher = Cipher.getInstance(alg);
      SecretKeySpec keyspec = new SecretKeySpec(KEY.getBytes(), "AES");
      IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));

      cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivParameterSpec);
      byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
      return Base64.encodeBase64String(encrypted);
    } catch (Exception e) {
      return null;
    }
  }

  public static String decrypt(String chiperText) {
    try {
      Cipher cipher = Cipher.getInstance(alg);
      SecretKeySpec keyspec = new SecretKeySpec(KEY.getBytes(), "AES");
      IvParameterSpec ivParameterSpec =
          new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));

      cipher.init(Cipher.DECRYPT_MODE, keyspec, ivParameterSpec);
      byte[] decrypted =
          cipher.doFinal(Base64.decodeBase64(chiperText));
      return new String(decrypted, StandardCharsets.UTF_8);
    } catch (Exception e) {
      return null;
    }
  }
}
