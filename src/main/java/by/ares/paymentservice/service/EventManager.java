package by.ares.paymentservice.service;


public interface EventManager<T> {
    void subscribe(EventListener<T> eventListener);
    void unsubscribe(EventListener<T> eventListener);
    void notify(T t);
}
