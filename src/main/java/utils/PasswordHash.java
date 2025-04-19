package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Utility class for handling password hashing with SHA-256
 */
public class PasswordHash {
    
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16; // 16 bytes = 128 bits
    
    /**
     * Generate a random salt for password hashing
     * @return random salt as byte array
     */
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }
    
    /**
     * Convert a byte array to hexadecimal string
     * @param bytes byte array to convert
     * @return hexadecimal string representation
     */
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
    
    /**
     * Convert a hexadecimal string to byte array
     * @param hex hexadecimal string to convert
     * @return byte array
     */
    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
    
    /**
     * Hash a password using SHA-256
     * @param password password to hash
     * @param salt salt for hashing
     * @return hashed password as hexadecimal string
     */
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
    
    /**
     * Hash a password with a new random salt
     * @param password password to hash
     * @return salt and hashed password separated by colon
     */
    public static String hashPasswordWithNewSalt(String password) {
        byte[] salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);
        return bytesToHex(salt) + ":" + hashedPassword;
    }
    
    /**
     * Verify if a password matches the stored hash
     * @param password password to verify
     * @param storedHash stored hash (salt:hash)
     * @return true if password matches, false otherwise
     */
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
