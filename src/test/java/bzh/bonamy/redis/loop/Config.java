package bzh.bonamy.redis.loop;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.stereotype.Service;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Service
public class Config {
    @Bean
    JedisConnectionFactory jedisConnectionFactory(GenericContainer<?> redisContainer) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisContainer.getHost(), redisContainer.getFirstMappedPort());
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {

        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new RedisMessageSubscriber());
    }

    @Bean
    ChannelTopic channelTopic() {
        return new ChannelTopic("test");
    }

    @Bean
    MessagePublisher redisPublisher(RedisTemplate<String, Object> redisTemplate, ChannelTopic channelTopic) {
        return new RedisMessagePublisher(redisTemplate, channelTopic);
    }

    @Bean
    RedisMessageListenerContainer redisMessageListenerContainer(MessageListenerAdapter adapter,
                                                                ChannelTopic topic, JedisConnectionFactory jedisConnectionFactory) {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory);
        container.addMessageListener(adapter, topic);
        //container.setRecoveryBackoff(new FixedBackOff(1, 5));
        return container;
    }

    @Bean
    GenericContainer<?> redisContainer() {
        GenericContainer<?> redis =
                new GenericContainer<>(DockerImageName.parse("redis:latest")).withExposedPorts(6379);
        redis.start();
        System.setProperty("spring.data.redis.host", redis.getHost());
        System.setProperty("spring.data.redis.port", redis.getMappedPort(6379).toString());

        System.out.println("Redis started");
        return redis;
    }

}
