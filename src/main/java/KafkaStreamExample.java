import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.apache.kafka.streams.StreamsBuilder;

import java.util.Iterator;
import java.util.Properties;

import com.mongodb.client.FindIterable;

public class KafkaStreamExample {

	public void SendMongo( String kafkaData ) {  
	      
		MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017");
		//MongoClientURI uri = new MongoClientURI("mongodb+srv://mongodb:mongodb@cluster0.hdobc.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("plar_db");
		MongoCollection<Document>data=database.getCollection("hl_collection");
		
		// Create a new document to insert
        Document doc = new Document("name", "kk")
                        .append("age", 29)
                        .append("kafkaData", kafkaData);

        // Insert the document into the collection
        data.insertOne(doc);
        
		//////////Filter Data  
//		 BasicDBObject whereQuery = new BasicDBObject();
//		 whereQuery.put("account_id", 77193);	
//		///////////////////////
		FindIterable<Document> dta=data.find();//whereQuery);
//
		int i = 1;
		// Getting the iterator
		Iterator it = dta.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
			i++;
			}
			mongoClient.close();
		 
	}
	
    public static void main(String[] args) {

        // Define properties for Kafka Streams application
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-example");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Kafka broker address
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        // Define the stream processing topology using StreamsBuilder (updated class)
        StreamsBuilder builder = new StreamsBuilder();

        // Create a stream by reading from the input topic "input-topic"
        KStream<String, String> inputStream = builder.stream("input-topic");
        
        String kafkaData=null;
        //
        inputStream.foreach(new ForeachAction<String, String>() {
            public void apply(String key, String value) {
                // You can extract the data here and do something with it
                System.out.println("Extracted data: " + key + " -> " + value);
                new KafkaStreamExample().SendMongo(key+"-->"+value.toUpperCase());
                // You could store values in a variable or process them as needed
                // However, for the sake of stream processing, consider pushing the data to another topic, a state store, or performing computations
            }
        });
        
        
        // Example: Convert each message to uppercase and write to output-topic using lambda expression
        inputStream
            .mapValues(new ValueMapper<String, Object>() {
				public Object apply(String value) {
					return value.toUpperCase();
				}
			})  // Lambda expression to convert value to uppercase
            .to("output-topic");  // Write processed data to the output topic
        
        
        
        // Build and start the Kafka Streams application
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        // Add a shutdown hook to stop the stream gracefully
     //   Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        System.out.println("Kafka Streams application started...");
    }
}


//mvn clean package
//java -cp target\KafkaStream-0.0.1-SNAPSHOT.jar KafkaStreamExample