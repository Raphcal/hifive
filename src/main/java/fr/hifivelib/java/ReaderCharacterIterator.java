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

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

/**
 * Iterator for the characters of a <code>Reader</code>.
 * 
 * @author Raphaël Calabro (raphael.calabro@netapsys.fr)
 */
public class ReaderCharacterIterator implements Iterator<Character> {
	
	private final Reader reader;
	private int nextCharacter;

	public ReaderCharacterIterator(Reader reader) {
		this.reader = reader;
		readNextCharacter();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasNext() {
		return nextCharacter != -1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character next() {
		if (nextCharacter != -1) {
			final char character = (char) nextCharacter;
			readNextCharacter();
			return character;
		} else {
			return null;
		}
	}
	
	private void readNextCharacter() {
		try {
			this.nextCharacter = reader.read();
		} catch (IOException ex) {
			this.nextCharacter = -1;
			ex.printStackTrace();
		}
	}
	
}
