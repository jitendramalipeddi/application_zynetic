package com.app.startstop.controller;

import com.app.startstop.entity.Transaction;
import com.app.startstop.service.StartTransactionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private StartTransactionHandler startTransactionHandler;

    @GetMapping("/all")
    private List<Transaction> findTransactions(){
        return startTransactionHandler.findAllTransactions();
    }

    @GetMapping("/transactionid")
    private Transaction findByTransactionId(@RequestBody int transacitonId){
        return startTransactionHandler.findTransaction(transacitonId);
    }
    @GetMapping("/chargerid")
    private Transaction findByChargerId(@RequestBody Long chargerId){
        return startTransactionHandler.findBychargerId(chargerId);
    }
}
