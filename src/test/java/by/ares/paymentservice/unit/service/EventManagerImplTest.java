package by.ares.paymentservice.unit.service;

import by.ares.paymentservice.service.EventListener;
import by.ares.paymentservice.service.EventManager;
import by.ares.paymentservice.service.impl.EventManagerImpl;
import org.junit.jupiter.api.Test;

import static by.ares.paymentservice.util.TestConstants.EVENT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class EventManagerImplTest {

    @Test
    void shouldNotifyAllSubscribers() {
        EventManager<String> manager = new EventManagerImpl<>();
        EventListener<String> listener1 = mock(EventListener.class);
        EventListener<String> listener2 = mock(EventListener.class);
        manager.subscribe(listener1);
        manager.subscribe(listener2);
        manager.notify(EVENT);
        verify(listener1).invoke(EVENT);
        verify(listener2).invoke(EVENT);
    }

    @Test
    void shouldNotNotifyAfterUnsubscribe() {
        EventManager<String> manager = new EventManagerImpl<>();
        EventListener<String> listener = mock(EventListener.class);
        manager.subscribe(listener);
        manager.unsubscribe(listener);
        manager.notify(EVENT);
        verify(listener, never()).invoke(any());
    }

    @Test
    void shouldHandleEmptyListenerList() {
        EventManager<String> manager = new EventManagerImpl<>();
        assertDoesNotThrow(() -> manager.notify(EVENT));
    }

}