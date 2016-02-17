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
	
	WAITING_FOR_CLASS {

		@Override
		public void execute(JavaParserEnvironment environment) {
			final String word = environment.nextWord();
			
			switch (word) {
			case "package":
				environment.setState(PACKAGE_DECLARATION);
				break;
			case "import":
				environment.setState(IMPORT_DECLARATION);
				break;
			case "public":
			case "protected":
			case "private":
			case "class":
				environment.setState(CLASS_START);
				break;
			default:
				if (!word.isEmpty() && word.charAt(0) == '@') {
					environment.setState(CLASS_START);
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
	CLASS_ANNOTATION,
	CLASS_START,
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
