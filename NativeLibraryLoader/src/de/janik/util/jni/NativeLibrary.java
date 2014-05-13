package de.janik.util.jni;

// <- Import ->
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import de.janik.util.OS;

// <- Static_Import ->
import static java.lang.System.getProperty;
import static de.janik.util.OS.GetCurrentOS;
import static java.io.File.separator;

/**
 * A representation of a native library. <br>
 * Operating-System independent.
 * 
 * @author Jan.Marcel.Janik [©2014]
 *
 */
public class NativeLibrary
{
	// <- Public ->

	// <- Protected ->

	// <- Private->
	/**
	 * The absolute path to the '/lib' directory.
	 */
	private static final String			Path;

	/**
	 * The name of the library
	 */
	private String						name;

	/**
	 * The absolute path to the directory, containing all the native-files
	 */
	private File						path;

	/**
	 * Hold's a list with all the dependencies for every Operating-System.
	 */
	private HashMap<OS, List<String>>	dependencies;

	// <- Static ->
	static
	{
		Path = getProperty("user.dir") + separator + "lib" + separator;
	}

	// <- Constructor ->
	/**
	 * Creates a object, which represents a native library.
	 * 
	 * @param name
	 *            The name of the library.
	 */
	public NativeLibrary(String name)
	{
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
	 * @param os
	 *            The Operating-System.
	 * @param file
	 *            The name of the native-file.
	 * 
	 * @see de.janik.util.OS
	 */
	public void addDependencie(OS os, String file)
	{
		dependencies.get(os).add(file);
	}

	/**
	 * Set's the list of dependencies, bound to the specified Operating-System to 'dependencies'.<br>
	 * 'dependencies', will overwrite all dependencies specified before this call by addDependencie(OS, String).
	 * 
	 * @param os
	 *            The Operating-System.
	 * 
	 * @param dependencies
	 *            An array of dependencies.
	 */
	public void setDependencies(OS os, String...dependencies)
	{
		List<String> list = this.dependencies.get(os);
		list.clear();

		Stream.of(dependencies).forEach(list::add);
	}

	/**
	 * Load's the native library into the JVM-Context.
	 * 
	 * @see de.janik.util.jni.NativeLibraryLoader#Load(File)
	 */
	public void load()
	{
		String nativeLibDir = OS.GetNativeLibraryDirectoryName();
		final String os = GetCurrentOS().getArchitecture().equals("amd64") ? nativeLibDir + separator + "x64" : nativeLibDir;

		List<String> dependencies = getDependencies();

		if (dependencies.size() == 0)
			Stream.of(new File(path.getAbsolutePath() + separator + os).listFiles(file -> file.isFile())).forEach(NativeLibraryLoader::Load);
		else
			dependencies.stream().map(dependencie -> new File(path.getAbsolutePath() + separator + os + separator + dependencie))
					.forEach(NativeLibraryLoader::Load);
	}

	// <- Getter & Setter ->
	/**
	 * Return's the name of the library.
	 * 
	 * @return The name of the library.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Return's a list of dependencies for the current Operating-System.<br>
	 * An empty list, if no dependencies were specified.
	 * 
	 * @return A list of dependencies.
	 */
	private List<String> getDependencies()
	{
		return dependencies.get(OS.CURRENT_OS);
	}

	// <- Static ->
}
