package by.ares.paymentservice.unit.service;

import by.ares.paymentservice.service.impl.SecurityValidationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import static by.ares.paymentservice.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class SecurityValidationServiceTest {

    @InjectMocks
    private SecurityValidationServiceImpl service;

    @Test
    void shouldAllowAccess_whenAdmin() {
        assertDoesNotThrow(() ->
                service.validateAccess(USER_ID, USER_ID_2, ADMIN)
        );
    }

    @Test
    void shouldAllowAccess_whenOwner() {
        assertDoesNotThrow(() ->
                service.validateAccess(USER_ID, USER_ID, USER)
        );
    }

    @Test
    void shouldDenyAccess_whenNotOwner() {
        assertThrows(AccessDeniedException.class, () ->
                service.validateAccess(USER_ID, USER_ID_2, USER)
        );
    }

}
