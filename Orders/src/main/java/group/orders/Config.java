package group.orders;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.util.backoff.FixedBackOff;
import org.springframework.kafka.listener.DefaultErrorHandler;

@Configuration
public class Config {
    @Bean
    public DefaultErrorHandler errorHandler() {
        return new DefaultErrorHandler(new FixedBackOff(1000L, 3));
    }

    @Bean
    public Logger logger(){
        return LoggerFactory.getLogger("Orders logger");
    }

    @Bean
    public NewTopic ordersTopic() {
        return TopicBuilder.name("new_orders")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
