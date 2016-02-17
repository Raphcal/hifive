package fr.hifivelib.java;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

/**
 * Iterator for the characters of a <code>Reader</code>.
 * 
 * @author Raphaël Calabro (raphael.calabro@netapsys.fr)
 */
public class ReaderCharacterIterator implements Iterator<Character> {
	
	private final Reader reader;
	private int nextCharacter;

	public ReaderCharacterIterator(Reader reader) {
		this.reader = reader;
		readNextCharacter();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasNext() {
		return nextCharacter != -1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character next() {
		if (nextCharacter != -1) {
			final char character = (char) nextCharacter;
			readNextCharacter();
			return character;
		} else {
			return null;
		}
	}
	
	private void readNextCharacter() {
		try {
			this.nextCharacter = reader.read();
		} catch (IOException ex) {
			this.nextCharacter = -1;
			ex.printStackTrace();
		}
	}
	
}
