package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

class SmartScriptParserTest {
	
	@Test
	public void testNullInput() {
		assertThrows(NullPointerException.class,()-> new SmartScriptParser(null));
	}
	
	@Test
	public void tagProcessingTest() {
		String str= "{$ 123 4 i $}";
		assertThrows(SmartScriptParserException.class,()-> new SmartScriptParser(str));	
	}
	
	@Test
	public void endTagProcessing() {
		String str="This is sample text.\r\n" + 
				"{$ FOR i 1 10 1 $}\r\n" + 
				" This is {$= i $}-th time this message is generated.\r\n" + 
				"{$END$}\r\n" + 
				"{$FOR i 0 10 2 $}\r\n" + 
				" sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n" + 
				"{$END$} {$END$}";
		assertThrows(SmartScriptParserException.class,()-> new SmartScriptParser(str));	 //3 end and 2 for
		
	}
	@Test
	public void elementsInEchoTest() {
		String str="This is sample text.\r\n" + 
				"{$ FOR i 1 2 3  10 1 $}\r\n" + 
				" This is {$= i 2 3 4 5  $}-th time this message is generated.\r\n" + 
				"{$END$}\r\n" + 
				"{$FOR i 0 10 2 $}\r\n" + 
				" sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n" + 
				"{$END$}";
		assertThrows(SmartScriptParserException.class,()-> new SmartScriptParser(str));	 //too many arguments
		
	}
	

	@Test
	void parserTest() {
		String str= "This is sample text.\r\n" + 
				"{$ FOR i 1 10 1 $}\r\n" + 
				" This is {$= i $}-th time this message is generated.\r\n" + 
				"{$END$}\r\n" + 
				"{$FOR i 0 10 2 $}\r\n" + 
				" sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n" + 
				"{$END$}";
		
		SmartScriptParser parser= new SmartScriptParser(str);
		assertEquals(parser.getDocumentNode().numberOfChildren(), 4);
		assertTrue(parser.getDocumentNode().getChild(0) instanceof TextNode);
		assertTrue(parser.getDocumentNode().getChild(1) instanceof ForLoopNode);
		assertEquals(parser.getDocumentNode().getChild(1).numberOfChildren(), 3);
		assertTrue(parser.getDocumentNode().getChild(2) instanceof TextNode);
		assertTrue(parser.getDocumentNode().getChild(3) instanceof ForLoopNode);
		assertEquals(parser.getDocumentNode().getChild(3).numberOfChildren(), 5);
	
	}

}
