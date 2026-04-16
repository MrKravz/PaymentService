package by.ares.paymentservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payment_collection")
@Accessors(chain = true)
public class Payment {

    @Id
    private Long id;

    @Indexed(unique = true)
    @Field(name = "order_id")
    private Long orderId;

    @Indexed
    @Field(name = "user_id")
    private Long userId;

    @Field(name = "status")
    private Status status;

    @Field(name = "timestamp")
    private LocalDateTime timestamp;

    @Field(name = "payment_amount")
    private Long paymentAmount;

}
