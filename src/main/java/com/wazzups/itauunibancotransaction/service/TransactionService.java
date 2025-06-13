package com.wazzups.itauunibancotransaction.service;

import com.wazzups.itauunibancotransaction.model.dto.StatisticsResponse;
import com.wazzups.itauunibancotransaction.model.dto.TransactionRequest;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    @Value("${stats.seconds}")
    private int seconds;

    private final List<TransactionRequest> store = new ArrayList<>();

    public void add(TransactionRequest tx) {
        log.debug("Adding transaction: value={} at {}", tx.getValue(), tx.getDateTime());
        store.add(tx);
        log.info("Transaction count: {}", store.size());
    }

    public void clear() {
        log.debug("Clearing all {} transactions", store.size());
        store.clear();
    }

    public StatisticsResponse getStatistics(Long seconds) {
        seconds = (seconds != null) ? seconds : this.seconds;

        log.debug("Computing statistics for last {} seconds", seconds);
        OffsetDateTime cutOff = OffsetDateTime.now().minusSeconds(seconds);
        DoubleSummaryStatistics stats = store.stream().filter(tx -> !tx.getDateTime().isBefore(cutOff))
            .mapToDouble(tx -> tx.getValue().doubleValue()).summaryStatistics();

        long count = stats.getCount();
        BigDecimal sum = BigDecimal.valueOf(stats.getSum());
        BigDecimal avg = count == 0 ? BigDecimal.ZERO : BigDecimal.valueOf(stats.getAverage());
        BigDecimal min = count == 0 ? BigDecimal.ZERO : BigDecimal.valueOf(stats.getMin());
        BigDecimal max = count == 0 ? BigDecimal.ZERO : BigDecimal.valueOf(stats.getMax());

        return new StatisticsResponse(count, sum, avg, min, max);
    }
}
