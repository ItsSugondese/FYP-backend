package fyp.canteen.fypapi.config.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Autowired
    private HandShakeHandlerConfig handShakeHandlerConfig;


    @Value("${base.origin}")
    private String[] origins;
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws")
//                .setAllowedOriginPatterns(origins)
                .setAllowedOrigins("http://localhost:4200")
//                .setAllowedOriginPatterns(origins.toArray(new String[0]))
                .setHandshakeHandler(handShakeHandlerConfig)
                .withSockJS()
                .setHeartbeatTime(900000);
    }
}



