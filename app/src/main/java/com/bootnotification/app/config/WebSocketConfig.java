package com.bootnotification.app.config;

import com.bootnotification.app.component.BootNotificationHandler;
import com.bootnotification.app.repository.ChargerStatusRepository;
import com.bootnotification.app.repository.ChargingStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private ChargingStationRepository chargingStationRepository;
    @Autowired
    private ChargerStatusRepository chargerStatusRepository;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new BootNotificationHandler(chargingStationRepository,chargerStatusRepository),"/ws/bootNotification");
    }

}
