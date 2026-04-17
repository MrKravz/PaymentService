package by.ares.paymentservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    @NotNull
    @Positive
    private Long userId;
    @NotNull
    @Positive
    private Long orderId;
    @NotNull
    @Positive
    private Long paymentAmount;
}
