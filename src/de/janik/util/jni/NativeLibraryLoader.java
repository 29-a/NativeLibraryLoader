package de.janik.util.jni;

// <- Import ->
import java.io.File;

// <- Static_Import ->
import static java.lang.System.load;
import static java.lang.System.loadLibrary;

/**
 * A utility-class to load native library's.
 *
 * @author Jan.Marcel.Janik [©2014]
 */
public final class NativeLibraryLoader {
    // <- Public ->
    // <- Protected ->
    // <- Private->
    // <- Static ->

    // <- Constructor ->
    private NativeLibraryLoader() {
        throw new IllegalStateException("Do not instantiate !~!");
    }

    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->

    // <- Static ->

    /**
     * Load's a given native library and all her dependencies.
     *
     * @param lib The library to be loaded.
     * @see NativeLibrary#load()
     */
    public static void Load(final NativeLibrary lib) {
        lib.load();
    }

    /**
     * Load's a native library from the file-system.
     *
     * @param file The file to be loaded.
     * @see System#load(String)
     */
    public static void Load(final File file) {
        load(file.getAbsolutePath());
    }

    /**
     * Load's a library from the file-system.<br>
     * <br>
     * Note: The JVM will only check in the directories specified by the JVM option '-Djava.library.path'<br>
     * or by a call to System.setProperty("java.library.path", path); <br>
     * <br>
     * This call is equal to System.loadLibrary(name), which is also equal to Runtime.getRuntime.loadLibrary(name)
     *
     * @param name The name of the library-file.
     * @see System#loadLibrary(String)
     * @see Runtime#loadLibrary(String)
     */
    public static void Load(final String name) {
        loadLibrary(name);
    }
}
