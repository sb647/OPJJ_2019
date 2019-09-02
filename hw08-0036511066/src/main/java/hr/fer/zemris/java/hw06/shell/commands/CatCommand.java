package hr.fer.zemris.java.hw06.shell.commands;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UnknownFormatConversionException;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.PathParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * This is an implementation of command cat. Cat takes 
 * one or two arguments. The first argument is path 
 * to some file and is mandatory. The second argument 
 * is charset name that should be used to interpret 
 * chars from bytes. If not provided, a default
 * platform charset is used.
 * @author Silvana
 *
 */
public class CatCommand implements ShellCommand{
	/**
	 * Size of buffer array.
	 */
	private static final int BUFFER_SIZE = 4096;
	/**
	 * This opens given file and writes its content to console.
	 * If file is invalid,method displays appropriate message.
	 * @throws IOException if reading fails
	 * @throws UnknownFormatConversionException if conversion 
	 * of symbols fails
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Path path;		
		PathParser parser = new PathParser();
		try {
			parser.parse(arguments);
			path = env.getCurrentDirectory().resolve(parser.getPath());
		}catch(IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
			return ShellStatus.CONTINUE;
		}
		Charset charset = Charset.defaultCharset();
		int index = parser.getPath().length() + parser.getSkippedSymbols();
		if( index  < arguments.length()) {   
			String str = arguments.substring(index).trim();
			try {
				charset = Charset.forName(str);
			}
			catch (IllegalArgumentException ex ) {
				env.writeln("Invalid charset!");
				return ShellStatus.CONTINUE;
				
			}	
		}
		
		try (InputStream is = Files.newInputStream(path, StandardOpenOption.READ)){
			byte[] buffer = new byte[BUFFER_SIZE];
			int readBytes;
			while((readBytes = is.read(buffer)) != -1) {
				byte[] bytes = Arrays.copyOfRange(buffer, 0, readBytes);
				String reconstitutedString = new String(bytes,charset);
				env.write(reconstitutedString);
			}
			env.writeln("");
			return ShellStatus.CONTINUE;
			
		}catch(IOException ex) {
			env.writeln("Can't read from that file!");
			return ShellStatus.CONTINUE;
			
		}catch(UnknownFormatConversionException er) {
			env.writeln("Conversion is not possible!");
			return ShellStatus.CONTINUE;
		}
		
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> instructions = new ArrayList<>();
		instructions.add("Command cat takes one or two arguments.");
		instructions.add("The first argument is path to some file and is mandatory. ");
		instructions.add("The second argument is charset name.");
		instructions.add("Command opens given file and writes its content to console");
		
		return Collections.unmodifiableList(instructions);
	}
	
}
