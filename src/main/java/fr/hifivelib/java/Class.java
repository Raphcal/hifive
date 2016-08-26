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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Represent a Java class.
 * 
 * @author Raphaël Calabro (ddaeke-github at yahoo.fr)
 */
public class Class implements Node {
	
	public static String getPackageNameFromFullName(final String classFullName) {
		final String packageFullName;
		
		final int lastDot = classFullName.lastIndexOf('.');
		if (lastDot == -1) {
			packageFullName = "";
		} else {
			packageFullName = classFullName.substring(0, lastDot);
		}
		
		return packageFullName;
	}
	
	public static String getClassNameFromFullName(final String classFullName) {
		final String name;
		
		final int lastDot = classFullName.lastIndexOf('.');
		if (lastDot == -1) {
			name = classFullName;
		} else {
			name = classFullName.substring(lastDot + 1);
		}
		
		return name;
	}
	
	private SourceFile sourceFile;
	
	private Node parent;
	private String name;
	private Kind kind;
	private Visibility visibility = Visibility.PACKAGE;
	
	private Collection<Node> innerClasses;
	
	private final Set<Instance<Annotation>> annotations = new LinkedHashSet<>();
	private Class superclass;
	private final Set<Class> interfaces = new LinkedHashSet<>();
	
	private final List<Field> fields = new ArrayList<>();
	
	public Class() {
	}
	
	public Class(SourceFile sourceFile) {
		this.sourceFile = sourceFile;
		this.parent = sourceFile.getPackage();
	}

	public Class(Node parent, String name) {
		this(parent, name, null);
	}
	
	public Class(Node parent, String name, Kind kind) {
		this.parent = parent;
		this.name = name;
		this.kind = kind;
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
		return Nodes.fullName(parent, name);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void merge(Node other) {
		if (other instanceof Class) {
			final Class otherClass = (Class) other;
			
			if (kind == null) {
				kind = otherClass.kind;
			}
			if (visibility == null) {
				visibility = otherClass.visibility;
			}
			Nodes.mergeNodes(innerClasses, otherClass.innerClasses);
			Nodes.mergeNodes(annotations, otherClass.annotations);
			if (superclass == null) {
				// TODO: Maybe create a special case for java.lang.Object ?
				superclass = otherClass.superclass;
			}
			Nodes.mergeNodes(interfaces, otherClass.interfaces);
		} else {
			throw new IllegalArgumentException("Node must be a class.");
		}
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Class getSubClassFromFullName(final String fullName) {
		throw new UnsupportedOperationException("Not implented yet.");
	}
	
	public Class getRelativeClass(final String name) {
		// TODO: Should handle inner classes.
		final Package parentPackage = (Package) parent;
		
		if (name.indexOf('.') == -1) {
			final Class samePackageClass = (Class) parentPackage.get(name);
			if (samePackageClass != null) {
				return samePackageClass;
			}

			for (final Class importedClass : sourceFile.getImports()) {
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

	public Visibility getVisibility() {
		return visibility;
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

	public Set<Instance<Annotation>> getAnnotations() {
		return annotations;
	}

	public List<Field> getFields() {
		return fields;
	}

}
