package fr.hifivelib.java.style;

/**
 * First version of the java source writer.
 * 
 * @author Raphaël Calabro (raphael.calabro@netapsys.fr)
 */
public class SourceStyle {
	
	private int indentationLevel;
	
	public void blockStart() {
		indentationLevel++;
	}
	
	public void blockEnd() {
		indentationLevel--;
	}
	
}
