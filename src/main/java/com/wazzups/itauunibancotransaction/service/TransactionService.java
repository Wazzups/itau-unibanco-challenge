package com.wazzups.itauunibancotransaction.service;

import com.wazzups.itauunibancotransaction.infra.exceptions.UnprocessableException;
import com.wazzups.itauunibancotransaction.model.dto.StatisticsResponse;
import com.wazzups.itauunibancotransaction.model.dto.TransactionRequest;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private List<TransactionRequest> transactionList = new ArrayList<>();

    public void addTransaction(TransactionRequest dto) {
        log.info("Init processing transaction");

        if (dto.dateTime().isAfter(OffsetDateTime.now())) {
            log.error("Datetime after now datetime");
            throw new UnprocessableException("Datetime after now datetime");
        }

        if (dto.value() < 0) {
            log.error("Value shouldn't be lesser than 0");
            throw new UnprocessableException("Value shouldn't be lesser than 0\"");
        }

        transactionList.add(dto);
    }

    public void cleanTransaction() {
        transactionList.clear();
    }

    public List<TransactionRequest> getTransactions(Long seconds) {
        OffsetDateTime timeInterval = OffsetDateTime.now().minusSeconds(seconds);

        return transactionList.stream().filter(t -> t.dateTime().isAfter(timeInterval)).toList();
    }

    public List<StatisticsResponse> calculateStatistics(Long seconds) {
        List<TransactionRequest> transactions = getTransactions(seconds);

        DoubleSummaryStatistics stats = transactions.stream().collect(Collectors.summarizingDouble(TransactionRequest::value));

        stats.

    }
}
