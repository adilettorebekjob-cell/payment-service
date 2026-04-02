package kz.almaplus.paymentservicetest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kz.almaplus.paymentservicetest.dto.request.CreatePaymentRequest;
import kz.almaplus.paymentservicetest.dto.response.ClientPaymentsResponse;
import kz.almaplus.paymentservicetest.dto.response.PaymentResponse;
import kz.almaplus.paymentservicetest.dto.response.PaymentStatusResponse;
import kz.almaplus.paymentservicetest.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Payment API", description = "Endpoints for managing payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Create a new payment", description = "Creates a payment with PENDING status by default")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Payment created successfully",
                    content = @Content(schema = @Schema(implementation = PaymentStatusResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/payments")
    public ResponseEntity<PaymentStatusResponse> createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createPayment(request));
    }

    @Operation(summary = "Get payment details", description = "Retrieves full payment information by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment found",
                    content = @Content(schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @GetMapping("/payments/{paymentId}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable String paymentId) {
        return ResponseEntity.ok(paymentService.getPayment(paymentId));
    }

    @Operation(summary = "Confirm payment", description = "Changes payment status to CONFIRMED")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment confirmed",
                    content = @Content(schema = @Schema(implementation = PaymentStatusResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid state transition (already confirmed/canceled)"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @PostMapping("/payments/{paymentId}/confirm")
    public ResponseEntity<PaymentStatusResponse> confirmPayment(@PathVariable String paymentId) {
        return ResponseEntity.ok(paymentService.confirmPayment(paymentId));
    }

    @Operation(summary = "Cancel payment", description = "Changes payment status to CANCELED")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment canceled",
                    content = @Content(schema = @Schema(implementation = PaymentStatusResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid state transition (already confirmed/canceled)"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @PostMapping("/payments/{paymentId}/cancel")
    public ResponseEntity<PaymentStatusResponse> cancelPayment(@PathVariable String paymentId) {
        return ResponseEntity.ok(paymentService.cancelPayment(paymentId));
    }

    @Operation(summary = "Get client payments", description = "Returns all payments for a specific client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of client payments",
                    content = @Content(schema = @Schema(implementation = ClientPaymentsResponse.class)))
    })
    @GetMapping("/clients/{clientId}/payments")
    public ResponseEntity<List<ClientPaymentsResponse>> getClientPayments(@PathVariable String clientId) {
        return ResponseEntity.ok(paymentService.getClientPayments(clientId));
    }
}
