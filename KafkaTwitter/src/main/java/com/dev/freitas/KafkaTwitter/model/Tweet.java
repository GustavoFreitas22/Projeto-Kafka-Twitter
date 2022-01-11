package com.dev.freitas.KafkaTwitter.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Slf4j
@Getter
@Setter
@Builder
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String texto;

    @NotNull
    private String user;

    public Tweet(int id, String texto, String user) {
        this.id = id;
        this.texto = texto;
        this.user = user;
    }

    public Tweet() {
    }
}
