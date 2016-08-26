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

import fr.hifivelib.java.parser.JavaParser;
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
	public void testParseSourceFromStringPackage() {
		System.out.println("parseFromStringPackage");
		
		final JavaParser instance = new JavaParser();
		final SourceFile sourceFile = instance.parseSourceFromString("package fr.hifivelib.java;");
		
		assertEquals("fr.hifivelib.java", sourceFile.getPackage().getFullName());
		assertNull(sourceFile.getPublicClass());
	}
	
	/**
	 * Test of parseSourceFromString method, of class JavaParser.
	 */
	@Test
	public void testParseSourceFromStringImports() {
		System.out.println("parseFromStringImports");
		
		final JavaParser instance = new JavaParser();
		final SourceFile sourceFile = instance.parseSourceFromString("import org.junit.Test;import org.junit.Assert;");
		
		assertEquals("", sourceFile.getPackage().getFullName());
		
		assertEquals(2, sourceFile.getImports().size());
		final Iterator<String> strings = Arrays.asList("org.junit.Test", "org.junit.Assert").iterator();
		for (final Class importedClass : sourceFile.getImports()) {
			assertEquals(strings.next(), importedClass.getFullName());
		}
		
		assertNull(sourceFile.getPublicClass());
	}
	
	/**
	 * Test of parseSourceFromString method, of class JavaParser.
	 */
	@Test
	public void testParseSourceFromString() {
		System.out.println("parseSourceFromString");
		
		final JavaParser instance = new JavaParser();
		final SourceFile sourceFile = instance.parseSourceFromString(
				"package fr.hifivelib.java;\n" +
				"\n" +
				"import java.io.File;\n" +
				"import org.junit.Test;\n" +
				"import org.junit.Assert;\n" +
				"import fr.hifivelib.annotation.MyInterface;\n" +
				"\n" +
				"@Test\n" +
				"public class JavaParserTest extends Boolean implements fr.hifivelib.other.SomeInterface, MyInterface {\n" +
				"	private String name;\n" +
				"\n" +
				"	@Test\n" +
				"	public void testParseSourceFromString() {\n" +
				"		System.out.println(\"parseSourceFromString\");\n" +
				"		for (final Class importedClass : sourceFile.getImports()) {\n" +
				"			assertEquals(strings.next(), importedClass.getFullName());\n" +
				"		}\n" +
				"	}\n" +
				"}");
		final Class publicClass = sourceFile.getPublicClass();
		assertEquals("JavaParserTest", publicClass.getName());
		assertEquals("fr.hifivelib.java", publicClass.parent().getFullName());
		assertEquals("fr.hifivelib.java.JavaParserTest", publicClass.getFullName());
		
		assertEquals(4, sourceFile.getImports().size());
		Iterator<String> strings = Arrays.asList("java.io.File", "org.junit.Test", "org.junit.Assert", "fr.hifivelib.annotation.MyInterface").iterator();
		for (final Class importedClass : sourceFile.getImports()) {
			assertEquals(strings.next(), importedClass.getFullName());
		}
		
		assertEquals(1, publicClass.getAnnotations().size());
		assertEquals("org.junit.Test", publicClass.getAnnotations().iterator().next().getFullName());
		
		assertEquals("java.lang.Boolean", publicClass.getSuperclass().getFullName());
		assertNotSame(Kind.ENUM, publicClass.getSuperclass().getKind());
		assertNotSame(Kind.INTERFACE, publicClass.getSuperclass().getKind());
		assertEquals(2, publicClass.getInterfaces().size());
		
		strings = Arrays.asList("fr.hifivelib.other.SomeInterface", "fr.hifivelib.annotation.MyInterface").iterator();
		for (final Class importedClass : publicClass.getInterfaces()) {
			assertEquals(strings.next(), importedClass.getFullName());
			assertEquals(Kind.INTERFACE, importedClass.getKind());
		}
	}
	
}
