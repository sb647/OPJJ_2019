package hr.fer.zemris.java.hw03;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
/**
 * Test class for {@link SmartScriptTester}
 * and {@link Lexer}.
 * @author Silvana Bakula
 *
 */
public class SmartScriptTester {
	
	/**
	 * Program starts here.
	 * Main method prints reparsed file content.
	 * @param args Path of "doc1.txt" file
	 */
	public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Illegal Path!");
            System.exit(1);
        }
        String docBody = new String();
        try {
            docBody = new String(
                    Files.readAllBytes(Paths.get(args[0])),
                    StandardCharsets.UTF_8
            );
        } catch (IOException ex) {
            System.out.println("Illegal Path!");
            System.exit(1);
        }

        SmartScriptParser parser = null;
        try {
            parser = new SmartScriptParser(docBody);
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.exit(1);
        }
         catch (Exception e) {
            System.out.println("If this line ever executes, you have failed " +
                    "this class!");
            System.exit(1);
        }
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = createOriginalDocumentBody(document);
        System.out.println(originalDocumentBody);
        SmartScriptParser parser2= new SmartScriptParser(originalDocumentBody);
        DocumentNode document2=parser2.getDocumentNode();
        String original= createOriginalDocumentBody(document2);
        System.out.println("Parse again: ");
        System.out.println(original);

    }
	/**
	 * This method is used to traverse document tree
	 * and add all nodes
	 * @param document represents the document node to be reparsed
	 * @return Reparsed document
	 */
	
	public static String createOriginalDocumentBody(Node document) {
		StringBuilder sb=new StringBuilder();
		for(int i=0; i< document.numberOfChildren(); i++) {
			Node node=document.getChild(i);
			if(node instanceof TextNode) {
				String string=textReparse((TextNode)node);
				sb.append(string);
				continue;
			}
			if(node instanceof ForLoopNode) {
				sb.append("{$FOR ");
				sb.append(node);
				sb.append(" $}");
				sb.append(createOriginalDocumentBody(node));
				sb.append("{$END$}");
				continue;
			}
			sb.append("{$= ");
			sb.append(node);
			sb.append(" $}");
			
		}
		return sb.toString();	
	}

	/**
	 * If text node contains '\' or '{',that's
	 * because of escaping,so this method adds '\'
	 * in front of any occurance of '\' or '{'.
	 * @param node Text Node to be reparsed
	 * @return reparsed node
	 */
	public static String textReparse(TextNode node) {
		StringBuilder sb= new StringBuilder();
		char[] array=node.toString().toCharArray();
		for(int i=0; i< array.length; i++) {
			switch(array[i]) {
				case '\\':
					sb.append('\\');
					sb.append('\\');
					break;
				case '{':
					sb.append('\\');
					sb.append('{');
					break;
				default:
					sb.append(array[i]);
			}
		}
		return sb.toString();
	}

	
//	public static String tagStringReparse(Element elem) {
//		StringBuilder sb=new StringBuilder();
//		char[] array= elem.toString().toCharArray();
//		for(int i=0; i< array.length; i++) {
//			switch(array[i]) {
//				case '\\':
//					sb.append('\\');
//					sb.append('\\');
//					break;
//				case '\"':
//					sb.append('\\');
//					sb.append('\"');
//					break;
//				case '\n':
//					sb.append('\\');
//					sb.append('n');
//					break;
//				case '\r':
//					sb.append('\\');
//					sb.append('r');
//					break;
//				case '\t':
//					sb.append('\\');
//					sb.append('t');
//					break;
//				default :
//					sb.append(array[i]);
//					
//			}
//		}
//		
//		return sb.toString();
//	}
	
}
