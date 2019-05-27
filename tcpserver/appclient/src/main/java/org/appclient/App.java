package org.appclient;


import com.esotericsoftware.minlog.Log;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        Log.set(Log.LEVEL_DEBUG);
		new AppClient();
    }
}
