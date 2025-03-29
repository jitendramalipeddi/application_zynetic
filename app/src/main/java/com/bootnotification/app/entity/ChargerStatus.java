package com.bootnotification.app.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name ="charger_status")
public class ChargerStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chargerId;
    private String status;
    private LocalDateTime lastUpdated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getChargingStationId() {
        return chargerId;
    }

    public void setChargingStationId(Long chargingStationId) {
        this.chargerId = chargingStationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedAt() {
        return lastUpdated;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.lastUpdated = updatedAt;
    }
}
