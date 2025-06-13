package com.wazzups.itauunibancotransaction.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wazzups.itauunibancotransaction.model.dto.TransactionRequest;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper mapper;

    @BeforeEach
    void clear() throws Exception {
        mockMvc.perform(delete("/transaction")).andExpect(status().isOk());
    }

    @Test
    void postValidThen201() throws Exception {
        TransactionRequest tx = new TransactionRequest();
        tx.setValue(new BigDecimal("12.24"));
        tx.setDateTime(OffsetDateTime.now().minusSeconds(10));

        mockMvc.perform(post("/transaction")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(tx))).andExpect(status().isCreated());
    }

    @Test
    void postFutureThen422() throws Exception {
        TransactionRequest tx = new TransactionRequest();
        tx.setValue(new BigDecimal("12.24"));
        tx.setDateTime(OffsetDateTime.now().plusSeconds(10));

        mockMvc.perform(post("/transaction")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(tx))).andExpect(status().isUnprocessableEntity());
    }

    @Test
    void statsEmptyThenZeros() throws Exception {
        mockMvc.perform(get("/statistics"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.count").value(0))
            .andExpect(jsonPath("$.sum").value(0.0))
            .andExpect(jsonPath("$.avg").value(0.0))
            .andExpect(jsonPath("$.min").value(0.0))
            .andExpect(jsonPath("$.max").value(0.0));
    }

}
