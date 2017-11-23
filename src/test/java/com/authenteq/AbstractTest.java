package com.authenteq;

import com.authenteq.api.StatusesApi;
import com.authenteq.model.Status;
import com.authenteq.model.Transaction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * Test scafolding and configuration
 *
 * With reespect to test.properties:
 *
 * There is a default test.properties file in test/resources
 * If you wish to use different properties you can set the environment variable "BDB_DRIVER_PROPERTIES" to point to a file, the
 * easiest thing is to copy the test/resoruces/test.properties to a file on your local filesystem and change the appropriate properties.
 * When specifying a file on the local file system prefix the file with "file:///"
 * The property file on a filesystem should be constructed as an URL,
 * for the property file in C:\Test\test.properties the environment variable would be set to: file:///C:/Test/test.properties for Windows
 * on Unix if the file is in /usr/local/test/test.properties it would be set to file:///usr/local/test/test.properties
 *
 */
public abstract class AbstractTest
{
	private static Map<String, String> env = System.getenv();
	private static Properties properties = initProperties();
	private static final String bdbDriverProperties = "BDB_DRIVER_PROPERTIES";

	/**
	 * Initialize the properties file
	 *
	 * @return initialized properties
	 * @throws RuntimeException no point in continuing if the properties file cannot be found
	 */
	private static Properties initProperties() throws RuntimeException
	{
		Properties props = new Properties();

		try( InputStream input = new FileInputStream( getInputFile( bdbDriverProperties, "test.properties" ) )) {
			props.load( input );
		} catch( FileNotFoundException ex ) {
			System.err.println( "cannot find test.properties, set environment variable test.properties with path name to a test properties file" );
			throw new RuntimeException( "cannot find test.properties, set environment variable test.properties with path name to a test properties file", ex );
		} catch( IOException ex ) {
			throw new RuntimeException( "Error reading properties files " + env.getOrDefault( bdbDriverProperties, "test.properties" ), ex );
		}
		
		return props;
	}

	/**
	 * Get the input file, a property file on the filesystem should be constructed as an URL,
	 * for the property file in C:\Test\test.properties the environment variable would be set to: file:///C:/Test/test.properties for Windows
	 * on Unix if the file is in /usr/local/test/test.properties it would be set to file:///usr/local/test/test.properties
	 *
	 * @param envKey the env variable with the file path to the properties file
	 * @param otherwise the default properties file on the classpath
	 * @return a url file name as a @String
	 */
	private static String getInputFile( final String envKey, final String otherwise )
	{
		String propertiesFile = env.getOrDefault( envKey, otherwise );

		URL url = null;
		if( propertiesFile.startsWith( "file:///" ) ) {
			try {
				url = new URL( propertiesFile );
				return url.getFile();
			} catch( MalformedURLException ex ) {
				System.err.println( "Error with properties file MalformedURLException " + propertiesFile + " Falling back to " + otherwise );
				System.err.println( ex.getMessage() );
				propertiesFile = otherwise;
			}
		}

		url = Thread.currentThread().getContextClassLoader().getResource( propertiesFile );
		return url.getFile();
	}

	/**
	 * Get a property value as a String
	 * @param key the property
	 * @param otherwise the value if it doesn't exist
	 * @return the value or otherwise
	 */
	protected static String get( final String key, final String otherwise )
	{
		return properties.getProperty( key, otherwise );
	}

	/**
	 * get a property as an int
	 *
	 * @param key the property
	 * @param otherwise the value if it doesn't exist
	 * @return the value or otherwise
	 */
	protected static int getInt( final String key, final int otherwise )
	{
		try {
			return Integer.parseInt( properties.getProperty( key ) );
		} catch( Exception ex ) {
			return otherwise;
		}
	}

	/**
	 * get system environment variable
	 *
	 * @param envKey environment variable name to fetch
	 * @param otherwise value if not set
	 * @return the value or otherwise
	 */
	protected static String getEnv( final String envKey, final String otherwise )
	{
		return env.getOrDefault( envKey, otherwise );
	}

	/**
	 *  poll for transaction status
	 *
	 * @param transaction transaction to check the status of
	 * @return the status
	 * @throws IOException network error
	 */
	protected Status getStatus( final Transaction transaction ) throws IOException
	{
		return getStatus( transaction, getInt( "test.status.retries", 60 ) );
	}

	/**
	 * Poll for transaction status
	 *
	 * @param transaction transaction to check the status of
	 * @param attempts how many tries while waiting for validation
	 * @return the status
	 * @throws IOException network error
	 */
	protected Status getStatus( final Transaction transaction, final int attempts ) throws IOException
	{
		for( int idx = 0; idx < attempts; idx++ ) {
			Status status = StatusesApi.getTransactionStatus( transaction.getId() );
			if( status.getStatus().equalsIgnoreCase( "valid" ) )
				return status;
		}

		Status invalid = new Status();
		invalid.setStatus( "timeout" );
		return invalid;
	}

	/**
	 * Wrap exact searches in quotes
	 *
	 * @param stringToQuote to be quoted
	 * @return the quoted string
	 */
	protected static String asQuoted( final String stringToQuote )
	{
		return "\"" + stringToQuote + "\"";
	}

	/**
	 * Get a unique ID
	 *
	 * @return @UUID as a @String
	 */
	protected static String getUUID()
	{
		return UUID.randomUUID().toString();
	}
}
