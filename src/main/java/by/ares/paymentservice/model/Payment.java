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

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payments")
@Accessors(chain = true)
public class Payment {

    @Id
    private String id;

    @Indexed
    @Field(name = "order_id")
    private Long orderId;

    @Indexed
    @Field(name = "user_id")
    private Long userId;

    @Field(name = "status")
    private Status status;

    @Field(name = "timestamp")
    private LocalDate timestamp;

    @Field(name = "payment_amount")
    private Long paymentAmount;

}
