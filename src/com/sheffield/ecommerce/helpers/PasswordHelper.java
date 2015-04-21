package com.sheffield.ecommerce.helpers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHelper {
	private static final int SALT_BYTE_SIZE = 24;
    private static final int HASH_BYTE_SIZE = 24;
    private static final int PBKDF2_ITERATIONS = 1000;
    private static final String RANDOM_ALGORITHM = "SHA1PRNG"; //The algorithm used to generate the salt
    private static final String KEY_ALGORITHM = "PBKDF2WithHmacSHA1"; //The algorithm used to generate the hash
	
	private byte[] passwordSalt; 
	private byte[] passwordHash;
	
	
	/**
	 * The password to hash is given and the Password Helper will generate a salt and hash
	 * 
	 * @param password
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public PasswordHelper(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
		passwordSalt = generateSalt();
		passwordHash = generateHash(password, passwordSalt);
	}
	
	
	/**
	 * Returns the password salt for the current instance
	 * @return
	 */
	public String getPasswordSalt() {
		return bytesToHexString(passwordSalt);
	}
	
	
	/**
	 * Returns the password hash for the current instance
	 * @return
	 */
	public String getPasswordHash() {
		return bytesToHexString(passwordHash);
	}
	
	
	/**
	 * Generates a random password salt
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
    private static byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance(RANDOM_ALGORITHM);
        byte[] salt = new byte[SALT_BYTE_SIZE];
        sr.nextBytes(salt);
        return salt;
    }
	
    
    /**
     * Takes a password and salt and produces a hash
     * 
     * @param password
     * @param salt
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
	private static byte[] generateHash(String password, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
		char[] passwordChars = password.toCharArray();
        PBEKeySpec spec = new PBEKeySpec(passwordChars, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
	}  

	
	/**
	 * Takes a salt used to make the given hash and uses it to hash the given password.
	 * After producing a hash, if it is equal to the given hash the passwords can be considered the same.
	 * This implies the password is correct.
	 * 
	 * @param password - The password to verify
	 * @param salt - The salt used to make the known hash
	 * @param knownHash - A previously made hash
	 * @return
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean verifyPassword(String password, String salt, String knownHash) throws InvalidKeySpecException, NoSuchAlgorithmException {
		byte[] newHash = generateHash(password, hexStringToBytes(salt));
		return Arrays.equals(hexStringToBytes(knownHash), newHash);
	}
	  
	
	/**
	 * Converts a byte array into a String by encoding it into hexadecimal
	 * @param bytes
	 * @return
	 */
	private static String bytesToHexString(byte[] bytes) {
		return DatatypeConverter.printHexBinary(bytes);
	}
	
	
	/**
	 * Converts a hexadecimal String into a byte array
	 * @param string
	 * @return
	 */
	private static byte[] hexStringToBytes(String string) {
		return DatatypeConverter.parseHexBinary(string);
	}
}