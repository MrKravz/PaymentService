package by.ares.paymentservice.dto.request;

import by.ares.paymentservice.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusRequest {
    private Long id;
    private Status status;
}
