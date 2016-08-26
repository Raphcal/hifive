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

/**
 * Utility class for <code>Node</code>s.
 * 
 * @author Raphaël Calabro (raphael.calabro@netapsys.fr)
 */
public final class Nodes {
	
	public static final char SEPARATOR = '.';
	
	/**
	 * Find the node with the given <code>name</code> in the given collection
	 * of <code>nodes</code>.
	 * 
	 * @param <T> Node type.
	 * @param nodes Collection of nodes to search in.
	 * @param name Name of the node to search.
	 * @return The instance of <code>Node</code> matching the given name,
	 * or <code>null</code> if none matched.
	 */
	public static <T extends Node> T findByName(final Collection<T> nodes, final String name) {
		if (name == null) {
			return null;
		}
		for (final T node : nodes) {
			if (name.equals(node.getName())) {
				return node;
			}
		}
		return null;
	}
	
	/**
	 * Merge the content of <code>others</code> inside <code>destination</code>.
	 * 
	 * @param <T> Node type.
	 * @param destination Collection where to add the content of <code>others</code>.
	 * @param others Nodes to add.
	 */
	public static <T extends Node> void mergeNodes(final Collection<T> destination, final Collection<T> others) {
		final ArrayList<T> newNodes = new ArrayList<>();
		
		for (final T otherNode : others) {
			final T localNode = findByName(destination, otherNode.getName());

			if (localNode == null) {
				newNodes.add(otherNode);
			} else {
				localNode.merge(otherNode);
			}
		}

		destination.addAll(newNodes);
	}
	
	public static <T extends Node> void addOrMerge(T node, Collection<T> nodes) {
		final T existingNode = findByName(nodes, node.getName());
		
		if (existingNode == null) {
			nodes.add(node);
		} else {
			existingNode.merge(node);
		}
	}
	
	/**
	 * Concatene the given <code>name</code> with the full name of the
	 * <code>parent</code>.
	 * 
	 * @param parent Parent node.
	 * @param name Name of the node.
	 * @return The full name.
	 */
	public static String fullName(final Node parent, final String name) {
		if (name == null || name.isEmpty()) {
			return "";
		}
		
		final String parentName = parent.getFullName();
		
		if (parentName.isEmpty()) {
			return name;
		} else {
			return parentName + SEPARATOR + name;
		}
	}
	
	private Nodes() {
		// No initialization.
	}
	
}
