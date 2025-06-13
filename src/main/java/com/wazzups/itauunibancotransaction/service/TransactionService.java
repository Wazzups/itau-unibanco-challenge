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
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final List<TransactionRequest> store = new ArrayList<>();

    public void add(TransactionRequest tx) {
        store.add(tx);
    }

    public void clear() {
        store.clear();
    }

    public StatisticsResponse getStatistics(Long seconds) {
        OffsetDateTime cutOff = OffsetDateTime.now().minusSeconds(60);
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
