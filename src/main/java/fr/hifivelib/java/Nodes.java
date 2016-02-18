package fr.hifivelib.java;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Raphaël Calabro (raphael.calabro@netapsys.fr)
 */
public final class Nodes {
	
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
	
	private Nodes() {
		
	}
	
}
