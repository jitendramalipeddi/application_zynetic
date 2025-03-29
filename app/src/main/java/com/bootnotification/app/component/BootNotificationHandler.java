package com.bootnotification.app.component;

import com.bootnotification.app.dto.BootNotificationRequest;
import com.bootnotification.app.dto.BootNotificationResponse;
import com.bootnotification.app.entity.ChargerStatus;
import com.bootnotification.app.entity.ChargingStation;
import com.bootnotification.app.repository.ChargerStatusRepository;
import com.bootnotification.app.repository.ChargingStationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.sound.midi.Soundbank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class BootNotificationHandler extends TextWebSocketHandler {
    private final Logger bootNotificationLogger = LoggerFactory.getLogger(BootNotificationHandler.class);
    private ChargingStationRepository chargingStationRepository;
    @Autowired
    private ChargerStatusRepository chargerStatusRepository;

    public BootNotificationHandler(ChargingStationRepository chargingStationRepository,ChargerStatusRepository chargerStatusRepository) {
        this.chargingStationRepository = chargingStationRepository;
        this.chargerStatusRepository = chargerStatusRepository;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ObjectMapper objectMapper = new ObjectMapper();
        BootNotificationRequest request = objectMapper.readValue(payload,BootNotificationRequest.class);
        bootNotificationLogger.info("sending the bootnotification request: {}",request.toString());
        //saving the boot notification details into the charging stations table database
        ChargingStation station = new ChargingStation();
        station.setVendor(request.getChargePointVendor());
        station.setModel(request.getChargePointModel());
        station.setStatus("Accepted");
        station.setLastUpdated(LocalDateTime.now());
        ChargingStation savedStation = chargingStationRepository.save(station);
        //using the above generated ID as the foreign key, save the status as available in charger status
        ChargerStatus chargerStatus = new ChargerStatus();
        chargerStatus.setChargingStationId(savedStation.getId());
        chargerStatus.setStatus("Available");
        chargerStatus.setUpdatedAt(LocalDateTime.now());
        chargerStatusRepository.save(chargerStatus);
        //response to the websocket connection
        BootNotificationResponse response = new BootNotificationResponse();
        response.setStatus("Accepted");
        response.setInterval(60);
        LocalDateTime currentTime = LocalDateTime.now();
        response.setCurrentTime(currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        bootNotificationLogger.info("bootNotificationResponse: {}",response.toString());
        String responseJson = objectMapper.writeValueAsString(response);
        session.sendMessage(new TextMessage(responseJson));
    }
}
