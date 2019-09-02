package hr.fer.zemris.java.hw06.crypto.impl;
/**
 * This class represents an implementation of conversion 
 * algorithm between byte array to hexadecimal String.
 * @author Silvana
 * @version 1.0
 *
 */
public class Util {
	
	/**
	 * This method is used for hexadecimal String to 
	 * byte Array conversion.
	 * Method supports both uppercase letters
	 * and lowercase letters.
	 * @param keyText hexadecimal string
	 * @return byte array representation
	 */
	 public static byte[] hextobyte(String keyText) {
		 if(keyText.isEmpty()) {
			 return new byte[0];
		 }
		 if (keyText.length() % 2 == 1) {
		        throw new IllegalArgumentException(
		          "Invalid hexadecimal String supplied.");
		 }
		 
		 int length = keyText.length();
		 byte[] data = new byte[length / 2];
		 for (int i = 0; i <length; i += 2) {
		     data[i / 2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4)
		                             + Character.digit(keyText.charAt(i+1), 16));
		 }
		 return data; 
		 
		 
	 }
	 /**
	  * This method returns hexadecimal representation
	  * of given byte array.
	  * The output will always be in lowercase.
	  * @param bytearray byte array
	  * @return hexadecimal representation
	  */
	 public static String bytetohex(byte[] bytearray) {
		 if(bytearray.length == 0) {
			 return "";
		 }
		 StringBuffer hexStringBuffer = new StringBuffer();
		    for (int i = 0; i < bytearray.length; i++) {
		    	char[] hexDigits = new char[2];
		        hexDigits[0] = Character.forDigit((bytearray[i] >> 4) & 0xF, 16);
		        hexDigits[1] = Character.forDigit((bytearray[i] & 0xF), 16);
		        hexStringBuffer.append(hexDigits);
		    }
		    return hexStringBuffer.toString();
		 
	 }

}
