package face.appserver;



import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.Arrays;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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
	}
	
}
