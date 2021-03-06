package fr.hifivelib.java;

import java.util.Collection;

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
public class Instance<T extends Node> implements Node {
	
    private final T of;

	public Instance(T of) {
		this.of = of;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return of.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFullName() {
		return of.getFullName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Node parent() {
		throw new UnsupportedOperationException("Not supported.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Node> children() {
		throw new UnsupportedOperationException("Not supported.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void merge(Node other) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	public T getOf() {
		return of;
	}
	
}
