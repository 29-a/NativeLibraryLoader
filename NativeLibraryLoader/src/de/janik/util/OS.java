package de.janik.util;

// <- Import ->
import java.util.Collection;
import java.util.HashSet;

// <- Static_Import ->
import static java.io.File.separator;
import static java.lang.String.format;
import static java.lang.System.getProperty;
import static java.lang.System.out;

/**
 * A simplified representation of an Operating-System.
 * 
 * @see#getName()
 * @see#getVersion()
 * @see#getArchitecture()
 * 
 * @author Jan.Marcel.Janik [©2014]
 * @version 1.0
 */
public enum OS
{
	/**
	 * Represent's all <b>Linux/Unix</b> like Operating-System's.
	 */
	LINUX("linux", "lin", "unix"),
	/**
	 * Represent's all Operating-System's that count as <b>Windows</b>.
	 */
	WINDOWS("windows", "win"),
	/**
	 * Represent's all Operating-System's which belong in the category of
	 * <b>OSX</b>
	 */
	OSX("mac"),
	/**
	 * Represent's all Operating-System's that are not
	 * <b>LINUX</b>,<b>WINDOWS</b>,<b>OSX</b>.
	 */
	UNKNOWN();

	// <- Public ->
	/**
	 * Hold's a reference to the Object, representing the current
	 * Operating-System.
	 */
	public static final OS			CURRENT_OS;

	// <- Protected ->

	// <- Private->
	/**
	 * The name of the directory, in which all 'Linux' related native-files are located.
	 */
	private static final String		_LINUX;

	/**
	 * The name of the directory, in which all 'Windows' related native-files are located.
	 */
	private static final String		_WINDOWS;

	/**
	 * The name of the directory, in which all 'Mac OSX' related native-files are located.
	 */
	private static final String		_OSX;

	/**
	 * A collection of all the Operating-System's this software might support.
	 */
	private static Collection<OS>	Supported;

	/**
	 * The aliases of the Operating-System, used to uniquely identify an
	 * Operating-System.
	 */
	private final String[]			aliases;

	/**
	 * The name of the Operating-System.
	 */
	private final String			name;

	/**
	 * The version of the Operating-System.
	 */
	private final String			version;

	/**
	 * The architecture of the Operating-System.
	 */
	private final String			architecture;

	// <- Static ->
	static
	{
		CURRENT_OS = GetCurrentOS();

		Supported = new HashSet<>();

		_LINUX = "linux";

		_WINDOWS = "windows";

		_OSX = "mac";
	}

	// <- Constructor ->
	/**
	 * Create's a new Operating-System object, with the given aliases.
	 * Initializes name, version & architecture of the Operating-System.
	 * 
	 * @see #GetOS_Name()
	 * @see #GetOS_Version()
	 * @see #GetOS_Architecture()
	 * 
	 * @param aliases
	 *            The aliases for the Operating-System.
	 */
	OS(final String...aliases)
	{
		// To make sure aliases is at least instantiated.
		this.aliases = (aliases == null ? new String[0] : aliases);

		name = GetOS_Name();
		version = GetOS_Version();
		architecture = GetOS_Architecture();
	}

	// <- Abstract ->

	// <- Object ->
	/**
	 * 
	 * @return Whether this Operating-System is supported by this
	 *         application or not.
	 */
	public boolean isSupported()
	{
		return Supported.contains(this);
	}

	/**
	 * Get's the path to the Java-Installation from the System-Properties.
	 * 
	 * @see java.lang.System#getProperty(String)
	 * 
	 * @return The path to the Java-Installation, including '/bin'.
	 */
	public String getJavaDir()
	{
		return getProperty("java.home") + separator + "bin" + separator;
	}

	/**
	 * Create's a formated output String containing all the information about
	 * the Operating-System.
	 * 
	 * @return A formated String, containing the name, version & architecture of
	 *         the Operating-System. </br><b>Note:</b> This is will produce a
	 *         multiple line output.
	 */
	public String getOS_Info()
	{
		return format("System info:\n" + "Operating system: '%s'.\n" + "System version: '%s'.\n" + "System architecture: '%s'.", getName(), getVersion(), getArchitecture());
	}

