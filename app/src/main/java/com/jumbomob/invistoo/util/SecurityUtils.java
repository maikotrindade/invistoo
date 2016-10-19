package com.jumbomob.invistoo.util;

import android.text.TextUtils;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author maiko.trindade
 * @since 17/10/2016
 */
public class SecurityUtils {

    private static final String TAG = SecurityUtils.class.getSimpleName();

    static public String generateId() {
        String result = null;
        try {
            final SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            final String randomNum = new Integer(secureRandom.nextInt()).toString();
            final MessageDigest sha = MessageDigest.getInstance("SHA-1");
            result = hexEncode(sha.digest(randomNum.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            Log.e(TAG, ex != null && !TextUtils.isEmpty(ex.getMessage()) ? ex.getMessage() : "NoSuchAlgorithmException");
        }
        return result;
    }

    static private String hexEncode(byte[] aInput) {
        StringBuilder result = new StringBuilder();
        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (int idx = 0; idx < aInput.length; ++idx) {
            byte b = aInput[idx];
            result.append(digits[(b & 0xf0) >> 4]);
            result.append(digits[b & 0x0f]);
        }
        return result.toString();
    }

}
