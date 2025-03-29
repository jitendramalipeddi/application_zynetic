package com.app.startstop.configurator;

import com.app.startstop.repository.TransactionRepository;
import com.app.startstop.service.StartTransactionHandler;
import com.app.startstop.service.StopTransactionHandler;
import com.bootnotification.app.repository.ChargerStatusRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ChargerStatusRepository chargerStatusRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new StartTransactionHandler(transactionRepository,chargerStatusRepository,objectMapper),"/ws/starttransaction").setAllowedOrigins("*");
        registry.addHandler(new StopTransactionHandler(transactionRepository,chargerStatusRepository,objectMapper),"/ws/stoptransaction").setAllowedOrigins("*");
    }
    @PostConstruct
    public void testingConfig(){
        System.out.println("started the websocket configurations");
    }
    @Bean
    public WebSocketConfig testConfigWebsocket(){
        System.out.println("*************testin the initialization of websocket handler*************");
        return new WebSocketConfig();
    }

}
