package de.janik.util.jni;

// <- Import ->
import de.janik.util.OS;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

// <- Static_Import ->
import static de.janik.util.OS.GetCurrentOS;
import static java.io.File.separator;
import static java.lang.System.getProperty;

/**
 * A representation of a native library. <br>
 * Operating-System independent.
 *
 * @author Jan.Marcel.Janik [©2014]
 */
public final class NativeLibrary {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    /**
     * The absolute path to the '/lib' directory.
     */
    private static final String Path;

    /**
     * The name of the library
     */
    private final String name;

    /**
     * The absolute path to the directory, containing all the native-files
     */
    private final File path;

    /**
     * Hold's a list with all the dependencies for every Operating-System.
     */
    private final HashMap<OS, List<String>> dependencies;

    // <- Static ->
    static {
        Path = getProperty("user.dir") + separator + "lib" + separator;
    }

    // <- Constructor ->

    /**
     * Creates a object, which represents a native library.
     *
     * @param name The name of the library.
     */
    public NativeLibrary(final String name) {
        this.name = name;
        path = new File(Path + name);

        dependencies = new HashMap<>();

        for (OS os : OS.values())
            dependencies.put(os, new LinkedList<>());
    }

    // <- Abstract ->

    // <- Object ->

    /**
     * Add's a file to the list of dependencies, bound to the specified Operating-System.
     *
     * @param os   The Operating-System.
     * @param file The name of the native-file.
     * @see OS
     */
    public void addDependency(final OS os, final String file) {
        dependencies.get(os).add(file);
    }

    /**
     * Set's the list of dependencies, bound to the specified Operating-System to 'dependencies'.<br>
     * 'dependencies', will overwrite all dependencies specified before this call by addDependency(OS, String).
     *
     * @param os           The Operating-System.
     * @param dependencies An array of dependencies.
     */
    public void setDependencies(final OS os, final String... dependencies) {
        List<String> list = this.dependencies.get(os);
        list.clear();

        Stream.of(dependencies).forEach(list::add);
    }

    /**
     * Load's the native library into the JVM-Context.
     *
     * @see de.janik.util.jni.NativeLibraryLoader#Load(File)
     */
    public void load() {
        String nativeLibDir = OS.GetNativeLibraryDirectoryName();
        final String os = GetCurrentOS().getArchitecture().equals("amd64") ? nativeLibDir + separator + "x64" : nativeLibDir;

        List<String> dependencies = getDependencies();

        if (dependencies.size() == 0)
            Stream.of(new File(path.getAbsolutePath() + separator + os).listFiles(File::isFile)).forEach(NativeLibraryLoader::Load);
        else
            dependencies.stream().map(dependency -> new File(path.getAbsolutePath() + separator + os + separator + dependency)).forEach(NativeLibraryLoader::Load);
    }

    /**
     * Return's a list of dependencies for the current Operating-System.<br>
     * Or an empty list, if no dependencies were specified.
     *
     * @return A list of dependencies.
     */
    private List<String> getDependencies() {
        return dependencies.get(OS.CURRENT_OS);
    }

    // <- Getter & Setter ->

    /**
     * Return's the name of the library.
     *
     * @return The name of the library.
     */
    public String getName() {
        return name;
    }

    // <- Static ->
}
