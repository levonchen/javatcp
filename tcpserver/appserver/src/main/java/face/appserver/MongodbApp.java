package face.appserver;



import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import face.appserver.objects.FacePagination;
import face.appserver.objects.UserFace;

public class MongodbApp {
	private static final MongodbApp instance = new MongodbApp();
	
	public static MongodbApp Instance() {
		return instance;
	}
	
	private MongoClient mongoClient;
	private MongoDatabase database;
	public void init() {
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		
		//MongoClientSettings settings = MongoClientSettings.builder()
		//        .codecRegistry(pojoCodecRegistry)

		 //       .build();
		
        //.applyToClusterSettings(builder ->
    	//builder.hosts(Arrays.asList(new ServerAddress("127.0.0.1", 27017))))
        
		mongoClient = new MongoClient( "127.0.0.1" , 27017 );
		//mongoClient = (MongoClient) MongoClients.create(settings);
		
		database = mongoClient.getDatabase("facev1");
		
		database = database.withCodecRegistry(pojoCodecRegistry);
	}
	
	public void insert() {
		MongoCollection<UserFace> collection = database.getCollection("userface", UserFace.class);
	
		UserFace obj = new UserFace();
		obj.setName("test");
		obj.setUserIdentify("8541253652515425852");
		obj.setCardNumber("800001");
		
		collection.insertOne(obj);
		
		
		List<UserFace> items = new ArrayList<UserFace>();
		
		for(int index = 1; index < 50; index++)
		{
			UserFace uf = new UserFace();
			
			uf.setName("小崔" + index);
			uf.setUserIdentify("HR00" + index);
			uf.setCardNumber("200000009-" + index);
			
			items.add(uf);
		}
		
		collection.insertMany(items);
	
	}
	
	/*
	 * pageIndex:start from 1
	 */
	public FacePagination<?> page(int pageIndex,int countPerPage)
	{
		if(countPerPage < 10)
		{
			countPerPage = 10;
		}
		
		MongoCollection<UserFace> collection = database.getCollection("userface", UserFace.class);
		
		long count = collection.countDocuments();
		
		FindIterable<UserFace> itors = collection.find().skip(countPerPage*(pageIndex -1)).limit(countPerPage);
		
		List<UserFace> items = new ArrayList<UserFace>();
		for(UserFace uf : itors)
		{
			items.add(uf);
		}
		
		FacePagination<UserFace> ret = new FacePagination<UserFace>();
		ret.items = items;
		ret.pageIndex = pageIndex;
		ret.countPerPage = countPerPage;
		
		int size = (int) (count/countPerPage);
		int left = (int)(count%countPerPage);
		if(left > 0)
		{
			size++;
		}
		ret.pageSize = size;
		
		return ret;
	}
	
	
}
