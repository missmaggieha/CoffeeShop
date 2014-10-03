// Source: Horstmann
// Modified by Peter Liu (March 24, 2003)
// Modified by Maggie Ha and Pedro Bellesa (March 31, 2014)

// 2 classes; CreateDB, DBConnection
// use of a properties file and an SQL file

// properties file name: database.properties
// SQL file name: products.sql, product_info.sql
// table name: products, product_info

import java.sql.*;
import java.io.*;

public class CreateDB {

	public static void main(String args[])
    {
        System.out.println( "Inside CreateDB" );

        /* register the JDBC driver */
	    DBConnection.register( "database.properties" );    // use of properties file

        /* connect to the database and create the database */
        Connection conn = DBConnection.getConnection();

        String table01 = "products";
        String table02 = "product_info";
        
        if ( conn != null )	{	/* test the connection */

	       try {
	    	   runSQLFile( conn, table01 );		// create products table
	    	   runSQLFile( conn, table02 );		// create product_info
	    	   conn.close();
           }
	       catch( SQLException e ) { e.printStackTrace(); }

        }

        System.exit(0);
    } // end main method


   public static void runSQLFile( Connection conn, String fileName ) throws SQLException {

      try {

	       Reader reader = new FileReader(fileName + ".sql" );

           BufferedReader br = new BufferedReader( reader );

	       /* drop the table if it already exists */
           try {
		        Statement dropTable = conn.createStatement();
                dropTable.executeUpdate( "DROP TABLE " + fileName ); // table name: fileName
           }
           catch( SQLException e ) { }

	       System.out.println( "Creating the table: " + fileName );

           Statement stat = conn.createStatement();

	       boolean done = false;

           /* read the SQL file and execute SQL statements */
           while ( !done ) {
        	   
			   String sqlLine = br.readLine();

			   if ( sqlLine == null ) done = true;

			   else {
				   try {
					   if ( stat.execute( sqlLine ) )

						   showResultSet( stat );
			       }
			       catch( SQLException e ) { e.printStackTrace(); }
	     	    }

           } // end while

		   br.close();
		   stat.close();
      }
      catch( IOException ioe ) { ioe.printStackTrace(); }

	} // end runSQLFile


	public static void showResultSet( Statement stat ) {

		try {
			ResultSet rs = stat.getResultSet();

			int columnCount = rs.getMetaData().getColumnCount(); // ResultSetMetaData

			while ( rs.next() ) {

				for ( int i=1; i <= columnCount; i++ )

					System.out.print( rs.getString(i) + " " );

					System.out.println();
			}

			rs.close();
	   }
	   catch( SQLException e ) { e.printStackTrace(); }
   }

} // end class CreateDB

