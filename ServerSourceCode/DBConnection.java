// used by CreateDB, QueryDB

// use of a properties file
// properties file name: database.properties

/* create a table
   collection name: MYBANKAxx or MYBANKBxx (AS/400 library name),
		    part of the database URL from the properties file

   Note: The library MYBANKAxx or MYBANKBxx has been created already.
 */


import java.sql.*;
import java.io.*;
import java.util.Properties;

class DBConnection {

    private static String url; 		// database URL
    private static String userid;
    private static String password;

    public static void register( String fileName ) {

	   Properties prop = new Properties();

	   try {
	          FileInputStream fis = new FileInputStream( fileName );

	          prop.load( fis );        // load the properties file
	   }
	   catch (IOException ioe ) { ioe.printStackTrace(); System.exit(1); }

	   String driver;
	   driver   = prop.getProperty( "jdbc.driver" );
	   url      = prop.getProperty( "jdbc.url" );
	   userid   = prop.getProperty( "jdbc.userid" );
	   password = prop.getProperty( "jdbc.password" );

	   try {
	           Class.forName( driver );  // load the Java class(i.e. JDBC driver) at run time
	   }
	   catch( ClassNotFoundException ec) { ec.printStackTrace();
					                       System.exit(1);
					                     }

       System.out.println( "database driver registered");
    }


    public static Connection getConnection() {

        Connection conn = null;

	    System.out.println( "Connecting to the system: " + url + "...");

	    //System.out.println( "username: " + userid + " password: " + password );

	    try {

	      /* datasbase URL: jdbc:<subprotocol>:<system url>/<collection name> */

          conn = DriverManager.getConnection( url, userid, password );
        }
        catch (SQLException exc)
        {
          System.out.println( "connection failed with: " +
                              exc.getMessage() );
          return conn;
        }

        System.out.println( "connection ok" );
        return conn;
    }

    // for testing purpose
    public static void main( String [] args ) {

		DBConnection.register( "database.properties" );

		System.out.println( "confidential data:\n" +
		                    DBConnection.userid + "\n" +
		                    DBConnection.password + "\n" +
		                    DBConnection.url );

		DBConnection.getConnection();

		System.out.println( "DBConnection testing finished..." );

    }
}
