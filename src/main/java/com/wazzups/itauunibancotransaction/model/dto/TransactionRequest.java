package com.wazzups.itauunibancotransaction.model.dto;

import java.time.OffsetDateTime;

public record TransactionRequest(Double value, OffsetDateTime dateTime) {
}
