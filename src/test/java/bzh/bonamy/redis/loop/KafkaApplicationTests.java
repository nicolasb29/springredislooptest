package bzh.bonamy.redis.loop;

import bzh.bonamy.redis.loop.RedisMessagePublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.testcontainers.containers.GenericContainer;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@EmbeddedKafka(topics = {"topic1", "topic2", "singleTopic1"}) // EmbeddedKafkaContextCustomizer
class KafkaApplicationTests  {

	@Autowired
	private RedisMessagePublisher redisMessagePublisher;

	@Autowired
	private GenericContainer<?> redisContainer;


	@Test
	void redis() throws InterruptedException {
		String message = "Message " + UUID.randomUUID();
		redisMessagePublisher.publish(message);
		Thread.sleep(1000);
		simulateRedisCrash();
	}

	private void simulateRedisCrash() throws InterruptedException {
		redisContainer.stop();
		Thread.sleep(10000);
	}


}
