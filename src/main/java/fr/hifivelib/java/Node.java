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
 * Parent interface for every java element.
 * 
 * @author Raphaël Calabro (ddaeke-github at yahoo.fr)
 */
public interface Node {
	
	/**
	 * Returns the name of the node.
	 * 
	 * @return the name of the node.
	 */
	String getName();
	
	/**
	 * Returns the full name of the node.
	 * <p>
	 * The full name if built from the name of this node preceded by the
	 * name of its parents. Names are separated with a dot.
	 * 
	 * @return the full name of the node.
	 * @see Nodes#SEPARATOR
	 */
	String getFullName();
	
	/**
	 * Returns the parent node of this node.
	 * 
	 * @return the parent node of this node.
	 */
	Node parent();
	
	/**
	 * Returns the children of this node.
	 * 
	 * @return the children of this node or an empty collection of none exists.
	 */
	Collection<Node> children();
	
	/**
	 * Merge the content of this node with the content of the given node.
	 * 
	 * @param other Node to merge with.
	 */
	void merge(Node other);
	
}
