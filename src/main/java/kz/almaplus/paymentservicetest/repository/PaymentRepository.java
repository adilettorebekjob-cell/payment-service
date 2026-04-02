package kz.almaplus.paymentservicetest.repository;

import kz.almaplus.paymentservicetest.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PaymentRepository {

    // Thread-safe in-memory storage
    private final Map<String, Payment> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Payment save(Payment payment) {
        if (payment.getPaymentId() == null || payment.getPaymentId().isBlank()) {
            payment.setPaymentId(String.valueOf(idGenerator.getAndIncrement()));
        }
        storage.put(payment.getPaymentId(), payment);
        return payment;
    }

    public Payment findById(String paymentId) {
        return storage.get(paymentId);
    }

    public List<Payment> findAllByClientId(String clientId) {
        return storage.values().stream()
                .filter(p -> p.getClientId().equals(clientId))
                .collect(Collectors.toList());
    }
}