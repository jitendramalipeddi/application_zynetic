package com.app.startstop.entity;

import com.app.startstop.dto.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class Transaction {
    @Id
    private int transactionId;
    private Long chargerId;
    private LocalDateTime startTime;
    private LocalDateTime stopTime;
    @Enumerated(EnumType.STRING)
    private Status status;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Long getChargerId() {
        return chargerId;
    }

    public void setChargerId(Long chargerId) {
        this.chargerId = chargerId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStopTime() {
        return stopTime;
    }

    public void setStopTime(LocalDateTime stopTime) {
        this.stopTime = stopTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
