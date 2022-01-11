package com.dev.freitas.KafkaTwitter.repository;

import com.dev.freitas.KafkaTwitter.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Integer> {
}
