package fr.hifivelib.java;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Raphaël Calabro (ddaeke-github at yahoo.fr)
 */
public class SourceFile {
	
	private Package parentPackage;
	private Class publicClass;
	private final Set<Class> otherClasses = new LinkedHashSet<>();
	
	private final Set<Class> imports = new LinkedHashSet<>();
	
	/**
	 * Sets the package by its full name.
	 * <p>
	 * The package can be set only once. Every further call to this method
	 * will throw an <code>IllegalArgumentException</code>.
	 * 
	 * @param packageFullName Full name of the package.
	 */
	public void setPackage(final String packageFullName) {
		if (this.parentPackage != null) {
			throw new IllegalArgumentException("Multiple package declaration found. Package is defined to '" + parentPackage.getFullName() + "' and is trying to be set to '" + packageFullName + "'.");
		}
		
		final Package aPackage = Package.createPackageWithFullName(packageFullName);
		this.parentPackage = aPackage;
		
		aPackage.mergeFromRoot(Package.getJavaLangPackage());
	}
	
	/**
	 * Adds a new import by its full name.
	 * 
	 * @param importedClass Full name of the class to import.
	 */
	public void addImport(final String importedClass) {
		imports.add(parentPackage.getClass(importedClass));
	}
	
	public Class getPublicClass() {
		return publicClass;
	}

	public void setPublicClass(Class publicClass) {
		this.publicClass = publicClass;
	}

	public Set<Class> getOtherClasses() {
		return otherClasses;
	}

	public Set<Class> getImports() {
		return imports;
	}

}
