package com.example.mototaxi.encryptapp;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.security.Key;

public class DarKnight {
    private static final String ALGORITHM = "AES";
    private static final byte[] SALT = "MotoService@123$".getBytes();// THE KEY MUST BE SAME
    private static final String X = DarKnight.class.getSimpleName();
    public static String getEncrypted(String plainText) {
        if (plainText == null) {
            return null;
        }

        Key salt = getSalt();

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, salt);
            byte[] encodedValue = cipher.doFinal(plainText.getBytes());
            return Base64.encodeToString(encodedValue,Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Failed to encrypt data");
    }

    public static String getDecrypted(String encodedText) {
        if (encodedText == null) {
            return null;
        }

        Key salt = getSalt();
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, salt);
            byte[] decodedValue = Base64.decode(encodedText, Base64.DEFAULT);
            byte[] decValue = cipher.doFinal(decodedValue);
            return new String(decValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static Key getSalt() {
        return new SecretKeySpec(SALT, ALGORITHM);
    }
}
