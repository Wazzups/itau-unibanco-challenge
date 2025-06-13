package com.wazzups.itauunibancotransaction.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionRequest {

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true, message = "value must be >= 0")
    private BigDecimal value;

    @NotNull
    @PastOrPresent(message = "dateTime must not be in the future")
    private OffsetDateTime dateTime;
}
