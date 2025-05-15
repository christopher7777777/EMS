package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Utility class for handling password hashing with SHA-256
 */
public class PasswordHash {
    
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16 ;// 16 bytes = 128 bits
    

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    

    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
    

    public static String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            return bytesToHex(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    

    public static String hashPasswordWithNewSalt(String password) {
        byte[] salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);
        return bytesToHex(salt) + ":" + hashedPassword;
    }
    

    public static boolean verifyPassword(String password, String storedHash) {
        String[] parts = storedHash.split(":");
        if (parts.length != 2) {
            return false;
        }
        
        byte[] salt = hexToBytes(parts[0]);
        String hashedPassword = hashPassword(password, salt);
        
        return hashedPassword.equals(parts[1]);
    }
}
