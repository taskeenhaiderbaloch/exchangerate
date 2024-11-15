package com.xische.exchangerate.controller;

import com.xische.exchangerate.model.BillCalculationRequest;
import com.xische.exchangerate.model.BillCalculationResponse;
import com.xische.exchangerate.service.ExchangeRateService;
import com.xische.exchangerate.service.MessageService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
@RequestMapping("/v1/{locale}/private/api")
@OpenAPIDefinition(
        info = @Info(
                title = "Bill Calculation Management",
                version = "1.0",
                description = "Bill Calculation Management APIs",
                contact = @Contact(email = "taskeenhaider7@gmail.com")
        ),
        tags = {
                @Tag(name = "V1", description = "Version 1")
        }
)
public class BillCalculationController {

    private final ExchangeRateService exchangeRateService;
    private final MessageService messageService;

    @PostMapping("/calculate")
    @Operation(
            operationId = "calculate",
            tags = "Bill Calculation API",
            summary = "Calculate discount",
            description = "Calculate the payable amount after applying discounts and currency conversion.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "OK", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BillCalculationRequest.class)),
                            @Content(mediaType = "application/json;charset=utf-8", schema = @Schema(implementation = BillCalculationRequest.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)),
                            @Content(mediaType = "application/json;charset=utf-8", schema = @Schema(implementation = Error.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)),
                            @Content(mediaType = "application/json;charset=utf-8", schema = @Schema(implementation = Error.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)),
                            @Content(mediaType = "application/json;charset=utf-8", schema = @Schema(implementation = Error.class))
                    }),
                    @ApiResponse(responseCode = "405", description = "Method Not allowed", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)),
                            @Content(mediaType = "application/json;charset=utf-8", schema = @Schema(implementation = Error.class))
                    }),
                    @ApiResponse(responseCode = "409", description = "Conflict", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)),
                            @Content(mediaType = "application/json;charset=utf-8", schema = @Schema(implementation = Error.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)),
                            @Content(mediaType = "application/json;charset=utf-8", schema = @Schema(implementation = Error.class))
                    })
            }
    )
    @Cacheable(value = "currencyCache", key = "#request.hashCode()")
    @RolesAllowed("USER")
    public ResponseEntity<String> calculateBill(@AuthenticationPrincipal OAuth2AuthenticationToken authentication, @RequestBody BillCalculationRequest request, @PathVariable Locale locale) {

        BillCalculationResponse billCalculationResponse = exchangeRateService.calculateFinalAmount(request);

        // Get response message based on locale
        String responseMessage = messageService.getMessage("bill.payable.amount",
                new Object[]{billCalculationResponse.getFinalAmount()}, locale);

        return ResponseEntity.ok(responseMessage);
    }
}