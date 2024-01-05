package bzh.bonamy.redis.loop;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka(kraft = false, topics = {"topic1", "topic2", "singleTopic1"}) // EmbeddedKafkaContextCustomizer
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AbstractKafka {


    @Autowired
    public EmbeddedKafkaBroker broker;



}
