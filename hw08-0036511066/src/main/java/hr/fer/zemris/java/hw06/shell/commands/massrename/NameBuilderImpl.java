package hr.fer.zemris.java.hw06.shell.commands.massrename;
import java.util.List;

/**
 * This class is used to store objects of
 * a type {@link NameBuilder} in one collection.
 * <p>
 * In this class, method execute invokes {@link exectute}
 * for each stored object in this collection.
 * 
 * @author Silvana
 *
 */
public class NameBuilderImpl implements NameBuilder {
	/**
	 * List of NameBuilders.
	 */
	private List<NameBuilder> list;
	/**
	 * Sole constructor.
	 * @param list list of objects of a type {@link NameBuilder}
	 */
	public NameBuilderImpl(List<NameBuilder> list) {
		this.list = list;
	}
	
	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		for(NameBuilder l : list) {
			l.execute(result, sb);
		}
		
	}

}
