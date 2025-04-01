# Learn How To Push Data Within Kafka Topics Without Having Need Of Producer / Consumer As Seperate Process

# Prerequisite
- **You Must Have Kafka/Zookeeper Server Available In Your Local Box**
- **You Must Have MongoDB And Mongo Shell Available In Your Local Box**

# Process

- **Enable Zookeeper Server**
    
        <yourLocalKafkaPath>/bin/zookeeper-server-start.sh/bat ../config/zookeeper.properties

- **Enable Kafka Server**

        <yourLocalKafkaPath>/bin/kafka-server-start.sh/bat ../config/server.properties

- **Create Kafka Topics**

		<yourLocalKafkaPath>/bin/kafka-topics.sh --create --topic input-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

		<yourLocalKafkaPath>/bin/kafka-topics.sh --create --topic output-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

- **Enable MongoDB And MongoShell**

          <yourLocalMongoPath>/bin/mongod.exe  --dbpath ../data/db
# Build Java Application 

        mvn clean package

# Run Java Application
**Execute Java Application Using Below Command on Prompt or Use Eclipse Run**
    

		java -cp target\KafkaStream-0.0.1-SNAPSHOT.jar KafkaStreamExample
		
# List Kafka Topics
*List Kafka Topics In Local Box*

		kafka-topics.sh --bootstrap-server kafka:9093 --list

# Kafka Console Producer

*Execute Console Producer*

	    kafka-console-producer.sh --bootstrap-server kafka:9093 --topic input-topic

# Kafka Console Consumer
*Execute Console Consumer*

	    kafka-console-consumer.sh --bootstrap-server kafka:9093 --topic output-topic
