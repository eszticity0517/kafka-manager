import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/** Creates a topic if it does not exist */
public class TopicCreator {
    public static void createTopicIfNotExistent() {

        Map<String, List<PartitionInfo>> topics;
        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, IKafkaConstants.GROUP_ID_CONFIG);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        topics = consumer.listTopics();
        consumer.close();

        if (topics.size() == 0 || !topics.containsKey(IKafkaConstants.TOPIC_NAME))
        {
           Properties adminProperties = new Properties();
           adminProperties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);

           // Creating an administrative client who can manage and inspect topics, brokers, configs etc.
           try (Admin admin = Admin.create(adminProperties)) {
               int partitions = 100;
               short replicationFactor = 1;
               NewTopic newTopic = new NewTopic(IKafkaConstants.TOPIC_NAME, partitions, replicationFactor);

               CreateTopicsResult result = admin.createTopics(Collections.singleton(newTopic));
               KafkaFuture<Void> future = result.values().get(IKafkaConstants.TOPIC_NAME);
               future.get();

               System.out.println("Topic created with name: " + IKafkaConstants.TOPIC_NAME);
           } catch (ExecutionException e) {
               e.printStackTrace();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
        }
        else {
            System.out.println("Topic found with name:" + IKafkaConstants.TOPIC_NAME);
        }
    }
}
