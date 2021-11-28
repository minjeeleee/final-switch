package com.kh.switchswitch.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.kh.switchswitch.alarm.handler.AlarmHandler;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketConfigurer, WebSocketMessageBrokerConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new AlarmHandler(), "/alarm")
				//to customize the initial HTTP WebSocket handshake request
				//.addInterceptors(new HttpSessionHandshakeInterceptor())
				//Allow only same-origin requests (default)
				//Allow all origins - *
				//Allow a specified list of origins - must start with http:// or https://
				.setAllowedOrigins("http://localhost:9090")
				//Enabling SockJS
				.withSockJS();
		
	}
	
	@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/connect")
        .setAllowedOrigins("http://localhost:9090")
        .withSockJS();  
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
    	//SimpAnnotationMethod (Controller->MessageMapping)
    	config.setApplicationDestinationPrefixes("/send"); 
    	//StompBrokerRelay
        config.enableSimpleBroker("/broker"); 
    }
	
	@Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }

}
