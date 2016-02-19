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
				environment.getPublicClass().setPackage("");
				environment.setState(IMPORT_DECLARATION);
				break;
			case "public":
			case "protected":
			case "private":
			case "class":
				environment.getPublicClass().setPackage("");
				environment.rewind();
				environment.setState(CLASS_START);
				break;
			default:
				if (!word.isEmpty() && word.charAt(0) == '@') {
					environment.getPublicClass().setPackage("");
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
				environment.getPublicClass().setPackage(word);
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
			case "class":
				environment.rewind();
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
				environment.getPublicClass().addImport(word);
			}
		}
		
	},
	CLASS_ANNOTATION {
		
		@Override
		public void execute(JavaParserEnvironment environment) {
			final String word = environment.nextWord();
			
			// TODO: Handle annotation arguments.
			if (!word.isEmpty() && word.charAt(0) == '@') {
				final Class annotationClass = environment.getPublicClass().getRelativeClass(word.substring(1));
				final Annotation annotation = Annotation.from(annotationClass);
				environment.getPublicClass().getAnnotations().add(new Instance<>(annotation));
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
					environment.getPublicClass().setVisibility(Visibility.PUBLIC);
					break;
				case "private":
				case "protected":
					throw new UnsupportedOperationException("Not supported yet");
				case "interface":
				case "enum":
				case "class":
					environment.getPublicClass().setKind(Kind.valueOf(word.toUpperCase()));
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
					environment.setState(CLASS_INNER);
					break;
				case "implements":
				case "extends":
					environment.rewind();
					environment.setState(CLASS_EXTENSION);
					break;
				default:
					environment.getPublicClass().setName(word);
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
						final Class clazz = environment.getPublicClass().getRelativeClass(word);
						
						if (mode == EXTENDS_MODE) {
							environment.getPublicClass().setSuperclass(clazz);
						} else if (mode == IMPLEMENTS_MODE) {
							if (clazz.getKind() == null) {
								clazz.setKind(Kind.INTERFACE);
							} else if (clazz.getKind() != Kind.INTERFACE) {
								throw new IllegalArgumentException("A class cannot implements a " + clazz.getKind());
							}
							environment.getPublicClass().getInterfaces().add(clazz);
						}
						break;
				}
				word = environment.nextWord();
			}
			
			if (environment.getPublicClass().getSuperclass() == null) {
				final Class object = ((Package) environment.getPublicClass().parent()).getClass("java.lang.Object");
				environment.getPublicClass().setSuperclass(object);
			}
			
			environment.setState(CLASS_INNER);
		}
		
	},
	CLASS_INNER,
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
