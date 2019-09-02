package hr.fer.zemris.java.custom.scripting.parser;

import java.util.Objects;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.hw03.prob1.LexerException;
/**
 * This class represents an implementation
 *  of a parser.
 * @author Silvana Bakula
 *
 */

public class SmartScriptParser {
	/**
	 * instace of lexer
	 */
	private Lexer lexer;
	/**
	 * It is used for implementation of
	 * a document tree.
	 */
	private ObjectStack stack;
	/**
	 * document tree
	 */
	private DocumentNode documentNode;
	/**
	 *  This is constructor which accepts a 
	 *  string that contains document body.
	 *  It creates an instance of lexer and 
	 *  initialize it with obtained text.
	 * @param text string witch document body
	 */
	
	public SmartScriptParser(String text) {
		documentNode= new DocumentNode();
		lexer= new Lexer(Objects.requireNonNull(text));
		stack=new ObjectStack();
		parse();
	}
	
	/**
	 * First token shoud be of a type
	 * text or tag_open.
	 * If token is of a type tag_open,
	 * this method invokes {@tagProcessing} method.
	 */
	public void parse() {
		this.stack.push(this.documentNode);
			try {
		        
				while(lexer.nextToken().getType()!= TokenType.EOF) {
				switch(lexer.getToken().getType()) {
					case TEXT: 
						TextNode text= new TextNode(lexer.getToken().getValue().toString());
						Node node=(Node)stack.peek();
						node.addChildNode(text);
						break;
					case TAG_OPEN:
						lexer.changeState(LexerState.TAG);
						tagProcessing();
						break;
					default:
						throw new SmartScriptParserException();
						
				  }

			   }
				if(stack.size() != 1) {
					throw new SmartScriptParserException();
				}
			}
			catch(LexerException ex) {
				throw new SmartScriptParserException();
			}
		
	}
	/**
	 * This method invokes {@link forTagProcessing()}
	 * or {@link equalsTagProcessing()} 
	 * or {@link endTagProcessing()},depending on 
	 * token type.
	 * @throws SmartScriptParserException if token is of another type
	 */
	public void tagProcessing() {
		
		Token token=lexer.nextToken();
			switch(token.getValue().toString().toUpperCase()) {
				case "FOR":
					forTagProcessing();
					break;
				case "=":
					equalsTagProcessing();
					break;
				case "END":
					endTagProcessing();
					break;
				default:
					throw new SmartScriptParserException();
					
			}	
	}
	
	/**
	 * Creates {@link ForLoopNode}, adds it as a child of Node 
	 * that was last pushed on the stack and than push this FORnode to the stack.
	 * @throws SmartScriptParserException for invalid number of for loop arguments
	 */
	public void forTagProcessing() {
		try {
			Token token=lexer.nextToken();
			if(token.getType() != TokenType.VARIABLE) {
				throw new SmartScriptParserException();
			}
			ElementVariable variable= new ElementVariable(token.getValue().toString());
			ArrayIndexedCollection elements= new ArrayIndexedCollection();
			while(lexer.nextToken().getType()!=TokenType.TAG_CLOSE) {
				TokenType type=lexer.getToken().getType();
				switch(type) {
					case STRING:
						ElementString string= new ElementString(lexer.getToken().getValue().toString());
						elements.add(string);
						break;
					case DOUBLE:
						ElementConstantDouble constantDouble= new ElementConstantDouble((Double)lexer.getToken().getValue());
						elements.add(constantDouble);
						break;
					case INTEGER:
						ElementConstantInteger constantInteger= new ElementConstantInteger((Integer)lexer.getToken().getValue());
						elements.add(constantInteger);
						break;
					default:
						throw new SmartScriptParserException();
		
				}	
			}
			if(elements.size() == 3 || elements.size()== 2) {
				ForLoopNode forLoop= new ForLoopNode(variable, (Element)elements.get(0),(Element)elements.get(1), elements.size() == 2? null : (Element)elements.get(2));
				Node node=(Node)stack.peek();
				node.addChildNode(forLoop);
				stack.push(forLoop);	
			lexer.changeState(LexerState.TEXT);
				return;
			}
			
			else {
				throw new SmartScriptParserException();
			}	
		}
		
		catch (LexerException ex) {
			throw new SmartScriptParserException();
		}	
	}
	/**
	 * Creates {@link EchoNode} and adds it as a child 
	 * of Node that was last pushed on the stack
	 * @throws SmartScriptParserException for invalid arguments
	 */
	public void equalsTagProcessing() {
		ArrayIndexedCollection elements=new ArrayIndexedCollection();
		while(lexer.nextToken().getType() != TokenType.TAG_CLOSE) {
			TokenType type=lexer.getToken().getType();
			
			switch(type) {
				case VARIABLE:
					ElementVariable variable= new ElementVariable(lexer.getToken().getValue().toString());
					elements.add(variable);
					break;
					
				case INTEGER:
					ElementConstantInteger constantInteger= new ElementConstantInteger((Integer)lexer.getToken().getValue());
					elements.add(constantInteger);
					break;
				
				case DOUBLE:
					ElementConstantDouble constantDouble= new ElementConstantDouble((Double)lexer.getToken().getValue());
					elements.add(constantDouble);
					break;
					
				case FUNCTION:
					ElementFunction function= new ElementFunction(lexer.getToken().getValue().toString());
					elements.add(function);
					break;
					
				case SYMBOL:
					if(!checkIfOperator((Character)lexer.getToken().getValue())) {
						throw new SmartScriptParserException();	
					}
					else {
						ElementOperator operator= new ElementOperator(lexer.getToken().getValue().toString());
						elements.add(operator);
						break;
					}
					
				case STRING:
					ElementString string= new ElementString(lexer.getToken().getValue().toString());
					elements.add(string);
					break;
					
				default:
					throw new SmartScriptParserException();
					
			}
		}
		Element[] echoElements=new Element[elements.size()];
		for(int i=0; i< elements.size(); i++) {
			echoElements[i]= (Element) elements.get(i);
		}
		EchoNode echoNode= new EchoNode(echoElements);
		Node node=(Node)stack.peek();
		node.addChildNode(echoNode);
		lexer.changeState(LexerState.TEXT);
	}
	/**
	 * When end tag appears, pop one entry from the stack. 
	 * If stack remains empty, there is error
	 *  in document – it contains more {$END$}-s than opened non-empty tags
	 *  @throws SmartScriptParserException
	 */
	public void endTagProcessing() {
		Token token= lexer.nextToken();
		if(token.getType() != TokenType.TAG_CLOSE) {
			throw new SmartScriptParserException();
		}
		lexer.changeState(LexerState.TEXT);
		try {
			Node node=(Node)stack.peek();
			if(node instanceof ForLoopNode) {
				stack.pop();
				return;
				
			}
			else {
				throw new SmartScriptParserException();
			}
		}
		catch(EmptyStackException | LexerException ex) {
			throw new SmartScriptParserException();
		}
	}
	
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
	/**
	 * @param c character 
	 * @return <code>true</code> if c is operator,otherwise <code>false</code>
	 */
	public static boolean checkIfOperator(char c) {
		if(c=='+' || c=='-' || c=='*' || c=='/' || c=='^') {
			return true;
		}
		return false;
	}
	

}
