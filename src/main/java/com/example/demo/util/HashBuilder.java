package com.example.demo.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by vandebroeck.k on 29/08/2017.
 */
public class HashBuilder {

    public static String createHash(String... text) {
        StringBuilder plainText = new StringBuilder();
        for (String s : text) {
            plainText.append(s);
        }
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert messageDigest != null;
        messageDigest.reset();
        messageDigest.update(plainText.toString().getBytes());
        StringBuilder hashtext = new StringBuilder(new BigInteger(1, messageDigest.digest()).toString(32));
        while (hashtext.length() < 32) {
            hashtext.insert(0, "0");
        }
        return hashtext.toString();
    }
}
