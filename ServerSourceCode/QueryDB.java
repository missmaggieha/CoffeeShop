/* Source: Horstmann
   Modified by Peter Liu (March 24, 2003) 
   Modified by Maggie Ha and Pedro Bellesa (March 28, 2014) */

/* database system: MySQL server */

// MySQL script file name: CoffeeShop.sql
// database name: coffeeshop
// table name: products, product_info

// command line: java QueryDB <connection, query>

import java.sql.*;

public class QueryDB {

    //static final String tableName = "Product";

	// Registers JDBC driver and connect to the database
    public Connection connectDB()
    {
        System.out.println( "Inside connectDB" );

        /* register the JDBC driver */
        /* read the database url, username and password from the properties file */
	    DBConnection.register( "database.properties" );

	    /* Connect to the database */
        Connection conn = DBConnection.getConnection();
        System.out.println ("in connectDB....");
        return conn;

    } // end main method
    
    public String[][] runQueries( Connection conn, String query ) throws SQLException {
    	System.out.println("in runQueries");
    	String queryLine = query + ";";
    	String[][] menu_items = null;
    	
    	try {
    		Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery(queryLine);
		  
			ResultSetMetaData rsmeta = rs.getMetaData();
		  
			int columnCount = rsmeta.getColumnCount();
			
			int rowCount = 0;
			if (rs.last()) {			// move to the last row
				rowCount = rs.getRow();
			    rs.beforeFirst();		// move to beginning
			}
			if ( rowCount != 0 ) {
				menu_items = new String[rowCount][columnCount];	// makes a new row
			
				//System.out.println("rowCount = " + rowCount + ", columnCount = " + columnCount );
				
				int row = 0;
				
				while ( rs.next() && row < rowCount) {

					for( int i = 1; i <= columnCount; i++ ) { 
						
						menu_items[row][i-1] = new String(rs.getString(i));

						//System.out.println( "menu_items[" + row + "][" + (i-1) + "] = " + menu_items[row][i-1] );
						
					}

//					if( rsmeta.getTableName(1).equals("products") ) { }
					
					row++;					
				}
			}
			rs.close();   // close the ResultSet object
			stat.close(); // close the Statement object		  
    	}
    	catch( Exception ioe ) { ioe.printStackTrace(); }
    	
		/* return results to server */
		return menu_items;
    }

	public static void showResultSet( Statement stat ) {

	   try {
		   ResultSet rs = stat.getResultSet();

		   int columnCount = rs.getMetaData().getColumnCount();

		   while ( rs.next() ) {
		
			   for ( int i=1; i <= columnCount; i++ ) {

				   System.out.print( rs.getString(i) + " " );
			   }

			   System.out.println();

		   }
		   rs.close();
	   }
	   catch( SQLException e ) { e.printStackTrace(); }

	}	// end showResultSet method

}	// end class QueryDB

