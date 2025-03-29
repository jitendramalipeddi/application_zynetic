package com.bootnotification.app.component;

import com.bootnotification.app.entity.ChargerStatus;
import com.bootnotification.app.repository.ChargerStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class StatusCheckingScheduler {

    private final ChargerStatusRepository chargerStatusRepository;
    public StatusCheckingScheduler(ChargerStatusRepository chargerStatusRepository){
        this.chargerStatusRepository=chargerStatusRepository;
    }
    @Scheduled(fixedRate = 60000)
    public void checkChargerStatus(){
        System.out.println("*** checking the charger status every minute ***");
        List<ChargerStatus> chargerStatuses = chargerStatusRepository.findByStatus("Available");
        for(ChargerStatus chargerStatus:chargerStatuses){
            if(Duration.between(chargerStatus.getUpdatedAt(), LocalDateTime.now()).toMinutes()>5){
                chargerStatus.setStatus("Faulted");
                chargerStatusRepository.save(chargerStatus);
            }
        }

    }
}
