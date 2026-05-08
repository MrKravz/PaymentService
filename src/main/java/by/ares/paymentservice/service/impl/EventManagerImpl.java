package by.ares.paymentservice.service.impl;

import by.ares.paymentservice.service.EventListener;
import by.ares.paymentservice.service.EventManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventManagerImpl<T> implements EventManager<T> {

    private List<EventListener<T>> eventListeners = new ArrayList<>();

    @Override
    public void subscribe(EventListener<T> eventListener) {
        eventListeners.add(eventListener);
    }

    @Override
    public void unsubscribe(EventListener<T> eventListener) {
        eventListeners.remove(eventListener);
    }

    @Override
    public void notify(T t) {
        eventListeners.forEach(x -> x.invoke(t));
    }

}
