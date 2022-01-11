package dev.freitas.kafkaConsumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

public class SqlConsumer {




    // Eu vou querer que o Consumer seja indepotente, logo vou fazer a logica aqui na main
    public static void main(String[] args) {

    }


    // Criação do consumer do Kafka
    public static KafkaConsumer<String, String > createConsumer(String topico){
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



}
