package com.telegram.film_bot.security;

import com.telegram.film_bot.security.checkers.AdminChecker;
import com.telegram.film_bot.security.checkers.RoleChecker;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class MenuKeyboardRestrictAccess extends RestrictAccess {

    private List<RoleChecker> rolesForGetMenuKeyboard;

    public MenuKeyboardRestrictAccess(AdminChecker adminChecker) {
        rolesForGetMenuKeyboard = Collections.singletonList(adminChecker);
    }

    @Override
    public List<Exception> validate(Integer userId) {
        List<Exception> exceptionList = new ArrayList<>();

        checkCorrectRoleOnly(rolesForGetMenuKeyboard, userId).filter(c -> !c)
                .ifPresent(c -> exceptionList.add(new Exception("Waite admin role")));

        return exceptionList;
    }

}
