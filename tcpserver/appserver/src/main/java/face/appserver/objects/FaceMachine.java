package face.appserver.objects;

import org.bson.types.ObjectId;



public class FaceMachine {
	
	//Database Id
	private ObjectId id;
	
	//Device Id
	private String DeviceId;
	
	private Boolean IsOnline;
	
	private Boolean IsActivated;
	
	private String Ip;
	
	private String Name;
	
	private String Comments;
	
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	
	public String getDeviceId() {
		return DeviceId;
	}
	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}
	
	
	public Boolean getIsOnline() {
		return IsOnline;
	}
	public void setIsOnline(Boolean isOnline) {
		IsOnline = isOnline;
	}
	
	
	public Boolean getIsActivated() {
		return IsActivated;
	}
	public void setIsActivated(Boolean isActivated) {
		IsActivated = isActivated;
	}
	
	
	public String getIp() {
		return Ip;
	}
	public void setIp(String ip) {
		Ip = ip;
	}
	
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	
	public String getComments() {
		return Comments;
	}
	public void setComments(String comments) {
		Comments = comments;
	}
}
