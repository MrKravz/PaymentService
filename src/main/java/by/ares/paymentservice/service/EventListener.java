package by.ares.paymentservice.service;

public interface EventListener<T> {
    void perform(T t);
}
