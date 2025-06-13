package com.wazzups.itauunibancotransaction.controller;

import com.wazzups.itauunibancotransaction.model.dto.StatisticsResponse;
import com.wazzups.itauunibancotransaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<StatisticsResponse> getStatistics(@RequestParam(value = "seconds", required = false, defaultValue = "30") Long seconds) {
        StatisticsResponse statisticsResponses = transactionService.getStatistics(seconds);
        return ResponseEntity.ok(statisticsResponses);
    }
}
