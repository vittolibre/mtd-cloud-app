package mtd.cloud.transport.configuration;

import mtd.client.subscriber.mqtt.MqttSubscriber;
import mtd.client.subscriber.mqtt.stomp.StompPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class TransportConfiguration implements WebSocketMessageBrokerConfigurer {

    //@Bean(initMethod="subscribe")
    MqttSubscriber MqttSubscriber(){
        return new MqttSubscriber();
    }

   // @Bean(initMethod = "publish")
    @Bean
    StompPublisher StompPublisher(){
        return new StompPublisher();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:4200").withSockJS();
    }

}
