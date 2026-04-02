package kz.almaplus.paymentservicetest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private String paymentId;
    private BigDecimal amount;
    private String currency;
    private String description;
    private String clientId;
    private PaymentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Payment createNew(String paymentId, BigDecimal amount, String currency,
                                    String description, String clientId) {
        return Payment.builder()
                .paymentId(paymentId)
                .amount(amount)
                .currency(currency.toUpperCase())
                .description(description)
                .clientId(clientId)
                .status(PaymentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public void confirm() {
        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalStateException(
                    "Cannot confirm payment with status: " + this.status);
        }
        this.status = PaymentStatus.CONFIRMED;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (this.status != PaymentStatus.PENDING) {
            throw new IllegalStateException(
                    "Cannot cancel payment with status: " + this.status);
        }
        this.status = PaymentStatus.CANCELED;
        this.updatedAt = LocalDateTime.now();
    }
}