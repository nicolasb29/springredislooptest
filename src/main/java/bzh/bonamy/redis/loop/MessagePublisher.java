package bzh.bonamy.redis.loop;

public interface MessagePublisher {

    void publish(final String message);
}