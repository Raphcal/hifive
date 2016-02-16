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

import java.util.Iterator;

/**
 *
 * @author Raphaël Calabro (raphael.calabro@netapsys.fr)
 */
public class StringCharacterIterator implements Iterator<Character> {

	private final char[] characters;
	private int cursor;
	
	public StringCharacterIterator(final String source) {
		this.characters = source.toCharArray();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasNext() {
		return cursor < characters.length;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character next() {
		return characters[cursor++];
	}
	
}
