package hr.fer.zemris.java.hw06.shell.commands.massrename;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
/**
 * This class represents an implementation of massrename
 * command.
 * Massrename takes 4 or 5 arguments :
 * <ul>
 * <li> DIR1 - first argument is always path to some 
 * directory. DIR1 is used as source directory and it 
 * contains files to be filtered or relocated.
 * </li>
 * <li> DIR2 - second arguments is also path to directory,
 * but this directory is used as destination directory 
 * (only when needed).
 * </li>
 * <li> subcommand - third arguments is always name of one 
 * of four supported subcommands.
 * </li>
 * <li> MASK -is a sequence of characters that define a 
 * filter pattern. 
 * </li>
 * <li> EXPRESSION - fifth argument is needed if subcommand
 * show or execute is invoked. It determinates how to
 * generate new file name.
 * </li>
 * </ul>
 * 
 * <p>
 * Massrename is complex command and it has, as already 
 * mentioned,4 subcommands:
 * <ul>
 * <li> filter - if this subcommand is invoked, massrename
 * command displays names of all files from DIR1 whose name 
 * matches given MASK.
 * </li>
 * <li> group - if this subcommand is invoked, massrename
 * command displays capturing groups of all files from DIR1 
 * whose name matches given MASK.
 * </li>
 * <li> show - if this subcommand is invoked,  massrename
 * command displays name and new generated name of all
 * files from DIR1 whose name matches given MASK.
 * </li>
 * <li> execute - if this subcommand is invoked,  massrename
 * command moves filtered files from DIR1 to DIR2 and for each
 * prints it's name and new generated name.
 * </li>
 * </ul>
 * 
 * 
 * @author Silvana
 *
 */
public class MassrenameCommand implements ShellCommand{
	/**
	 * Keyword for filter subcommand.
	 */
	private static final String FILTER= "filter";
	/**
	 * Keyword for show subcommand.
	 */
	private static final String SHOW = "show";
	/**
	 * Keyword for groups subcommand.
	 */
	private static final String GROUPS = "groups";
	/**
	 * Keyword for execute subcommand.
	 */
	private static final String EXECUTE = "execute";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		List<Object> args;
		try {
			args = Util.extractArguments(arguments);
			
			if(args.size() != 4 || args.size() != 5) {
				env.writeln("Invalid number of arguments!");
				return ShellStatus.CONTINUE;
			}
			
		}catch(IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		Path src1 = (Path)args.get(0);
		Path src2 = (Path)args.get(1);
		
		if(! Files.isDirectory(src1) || ! Files.isDirectory(src2)) {
			env.writeln("Massrename works only with directories!");
			return ShellStatus.CONTINUE;
		}
		
		List<FilterResult> output;
		try {
			output = filter(src1, args.get(3).toString());
		}catch(IOException ex) {
			env.writeln("An error occurred while streaming!");
			return ShellStatus.CONTINUE;
		}
		switch(args.get(2).toString()) {
		
			case FILTER:
				
				for(FilterResult o : output) {
					env.writeln(o.toString());
				}
			
			case GROUPS:
				
				for(FilterResult o : output) {
					env.write(o.toString());
					for(int i = 0; i < o.numberOfGroups(); i++) {
						env.write(" " +i+ ": " +o.group(i));
					}
					env.writeln("");
				}
				
			case SHOW:
				
				NameBuilderParser parser = new NameBuilderParser(args.get(4).toString());
				NameBuilder nb = parser.getNameBuilder();
				try {
					for(FilterResult o : output) {
						env.write(o.toString());
						env.write(" => ");
						StringBuilder build = new StringBuilder();
						nb.execute(o,build);
						env.write(build.toString());
						env.write(System.lineSeparator());
					}
					
				} catch (IllegalArgumentException e) {
					env.writeln(e.getMessage());
					return ShellStatus.CONTINUE;
				}
				
			case EXECUTE:
				
				parser = new NameBuilderParser(args.get(4).toString());
				nb = parser.getNameBuilder();
				for(FilterResult o : output) {
					StringBuilder build = new StringBuilder();
					nb.execute(o,build);
					try {
						Files.move(o.getFile() , src2.resolve(build.toString()), StandardCopyOption.REPLACE_EXISTING);
						env.write(src1.getFileName().toString() + "/");
						env.write(o.toString());
						env.write(" => ");
						env.write(src2.getFileName().toString() + "/");
						env.write(build.toString());
						env.writeln("");
						
					} catch (IOException | IllegalArgumentException e) {
						env.writeln("An error occured while transfering files!");
						return ShellStatus.CONTINUE;
					}	
				}
						
		}
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * This method creates and returns list of objects of a type 
	 * {@code FilterResult}. Each list object represents one file from 
	 * source directory.
	 * 
	 * @param dir source directory
	 * @param pat given pattern
	 * @return list of {@code FilterResult} objects
	 * @throws IOException if file streaming fails
	 */
	private static List<FilterResult> filter(Path dir, String pat) throws IOException{
		List<FilterResult> result = new ArrayList<>();
		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir);
		Pattern pattern = Pattern.compile(pat);
		for(Path p : directoryStream) {
			if(Files.isDirectory(p)) {
				continue;
			}
			FilterResult fr = new FilterResult(p, pattern);
			if(fr.numberOfGroups() > 0) {
				result.add(fr);
			}
		}
		return result;
	}

	@Override
	public String getCommandName() {
		
		return "massrename";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Massrename takes 4 or 5 arguments : DIR1, DIR2, subcommand, MASK, EXPRESSION.");
		description.add("Supported subcommands are: filter, groups, show and execute.");
		description.add("Filter displays names of all files from DIR1 whose name matches given MASK.");
		description.add("Groups displays capturing groups of all files from DIR1 whose name matches given MASK.");
		description.add("Show displays name and new generated name of all files from DIR1 whose name matches given MASK.");
		description.add("Execute moves filtered files from DIR1 to DIR2 and for each prints it's name and new generated name.");
		
		return description;
	}

}
