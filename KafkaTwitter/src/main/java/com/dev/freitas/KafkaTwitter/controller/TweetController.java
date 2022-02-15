package com.dev.freitas.KafkaTwitter.controller;

import com.dev.freitas.KafkaTwitter.model.Tweet;
import com.dev.freitas.KafkaTwitter.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
@CrossOrigin("*")
public class TweetController {

    @Autowired
    private TweetRepository repository;

    @GetMapping
    public ResponseEntity<List<Tweet>> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Tweet> insertTweet(@RequestBody Tweet tweet){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(tweet));
    }
}
