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

import fr.hifivelib.java.parser.StringWordIterator;
import fr.hifivelib.java.parser.StringCharacterIterator;
import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for <code>StringWordIterator</code>.
 * 
 * @author Raphaël Calabro (raphael.calabro@netapsys.fr)
 */
public class StringWordIteratorTest {
	
	/**
	 * Test of hasNext method, of class StringWordIterator.
	 */
	@Test
	public void testHasNext() {
		System.out.println("hasNext");
		StringWordIterator instance = new StringWordIterator(new Iterator<Character>() {
			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public Character next() {
				throw new UnsupportedOperationException("Not supported.");
			}
		});
		assertEquals(false, instance.hasNext());
		
		instance = new StringWordIterator(new Iterator<Character>() {
			@Override
			public boolean hasNext() {
				return true;
			}

			@Override
			public Character next() {
				throw new UnsupportedOperationException("Not supported.");
			}
		});
		assertEquals("", true, instance.hasNext());
	}

	/**
	 * Test of next method, of class StringWordIterator.
	 */
	@Test
	public void testNext() {
		System.out.println("next");
		StringWordIterator instance = new StringWordIterator(new StringCharacterIterator("final String s = \"Pas; de contenu ?\";\n"
				+ "final char c = 'a';"));
		final String[] expected = {"final", "String", "s", "=", "Pas; de contenu ?", ";", "final", "char", "c", "=", "a", ";"};
		
		for (final String entry : expected) {
			assertTrue("The iterator has no more word but it should have.", instance.hasNext());
			final String result = instance.next();
			assertEquals("Bad word value.", entry, result);
		}
		assertFalse("The iterator still has words when it should not.", instance.hasNext());
	}

	/**
	 * Test of next method, of class StringWordIterator.
	 */
	@Test
	public void testNextEmpty() {
		System.out.println("next");
		StringWordIterator instance = new StringWordIterator(new StringCharacterIterator("   "));
		final String[] expected = {""};
		
		for (final String entry : expected) {
			assertTrue("The iterator has no more word but it should have.", instance.hasNext());
			final String result = instance.next();
			assertEquals("Bad word value.", entry, result);
		}
		assertFalse("The iterator still has words when it should not.", instance.hasNext());
	}
	
}
