package bzh.bonamy.redis.loop;

import bzh.bonamy.redis.loop.RedisMessagePublisher;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.testcontainers.containers.GenericContainer;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class KafkaApplicationTests extends AbstractKafka {

	@Autowired
	private RedisMessagePublisher redisMessagePublisher;

	@Test
	void a() {
		Map<String, Object> producerProps = KafkaTestUtils.producerProps(broker);
		KafkaProducer<Integer, String> producer = new KafkaProducer<>(producerProps);
		producer.send(new ProducerRecord<>("singleTopic1", 0, 1, "foo"));
		producer.close();
		System.out.println("Producer");

	}

	@Test
	void b() {
		Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("ktuTests1", "false", broker);
		KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(consumerProps);
		broker.consumeFromAllEmbeddedTopics(consumer);
		Object o = KafkaTestUtils.getSingleRecord(consumer, "singleTopic1");
		consumer.close();
	}


	@Test
	void redis() throws InterruptedException {
		String message = "Message " + UUID.randomUUID();
		redisMessagePublisher.publish(message);
		Thread.sleep(1000);
	}




}
