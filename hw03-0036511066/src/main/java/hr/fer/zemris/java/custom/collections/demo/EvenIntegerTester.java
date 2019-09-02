package hr.fer.zemris.java.custom.collections.demo;
import hr.fer.zemris.java.custom.collections.Tester;
/**
 * This is test program.
 * @author Silvana Bakula
 *
 */
public class EvenIntegerTester implements Tester {
	
	 public boolean test(Object obj) {
		 if(!(obj instanceof Integer)) return false;
		 Integer i = (Integer)obj;
		 return i % 2 == 0;
		 }
	 /**
	  * Program starts here.
	  */
	 public static void main(String[] args) {
		 Tester t = new EvenIntegerTester();
		 System.out.println(t.test("Ivo"));
		 System.out.println(t.test(22));
		 System.out.println(t.test(3));

	}
}
