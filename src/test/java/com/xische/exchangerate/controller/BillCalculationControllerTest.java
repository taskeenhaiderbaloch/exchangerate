package com.xische.exchangerate.controller;

import com.xische.exchangerate.model.BillCalculationRequest;
import com.xische.exchangerate.model.BillCalculationResponse;
import com.xische.exchangerate.service.ExchangeRateService;
import com.xische.exchangerate.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Locale;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BillCalculationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExchangeRateService exchangeRateService;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private BillCalculationController billCalculationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(billCalculationController).build();
    }

    @Test
    public void testCalculateBill_ValidRequest_ShouldReturnOk() throws Exception {
        // Arrange
        BillCalculationRequest request = new BillCalculationRequest();
        request.setOriginalCurrency("USD");
        request.setTargetCurrency("EUR");

        BillCalculationResponse response = new BillCalculationResponse();
        response.setFinalAmount(BigDecimal.valueOf(90.0));

        when(exchangeRateService.calculateFinalAmount(any(BillCalculationRequest.class)))
                .thenReturn(response);
        when(messageService.getMessage(anyString(), any(), eq(Locale.ENGLISH)))
                .thenReturn("Payable Amount: 90.0");

        // Act & Assert
        mockMvc.perform(post("/v1/en/private/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"items\": [], \"user\": { \"id\": 1 }, \"originalCurrency\": \"USD\", \"targetCurrency\": \"EUR\" }"))
                .andExpect(status().isOk())
                .andExpect(content().string("Payable Amount: 90.0"));
    }

    @Test
    public void testCalculateBill_BadRequest_ShouldReturnBadRequest() throws Exception {
        // Arrange: No body content (Bad request)
        String invalidJson = "";

        // Act & Assert
        mockMvc.perform(post("/v1/en/private/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCalculateBill_ValidRequest_CacheableResponse() throws Exception {
        // Arrange
        BillCalculationRequest request = new BillCalculationRequest();
        request.setOriginalCurrency("USD");
        request.setTargetCurrency("EUR");

        BillCalculationResponse response = new BillCalculationResponse();
        response.setFinalAmount(BigDecimal.valueOf(90.0));

        when(exchangeRateService.calculateFinalAmount(any(BillCalculationRequest.class)))
                .thenReturn(response);
        when(messageService.getMessage(anyString(), any(), eq(Locale.ENGLISH)))
                .thenReturn("Payable Amount: 90.0");

        // Act & Assert (Check Cacheable Response)
        mockMvc.perform(post("/v1/en/private/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"items\": [], \"user\": { \"id\": 1 }, \"originalCurrency\": \"USD\", \"targetCurrency\": \"EUR\" }"))
                .andExpect(status().isOk())
                .andExpect(content().string("Payable Amount: 90.0"));
        verify(exchangeRateService, times(1)).calculateFinalAmount(any(BillCalculationRequest.class));
    }
}
