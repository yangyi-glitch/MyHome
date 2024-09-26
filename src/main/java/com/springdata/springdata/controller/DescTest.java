package com.springdata.springdata.controller;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class DescTest {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        String clearText = "yangyi";
        String OriginKey = "12345678";

        String cipherText = desEncript(clearText, OriginKey);
        System.out.println(cipherText);

        String cipherText1 = desDecript(cipherText, OriginKey);
        System.out.println(cipherText1);
    }

    private static String desDecript(String cipherText, String originKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("DES");
        Key key = getKey(originKey);
        cipher.init(Cipher.DECRYPT_MODE,key);

        byte[] decode = Base64.getDecoder().decode(cipherText);
        byte[] bytes = cipher.doFinal(decode);
        return new String(bytes);
    }

    private static String desEncript(String clearText, String originKey) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("DES");
        SecretKeySpec key = getKey(originKey);
        cipher.init(Cipher.ENCRYPT_MODE,key);

        byte[] doFinal = cipher.doFinal(clearText.getBytes());
        byte[] encode = Base64.getEncoder().encode(doFinal);
        return new String(encode);
    }

    private static SecretKeySpec getKey(String originKey) {
        SecretKeySpec key = new SecretKeySpec(originKey.getBytes(),"DES");
        return key;
    }
}
