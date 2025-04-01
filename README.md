# Learn How To Push Data Within Kafka Topics Without Having Need Of Producer / Consumer As Seperate Process

# Prerequisite
- **You Must Have Kafka/Zookeeper Server Available In Your Local Box**
- **You Must Have MongoDB And Mongo Shell Available In Your Local Box**

# Process

- **Enable Zookeeper Server**
    
        <yourLocalKafkaPath>/bin/zookeeper-server-start.sh/bat ../config/zookeeper.properties

- **Enable Kafka Server**

        <yourLocalKafkaPath>/bin/kafka-server-start.sh/bat ../config/server.properties

- **Enable MongoDB And MongoShell**

          <yourLocalMongoPath>/bin/mongod.exe  --dbpath ../data/db

# Build Java Application 

        mvn clean package

# Run Java Application
**Execute Java Application Using Below Command on Prompt or Use Eclipse Run**
    

        java -cp target\KafkaStream-0.0.1-SNAPSHOT.jar KafkaStreamExample