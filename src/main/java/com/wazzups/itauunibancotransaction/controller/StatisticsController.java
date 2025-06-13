package com.wazzups.itauunibancotransaction.controller;

import com.wazzups.itauunibancotransaction.model.dto.StatisticsResponse;
import com.wazzups.itauunibancotransaction.service.TransactionService;
import java.util.List;
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
    public ResponseEntity<List<StatisticsResponse>> getStatistics(@RequestParam(value = "dateInterval", required = false, defaultValue = "30") Long dateInterval) {
        List<StatisticsResponse> statisticsResponses = transactionService.calculateStatistics(dateInterval);
        return ResponseEntity.ok(statisticsResponses);
    }
}
