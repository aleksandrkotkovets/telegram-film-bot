package com.telegram.film_bot.security.checkers;

public interface RoleChecker {
    boolean check(Integer userId);
}
