package by.ares.paymentservice.service.impl;

import by.ares.paymentservice.model.Role;
import by.ares.paymentservice.service.SecurityValidationService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class SecurityValidationServiceImpl implements SecurityValidationService {

    @Override
    public void validateAccess(Long id, Long userId, String role) {
        if (isAdmin(Role.valueOf(role)) || isOwner(id, userId)) {
            return;
        }
        throw new AccessDeniedException("Access denied");
    }

    private boolean isOwner(Long id, Long userId) {
        return id.equals(userId);
    }

    private boolean isAdmin(Role role) {
        return role.equals(Role.ADMIN);
    }

}
