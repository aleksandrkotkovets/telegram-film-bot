package com.telegram.film_bot.security.checkers;

import com.telegram.film_bot.cache.DataCache;
import org.springframework.stereotype.Component;

@Component
public class AdminChecker implements RoleChecker {

    private final DataCache userDataCache;

    public AdminChecker(DataCache userDataCache) {
        this.userDataCache = userDataCache;
    }

    @Override
    public boolean check(Integer userId) {
        return userDataCache.getAdminList().stream()
                .anyMatch(adminId -> adminId.equals(userId));
    }
}
