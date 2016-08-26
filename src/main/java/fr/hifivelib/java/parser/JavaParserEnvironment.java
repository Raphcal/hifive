package fr.hifivelib.java.parser;

import fr.hifivelib.java.Annotation;
import fr.hifivelib.java.Class;
import fr.hifivelib.java.Instance;
import fr.hifivelib.java.SourceFile;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
	private JavaParserState state = JavaParserState.WAITING_FOR_PACKAGE;
	
	private Iterator<String> wordIterator;
	private String lastWord;
	
	private final SourceFile sourceFile = new SourceFile();
	private final Deque<Class> classStack = new ArrayDeque<>();
	
	private final Map<Class, Integer> scopes = new HashMap<>();
	
	private final Set<Instance<Annotation>> annotations = new LinkedHashSet<>();
	
	private int blocks;

	public JavaParserEnvironment(final Iterator<String> wordIterator) {
		this.wordIterator = wordIterator;
	}

	public boolean hasNext() {
		return wordIterator.hasNext();
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
	
	public void rewind() {
		final Iterator<String> originalIterator = wordIterator;
		
		this.wordIterator = new Iterator<String>() {
			
			@Override
			public boolean hasNext() {
				return true;
			}

			@Override
			public String next() {
				wordIterator = originalIterator;
				return lastWord;
			}
		};
	}
	
	public JavaParserState getState() {
		return state;
	}

	public void setState(JavaParserState state) {
		this.state = state;
	}

	public SourceFile getSourceFile() {
		return sourceFile;
	}

	public Deque<Class> getClassStack() {
		return classStack;
	}
	
	public Class getCurrentClass() {
		return classStack.peek();
	}

	public Set<Instance<Annotation>> getAnnotations() {
		return annotations;
	}
	
	public Set<Instance<Annotation>> popAnnotations() {
		final Set<Instance<Annotation>> value = new LinkedHashSet<>(annotations);
		annotations.clear();
		return value;
	}

	public void openBlock() {
		blocks++;
	}
	
	public void closeBlock() {
		blocks--;
	}

	public int getBlocks() {
		return blocks;
	}

	public Map<Class, Integer> getScopes() {
		return scopes;
	}

}
