package bzh.bonamy.redis.loop;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.testcontainers.containers.GenericContainer;

import java.util.Map;
import java.util.UUID;

@SpringBootTest
class KafkaApplicationTests4 extends AbstractKafka {

	@Autowired
	private RedisMessagePublisher redisMessagePublisher;

	@TestConfiguration
	static class Config {
		@Bean
		public String bean() {
			return "KafkaApplicationTest4";
		}
	}

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
	void redis4() throws InterruptedException {
		String message = "Message " + UUID.randomUUID();
		redisMessagePublisher.publish(message);
		Thread.sleep(1000);
		simulateRedisCrash();
	}

	private void simulateRedisCrash() throws InterruptedException {
		System.out.println("""
    		################################################################
    		SIGKILL redis now
    		################################################################
				""");
		Thread.sleep(10000);
	}


}
