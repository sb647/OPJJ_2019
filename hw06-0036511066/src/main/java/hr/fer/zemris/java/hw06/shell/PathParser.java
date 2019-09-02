package hr.fer.zemris.java.hw06.shell;
/**
 * This class is used to extract path from user's input.
 * @author Silvana
 *
 */
public class PathParser {
	/**
	 * It is used to count skipped symbols.
	 */
	private int skippedSymbols;
	/**
	 * Extracted path.
	 */
	private String path;
	/**
	 * Number of skipped symbols getter.
	 * @return skippedSymbols
	 */
	public int getSkippedSymbols() {
		return skippedSymbols;
	}
    /**
     * Extracted path getter.
     * @return path
     */
	public String getPath() {
		return path;
	}

    /**
     * Extracts and sets path from user's input.
     * @throws IllegalArgumentException if there is 
     * any invalid escaping or if string is never closed
     * @param user's input
     */
	public void parse(String argument) {
		if(! argument.startsWith("\"")) {
			String[] parts = argument.split(" ");
			path = parts[0];
			return;
		}
		else {
			StringBuilder sb = new StringBuilder();
			char[] chars = argument.toCharArray();
			int i = 1;
			while(true ) {
				if(i < chars.length && chars[i] == '\"') {
					break;
				}
				if(i ==chars.length) {
					throw new IllegalArgumentException("The string literal is never closed!");
				}
				
				if(chars[i] == '\\' && i+1 < chars.length ) {
					switch(chars[i+=1]) {
						case '\\':
							sb.append('\\');
							skippedSymbols++;
							break;
						case '"':
							sb.append('"');
							skippedSymbols++;
							i++;
							while(i+1 < chars.length && chars[i] != '\\' && chars[i+1]!='"') {
								sb.append(chars[i++]);
							}
						    if(i+1 < chars.length) {
						    	skippedSymbols++;
						    	sb.append('"');
						    	break;
						    }
							
						default:
							throw new IllegalArgumentException("Invalid escaping!");
							
				}
					i++;
					continue;
					
				}
				sb.append(chars[i]);
				i++;	
			}
			skippedSymbols+=2;     // pair of "
			path = sb.toString();
		}
	
	}
	

}
