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
 * Represent a Java package.
 * 
 * @author Raphaël Calabro (ddaeke-github at yahoo.fr)
 */
public class Package implements Node {
	
	private Package parent;
	private String name;
	
	private Collection<Node> children;
	
	@Override
	public Node parent() {
		return parent;
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
	
}
