package fr.hifivelib.java;

import java.util.Arrays;
import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for <code>JavaParser</code>.
 * 
 * @author Raphaël Calabro (raphael.calabro@netapsys.fr)
 */
public class JavaParserTest {
	
	/**
	 * Test of parseSourceFromFile method, of class JavaParser.
	 */
	@Test
	public void testParseSourceFromFile() {
		System.out.println("parseSourceFromFile");
		// TODO: Write the test.
	}

	/**
	 * Test of parseSourceFromString method, of class JavaParser.
	 */
	@Test
	public void testParseSourceFromString() {
		System.out.println("parseSourceFromString");
		
		JavaParser instance = new JavaParser();
		Class result = instance.parseSourceFromString(
				"package fr.hifivelib.java;\n" +
				"\n" +
				"import java.io.File;\n" +
				"import org.junit.Test;\n" +
				"import org.junit.Assert;\n" +
				"\n" +
				"public class JavaParserTest {");
		assertEquals("fr.hifivelib.java", result.parent().getFullName());
		assertEquals(3, result.getImports().size());
		
		final Iterator<String> strings = Arrays.asList("java.io.File", "org.junit.Test", "org.junit.Assert").iterator();
		for (final Class importedClass : result.getImports()) {
			assertEquals(strings.next(), importedClass.getFullName());
		}
	}
	
}
