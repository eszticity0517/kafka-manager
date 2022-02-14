public interface IKafkaConstants {
    /** The Kafka broker's address. If Kafka is running in a cluster then you can provide comma (,) seperated addresses.
     * For example: localhost:9091,localhost:90 */
    String KAFKA_BROKERS = "localhost:9092";

    /** Messages to sent for testing */
    Integer MESSAGE_COUNT=1000;

    /** Only relevant when there is a restricted access defined for each topics */
    String CLIENT_ID="client1";

    /** The topic to subscribe to */
    String TOPIC_NAME="demo";

    /** The consumer group ID */
    String GROUP_ID_CONFIG="consumerGroup1";

    /** The threshold of polling with no messages coming back, for testing */
    Integer MAX_NO_MESSAGE_FOUND_COUNT=100;

    /** will cause the consumer to fetch records from the new records.
     * By new records mean those created after the consumer group became active.*/
    String OFFSET_RESET_LATEST="latest";

    /** Will cause the consumer to fetch records from the beginning of offset i.e from zero*/
    String OFFSET_RESET_EARLIER="earliest";

    /** The max count of records that the consumer will fetch in one iteration. */
    Integer MAX_POLL_RECORDS=1;
}
