package by.ares.paymentservice.repository;

import by.ares.paymentservice.dto.request.DateRangeRequest;
import by.ares.paymentservice.model.Payment;
import by.ares.paymentservice.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, Long> {
    @Query("{ 'userId' : ?1 }")
    Page<Payment> findAllByUserId(Pageable pageable, Long userId);
    @Query("{ 'status' : ?1 }")
    Page<Payment> findAllByStatus(Pageable pageable, Status status);
    @Query("{ 'orderId' : ?0 }")
    Optional<Payment> findByOrderId(Long orderId);

    @Aggregation(pipeline = {
            "{ $match: { userId: ?1, actionTime: { $gte: ?0.start, $lte: ?0.end } } }",
            "{ $group: { _id: null, total: { $sum: '$paymentAmount' } } }"
    })
    Long totalSum(DateRangeRequest dateRangeRequest, Long userId);

    @Aggregation(pipeline = {
            "{ $match: { actionTime: { $gte: ?0.start, $lte: ?0.end } } }",
            "{ $group: { _id: null, total: { $sum: '$paymentAmount' } } }"
    })
    Long totalSum(DateRangeRequest dateRangeRequest);
}
