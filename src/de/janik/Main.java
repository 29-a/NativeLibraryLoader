package de.janik;

// <- Import ->
import de.janik.util.jni.NativeLibrary;

// <- Static_Import ->
import static de.janik.util.OS.LINUX;
import static de.janik.util.OS.WINDOWS;
import static java.lang.System.out;

/**
 * @author Jan.Marcel.Janik [©2014]
 */
public final class Main {
    // <- Static ->
    static {
        /*
         * A Simple C/C++ library, that calls an Assembly function that writes the integer value '8' into the register 'eax'.
		 * Source code is included in the 'lib'-directory.
		 */
        final NativeLibrary library = new NativeLibrary("GetInt");

		/*
         * Optional, if no dependencies are specified, every file in the directory
		 * '/lib/"Library-Name"/"Operating-System"/"Architecture"' is loaded.
		 */
        // Windows version compiled with 'Microsoft Visual-Studio 2013 Express (Desktop)' & MASM
        library.addDependency(WINDOWS, "GetInt.dll");

        // Linux version compiled with 'GCC, the GNU Compiler Collection' & NASM
        library.addDependency(LINUX, "libGetInt.so");

        library.load();
    }

    // <- Constructor ->
    private Main() {
        throw new IllegalStateException("Do not instantiate !~!");
    }

    // <- Static ->
    public static void main(final String[] args) {
        out.println(GetInt());
    }

    public static native int GetInt();
}
