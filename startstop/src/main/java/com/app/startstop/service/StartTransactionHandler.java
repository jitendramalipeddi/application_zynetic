package com.app.startstop.service;

import com.app.startstop.dto.StartTransactionRequest;
import com.app.startstop.dto.StartTransactionResponse;
import com.app.startstop.dto.Status;
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
import java.util.Random;
@Service
public class StartTransactionHandler extends TextWebSocketHandler {
    private static final Logger startTransactionLogger = LoggerFactory.getLogger(StartTransactionHandler.class);
    private  TransactionRepository transactionRepository;
    private  ChargerStatusRepository chargerStatusRepository;
    private  ObjectMapper objectMapper;
    @Autowired
    public StartTransactionHandler(TransactionRepository transactionRepository, ChargerStatusRepository chargerStatusRepository, ObjectMapper objectMapper){
        this.transactionRepository= transactionRepository;
        this.chargerStatusRepository = chargerStatusRepository;
        this.objectMapper=objectMapper;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        StartTransactionRequest transactionRequest = objectMapper.readValue(payload,StartTransactionRequest.class);
        startTransactionLogger.info("startTransactionRequest :{}",transactionRequest.toString());
        List<ChargerStatus> chargerStatus = chargerStatusRepository.findByChargerId(transactionRequest.getConnectorId());
        if(!chargerStatus.isEmpty()){
            ChargerStatus status = chargerStatus.get(0);
            status.setStatus("Charging"); // changing the status of the charger to cahrging after starting the transaction
            status.setUpdatedAt(LocalDateTime.now());
            chargerStatusRepository.save(status);
        }
        Transaction transaction = new Transaction();
        int transactionId = generateTransactionId();
        transaction.setTransactionId(transactionId);
        transaction.setStatus(Status.Accepted);
        transaction.setChargerId(transactionRequest.getConnectorId());
        transaction.setStartTime(LocalDateTime.now());
        transactionRepository.save(transaction);

        StartTransactionResponse response = new StartTransactionResponse();
        response.setStatus(Status.valueOf("Accepted"));
        response.setTransactionId(transactionId);
        String responseString = objectMapper.writeValueAsString(response);

        session.sendMessage(new TextMessage(responseString));
    }
    private int generateTransactionId(){
        // to generate a random 4 digit transaction id
        Random random = new Random();
        return 1000+random.nextInt(9000);
    }

    public List<Transaction> findAllTransactions(){
        return transactionRepository.findAll();
    }

    public Transaction findTransaction(int transactionId){
        return transactionRepository.findByTransactionId(transactionId);
    }

    public Transaction findBychargerId(Long chargerId){
        return transactionRepository.findByChargerId(chargerId);
    }
}
