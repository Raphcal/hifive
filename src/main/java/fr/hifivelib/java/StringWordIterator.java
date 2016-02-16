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
 * Identify words in a collection of characters.
 * 
 * @author Raphaël Calabro (raphael.calabro@netapsys.fr)
 */
public class StringWordIterator implements Iterator<String> {
	
	private final Iterator<Character> source;
	private Character lastCharacter;

	public StringWordIterator(Iterator<Character> source) {
		this.source = source;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasNext() {
		return source.hasNext() || lastCharacter != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String next() {
		if (lastCharacter == null) {
			nextCharacter();
		}
		skipWhitespaces();
		
		if (lastCharacter == null) {
			return "";
		}
		
		if (lastCharacter == '"' || lastCharacter =='\'') {
			return readQuotedWords();
		} else {
			return readWord();
		}
	}

	private String readWord() {
		final StringBuilder wordBuilder = new StringBuilder();
		
		if (isEndOfWord(lastCharacter)) {
			final String result = Character.toString(lastCharacter);
			nextCharacter();
			return result;
		}
		do {
			wordBuilder.append(lastCharacter);
			nextCharacter();
		} while (lastCharacter != null && !isEndOfWord(lastCharacter));
		
		return wordBuilder.toString();
	}

	private String readQuotedWords() {
		final StringBuilder wordBuilder = new StringBuilder();
		final char quoteMark = lastCharacter;
		nextCharacter();
		while (lastCharacter != quoteMark && lastCharacter != null) {
			wordBuilder.append(lastCharacter);
			nextCharacter();
		}
		nextCharacter();
		return wordBuilder.toString();
	}

	private void skipWhitespaces() {
		while (lastCharacter != null && Character.isWhitespace(lastCharacter)) {
			nextCharacter();
		}
	}
	
	private void nextCharacter() {
		if (source.hasNext()) {
			lastCharacter = source.next();
		} else {
			lastCharacter = null;
		}
	}
	
	private boolean isEndOfWord(final char c) {
		return Character.isWhitespace(c) 
				|| c == ';'
				|| c == '('
				|| c == ')';
	}
	
}
