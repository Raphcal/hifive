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
				"public class JavaParserTest extends File implements fr.hifivelib.other.SomeInterface, Test {\n" +
				"}");
		assertEquals("JavaParserTest", result.getName());
		assertEquals("fr.hifivelib.java", result.parent().getFullName());
		assertEquals("fr.hifivelib.java.JavaParserTest", result.getFullName());
		assertEquals(3, result.getImports().size());
		
		Iterator<String> strings = Arrays.asList("java.io.File", "org.junit.Test", "org.junit.Assert").iterator();
		for (final Class importedClass : result.getImports()) {
			assertEquals(strings.next(), importedClass.getFullName());
		}
		
		assertEquals("java.io.File", result.getSuperclass().getFullName());
		assertNotSame(Kind.ENUM, result.getSuperclass().getKind());
		assertNotSame(Kind.INTERFACE, result.getSuperclass().getKind());
		assertEquals(2, result.getInterfaces().size());
		
		strings = Arrays.asList("fr.hifivelib.other.SomeInterface", "org.junit.Test").iterator();
		for (final Class importedClass : result.getInterfaces()) {
			assertEquals(strings.next(), importedClass.getFullName());
			assertEquals(Kind.INTERFACE, importedClass.getKind());
		}
	}
	
}
