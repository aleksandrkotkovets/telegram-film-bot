package com.telegram.film_bot.data.entities;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "recommended_films")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecommendFilm {

    @Id
    String id;

    @Field(name = "user_id")
    Integer userId;

    @Field(name = "chat_id")
    Long chatId;

    @Field(name = "message_id")
    Integer messageId;

    @Field(name = "message_text")
    String messageText;

    @Field(name = "read")
    boolean read;

    public void setRead() {
        read = true;
    }
}
