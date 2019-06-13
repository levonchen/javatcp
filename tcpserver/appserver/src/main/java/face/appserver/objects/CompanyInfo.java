package face.appserver.objects;

import org.bson.types.ObjectId;

public class CompanyInfo {

	//Database Id
	private ObjectId id;
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	private String CompanyName;
	
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}


	
	private byte[] IconData;
	
	public byte[] getIconData() {
		return IconData;
	}
	public void setIconData(byte[] iconData) {
		IconData = iconData;
	}

	
	private String IconName;
	
	public String getIconName() {
		return IconName;
	}
	public void setIconName(String iconName) {
		IconName = iconName;
	}
	
	
}
