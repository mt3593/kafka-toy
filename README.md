
Move to the kafka directory in test/resources/kafka-version

Start up zookeeper:

./bin/zookeeper-server-start.sh config/zookeeper.properties 

Start up kafka:

./bin/kafka-server-start.sh config/server.properties 

Create a topic:

./bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic test --partitions 1 --replication-factor 1

Add messages to topic:

./bin/kafka-console-producer.sh  --broker-list localhost:9092 --topic test

Listen for messages on topic:

./bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test