	@Override
	public String toString()
	{
		String s = "OS [Name: " + getName().toUpperCase();

		if (!getVersion().equals(new String()))
			s += " Version:" + getVersion();

		if (!getArchitecture().equals(new String()))
			s += " Architecture:" + getArchitecture();

		return s;
	}

	// <- Getter & Setter ->
	/**
	 * Return's all the given aliases for the Operating-System.
	 * 
	 * @return The aliases for the Operating-System.
	 */
	public String[] getAliases()
	{
		return this.aliases;
	}

	/**
	 * Return's the name of the Operating-System.
	 * 
	 * @return The name of the Operating-System.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Return's the version of the Operating-System.
	 * 
	 * @return The version of the Operating-System.
	 */
	public String getVersion()
	{
		return version;
	}

	/**
	 * Return's the architecture of the Operating-System.
	 * 
	 * @return The architecture of the Operating-System.
	 */
	public String getArchitecture()
	{
		return architecture;
	}

	// <- Static ->
	/**
	 * Print's some information of the <b>CURRENT_OS</b> to the standard-output.
	 * 
	 * @see#getOS_Info()
	 * 
	 */
	public static void PrintOS_Info()
	{
		out.println(CURRENT_OS.getOS_Info());
	}

	/**
	 * Return's the <b>CURRENT_OS</b>, if not instantiated, instantiates
	 * OS.CURRENT_OS, you may use this static-reference instead of this method.
	 * 
	 * @return The object representing the <b>CURRENT_OS</b>.
	 */
	public static OS GetCurrentOS()
	{
		if (CURRENT_OS == null)
		{
			String name = GetOS_Name();

			for (OS os : values())
				for (String alias : os.getAliases())
					if (name.equals(alias))
						return os;
					else
						if (name.contains(alias))
							return os;

			return UNKNOWN;
		}
		else
			return CURRENT_OS;
	}

	/**
	 * Return's the current OS's name given by the system property "os.name".
	 * 
	 * @see java.lang.System#getProperty(String)
	 * 
	 * @return The Operating-System's name.
	 */
	private static String GetOS_Name()
	{
		String name = getProperty("os.name").toLowerCase();

		return name == null ? new String() : name;
	}

	/**
	 * Return's the current OS's version given by the system property
	 * "os.version".
	 * 
	 * @see java.lang.System#getProperty(String)
	 * 
	 * @return The Operating-System's version.
	 */
	private static String GetOS_Version()
	{
		String version = getProperty("os.version");

		return version == null ? new String() : version;
	}

	/**
	 * Return's the current OS's architecture given by the system property
	 * "os.arch".
	 * 
	 * @see java.lang.System#getProperty(String)
	 * 
	 * @return The Operating-System's architecture.
	 */
	private static String GetOS_Architecture()
	{
		String architecture = getProperty("os.arch");

		return architecture == null ? new String() : architecture;
	}

	/**
	 * Add's a Operating-System to the list of supported Operating-System's.
	 * 
	 * @param os
	 *            The Operating-System that should also be supported;
	 */
	public static void AddSupportedOS(final OS os)
	{
		Supported.add(os);
	}

	/**
	 * Remove's a Operating-System from the list of supported Operating-System's.
	 * 
	 * @param os
	 *            The Operating-System that should be removed from the list of supported Operating-System's.
	 */
	public static void RemoveSupportedOS(final OS os)
	{
		Supported.remove(os);
	}

	/**
	 * Return's all Operating-System's that are supported by this software.
	 * 
	 * @return All Operating-System's that are supported by this software.
	 */
	public Collection<OS> GetSupportedOSs()
	{
		return Supported;
	}

	/**
	 * Return's the directory name for the native-files of the current Operating-System.
	 * 
	 * @return The directory name of the current Operating-System.
	 */
	public static String GetNativeLibraryDirectoryName()
	{
		String directory = null;

		switch (OS.CURRENT_OS)
		{
			case LINUX:
				directory = _LINUX;
				break;
			case OSX:
				directory = _OSX;
				break;

			case WINDOWS:
				directory = _WINDOWS;
				break;
			case UNKNOWN:
			default:
				break;
		}

		return directory;
	}
}
