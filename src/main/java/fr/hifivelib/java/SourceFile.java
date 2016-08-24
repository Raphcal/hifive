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

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Raphaël Calabro (ddaeke-github at yahoo.fr)
 */
public class SourceFile {
	
	private Package parentPackage;
	private Class publicClass;
	private final Set<Class> otherClasses = new LinkedHashSet<>();
	
	private final Set<Class> imports = new LinkedHashSet<>();
	
	/**
	 * Sets the package by its full name.
	 * <p>
	 * The package can be set only once. Every further call to this method
	 * will throw an <code>IllegalArgumentException</code>.
	 * 
	 * @param packageFullName Full name of the package.
	 */
	public void setPackage(final String packageFullName) {
		if (this.parentPackage != null) {
			throw new IllegalArgumentException("Multiple package declaration found. Package is defined to '" + parentPackage.getFullName() + "' and is trying to be set to '" + packageFullName + "'.");
		}
		
		final Package aPackage = Package.createPackageWithFullName(packageFullName);
		this.parentPackage = aPackage;
		
		aPackage.mergeFromRoot(Package.getJavaLangPackage());
	}
	
	/**
	 * Adds a new import by its full name.
	 * 
	 * @param importedClass Full name of the class to import.
	 */
	public void addImport(final String importedClass) {
		imports.add(parentPackage.getClass(importedClass));
	}
	
	public Class getPublicClass() {
		return publicClass;
	}

	public void setPublicClass(Class publicClass) {
		this.publicClass = publicClass;
	}

	public Set<Class> getOtherClasses() {
		return otherClasses;
	}

	public Set<Class> getImports() {
		return imports;
	}

}
