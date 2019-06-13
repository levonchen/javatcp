package face.appserver.objects;

import org.bson.types.ObjectId;

public class UserFace {
	private ObjectId id;
	private String name;
	private String userIdentify;
	private String cardNumber;
	
	//特征值
	private byte[] feature;
	
	private byte[] userImage;
	
	private String ImageName;
	
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserIdentify() {
		return userIdentify;
	}
	public void setUserIdentify(String userIdentify) {
		this.userIdentify = userIdentify;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	
	public byte[] getFeature() {
		return feature;
	}
	public void setFeature(byte[] feature) {
		this.feature = feature;
	}
	
	
	public byte[] getUserImage() {
		return userImage;
	}
	public void setUserImage(byte[] userImage) {
		this.userImage = userImage;
	}
	
	
	public String getImageName() {
		return ImageName;
	}
	public void setImageName(String imageName) {
		ImageName = imageName;
	}
	
}
