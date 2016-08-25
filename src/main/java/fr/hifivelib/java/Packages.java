package fr.hifivelib.java;

/**
 * Utility class for {@link Package} objects.
 * 
 * @author Raphaël Calabro (raphael.calabro@netapsys.fr)
 */
public final class Packages {
	
	public static Package getJavaLangPackage() {
		final Package javaLangPackage = Package.createPackageWithFullName("java.lang");
		
		javaLangPackage.add(new Class(javaLangPackage, "Appendable", Kind.INTERFACE));
		javaLangPackage.add(new Class(javaLangPackage, "AutoCloseable", Kind.INTERFACE));
		javaLangPackage.add(new Class(javaLangPackage, "CharSequence", Kind.INTERFACE));
		javaLangPackage.add(new Class(javaLangPackage, "Cloneable", Kind.INTERFACE));
		javaLangPackage.add(new Class(javaLangPackage, "Comparable", Kind.INTERFACE));
		javaLangPackage.add(new Class(javaLangPackage, "Iterable", Kind.INTERFACE));
		javaLangPackage.add(new Class(javaLangPackage, "Readable", Kind.INTERFACE));
		javaLangPackage.add(new Class(javaLangPackage, "Runnable", Kind.INTERFACE));
		// TODO: Thread.UncaughtExceptionHandler
		
		javaLangPackage.add(new Class(javaLangPackage, "Boolean", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Byte", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Character", Kind.CLASS));
		// TODO: Character.Subset, Character.UnicodeBlock
		javaLangPackage.add(new Class(javaLangPackage, "Class", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ClassLoader", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ClassValue", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Compiler", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Double", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Enum", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Float", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "InheritableThreadLocal", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Integer", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Long", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Math", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Number", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Object", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Package", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Process", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ProcessBuilder", Kind.CLASS));
		// TODO: ProcessBuilder.Redirect
		javaLangPackage.add(new Class(javaLangPackage, "Runtime", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "RuntimePermission", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "SecurityManager", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Short", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "StackTraceElement", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "StrictMath", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "String", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "StringBuffer", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "StringBuilder", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "System", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Thread", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ThreadGroup", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ThreadLocal", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Throwable", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Void", Kind.CLASS));
		
		// TODO: Character.UnicodeScript, ProcessBuilder.Redirect.Type, Thread.State
		
		javaLangPackage.add(new Class(javaLangPackage, "ArithmeticException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ArrayIndexOutOfBoundsException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ArrayStoreException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ClassCastException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ClassNotFoundException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "CloneNotSupportedException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "EnumConstantNotPresentException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "Exception", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "IllegalAccessException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "IllegalArgumentException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "IllegalMonitorStateException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "IllegalStateException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "IllegalThreadStateException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "IndexOutOfBoundsException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "InstantiationException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "InterruptedException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "NegativeArraySizeException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "NoSuchFieldException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "NoSuchMethodException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "NullPointerException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "NumberFormatException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "ReflectiveOperationException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "RuntimeException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "SecurityException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "StringIndexOutOfBoundsException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "TypeNotPresentException", Kind.CLASS));
		javaLangPackage.add(new Class(javaLangPackage, "UnsupportedOperationException", Kind.CLASS));
		
		javaLangPackage.add(new Class(javaLangPackage, "Deprecated", Kind.ANNOTATION));
		javaLangPackage.add(new Class(javaLangPackage, "FunctionalInterface", Kind.ANNOTATION));
		javaLangPackage.add(new Class(javaLangPackage, "Override", Kind.ANNOTATION));
		javaLangPackage.add(new Class(javaLangPackage, "SafeVarargs", Kind.ANNOTATION));
		javaLangPackage.add(new Class(javaLangPackage, "SuppressWarnings", Kind.ANNOTATION));
		
		return javaLangPackage;
	}
	
	/**
	 * Private constructor.
	 */
	private Packages() {
		// Empty.
	}
	
}
