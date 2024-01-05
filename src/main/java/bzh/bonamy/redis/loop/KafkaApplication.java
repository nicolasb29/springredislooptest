package bzh.bonamy.redis.loop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaApplication {
// RedisMessageListenerContainer::protected void handleSubscriptionException
	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}


}
