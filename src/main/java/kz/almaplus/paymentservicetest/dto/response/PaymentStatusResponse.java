package kz.almaplus.paymentservicetest.dto.response;

import kz.almaplus.paymentservicetest.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatusResponse {
    private String paymentId;
    private PaymentStatus status;

    public static PaymentStatusResponse fromPayment(kz.almaplus.paymentservicetest.model.Payment payment) {
        return PaymentStatusResponse.builder()
                .paymentId(payment.getPaymentId())
                .status(payment.getStatus())
                .build();
    }
}