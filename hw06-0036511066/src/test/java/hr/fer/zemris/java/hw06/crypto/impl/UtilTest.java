package hr.fer.zemris.java.hw06.crypto.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilTest {
	
		@Test
		void hexToByteTest() {
			String str = "01aE22";
			byte[] bytes = Util.hextobyte(str);
			assertTrue(bytes.length == 3);
			assertEquals(1 , bytes[0]);
			assertEquals(-82 , bytes[1]);
			assertEquals(34 , bytes[2]);
		}
		
		@Test
		void byteToHexTest() {
			byte[] bytes = {1, -82, 34};
			String result = Util.bytetohex(bytes);
			assertEquals("01ae22", result);
		}
		
		@Test
		void testEmptyString() {
			String str = "";
			byte[] bytes = Util.hextobyte(str);
			assertTrue(bytes.length == 0);
		}
		
		@Test
		void testEmptyArray() {
			byte[] bytes = new byte[0];
			String result = Util.bytetohex(bytes);
			assertEquals("", result);
		}


}
