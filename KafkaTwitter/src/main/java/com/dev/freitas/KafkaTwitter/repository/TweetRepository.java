package com.dev.freitas.KafkaTwitter.repository;

import com.dev.freitas.KafkaTwitter.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TweetRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private String QUERY_SAVE_TWEET = "INSERT INTO TWEET (ID_TWEET_STRING, TEXTO, USER)";
    private String QUERY_FIND_ALL = "SELECT * FROM TWEET";


    @Transactional
    public Tweet save(Tweet tweet) {

        jdbcTemplate.update(QUERY_SAVE_TWEET, ps -> {
                    ps.setNString(1, tweet.getIdTweetString());
                    ps.setNString(2, tweet.getTexto());
                    ps.setNString(3, tweet.getUser());
                }
        );
        return tweet;
    }

    public List<Tweet> findAll() {

        List<Tweet> tweet = new ArrayList();

        return null;
    }
}
