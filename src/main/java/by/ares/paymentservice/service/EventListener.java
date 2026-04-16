package by.ares.paymentservice.service;

public interface EventListener<T> {
    void invoke(T t);
}
