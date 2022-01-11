package com.dev.freitas.KafkaTwitter.util;

import com.dev.freitas.KafkaTwitter.model.Tweet;
import com.google.gson.JsonParser;

public class ConvertTweet {

    public static JsonParser jsonParser = new JsonParser();

    public Tweet convertJsonByTweet(String json) {
        Tweet tweet = new Tweet();

        try {
            tweet.setUser(jsonParser.parse(json).getAsJsonObject().get("_source")
                    .getAsJsonObject().get("user")
                    .getAsJsonObject().get("screen_name")
                    .getAsString());
            tweet.setTexto(jsonParser.parse(json).getAsJsonObject().get("_source")
                    .getAsJsonObject().get("text")
                    .getAsString());

        }catch (Exception e){
            e.printStackTrace();
        }

        return tweet;
    }

}
