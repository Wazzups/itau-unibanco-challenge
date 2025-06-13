package com.wazzups.itauunibancotransaction.controller;

import com.wazzups.itauunibancotransaction.model.dto.TransactionRequest;
import com.wazzups.itauunibancotransaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Void> addTransaction(@RequestBody TransactionRequest request) {
        transactionService.addTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTransactions() {
        transactionService.cleanTransaction();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
