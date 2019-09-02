package hr.fer.zemris.java.hw06.shell;

/**
 * This program allows user to use 
 * implemented commands in particular environment.
 * @author Silvana
 *
 */
public class MyShell {
	/**
	 * Program starts here and terminates when user
	 * enters "kraj" or if some method throws
	 * {@link ShellIOException}.
	 * @param args command line arguments. args is empty.
	 */
	public static void main(String[] args) {
		Environment env = new EnvironmentImpl();
		System.out.println("Welcome to MyShell v 1.0");
		ShellStatus status = ShellStatus.CONTINUE;
		do {
	        try {
	        	System.out.print(env.getPromptSymbol() + " ");
				String l = env.readLine();
				String[] parts = l.split(" ",2);
				String commandName = parts[0].trim();
				String arguments = parts.length == 1 ? "" : parts[1].trim();
				ShellCommand command = env.commands().get(commandName);
				if(command == null) {
					System.out.println("Command is not recognized!");
					continue;
				}
				status = command.executeCommand(env, arguments);	
				
	        }catch(ShellIOException ex) {
	        	status = ShellStatus.TERMINATE;
	        }
	        
		}while (status != ShellStatus.TERMINATE);
	}

	
}
