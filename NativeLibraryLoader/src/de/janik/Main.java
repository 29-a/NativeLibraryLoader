package de.janik;

// <- Import ->
import de.janik.util.jni.NativeLibrary;

// <- Static_Import ->
import static java.lang.System.out;
import static de.janik.util.OS.LINUX;
import static de.janik.util.OS.WINDOWS;

/**
 * @author Jan.Marcel.Janik [©2014]
 *
 */
public final class Main
{
	// <- Static ->
	static
	{
		/*
		 * A Simple C/C++ library, that calls an Assembler-Function that writes the number 8 into the register 'eax'.
		 * Source-Code is included in the 'lib'-directories.
		 */
		NativeLibrary lib = new NativeLibrary("GetInt");

		/*
		 * Optional, if no dependencies are specified, every file in the directory
		 * '/lib/"Library-Name"/"Operating-System"/"Architecture"' is loaded.
		 */
		// Windows version compiled with 'Microsoft Visual-Studio 2013 Express (Desktop)' & MASM
		lib.addDependencie(WINDOWS, "GetInt.dll");

		// Linux version compiled with 'GCC, the GNU Compiler Collection' & NASM
		lib.addDependencie(LINUX, "libGetInt.so");

		lib.load();
	}

	// <- Constructor ->
	private Main()
	{
		throw new IllegalStateException("Do not instanciate !!!");
	}

	// <- Static ->
	public static void main(String[] args)
	{
		out.println(GetInt());
	}

	public static native int GetInt();
}
