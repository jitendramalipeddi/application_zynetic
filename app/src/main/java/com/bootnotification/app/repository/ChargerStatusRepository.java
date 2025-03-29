package com.bootnotification.app.repository;

import com.bootnotification.app.entity.ChargerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ChargerStatusRepository extends JpaRepository<ChargerStatus,Long> {
    List<ChargerStatus> findByChargerId(Long chargingStationId);
    List<ChargerStatus> findByStatus(String status);
}
