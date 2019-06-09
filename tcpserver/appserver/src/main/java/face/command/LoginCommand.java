package face.command;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.appcommon.AppNetwork;

import com.esotericsoftware.minlog.Log;

public class LoginCommand extends CommandBase{

	@Override
	public void OnExecute(Context ct) {
	    System.out.println( "login" );
	    Log.info("call login command ");
		if(ct.field instanceof AppNetwork.ReqLogin) {
		
			AppNetwork.ReqLogin req = (AppNetwork.ReqLogin)ct.field;
			
			AppNetwork.RspLogin rsp = new AppNetwork.RspLogin();
			rsp.Seq = req.Seq;
			if(req.Id == "admin")
			{
				rsp.result = false;
			}else
			{		
				rsp.result = true;
			}
			
			//发回调消息
			ct.server.sendToTCP(ct.connect.getID(), rsp);
			
			
			AppNetwork.RtnLogin rtn = new AppNetwork.RtnLogin();
			rtn.Id = req.Id;
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	          
			rtn.msg = df.format(new Date());// new Date()为获取当前系统时间
			
			//发广播消息
			ct.server.sendToAllTCP(rtn);
			
		}else
		{
			Log.error("Not support field in LoginCommand");
		}
	}
}
