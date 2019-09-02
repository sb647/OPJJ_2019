package hr.fer.zemris.java.hw06.shell.commands.massrename;
/**
 * Objects of a type {@link NameBuilder} are used to
 * generate subsequences of new file name and to append
 * them to object of a type {@link StringBuilder}.
 * (passed object to method {@link execute}.
 * 
 * @author Silvana
 *
 */
public interface NameBuilder {
	/**
	 * Appends generated subsequence of file name to {@code sb}.
	 * @param result object of a type {@link FilterResult}, it
	 * represents some file
	 * @param sb object of a type {@link StringBuilder}
	 */
	void execute(FilterResult result, StringBuilder sb);
}
