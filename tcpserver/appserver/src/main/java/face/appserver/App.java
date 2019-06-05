package face.appserver;

import java.io.IOException;

import org.appcommon.AppNetwork;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import face.utils.LicenseUtil;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Log.set(Log.LEVEL_DEBUG);
    	
    	try {
    		
    		//MongodbApp.Instance().init();
    		
    		//MongodbApp.Instance().insert();
    		String result = LicenseUtil.GenerateLicense("f81de0fb4569c602a9db466f120ddd1b");
    		
    		Log.debug(result);
    		
			new AppServer();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }  
    


}
