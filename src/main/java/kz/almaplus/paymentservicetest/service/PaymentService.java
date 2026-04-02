package kz.almaplus.paymentservicetest.service;

import kz.almaplus.paymentservicetest.dto.request.CreatePaymentRequest;
import kz.almaplus.paymentservicetest.dto.response.ClientPaymentsResponse;
import kz.almaplus.paymentservicetest.dto.response.PaymentResponse;
import kz.almaplus.paymentservicetest.dto.response.PaymentStatusResponse;
import kz.almaplus.paymentservicetest.exception.InvalidPaymentStateException;
import kz.almaplus.paymentservicetest.exception.PaymentNotFoundException;
import kz.almaplus.paymentservicetest.model.Payment;
import kz.almaplus.paymentservicetest.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentStatusResponse createPayment(CreatePaymentRequest request) {
        Payment payment = Payment.createNew(
                null, // ID генерируется в репозитории
                request.getAmount(),
                request.getCurrency(),
                request.getDescription(),
                request.getClientId()
        );
        Payment saved = paymentRepository.save(payment);
        return PaymentStatusResponse.fromPayment(saved);
    }

    public PaymentResponse getPayment(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment == null) {
            throw new PaymentNotFoundException(paymentId);
        }
        return PaymentResponse.fromPayment(payment);
    }

    public PaymentStatusResponse confirmPayment(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment == null) {
            throw new PaymentNotFoundException(paymentId);
        }
        try {
            payment.confirm();
        } catch (IllegalStateException e) {
            throw new InvalidPaymentStateException(e.getMessage());
        }
        paymentRepository.save(payment);
        return PaymentStatusResponse.fromPayment(payment);
    }

    public PaymentStatusResponse cancelPayment(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment == null) {
            throw new PaymentNotFoundException(paymentId);
        }
        try {
            payment.cancel();
        } catch (IllegalStateException e) {
            throw new InvalidPaymentStateException(e.getMessage());
        }
        paymentRepository.save(payment);
        return PaymentStatusResponse.fromPayment(payment);
    }

    public List<ClientPaymentsResponse> getClientPayments(String clientId) {
        List<Payment> payments = paymentRepository.findAllByClientId(clientId);
        // Возвращаем пустой список, если у клиента нет платежей (стандарт REST API)
        return payments.stream()
                .map(ClientPaymentsResponse::fromPayment)
                .toList();
    }
}