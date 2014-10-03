// a simple client/server application
// console-based

// a SERVER program that uses a stream socket connection
// use of DataOutputStream and DataInputStream classes

// the server processes only ONE connection

import java.net.*;
import java.io.*;

public class SimpleServer{

   public static void main(String[] args) {

	ServerSocket serverSocket;		// TCP socket used for listening

	try {
           /* step 1: create a server socket
                        port number:     8000
            */

	       serverSocket = new ServerSocket( 8000 );         // create a server socket
							      							// with port number

           /* setp 2: listen for a connection and create a socket
            */
	       System.out.println( "listening for a connection..." );

           Socket socketConnection
                     = serverSocket.accept();  // listen and wait
                                               // socketConnection: a TCP socket
			                                  // that is connected to the client

	     /* step 3: connect input and output streams to the socket
              */
	     DataOutputStream dosToClient = new DataOutputStream(
                                                socketConnection.getOutputStream() );

	     DataInputStream  disFromClient = new DataInputStream(
                                                  socketConnection.getInputStream() );

	     System.out.println( "I/O streams connected to the socket" );

	     /* step 4: exchange data with THE client
              */
         try {
                 while (true) {  // keep on getting data from the client

		             // read data from THE client
                     double radius = disFromClient.readDouble();

                     System.out.println( "*** receive radius from the client: " + radius );

		             // compute the area
                     double area = radius * radius * Math.PI;

		             // send the data to THE client
                     dosToClient.writeDouble( area );
                     dosToClient.flush();

		             // display the result to the screen of the server program
                     System.out.println( "\t*** send area to the client: " + area );

	          	} // end while
	     }
         catch( EOFException eof ) {  // loss of connection
                                      System.out.println( "*** THE client has terminated connection ***" );
                                   }

         catch(IOException e ) {  // I/O error in data exchange
                                  System.out.println( "I/O error in data exchange" );
				                  e.printStackTrace();    								}

	     /* step 5: close the connection to the client
              */
	     dosToClient.close();
	     disFromClient.close();
         socketConnection.close();
	 }
	 catch(IOException e ) {     // I/O error in socket connection
                                 System.out.println( "I/O error in socket connection" );
                                 e.printStackTrace();     }

     System.out.println( "*** the server is going to stop running! ***" );

   } // end main
}
