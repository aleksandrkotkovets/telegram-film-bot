package com.telegram.film_bot.security;

import com.telegram.film_bot.security.checkers.RoleChecker;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class RestrictAccess {

    public List<Exception> validate(Integer userId) {
        return Collections.emptyList();
    }

    Optional<Boolean> checkCorrectRoleOnly(List<RoleChecker> checkers, Integer userId) {
        return checkRoles(checkers, userId);
    }

    private Optional<Boolean> checkRoles(List<RoleChecker> checkers, Integer userId) {
        return Optional.of(checkers.stream().map(c -> c.check(userId)).anyMatch(b -> b.equals(true)));
    }
}
