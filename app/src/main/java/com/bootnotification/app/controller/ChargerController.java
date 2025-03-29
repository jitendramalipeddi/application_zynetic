package com.bootnotification.app.controller;

import com.bootnotification.app.entity.ChargerStatus;
import com.bootnotification.app.repository.ChargerStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/app")
public class ChargerController {
    @Autowired
    ChargerStatusRepository chargerStatusRepository;
    @PostMapping("/heartbeat")
    public ResponseEntity<String> sendHeartBeat(@RequestBody Long chargerId){
        List<ChargerStatus> currentStatus = chargerStatusRepository.findByChargerId(chargerId);
        if(!currentStatus.isEmpty()){
            ChargerStatus status = currentStatus.get(0);
            status.setUpdatedAt(LocalDateTime.now());
            status.setStatus("Available");
            chargerStatusRepository.save(status);
            return ResponseEntity.ok("heartbeat recienved");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("the charger is not ther");
        }


    }
}
