package by.ares.paymentservice.service;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEventManager<T> implements EventManager<T> {

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
        eventListeners.forEach(x -> x.perform(t));
    }

}
