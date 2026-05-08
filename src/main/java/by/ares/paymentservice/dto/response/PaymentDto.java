package by.ares.paymentservice.dto.response;

import by.ares.paymentservice.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private String id;
    private Long orderId;
    private Long userId;
    private Status status;
    private LocalDate timestamp;
    private Long paymentAmount;
}
