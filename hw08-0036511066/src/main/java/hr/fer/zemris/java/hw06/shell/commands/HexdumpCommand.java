package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.PathParser;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * This class represents an implementation of 
 * hexdump command.The hexdump command expects 
 * a single argument: file name, and produces hex-output
 * of that file.
 * @author Silvana
 *
 */
public class HexdumpCommand implements ShellCommand{
	/**
	 * Size of buffer array.
	 */
	private static final int BUFFER_SIZE = 16;
	/**
	 * Position to place '|' symbol.
	 */
	private static final int BORDER_POSITION = 7;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		PathParser parser = new PathParser();
		Path file;
		try {
			parser.parse(arguments);
			if(parser.getPath().length() + parser.getSkippedSymbols() != arguments.trim().length()) {
				env.writeln("Hexdump command takes only one argument!");
				return ShellStatus.CONTINUE;
			}
			file = env.getCurrentDirectory().resolve(parser.getPath());
		} catch (IllegalArgumentException ex) {
			env.writeln("Invalid path!");
			return ShellStatus.CONTINUE;
		}
		 
		try (InputStream is = Files.newInputStream(file, StandardOpenOption.READ)){
			byte[] buffer = new byte[BUFFER_SIZE];
			int readBytes;
			int counter = 0;
			while((readBytes = is.read(buffer)) != -1) {
				StringBuilder sb = new StringBuilder();
				sb.append(String.format("%08X",counter));
				sb.append(": ");
				int i ;
				for(i = 0; i < readBytes ; i++) {
					sb.append(String.format("%02X",buffer[i]));
					sb.append((i != BORDER_POSITION) ? " " : "|");
				}
				while( i < BUFFER_SIZE) {
					sb.append((i != BORDER_POSITION ) ? "   " : "  |");
					i++;
				}
				sb.append("| ");
				
				for(i = 0; i <readBytes ; i++) {
					sb.append((buffer[i] < 32 || buffer[i] > 127) ? '.' : new String(new byte[] { buffer[i] }));
				}
				env.writeln(sb.toString());
				counter += BUFFER_SIZE;
			}
			
			return ShellStatus.CONTINUE;
			
			
		}catch(IOException ex) {
			env.writeln("Cannot read from that file!");
			return ShellStatus.CONTINUE;
		}
	}
	@Override
	public String getCommandName() {
		
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> instructions = new ArrayList<>();
		instructions.add("The hexdump command takes a single argument.");
		instructions.add("That argument represents file name. ");
		instructions.add("Command produces hex-output of that file.");
		
		return Collections.unmodifiableList(instructions);
	}

}
