package hr.fer.zemris.java.hw06.crypto.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class  allow the user to encrypt/decrypt 
 * given file using the AES cryptoalgorithm and 
 * the 128-bit encryption key or calculate and 
 * check the SHA-256 file digest. 
 * @author Silvana
 *
 */
public class Crypto {
	/**
	 * Size of buffer array.
	 */
	private static final int BUFFER_SIZE = 4096;
	/**
	 * Program starts here.
	 * It allows communication with user.
	 * @param args keyword and path
	 */
	public static void main(String[] args) {
		
		if(args.length != 2 && args.length != 3 ) {
			System.out.println("Too " +(args.length < 2 ? "few" : "many")+ " command arguments.");
			return;
		}else if(args.length == 2 && args[0].equals("checksha")) {
			
			Scanner sc = new Scanner(System.in);
			System.out.println("Please provide expected sha-256 digest for "+args[1]+":");
			System.out.printf("> ");
			String expectedDigest = sc.nextLine();
			sc.close();
			String digest;
			try {
				digest = computeTheDigest(args[1]);
			}catch(IllegalArgumentException | NoSuchAlgorithmException ex) {
				System.out.println(ex.getMessage());
				return;
			}
			
			if(expectedDigest.equals(digest)) {
				System.out.println("Digesting completed. Digest of " +args[1]+ " matches expected digest.");
				return;
			}
			System.out.println("Digesting completed. Digest of "+ args[1] + " does not match the expected digest. Digest\r\n" + 
					"was: "+digest);
			return;
			
				
		}else if(args.length == 3 && args[0].equals("encrypt") || args[0].equals("decrypt")) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			System.out.printf("> ");
			String password = sc.nextLine();
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			System.out.printf("> ");
			String vector = sc.nextLine();
			sc.close();
			try {
			performCryptography(args[0], password, vector, args[1], args[2]);
			}catch(IllegalArgumentException ex ) {
				System.out.println(ex.getMessage());
				return;
			}
			
			System.out.println("" + (args[0].equals("encrypt") ? "Encryption" : "Decryption") + " completed. Generated file " +
					args[2] + " based on file "+args[1]+".");
			
			
		}else {
			System.out.println("Keyword is not recognized!");
			return;
		}
	}
	/**
	 * Implementation of encryption/decryption of file
	 * using the AES cryptoalgorithm and the 128-bit encryption key.
	 * The same secret key is used to both encrypt and decrypt the data.
	 * @param keyword "encrypt" for encryption of data or "decrypt" for decryption of data
	 * @param key password 
	 * @param vector initialization vector
	 * @param firstFile source file
	 * @param secondFile destination file
	 */
	private static void performCryptography(String keyword, String key, String vector, String firstFile, String secondFile) {
		try (InputStream is = Files.newInputStream(Paths.get(firstFile) , StandardOpenOption.READ);
			 OutputStream os = Files.newOutputStream(Paths.get(secondFile) , StandardOpenOption.CREATE_NEW)){
			
			 SecretKey aesKey = new SecretKeySpec(Util.hextobyte(key), "AES");
			 AlgorithmParameterSpec param = new IvParameterSpec(Util.hextobyte(vector));
			 Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			 int mode = keyword.equals("encrypt") ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
			 aesCipher.init(mode, aesKey, param);
			
			 
			 byte[] buffer = new byte[BUFFER_SIZE];
			 int readBytes;
			 
			 while((readBytes = is.read(buffer)) != -1) {
					os.write(aesCipher.update(buffer,0,readBytes));
			 }
			 
			 aesCipher.doFinal();
		 }catch(IOException | NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException 
				 |InvalidAlgorithmParameterException |BadPaddingException |IllegalBlockSizeException ex) {
			 throw new IllegalArgumentException(ex.getMessage());
		 }
		
	}
	/**
	 * This method is used to calculate and check the SHA-256 file digest.
	 * A cryptographically secure message digest takes 
	 * arbitrary-sized input (a byte array), and generates 
	 * a fixed-size output, called a digest,
	 * @param fileName path
	 * @return digest
	 * @throws NoSuchAlgorithmException if there is no installed 
	 * provider that implements given algorithm.
	 */
	private static String computeTheDigest(String fileName) throws NoSuchAlgorithmException {
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		try (InputStream is = Files.newInputStream(Paths.get(fileName), StandardOpenOption.READ)) {
			byte[] buffer = new byte[BUFFER_SIZE];
			int readBytes;
			while((readBytes = is.read(buffer)) != -1) {
				sha.update(buffer,0,readBytes);
			}
			byte[] hash = sha.digest();
			String digest = Util.bytetohex(hash);
			return digest;
			
		}catch(IOException ex) {
			throw new IllegalArgumentException("File name is not recognized.");
			
		}
			
	}

}
