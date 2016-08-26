package fr.hifivelib.java.parser;

import fr.hifivelib.java.Annotation;
import fr.hifivelib.java.Class;
import fr.hifivelib.java.Field;
import fr.hifivelib.java.Instance;
import fr.hifivelib.java.Kind;
import fr.hifivelib.java.Package;
import fr.hifivelib.java.Visibility;
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
					environment.getCurrentClass().getAnnotations().addAll(environment.popAnnotations());
					environment.setState(CLASS_NAME);
					break;
				case "interface":
				case "enum":
				case "class":
					environment.getCurrentClass().setKind(Kind.valueOf(word.toUpperCase()));
					environment.getCurrentClass().getAnnotations().addAll(environment.popAnnotations());
					environment.setState(CLASS_NAME);
					break;
				default:
					throw new IllegalArgumentException("'" + word + "' is not valid. Should be one of 'public', 'private', 'protected', 'interface', 'enum' or 'class'.");
			}
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
			final Class currentClass = environment.getCurrentClass();
			Integer scope = environment.getScopes().get(currentClass);
			if (scope == null) {
				scope = environment.getBlocks();
				environment.getScopes().put(currentClass, scope);
			}
			
			final int parentScope = scope - 1;
			
			while (environment.hasNext() && environment.getBlocks() > parentScope) {
				final String word = environment.nextWord();
				
				// TODO: Handle fields, methods and inner classes.
				switch (word) {
					case "{":
						environment.openBlock();
						break;
						
					case "}":
						environment.closeBlock();
						break;
						
					case ";":
						// Ignored.
						break;
						
					default:
						if (environment.getBlocks() == scope) {
							environment.rewind();
							if (!word.isEmpty() && word.charAt(0) == '@') {
								environment.setState(FIELD_ANNOTATION);
							} else {
								environment.setState(FIELD_OR_METHOD);
							}
							return;
						}
						break;
				}
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
	FIELD_OR_METHOD {
		
		@Override
		public void execute(JavaParserEnvironment environment) {
			Visibility visibility = Visibility.PACKAGE;
			Class returnType = null;
			String name = null;
			
			while (environment.hasNext()) {
				final String word = environment.nextWord();
				
				switch (word) {
					case "private":
					case "protected":
					case "public":
						visibility = Visibility.valueOf(word.toUpperCase());
						break;
						
					case "(":
						environment.setState(METHOD);
						return;
						
					case ";":
						final Class currentClass = environment.getCurrentClass();
						// TODO: Should support annotations.
						currentClass.getFields().add(new Field(visibility, returnType, name, environment.popAnnotations()));
						
						environment.setState(CLASS_INNER);
						return;

					default:
						if (returnType == null) {
							returnType = environment.getSourceFile().getRelativeClass(word);
						}
						else if (name == null) {
							name = word;
						}
						break;
				}
			}
		}
		
	},
	FIELD,
	FIELD_ANNOTATION {
		
		@Override
		public void execute(JavaParserEnvironment environment) {
			final String annotationName = environment.nextWord();
			
			if (environment.hasNext()) {
				String word = environment.nextWord();
				if ("(".equals(word)) {
					while (!")".equals(word) && environment.hasNext()) {
						// TODO: Handle arguments.
						word = environment.nextWord();
					}
				} else {
					environment.rewind();
				}
			}
			
			final Class annotationClass = environment.getSourceFile().getRelativeClass(annotationName.substring(1));
			final Annotation annotation = Annotation.from(annotationClass);
			environment.getAnnotations().add(new Instance<>(annotation));
			
			environment.setState(CLASS_INNER);
		}
		
	},
	METHOD {
		
		@Override
		public void execute(JavaParserEnvironment environment) {
			// TODO: Handle this state.
			while (environment.hasNext()) {
				final String word = environment.nextWord();
				if (")".equals(word)) {
					environment.popAnnotations();
					environment.setState(CLASS_INNER);
					return;
				}
			}
		}
		
	},
	LINE_END_COMMENT,
	BLOC_COMMENT,
	JAVADOC;
	
	public void execute(JavaParserEnvironment environment) {
		environment.nextWord();
	}
			
}
