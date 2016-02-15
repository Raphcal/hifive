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
 *
 * @author Raphaël Calabro (ddaeke-github at yahoo.fr)
 */
public enum JavaParserState {
	
	WAITING_FOR_PACKAGE_DECLARATION,
	PACKAGE_DECLARATION,
	WAITING_FOR_IMPORTS,
	IMPORT_DECLARATION,
	CLASS_START,
	FIELD,
	METHOD,
	LINE_END_COMMENT,
	BLOC_COMMENT,
	JAVADOC;
	
	public void execute(JavaParserEnvironment environment) {
		// No action.
	}
			
}