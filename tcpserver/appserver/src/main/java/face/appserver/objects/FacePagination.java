package face.appserver.objects;

import java.util.List;

public class FacePagination <T>{
	public List<T> items = null;
	
	
	public int pageSize;
	
	public int countPerPage;
	
	public int pageIndex;
}
