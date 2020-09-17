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

@Document(collection = "films")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film {
    @Id
    String id;

    @Field(name = "chat_id")
    Long channelId;

    @Field(name = "message_id")
    Long messageId;

    @Field(name = "message_text")
    String messageText;

    @Field(name = "photo_id")
    Long photoId;
}
