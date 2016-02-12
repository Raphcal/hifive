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

/**
 * Represent a Java class.
 * 
 * @author Raphaël Calabro (ddaeke-github at yahoo.fr)
 */
public class Class implements Node {
	
	private Node parent;
	private String name;
	private Kind kind;
	
	private Collection<Node> innerClasses;
	
	private Collection<Class> imports;
	private Collection<Annotation<?>> annotations;
	private Class superclass;
	private Collection<Class> interfaces;
	
	private Collection<String> authors;
	
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

}
