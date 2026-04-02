package kz.almaplus.paymentservicetest.dto.response;

import kz.almaplus.paymentservicetest.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientPaymentsResponse {
    private String paymentId;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;

    public static ClientPaymentsResponse fromPayment(kz.almaplus.paymentservicetest.model.Payment payment) {
        return ClientPaymentsResponse.builder()
                .paymentId(payment.getPaymentId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .build();
    }
}