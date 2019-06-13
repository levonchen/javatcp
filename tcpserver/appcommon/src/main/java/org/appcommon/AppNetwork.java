package org.appcommon;

import java.io.Serializable;
import java.util.Date;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;


//This class is a convenient place to keep things common to both the client and server.
public class AppNetwork {
	static public final int port = 54555;
	
	static public final int C_Req_Login = 10;
	static public final int C_Rsp_Login = 11;
	static public final int C_Rtn_Login = 12;
	
	
	static public final int C_Req_Add_FaceInfo = 2020;
	static public final int C_Rsp_Add_FaceInfo = 2021;
	static public final int C_Rtn_Add_FaceInfo = 2022;
	
	static public final int C_Req_Delete_FaceInfo = 2030;
	static public final int C_Rsp_Delete_FaceInfo = 2031;
	static public final int C_Rtn_Delete_FaceInfo = 2032;
	
	//更新多个
	static public final int C_Req_Update_FaceInfo = 2040;
	static public final int C_Rsp_Update_FaceInfo = 2041;
	static public final int C_Rtn_Update_FaceInfo = 2042;
	
	//重新同步
	static public final int C_Req_Refresh_FaceInfo = 2050;
	static public final int C_Rsp_Refresh_FaceInfo = 2051;
	static public final int C_Rtn_Refresh_FaceInfo = 2052;
	
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
	
	
	static public class UserFace
	{
		public String id;
		
		public String name;
		
		//人员编号
		public String userIdentify;
		
		//卡号：人卡对比是使用
		public String cardNumber;
		
		//部门名称： 部门信息人脸机上不需要有，所有部门的设置在管理端完成
		public String department;
		
		//特征值
		public byte[] feature;
		
		public byte[] userImage;
		
		public String ImageName;
		
		public Date createDate;
	}
	
	static public class ReqAddFaceInfo extends FaceObjBase
	{
		public ReqAddFaceInfo()
		{
			super();			
			this.Command = AppNetwork.C_Req_Add_FaceInfo;
		}
		public UserFace[] userFaces;
	}
	static public class RspAddFaceInfo extends FaceObjBase
	{
		public RspAddFaceInfo()
		{
			this.Command = AppNetwork.C_Rsp_Add_FaceInfo;
		}
		public Boolean Result;
	}
	
	static public class RtnAddFaceInfo extends FaceObjBase
	{
		public RtnAddFaceInfo()
		{
			this.Command = AppNetwork.C_Rtn_Add_FaceInfo;
		}
		public UserFace[] userFaces;
	}
	
	
	static public class ReqDeleteFaceInfo extends FaceObjBase
	{
		public ReqDeleteFaceInfo()
		{
			super();
			this.Command = AppNetwork.C_Req_Delete_FaceInfo;
		}
		public String[] ids;
	}
	
	static public class RspDeleteFaceInfo extends FaceObjBase
	{
		public RspDeleteFaceInfo()
		{
			this.Command = AppNetwork.C_Rsp_Delete_FaceInfo;
		}
		public Boolean Result;
	}
	
	static public class RtnDeleteFaceInfo extends FaceObjBase
	{
		public RtnDeleteFaceInfo()
		{
			this.Command = AppNetwork.C_Rtn_Delete_FaceInfo;
		}
		public String[] ids;
	}
	
	static public class ReqUpdateFaceInfo extends FaceObjBase
	{
		public ReqUpdateFaceInfo()
		{
			super();
			this.Command = AppNetwork.C_Req_Update_FaceInfo;
		}
		public UserFace[] userFaces;
	}
	
	static public class RspUpdateFaceInfo extends FaceObjBase
	{
		public RspUpdateFaceInfo()
		{
			this.Command = AppNetwork.C_Rsp_Update_FaceInfo;
		}
		public Boolean Result;
	}
	
	static public class RtnUpdateFaceInfo extends FaceObjBase
	{
		public RtnUpdateFaceInfo()
		{
			this.Command = AppNetwork.C_Rtn_Update_FaceInfo;
		}
		public UserFace[] userFaces;
	}
	
	
	static public class ReqRefreshFaceInfo extends FaceObjBase
	{
		public ReqRefreshFaceInfo()
		{
			super();
			this.Command = AppNetwork.C_Req_Refresh_FaceInfo;
		}
		public String DeviceId;
	}
	
	/**
	 * 强制更新客户端信息
	 * 如果 DeleteOld 为 true， 则表示删除掉终端已有的信息
	 * 多次调用只需要第一次为true，后面为false
	 * @author TF
	 *
	 */
	static public class RtnRefreshFaceInfo extends FaceObjBase
	{
		public RtnRefreshFaceInfo()
		{
			this.Command = AppNetwork.C_Rtn_Refresh_FaceInfo;
			
			this.DeleteOld = false;
		}
		
		public UserFace[] userFaces;
		public Boolean DeleteOld;
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