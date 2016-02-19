package fr.hifivelib.java;

/*
 * #%L
 * Hifive
 * %%
 * Copyright (C) 2016 Raphaël Calabro
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

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
				"import fr.hifivelib.annotation.MyInterface;\n" +
				"\n" +
				"@Test\n" +
				"public class JavaParserTest extends Boolean implements fr.hifivelib.other.SomeInterface, MyInterface {\n" +
				"}");
		assertEquals("JavaParserTest", result.getName());
		assertEquals("fr.hifivelib.java", result.parent().getFullName());
		assertEquals("fr.hifivelib.java.JavaParserTest", result.getFullName());
		assertEquals(4, result.getImports().size());
		
		Iterator<String> strings = Arrays.asList("java.io.File", "org.junit.Test", "org.junit.Assert", "fr.hifivelib.annotation.MyInterface").iterator();
		for (final Class importedClass : result.getImports()) {
			assertEquals(strings.next(), importedClass.getFullName());
		}
		
		assertEquals(1, result.getAnnotations().size());
		assertEquals("org.junit.Test", result.getAnnotations().iterator().next().getFullName());
		
		assertEquals("java.lang.Boolean", result.getSuperclass().getFullName());
		assertNotSame(Kind.ENUM, result.getSuperclass().getKind());
		assertNotSame(Kind.INTERFACE, result.getSuperclass().getKind());
		assertEquals(2, result.getInterfaces().size());
		
		strings = Arrays.asList("fr.hifivelib.other.SomeInterface", "fr.hifivelib.annotation.MyInterface").iterator();
		for (final Class importedClass : result.getInterfaces()) {
			assertEquals(strings.next(), importedClass.getFullName());
			assertEquals(Kind.INTERFACE, importedClass.getKind());
		}
	}
	
}
