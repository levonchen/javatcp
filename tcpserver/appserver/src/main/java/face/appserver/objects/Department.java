package face.appserver.objects;

import org.bson.types.ObjectId;

public class Department {
	
	private ObjectId id;
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}

	public String getParentId() {
		return ParentId;
	}
	public void setParentId(String parentId) {
		ParentId = parentId;
	}

	private String Name;
	
	private String ParentId;
	
}
