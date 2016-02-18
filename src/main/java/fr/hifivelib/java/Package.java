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
import java.util.HashSet;
import java.util.Set;

/**
 * Represent a Java package.
 * 
 * @author Raphaël Calabro (ddaeke-github at yahoo.fr)
 */
public class Package implements Node {
	
	public static Package getJavaLangPackage() {
		final Package javaLangPackage = createPackageWithFullName("java.lang");
		
		javaLangPackage.add(new Class(javaLangPackage, "Appendable", Kind.INTERFACE));
		javaLangPackage.add(new Class(javaLangPackage, "AutoCloseable", Kind.INTERFACE));
		javaLangPackage.add(new Class(javaLangPackage, "CharSequence", Kind.INTERFACE));
		javaLangPackage.add(new Class(javaLangPackage, "Cloneable", Kind.INTERFACE));
		javaLangPackage.add(new Class(javaLangPackage, "Comparable", Kind.INTERFACE));
		javaLangPackage.add(new Class(javaLangPackage, "Iterable", Kind.INTERFACE));
		javaLangPackage.add(new Class(javaLangPackage, "Readable", Kind.INTERFACE));
		javaLangPackage.add(new Class(javaLangPackage, "Runnable", Kind.INTERFACE));
		// TODO: Thread.UncaughtExceptionHandler
		
		javaLangPackage.add(new Class(javaLangPackage, "Boolean", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Byte", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Character", Kind.CLASS));
		// TODO: Character.Subset, Character.UnicodeBlock
		javaLangPackage.add(new Class(javaLangPackage, "Class", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ClassLoader", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ClassValue", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Compiler", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Double", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Enum", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Float", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "InheritableThreadLocal", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Integer", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Long", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Math", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Number", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Object", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Package", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Process", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ProcessBuilder", Kind.CLASS));
		// TODO: ProcessBuilder.Redirect
		javaLangPackage.add(new Class(javaLangPackage, "Runtime", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "RuntimePermission", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "SecurityManager", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Short", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "StackTraceElement", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "StrictMath", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "String", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "StringBuffer", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "StringBuilder", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "System", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Thread", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ThreadGroup", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ThreadLocal", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Throwable", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Void", Kind.CLASS));
		
		// TODO: Character.UnicodeScript, ProcessBuilder.Redirect.Type, Thread.State
		
		javaLangPackage.add(new Class(javaLangPackage, "ArithmeticException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ArrayIndexOutOfBoundsException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ArrayStoreException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ClassCastException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ClassNotFoundException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "CloneNotSupportedException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "EnumConstantNotPresentException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Exception", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "IllegalAccessException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "IllegalArgumentException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "IllegalMonitorStateException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "IllegalStateException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "IllegalThreadStateException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "IndexOutOfBoundsException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "InstantiationException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "InterruptedException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "NegativeArraySizeException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "NoSuchFieldException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "NoSuchMethodException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "NullPointerException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "NumberFormatException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ReflectiveOperationException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "RuntimeException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "SecurityException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "StringIndexOutOfBoundsException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "TypeNotPresentException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "UnsupportedOperationException", Kind.CLASS));
		
		javaLangPackage.add(new Class(javaLangPackage, "Deprecated", Kind.ANNOTATION));
		javaLangPackage.add(new Class(javaLangPackage, "FunctionalInterface", Kind.ANNOTATION));
		javaLangPackage.add(new Class(javaLangPackage, "Override", Kind.ANNOTATION));
		javaLangPackage.add(new Class(javaLangPackage, "SafeVarargs", Kind.ANNOTATION));
		javaLangPackage.add(new Class(javaLangPackage, "SuppressWarnings", Kind.ANNOTATION));
		
		return javaLangPackage;
	}
	
	public static Package createPackageWithFullName(final String fullName) {
		final Package result = new Package();
		
		Package topPackage = result;
		int lastIndex = fullName.length();
		for (int index = fullName.lastIndexOf('.'); lastIndex >= 0; index = fullName.lastIndexOf('.', index - 1)) {
			topPackage.setName(fullName.substring(index + 1, lastIndex));
			
			final Package parentPackage = new Package();
			topPackage.setParent(parentPackage);
			parentPackage.add(topPackage);
			
			topPackage = parentPackage;
			lastIndex = index;
		}
		
		return result;
	}
	
	private Package parent;
	private String name;
	
	private final Set<Node> children = new HashSet<>();

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
	
	public void setParent(Package parent) {
		this.parent = parent;
	}

	@Override
	public Collection<Node> children() {
		return children;
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
			final String parentFullName = parent.getFullName();
			stringBuilder.append(parentFullName);
			if (!parentFullName.isEmpty()) {
				stringBuilder.append('.');
			}
		}
		if (name != null) {
			stringBuilder.append(name);
		}
		return stringBuilder.toString();
	}

	public void add(Node node) {
		children.add(node);
	}
	
	public Class getClass(final String classFullName) {
		return getClass(classFullName, true);
	}
	
	public Class getClass(final String classFullName, boolean createClassIfNull) {
		final String packageNameOfClass = Class.getPackageNameFromFullName(classFullName);
		final String fullName = getFullName();
		
		final String packagePrefix = fullName.isEmpty() ? "" : fullName + '.';
		
		if (fullName.equals(packageNameOfClass)) {
			final Node node = get(Class.getClassNameFromFullName(classFullName));
			
			if (node == null) {
				if (createClassIfNull) {
					final String className = Class.getClassNameFromFullName(classFullName);
					final Class clazz = new Class(this, className);
					add(clazz);
					return clazz;
				} else {
					return null;
				}
			} else if (node instanceof Class) {
				return (Class) node;
			} else {
				throw new IllegalArgumentException("Node '" + packageNameOfClass + "' is not a 'Class' but '" + node.getClass().getName() + "'.");
			}
		} else if (packageNameOfClass.startsWith(packagePrefix)) {
			final int startIndex = packagePrefix.length();
			int endIndex = packageNameOfClass.indexOf('.', startIndex);
			if (endIndex == -1) {
				endIndex = packageNameOfClass.length();
			}
			
			final String subPackageName = packageNameOfClass.substring(startIndex, endIndex);
			
			final Node node = get(subPackageName);
			if (node == null) {
				if (createClassIfNull) {
					final Package subPackage = new Package(this, subPackageName);
					add(subPackage);
					return subPackage.getClass(classFullName, createClassIfNull);
				} else {
					return null;
				}
			} else if (node instanceof Package) {
				return ((Package) node).getClass(classFullName, createClassIfNull);
			} else if (node instanceof Class) {
				return ((Class) node).getSubClassFromFullName(fullName);
			} else {
				throw new IllegalArgumentException("Node '" + packageNameOfClass + "' is not a 'Class' but '" + node.getClass().getName() + "'.");
			}
		} else {
			return parent.getClass(classFullName, createClassIfNull);
		}
	}

	public Node get(String name) {
		return Nodes.findByName(children, name);
	}
	
	public void mergeFromRoot(final Package other) {
		getRootPackage().merge(other.getRootPackage());
	}
	
	@Override
	public void merge(final Node other) {
		if (other instanceof Package) {
			Nodes.mergeNodes(children, ((Package) other).children);
		} else {
			throw new IllegalArgumentException("Node must be a package.");
		}
	}
	
	public Package getRootPackage() {
		Package current = this;
		
		while (current.parent != null) {
			current = current.parent;
		}
		
		return current;
	}
	
}
