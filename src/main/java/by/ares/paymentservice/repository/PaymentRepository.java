package by.ares.paymentservice.repository;

import by.ares.paymentservice.model.Payment;
import by.ares.paymentservice.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {
    @Query("{ 'userId' : ?0 }")
    Page<Payment> findAllByUserId(Pageable pageable, Long userId);
    @Query("{ 'status' : ?0 }")
    Page<Payment> findAllByStatus(Pageable pageable, Status status);
    @Query("{ 'orderId' : ?0 }")
    Page<Payment> findAllByOrderId(Pageable pageable, Long orderId);

    @Aggregation(pipeline = {
            "{ $match: { user_id: ?2, timestamp: { $gte: ?0, $lte: ?1 } } }",
            "{ $group: { _id: null, total: { $sum: '$payment_amount' } } }"
    })
    Long totalSum(LocalDate start, LocalDate end, Long userId);

    @Aggregation(pipeline = {
            "{ $match: { timestamp: { $gte: ?0, $lte: ?1 } } }",
            "{ $group: { _id: null, total: { $sum: '$payment_amount' } } }"
    })
    Long totalSum(LocalDate start, LocalDate end);
}
