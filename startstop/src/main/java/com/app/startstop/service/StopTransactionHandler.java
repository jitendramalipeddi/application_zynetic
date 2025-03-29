package com.app.startstop.service;

import com.app.startstop.dto.Status;
import com.app.startstop.dto.StopTransactionRequest;
import com.app.startstop.dto.StopTransactionResponse;
import com.app.startstop.entity.Transaction;
import com.app.startstop.repository.TransactionRepository;
import com.bootnotification.app.entity.ChargerStatus;
import com.bootnotification.app.repository.ChargerStatusRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class StopTransactionHandler extends TextWebSocketHandler {
    private static final Logger stopTransactionLogger = LoggerFactory.getLogger(StopTransactionHandler.class);
    private final TransactionRepository transactionRepository;
    private final ChargerStatusRepository chargerStatusRepository;
    private final ObjectMapper objectMapper;
    @Autowired
    public StopTransactionHandler(TransactionRepository transactionRepository, ChargerStatusRepository chargerStatusRepository, ObjectMapper objectMapper) {
        this.transactionRepository = transactionRepository;
        this.chargerStatusRepository = chargerStatusRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ObjectMapper objectMapper = new ObjectMapper();
        StopTransactionRequest stopTransactionRequest = objectMapper.readValue(payload, StopTransactionRequest.class);

        Transaction transaction = transactionRepository.findByTransactionId(stopTransactionRequest.getTransactionId());

        stopTransactionLogger.info("stopTransactionRequest {}",stopTransactionRequest.toString());
        Long chargerId = transaction.getChargerId();
        int transactionId =transaction.getTransactionId();
        if(transaction!=null){
            stopTransactionLogger.info("updating the transaction Status to completed");
            transaction.setStopTime(LocalDateTime.now());
            transaction.setStatus(Status.Completed);
            transactionRepository.save(transaction);
        }
        List<ChargerStatus> chargerStatus = chargerStatusRepository.findByChargerId(chargerId);
        if(!chargerStatus.isEmpty()){
            ChargerStatus status = chargerStatus.get(0);
            status.setStatus("Available"); //changing the status back to Availabel after completing transaciton
            status.setUpdatedAt(LocalDateTime.now());
            chargerStatusRepository.save(status);
        }

        StopTransactionResponse response = new StopTransactionResponse();
        response.setTransactionId(transactionId);
        response.setMessage("comleted successfully");
        String responseString = objectMapper.writeValueAsString(response);
        session.sendMessage(new TextMessage(responseString));
    }
}
