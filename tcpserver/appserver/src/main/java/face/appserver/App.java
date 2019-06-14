package face.appserver;

import java.io.IOException;

import org.appcommon.AppNetwork;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import face.appserver.objects.FacePagination;
import face.appserver.objects.UserFace;
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
    		
    		//TestMongodb();
   
    		String result = LicenseUtil.GenerateLicense("f81de0fb4569c602a9db466f120ddd1b");
    		
    		Log.debug(result);
    		
			new AppServer();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }  
    
    public static void TestMongodb()
    {
 		MongodbApp.Instance().init();
		
		MongodbApp.Instance().insert();
		
		FacePagination<UserFace> faces =  (FacePagination<UserFace>) MongodbApp.Instance().page(1, 10);
		
		System.out.println("Page Index:" + 1);
		
		for(UserFace uf: faces.items)
		{
			System.out.println("name:" +  uf.getName()  + " Identity:" + uf.getUserIdentify() + " Card:" + uf.getCardNumber());
		}
		
		int index = 1;
		
		for(index = 2; index <= faces.pageSize; index++)
		{
			FacePagination<UserFace> facesTmp =  (FacePagination<UserFace>) MongodbApp.Instance().page(index, 10);
			
			System.out.println("Page Index:" + index);
			
			for(UserFace uf: facesTmp.items)
			{
				System.out.println("name:" +  uf.getName()  + " Identity:" + uf.getUserIdentify() + " Card:" + uf.getCardNumber());
			}
		}
    }
    


}
