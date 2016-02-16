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
import java.util.HashMap;
import java.util.Map;

/**
 * Represent a Java package.
 * 
 * @author Raphaël Calabro (ddaeke-github at yahoo.fr)
 */
public class Package implements Node {
	
	private Package parent;
	private String name;
	
	private final Map<String, Node> children = new HashMap<>();

	public Package() {
	}

	public Package(Package parent, String name) {
		this.parent = parent;
		this.name = name;
	}
	
	@Override
	public Node parent() {
		return parent;
	}

	@Override
	public Collection<Node> children() {
		return children.values();
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
		if (parent != null) {
			stringBuilder.append(parent.getFullName()).append('.');
		}
		if (name != null) {
			stringBuilder.append(name);
		}
		return stringBuilder.toString();
	}

	public void setParent(Package parent) {
		this.parent = parent;
	}
	
	public Class getClass(final String classFullName) {
		final String packageNameOfClass = Class.getPackageNameFromFullName(classFullName);
		final String fullName = getFullName();
		
		if (fullName.equals(packageNameOfClass)) {
			final Node node = children.get(classFullName);
			
			if (node == null) {
				final String className = Class.getClassNameFromFullName(classFullName);
				final Class clazz = new Class(this, className);
				children.put(className, clazz);
				return clazz;
			} else if (node instanceof Class) {
				return (Class) node;
			} else {
				throw new IllegalArgumentException("Node '" + packageNameOfClass + "' is not a 'Class' but '" + node.getClass().getName() + "'.");
			}
		} else if (packageNameOfClass.startsWith(fullName + '.')) {
			final int startIndex = fullName.length() + 2;
			int endIndex = packageNameOfClass.indexOf('.', startIndex);
			if (endIndex == -1) {
				endIndex = packageNameOfClass.length();
			}
			
			final String subPackageName = packageNameOfClass.substring(startIndex, endIndex);
			
			final Node node = children.get(subPackageName);
			if (node == null) {
				final Package subPackage = new Package(this, subPackageName);
				children.put(subPackageName, subPackage);
				return subPackage.getClass(classFullName);
			} else if (node instanceof Package) {
				return ((Package) node).getClass(classFullName);
			} else if (node instanceof Class) {
				return ((Class) node).getSubClassFromFullName(fullName);
			} else {
				throw new IllegalArgumentException("Node '" + packageNameOfClass + "' is not a 'Class' but '" + node.getClass().getName() + "'.");
			}
		} else {
			return parent.getClass(classFullName);
		}
	}
	
}
