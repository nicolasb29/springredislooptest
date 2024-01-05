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
class KafkaApplicationTests3 extends AbstractKafka {

	@Autowired
	private RedisMessagePublisher redisMessagePublisher;

	@TestConfiguration
	static class Config {
		@Bean
		public String bean() {
			return "KafkaApplicationTests3";
		}
	}


	@Test
	void redis3() throws InterruptedException {
		String message = "Message " + UUID.randomUUID();
		redisMessagePublisher.publish(message);
		Thread.sleep(1000);
	}


}
