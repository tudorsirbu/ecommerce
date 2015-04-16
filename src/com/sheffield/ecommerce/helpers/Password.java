package com.sheffield.ecommerce.helpers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Password {
	private static final int SALT_BYTE_SIZE = 24;
    private static final int HASH_BYTE_SIZE = 24;
    private static final int PBKDF2_ITERATIONS = 1000;
    private static final String RANDOM_ALGORITHM = "SHA1PRNG";
    private static final String KEY_ALGORITHM = "PBKDF2WithHmacSHA1";
	
	private byte[] passwordSalt;
	private byte[] passwordHash;
	
	
	public Password(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
		passwordSalt = generateSalt();
		passwordHash = generateHash(password, passwordSalt);
	}
	
	public String passwordSalt() {
		return bytesToHexString(passwordSalt);
	}
	
	public String passwordHash() {
		return bytesToHexString(passwordHash);
	}
	
    private static byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance(RANDOM_ALGORITHM);
        byte[] salt = new byte[SALT_BYTE_SIZE];
        sr.nextBytes(salt);
        return salt;
    }
	
	private static byte[] generateHash(String password, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
		char[] passwordChars = password.toCharArray();
		
        PBEKeySpec spec = new PBEKeySpec(passwordChars, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
	}  

	public static boolean verifyPassword(String password, String salt, String knownHash) throws InvalidKeySpecException, NoSuchAlgorithmException {
		byte[] newHash = generateHash(password, hexStringToBytes(salt));
		return compareHashes(hexStringToBytes(knownHash), newHash);
	}
	
	private static boolean compareHashes(byte[] hash1, byte[] hash2) {
		return Arrays.equals(hash1, hash2);
	}
     
	private static String bytesToHexString(byte[] bytes) {
		return DatatypeConverter.printHexBinary(bytes);
	}
	
	private static byte[] hexStringToBytes(String string) {
		return DatatypeConverter.parseHexBinary(string);
	}


	
}
