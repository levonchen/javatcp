package org.appcommon;

import java.io.Serializable;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;


//This class is a convenient place to keep things common to both the client and server.
public class AppNetwork {
	static public final int port = 54555;
	
	static public final int C_Req_Login = 10;
	static public final int C_Rsp_Login = 11;
	static public final int C_Rtn_Login = 12;
	
	
	static public final int C_Req_Add_FaceInfo = 20;
	static public final int C_Rsp_Add_FaceInfo = 21;
	static public final int C_Rtn_Add_FaceInfo = 22;
	
	static public final int C_Req_Check_Exist = 30;
	static public final int C_Rsp_Check_Exist = 31;
	static public final int C_Rtn_Check_Exist = 32;
	
	static public final int C_Req_Update_FaceInfo_Store = 50;
	static public final int C_Rsp_Update_FaceInfo_Store = 51;
	static public final int C_Rtn_Update_FaceInfo_Store = 52;
	

	// This registers objects that are going to be sent over the network.
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		
		kryo.register(FaceObjBase.class);
		kryo.register(ReqLogin.class);
		kryo.register(RspLogin.class);
		kryo.register(RtnLogin.class);
		
		kryo.register(ReqAddFaceInfo.class);
		
		
		
		kryo.register(RegisterName.class);
		kryo.register(String[].class);
		kryo.register(UpdateNames.class);
		kryo.register(ChatMessage.class);
	}
	
	static private int S_Seq = 0;
	
	
	@SuppressWarnings("serial")
	static public class FaceObjBase implements Serializable 
	{		
		public int Command;
		public int Seq;
		
		public FaceObjBase()
		{
			Seq = S_Seq++;
		}
	}
	
	static public class ReqLogin extends FaceObjBase
	{
		public ReqLogin()
		{
			super();			
			this.Command = AppNetwork.C_Req_Login;
		}
		public String Id;
	}
	
	
	static public class RspLogin extends FaceObjBase
	{
		public RspLogin()
		{
			this.Command = AppNetwork.C_Rsp_Login;
		}
		
		public boolean result;
	}
	
	static public class RtnLogin extends FaceObjBase
	{
		public RtnLogin()
		{
			this.Command = AppNetwork.C_Rtn_Login;
		}
		
		public String Id;
		public String msg;
	}
	
	static public class ReqAddFaceInfo extends FaceObjBase
	{
		public String Name;
		public String SerialNum;
		public String CardId;
	}

	
	static public class RegisterName {
		public String name;
	}

	static public class UpdateNames {
		public String[] names;
	}

	static public class ChatMessage {
		public String text;
	}
}