package com.cusc.certificategenerationsystem.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;

/**
 *
 * @author PC_HP
 */
public class MD5Encryptor {
    public static String enrText;
    public static String encrypt(String password){
        try {
            
            MessageDigest msd = MessageDigest.getInstance("MD5");
            byte[] srcTextBytes = password.getBytes("UTF-8");
            byte[] enrTextBytes = msd.digest(srcTextBytes);
            
            BigInteger bigInt = new BigInteger(1, enrTextBytes);
            enrText = bigInt.toString();
            
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            JOptionPane.showMessageDialog(null, "Password encryption failed!", "Message", 1);
        }
        return enrText;
    }    
   
}
