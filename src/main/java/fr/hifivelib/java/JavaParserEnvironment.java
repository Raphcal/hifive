package fr.hifivelib.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	
	private Node peek = new Package(null, "");
	
	private List<Class> imports = new ArrayList<>();
	private List<Instance<Class>> annotations = new ArrayList<>();
	
	private Class publicClass;

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

	public Class getPublicClass() {
		return publicClass;
	}

	public Node peek() {
		return peek;
	}
	
	public void push(Node node) {
		this.peek.add(node);
		node.setParent(this.peek);
		this.peek = node;
	}
	
	public Node pop() {
		final Node node = peek;
		this.peek = peek.parent();
		return node;
	}
	
	public Package topPackage() {
		Node node = peek;
		
		while (node != null) {
			if (node instanceof Package) {
				return (Package) node;
			}
			node = node.parent();
		}
		
		return null;
	}
	
	/**
	 * Adds a new import by its full name.
	 * 
	 * @param importedClass Full name of the class to import.
	 */
	public void addImport(final String importedClass) {
		if (peek instanceof Package) {
			imports.add(topPackage().getClass(importedClass));
		} else {
			throw new IllegalStateException("Only public outer classes can import classes.");
		}
	}
	
	public void addAnnotation(final Class annotation, Map<String, String> values) {
		annotations.add(new Instance<>(annotation));
	}

	public List<Class> getImports() {
		return imports;
	}
	
	public Class getClassWithRelativeName(final String name) {
		final Package parentPackage = topPackage();
		
		if (name.indexOf('.') == -1) {
			final Node samePackageClass = parentPackage.get(name);
			if (samePackageClass instanceof Class) {
				return (Class) samePackageClass;
			}

			for (final Class importedClass : imports) {
				if (name.equals(importedClass.getName())) {
					return importedClass;
				}
			}

			final Class javaLangClass = parentPackage.getClass("java.lang." + name, false);
			if (javaLangClass != null) {
				return javaLangClass;
			}
		}
		
		return parentPackage.getClass(name);
	}
	
}
