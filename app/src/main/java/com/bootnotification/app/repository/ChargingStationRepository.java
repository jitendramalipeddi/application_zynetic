package com.bootnotification.app.repository;

import com.bootnotification.app.entity.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargingStationRepository extends JpaRepository<ChargingStation,Long> {
}
