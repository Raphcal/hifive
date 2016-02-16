package fr.hifivelib.java;

import java.util.ArrayDeque;
import java.util.Iterator;

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

/**
 * Environment of the java parser.
 * 
 * @author Raphaël Calabro (ddaeke-github at yahoo.fr)
 */
public class JavaParserEnvironment {
	
	/**
	 * Current parser state.
	 */
	private JavaParserState state = JavaParserState.WAITING_FOR_CLASS;
	
	private final Iterator<String> wordIterator;
	private String lastWord;
	
	private Class publicClass = new Class();

	public JavaParserEnvironment(final Iterator<String> wordIterator) {
		this.wordIterator = wordIterator;
	}

	public boolean hasNext() {
		return wordIterator.hasNext() || lastWord != null;
	}
	
	public String nextWord() {
		if (wordIterator.hasNext()) {
			lastWord = wordIterator.next();
		} else {
			lastWord = null;
		}
		return lastWord;
	}
	
	public String lastWord() {
		return lastWord;
	}
	
	public JavaParserState getState() {
		return state;
	}

	public void setState(JavaParserState state) {
		this.state = state;
	}

	public Class getPublicClass() {
		return publicClass;
	}
	
}
