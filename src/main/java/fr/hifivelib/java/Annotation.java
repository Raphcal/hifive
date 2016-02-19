package fr.hifivelib.java;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

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
 * Represent an annotation.
 * 
 * @author Raphaël Calabro (ddaeke-github at yahoo.fr)
 */
public class Annotation implements Node {
	
	/**
	 * Parent of the annotation (may be a class or a package).
	 */
	private Node parent;
	
	/**
	 * Name of the annotation.
	 */
	private String name;
	
	/**
	 * Map of the annotation fields associated to its default value (if any).
	 */
	private Map<Field, String> fields;

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
		return Nodes.fullName(parent, name);
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
		return Collections.emptyList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void merge(Node other) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
