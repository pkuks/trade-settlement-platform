package com.example.capitalmarkets.tradesettlement.common.exception;

import java.util.Set;

public class RoleNotFoundException extends RuntimeException {
    private final Set<String> missingRoles;

    public RoleNotFoundException(Set<String> missingRoles) {
        super("Validation failed. The following roles do not exist: ");
        this.missingRoles = missingRoles;
    }

    public Set<String> getMissingRoles() {
        return missingRoles;
    }
}
