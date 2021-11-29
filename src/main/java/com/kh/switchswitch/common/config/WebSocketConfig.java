package com.kh.switchswitch.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

//@Configuration
//@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
	
	
	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
        		.simpDestMatchers("/alarm/*").hasAnyAuthority("B")
                .simpDestMatchers("/chatting/*").hasAnyAuthority("B","C")
                .simpTypeMatchers().denyAll()
                .anyMessage().denyAll(); ;
    }
	
	@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/alarm")
        .addInterceptors(new HttpSessionHandshakeInterceptor())
        .setAllowedOrigins("http://localhost:9090")
        .withSockJS();  
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
    	//SimpAnnotationMethod (Controller->MessageMapping)
    	config.setApplicationDestinationPrefixes("/app"); 
    	//StompBrokerRelay
        config.enableSimpleBroker("/subscribe","/send"); 
    }
	
	@Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }

}
