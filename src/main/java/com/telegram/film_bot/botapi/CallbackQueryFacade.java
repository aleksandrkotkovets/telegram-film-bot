package com.telegram.film_bot.botapi;

import com.telegram.film_bot.botapi.handler.callbackquery.CallbackQueryHandler;
import com.telegram.film_bot.botapi.handler.callbackquery.CallbackQueryType;
import com.telegram.film_bot.botapi.handler.callbackquery.impl.ParamCallQueryHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;
import java.util.Optional;

@Component
public class CallbackQueryFacade {

    private List<CallbackQueryHandler> callbackQueryHandlers;
    private final ParamCallQueryHandler paramCallQueryHandler;

    public CallbackQueryFacade(List<CallbackQueryHandler> callbackQueryHandlers,
                               ParamCallQueryHandler paramCallQueryHandler) {
        this.callbackQueryHandlers = callbackQueryHandlers;
        this.paramCallQueryHandler = paramCallQueryHandler;
    }

    public BotApiMethod<?> processCallbackQuery(CallbackQuery usersQuery) {
        BotApiMethod<?> replyToUser;

        String queryType = usersQuery.getData().split("\\|")[0];
        boolean matches = queryType.matches("[A-Z]+");
        if (matches) {
            CallbackQueryType usersQueryType = CallbackQueryType.valueOf(queryType);

            Optional<CallbackQueryHandler> queryHandler = callbackQueryHandlers.stream().
                    filter(callbackQuery -> callbackQuery.getHandlerQueryType().equals(usersQueryType)).findFirst();

            replyToUser = queryHandler.map(handler -> handler.handleCallbackQuery(usersQuery)).get();
        } else {
            replyToUser = paramCallQueryHandler.handleCallbackQueryByParam(usersQuery, queryType);
        }
        return replyToUser;
    }

}
