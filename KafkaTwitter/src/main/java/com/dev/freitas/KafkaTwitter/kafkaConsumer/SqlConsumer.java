package com.dev.freitas.KafkaTwitter.kafkaConsumer;

import com.dev.freitas.KafkaTwitter.model.Tweet;
import com.dev.freitas.KafkaTwitter.repository.TweetRepository;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

@Slf4j
public class SqlConsumer{

    @Autowired
    TweetRepository repository;

    @KafkaListener
    public static void run(TweetRepository repository) {
        KafkaConsumer<String, String> consumer = createConsumer("Tweets_from_Animes");
        int contRecords;

        while (true) {
            // criando records para o enviar ao banco
            ConsumerRecords<String, String> record = consumer.poll(Duration.ofMillis(100));
            contRecords = record.count();
            log.info("Quantidade de dados recebidos: ", contRecords);

            for (ConsumerRecord<String, String> recordData : record) {
                try {
                    Tweet tweet = convertTweet(recordData.toString());
                    repository.save(tweet);
                    Thread.sleep(1000);

                } catch (Exception e) {
                    log.error("deu ruim irmão: ", e);
                }
            }

        }
    }

    // Criação do consumer do Kafka
    public static KafkaConsumer<String, String> createConsumer(String topico) {
        String server = "localhost:9092";
        String groupId = "Kafka-MySQL";
        Properties properties = new Properties();


        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        consumer.subscribe(Arrays.asList(topico));

        return consumer;
    }


    public static Tweet convertTweet(String json) {
        Tweet tweet = new Tweet();
        JsonParser jsonParser = new JsonParser();

        try {
            tweet.setUser(jsonParser.parse(json).getAsJsonObject().get("_source")
                    .getAsJsonObject().get("user")
                    .getAsJsonObject().get("screen_name")
                    .getAsString());
            tweet.setTexto(jsonParser.parse(json).getAsJsonObject().get("_source")
                    .getAsJsonObject().get("text")
                    .getAsString());
            tweet.setIdTweetString(jsonParser.parse(json)
                    .getAsJsonObject().get("id_str")
                    .getAsString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tweet;
    }

}
