package fr.hifivelib.java;

import java.util.Deque;
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
 * States of the java parser. The actual parsing is done in this class.
 * 
 * @author Raphaël Calabro (ddaeke-github at yahoo.fr)
 */
public enum JavaParserState {
	
	WAITING_FOR_PACKAGE {

		@Override
		public void execute(JavaParserEnvironment environment) {
			final String word = environment.nextWord();
			
			switch (word) {
			case "package":
				environment.setState(PACKAGE_DECLARATION);
				break;
			case "import":
				environment.getSourceFile().setPackage("");
				environment.setState(IMPORT_DECLARATION);
				break;
			case "public":
			case "protected":
			case "private":
			case "abstract":
			case "interface":
			case "@interface":
			case "enum":
			case "class":
				environment.getSourceFile().setPackage("");
				environment.rewind();
				environment.getClassStack().push(new Class(environment.getSourceFile()));
				environment.setState(CLASS_START);
				break;
			default:
				if (!word.isEmpty() && word.charAt(0) == '@') {
					environment.getSourceFile().setPackage("");
					environment.setState(CLASS_ANNOTATION);
				}
				break;
			}
		}
		
	},
	PACKAGE_DECLARATION {

		@Override
		public void execute(JavaParserEnvironment environment) {
			final String word = environment.nextWord();
			
			if (";".equals(word)) {
				environment.setState(WAITING_FOR_CLASS);
			} else {
				environment.getSourceFile().setPackage(word);
			}
		}
		
	},
	WAITING_FOR_CLASS {

		@Override
		public void execute(JavaParserEnvironment environment) {
			final String word = environment.nextWord();
			
			switch (word) {
			case "package":
				throw new IllegalArgumentException("Package is not allowed here.");
			case "import":
				environment.setState(IMPORT_DECLARATION);
				break;
			case "public":
			case "protected":
			case "private":
			case "abstract":
			case "interface":
			case "@interface":
			case "enum":
			case "class":
				environment.rewind();
				environment.getClassStack().push(new Class(environment.getSourceFile()));
				environment.setState(CLASS_START);
				break;
			default:
				if (!word.isEmpty() && word.charAt(0) == '@') {
					environment.rewind();
					environment.setState(CLASS_ANNOTATION);
				}
				break;
			}
		}
		
	},
	IMPORT_DECLARATION {

		@Override
		public void execute(JavaParserEnvironment environment) {
			final String word = environment.nextWord();
			
			if (";".equals(word)) {
				environment.setState(WAITING_FOR_CLASS);
			} else {
				environment.getSourceFile().addImport(word);
			}
		}
		
	},
	CLASS_ANNOTATION {
		
		@Override
		public void execute(JavaParserEnvironment environment) {
			final String word = environment.nextWord();
			
			// TODO: Handle annotation arguments.
			if (!word.isEmpty() && word.charAt(0) == '@') {
				final Class annotationClass = environment.getSourceFile().getRelativeClass(word.substring(1));
				final Annotation annotation = Annotation.from(annotationClass);
				environment.getAnnotations().add(new Instance<>(annotation));
			}
			
			environment.setState(WAITING_FOR_CLASS);
		}
		
	},
	CLASS_START {
		
		@Override
		public void execute(JavaParserEnvironment environment) {
			final String word = environment.nextWord();
			
			switch (word) {
				case "public":
					environment.getCurrentClass().setVisibility(Visibility.PUBLIC);
					break;
				case "private":
					environment.getCurrentClass().setVisibility(Visibility.PRIVATE);
					break;
				case "protected":
					environment.getCurrentClass().setVisibility(Visibility.PROTECTED);
					break;
				case "@interface":
					environment.getCurrentClass().setKind(Kind.ANNOTATION);
					setAnnotations(environment);
					environment.setState(CLASS_NAME);
					break;
				case "interface":
				case "enum":
				case "class":
					environment.getCurrentClass().setKind(Kind.valueOf(word.toUpperCase()));
					setAnnotations(environment);
					environment.setState(CLASS_NAME);
					break;
				default:
					throw new IllegalArgumentException("'" + word + "' is not valid. Should be one of 'public', 'private', 'protected', 'interface', 'enum' or 'class'.");
			}
		}
		
		private void setAnnotations(JavaParserEnvironment environment) {
			final Set<Instance<Annotation>> annotations = environment.getAnnotations();
			environment.getCurrentClass().getAnnotations().addAll(annotations);
			annotations.clear();
		}
		
	},
	CLASS_NAME {
		
		@Override
		public void execute(JavaParserEnvironment environment) {
			final String word = environment.nextWord();
			
			switch (word) {
				case "{":
					environment.openBlock();
					environment.setState(CLASS_INNER);
					break;
				case "implements":
				case "extends":
					environment.rewind();
					environment.setState(CLASS_EXTENSION);
					break;
				default:
					environment.getCurrentClass().setName(word);
					environment.setState(CLASS_EXTENSION);
					break;
			}
		}
		
	},
	CLASS_EXTENSION {
		
		private static final int NO_MODE = 0;
		private static final int EXTENDS_MODE = 1;
		private static final int IMPLEMENTS_MODE = 2;
		
		@Override
		public void execute(JavaParserEnvironment environment) {
			String word = environment.nextWord();
			
			int mode = NO_MODE;
			
			while (!"{".equals(word)) {
				switch (word) {
					case "implements":
						mode = IMPLEMENTS_MODE;
						break;
					case "extends":
						mode = EXTENDS_MODE;
						break;
					case ",":
						break;
					default:
						final Class clazz = environment.getCurrentClass().getRelativeClass(word);
						
						if (mode == EXTENDS_MODE) {
							environment.getCurrentClass().setSuperclass(clazz);
						} else if (mode == IMPLEMENTS_MODE) {
							if (clazz.getKind() == null) {
								clazz.setKind(Kind.INTERFACE);
							} else if (clazz.getKind() != Kind.INTERFACE) {
								throw new IllegalArgumentException("A class cannot implements a " + clazz.getKind());
							}
							environment.getCurrentClass().getInterfaces().add(clazz);
						}
						break;
				}
				word = environment.nextWord();
			}
			
			if (environment.getCurrentClass().getSuperclass() == null) {
				final Class object = ((Package) environment.getCurrentClass().parent()).getClass("java.lang.Object");
				environment.getCurrentClass().setSuperclass(object);
			}
			
			environment.openBlock();
			environment.setState(CLASS_INNER);
		}
		
	},
	CLASS_INNER {
		
		@Override
		public void execute(JavaParserEnvironment environment) {
			String word = environment.nextWord();
			
			final int parentScope = environment.getBlocks() - 1;
			
			while (environment.getBlocks() > parentScope) {
				if ("{".equals(word)) {
					environment.openBlock();
				} else if ("}".equals(word)) {
					environment.closeBlock();
				}
				// TODO: Handle fields, methods and inner classes.
				word = environment.nextWord();
			}
			
			final Deque<Class> classStack = environment.getClassStack();
			
			final Class clazz = classStack.pop();
			if (classStack.isEmpty()) {
				if (clazz.getVisibility() == Visibility.PUBLIC) {
					if (environment.getSourceFile().getPublicClass() != null) {
						throw new IllegalArgumentException("A source file cannot have more than one outer public class.");
					}
					environment.getSourceFile().setPublicClass(clazz);
				} else {
					environment.getSourceFile().getOtherClasses().add(clazz);
				}
			} else {
				classStack.peek().children().add(clazz);
			}
		}
		
	},
	FIELD,
	FIELD_ANNOTATION,
	METHOD,
	LINE_END_COMMENT,
	BLOC_COMMENT,
	JAVADOC;
	
	public void execute(JavaParserEnvironment environment) {
		environment.nextWord();
	}
			
}
