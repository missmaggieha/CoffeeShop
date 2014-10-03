// a "mutlithreaded" SERVER program that uses a stream socket connection
// use of DataOutputStream and DataInputStream classes

import java.net.*;
import java.sql.Connection;		// SQL
import java.sql.SQLException;	// SQL
import java.io.*;

public class Server_main{

	public static void main(String[] args) {

	ServerSocket serverSocket;		// TCP server socket used for listening

    System.out.println( "*** a multithreaded server is running ***" );

	try {
		/* Create a server socket */
		serverSocket = new ServerSocket( 8000 );

		/* A loop that listens for connections and creates THREADS with sockets */
		int count = 1;

     	while (true) {

     		System.out.println( "listening for a connection..." );

     		Socket socketConnection = serverSocket.accept();  // listen and wait
                   // socketConnection: a TCP socket that is connected to the client

        	System.out.println( "start a thread for client #" + count );
        	System.out.println( "\t host name: " + socketConnection.getInetAddress().getHostName() +
                                "\t IP address: " + socketConnection.getInetAddress().getHostAddress() );

        	// System.out.println( "at port: " + socketConnection.getPort() );

        	Thread t = new HandleClientThread( socketConnection );
        	t.start();

        	count++;
     	}
	}
	catch(IOException e ) { e.printStackTrace(); }

	System.out.println( "*** the server is going to stop running ***" );

	} // end main
}	// end server main class

class HandleClientThread extends Thread {

	private Socket connection;
	
	public HandleClientThread(Socket sock) { connection = sock; }
	
	public void run() {

		try {
			/* connect input and output streams to the socket */
			DataOutputStream dosToClient = new DataOutputStream( connection.getOutputStream() );
			DataInputStream  disFromClient = new DataInputStream( connection.getInputStream() );
			
			OutputStream socketStream = connection.getOutputStream();
 		 	ObjectOutputStream objectOutput = new ObjectOutputStream(socketStream);
			
			System.out.println( "I/O streams connected to the socket" );

	   		/* exchange data with ONE client */
			try {
				while (true) {  // keep on getting data from the client

					String menu_option = disFromClient.readUTF();
			     	System.out.println( "*** received: " + menu_option );
			     	
			     	QueryDB db = new QueryDB();
			     	Connection conn = db.connectDB();
			     	
		     		// Compute the query
					if ( conn != null ) {		// test the connection
						try {
							
							//db.runQueries( conn, tableName );
							String[][] menu_items = new String[][] {{""}, {""}};
					     	switch( menu_option ) {
					     		case "products" :	System.out.println("In switch... " ); menu_items = db.runQueries( conn, "SELECT name, description FROM products" ); 
					     							break;
					     		case "coffee"	:	menu_items = db.runQueries( conn, "SELECT type, name, description FROM product_info WHERE category_id = 1" ); 
					     							break;
					     		case "latte"	:	menu_items = db.runQueries( conn, "SELECT type, name, description FROM product_info WHERE category_id = 2" ); 
					     							break;
					     		case "otherDrinks": menu_items = db.runQueries( conn, "SELECT type, name, description FROM product_info WHERE category_id = 3" ); 
					     							break;
					     		
					     		/* coffee */
//					     		case "mild": 		menu_items = db.runQueries( conn, "SELECT name, description FROM product_info WHERE category_id = 1 AND type LIKE '%mild%'" ); break;
//					     		case "full": 		
//					     		case "intense": 	
//					     		case "flavoured": 	menu_items = db.runQueries( conn, "SELECT name, description FROM product_info WHERE category_id = 1 AND type LIKE '%flavoured%'" ); break;

//					     		/* latte */
//					     		case "tealattes": 
//					     		case "overice": 
//					     		case "europeans": 
//					     		
//				     			/* otherdrinks */
//					     		case "espressos": 
//					     		case "teas": 
//					     		case "overice2": 
//					     		case "chillers": 
//					     		case "fruitsmoothies": 
//					     		case "secondcupspecialties": 
//					     			
				     			default: break;
					     	}	// end switch
					     	
					     	System.out.println( "-----Sending to Client -----" );
				     		
				     		for( int j = 0 ; j < menu_items.length ; j ++ ) {
					     		for(int i = 0; i < menu_items[j].length; i++){
					     			System.out.println(menu_items[j][i]);
						    	 }
				     		}
							
				     		// Send object to Client
				     		objectOutput.writeObject( menu_items );
				     		objectOutput.flush();
				     		
				     		// Close connection reference object
			 	    		conn.close();
					    }
						catch( SQLException e ) { e.printStackTrace(); }
					}
				}	// end while loop
					
 	   	}
  		catch( EOFException eof ) { System.out.println( "*** the CLIENT has terminated connection ***" ); }

 		/* close the connection to the client */
 		dosToClient.close();
 		disFromClient.close();
     	connection.close();
	}
	catch(IOException e ) { e.printStackTrace(); }

    } // end run

} // end HandleClientThread
