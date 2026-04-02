package kz.almaplus.paymentservicetest.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    @Pattern(regexp = "^(KZT|USD|EUR|RUB|CNY)$",
            message = "Currency must be one of: KZT, USD, EUR, RUB, CNY")
    private String currency;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "ClientId is required")
    private String clientId;
}
