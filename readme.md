
BTP400 Assignment 2
Authors: Pedro Bellesa and Maggie Ha
Date: April 1, 2014

Tested with Android Developer Tools (ADT) Build: v22.3.0-887826
and JRE7/JDK7

/* STEP 1: SERVER AND DATABASE */

    // For mysql
    Have mysql 5.5+ installed

    By default, the "root" user's password is "" (Password field blank/no password)
    
    If your password is different for root user, change the password in "database.properties" file
    
    In command line: 
    
    // This will create the required database tables for querying.
    javac CreateDB.java
    java CreateDB
    
    // Start multithreaded server
    javac Server_main.java
    java Server_main
        
/* STEP 2: RUNNING THE APP */

    Start your Android emulator.

Import the project zip file into ADT, open the build path configuration and make sure all libraries are found: 

	android-support-v4.jar
	android-support-v7.appcompat.jar
	mysql-connector-java-5.1.29-bin.jar

These are found in the libs folder of the project folder.

Remove double referenced libraries if any. (Error tag)

Compile and Run.

ALTERNATE INSTRUCTIONS (Preferred method):

    In command line,

    Navigate to your ADT Bundle folder, under "sdk" and under "platform-tools"
    Example: cd  /home/android-adt/sdk/platform-tools/

    From this folder, type:
     adb devices
     
    It will show a list of emulated devices currently running. The emulator has to be running.

    Type:
     adb install <Coffeeshop.apk location>
     
    * Fill in brackets with the path to where the apk file is in your computer.

    It should install the apk in the emulator.
    Example:
         1083 KB/s (338651 bytes in 0.305s)
         pkg: /data/local/tmp/Coffeeshop.apk
         Success

    Then with the server running, open your App Tray in the Android emulator and open Coffeeshop app.
    
 