package com.tvoyagryvnia.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Cipher {

    public static String generateVerificationToken(String str) {
        return encrypt(str + new Date());
    }

    public static String encrypt(String str) {
        String s = str;
        try {
            s = encryptToSHA(s);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
        }

        return s;
    }

    private static String encryptToSHA(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        digest.update(str.getBytes());

        return HexBin.encode(digest.digest()).toLowerCase();
    }
}