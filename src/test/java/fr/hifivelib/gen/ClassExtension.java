package fr.hifivelib.gen;

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

import fr.hifivelib.annotation.Column;
import fr.hifivelib.annotation.DAO;
import fr.hifivelib.annotation.ManyToOne;
import fr.hifivelib.annotation.OneToMany;
import fr.hifivelib.annotation.PrimaryKey;
import fr.hifivelib.annotation.Request;
import fr.hifivelib.annotation.Table;
import java.util.Collections;
import java.util.Set;

/**
 * General idea behind the lazy class generation.
 * 
 * @author Raphaël Calabro (raphael.calabro@netapsys.fr)
 */
public class ClassExtension {
	
	@Table
	class SampleEntry {
		
		@PrimaryKey
		private String category;
		
		@PrimaryKey
		private Integer section;
		
		@ManyToOne
		private SampleEntity parentEntity;
		
	}
	
	@Table
	class SampleEntity {
		
		@PrimaryKey
		private Integer id;
		
		@Column
		private String name;
		
		@OneToMany
		private Set<SampleEntry> entries;

		/**
		 * Returns the entries.
		 * 
		 * @return The entries
		 */
		public Set<SampleEntry> getEntries() {
			return entries;
		}
		
		public void setEntries(Set<SampleEntry> entries) {
			this.entries = entries;
		}
		
	}
	
	@DAO(of = SampleEntity.class)
	interface SampleEntityDAO {
		
		SampleEntity getSampleEntityById(Integer id);
		
		@Request(sql = "SELECT * FROM SampleEntity WHERE name = ?")
		Set<SampleEntity> getSampleEntitiesByName(String name);
		
	}
	
	// Generated
	
	class LazySampleEntity extends SampleEntity {

		private HiFiveSampleEntryDAO sampleEntryDAO;
		
		/**
		 * Lazy implementation of {@link SampleEntity#getEntries()}.
		 * 
		 * @return The entries.
		 */
		@Override
		public Set<SampleEntry> getEntries() {
			Set<SampleEntry> entries = super.getEntries();
			if (entries == null) {
				entries = sampleEntryDAO.getSampleEntriesBySampleEntity(this);
				setEntries(entries);
			}
			return entries;
		}
		
	}
	
	class HiFiveSampleEntityDAO implements SampleEntityDAO {

		@Override
		public SampleEntity getSampleEntityById(Integer id) {
			return null;
		}

		@Override
		public Set<SampleEntity> getSampleEntitiesByName(String name) {
			return Collections.<SampleEntity>emptySet();
		}
		
	}
	
	class HiFiveSampleEntryDAO {
		
		public SampleEntry getSampleEntryByPrimaryKey(final String category, final Integer section) {
			return null;
		}
		
		public Set<SampleEntry> getSampleEntriesBySampleEntity(final SampleEntity entry) {
			return Collections.<SampleEntry>emptySet();
		}
		
	}
}
