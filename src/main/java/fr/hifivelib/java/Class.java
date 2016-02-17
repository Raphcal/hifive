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

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Represent a Java class.
 * 
 * @author Raphaël Calabro (ddaeke-github at yahoo.fr)
 */
public class Class implements Node {
	
	public static String getPackageNameFromFullName(String classFullName) {
		final String packageFullName;
		
		final int lastDot = classFullName.lastIndexOf('.');
		if (lastDot == -1) {
			packageFullName = "";
		} else {
			packageFullName = classFullName.substring(0, lastDot);
		}
		
		return packageFullName;
	}
	
	public static String getClassNameFromFullName(String classFullName) {
		final String name;
		
		final int lastDot = classFullName.lastIndexOf('.');
		if (lastDot == -1) {
			name = classFullName;
		} else {
			name = classFullName.substring(lastDot + 1);
		}
		
		return name;
	}
	
	private Node parent;
	private String name;
	private Kind kind;
	private Visibility visibility;
	
	private Collection<Node> innerClasses;
	
	private Collection<Class> imports = new LinkedHashSet<>();
	private Collection<Annotation<?>> annotations = new LinkedHashSet<>();
	private Class superclass;
	private Collection<Class> interfaces = new LinkedHashSet<>();
	
	private Collection<String> authors;

	public Class() {
	}

	public Class(Node parent, String name) {
		this.parent = parent;
		this.name = name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Node parent() {
		return parent;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Node> children() {
		return innerClasses;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFullName() {
		final StringBuilder stringBuilder = new StringBuilder();
		final String parentFullName = parent.getFullName();
		if (!parentFullName.isEmpty()) {
			stringBuilder.append(parentFullName).append('.');
		}
		return stringBuilder.append(name).toString();
	}
	
	public void setPackage(final String packageFullName) {
		if (this.parent != null) {
			throw new IllegalArgumentException("Multiple package declaration found. Package is defined to '" + parent.getFullName() + "' and is trying to be set to '" + packageFullName + "'.");
		}
		
		final Package packageOfThisClass = new Package();
		this.parent = packageOfThisClass;
		
		packageOfThisClass.add(this);
		
		if (packageFullName.isEmpty()) {
			return;
		}
		
		Package topPackage = packageOfThisClass;
		int lastIndex = packageFullName.length();
		for (int index = packageFullName.lastIndexOf('.'); lastIndex >= 0; index = packageFullName.lastIndexOf('.', index - 1)) {
			topPackage.setName(packageFullName.substring(index + 1, lastIndex));
			
			final Package parentPackage = new Package();
			topPackage.setParent(parentPackage);
			parentPackage.add(topPackage);
			
			topPackage = parentPackage;
			lastIndex = index;
		}
	}
	
	public void addImport(final String importedClass) {
		if (parent instanceof Package) {
			imports.add(((Package) parent).getClass(importedClass));
		} else {
			throw new IllegalStateException("Only public outer classes can import classes.");
		}
	}

	public Collection<Class> getImports() {
		return imports;
	}

	public Class getSubClassFromFullName(final String fullName) {
		throw new UnsupportedOperationException("Not implented yet.");
	}
	
	public Class getRelativeClass(final String name) {
		// TODO: Should handle inner classes.
		final Class samePackageClass = (Class) ((Package) parent).get(name);
		if (samePackageClass != null) {
			return samePackageClass;
		}
		
		for (final Class importedClass : imports) {
			if (name.equals(importedClass.getName())) {
				return importedClass;
			}
		}
		
		return ((Package) parent).getClass(name);
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public Kind getKind() {
		return kind;
	}
	
	public void setKind(Kind kind) {
		this.kind = kind;
	}

	public Class getSuperclass() {
		return superclass;
	}

	public void setSuperclass(Class superclass) {
		this.superclass = superclass;
	}

	public Collection<Class> getInterfaces() {
		return interfaces;
	}
	
}
