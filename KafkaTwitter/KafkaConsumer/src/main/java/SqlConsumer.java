import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

@Slf4j
@Service
public class SqlConsumer {

    @Autowired
    private static com.dev.freitas.KafkaTwitter.repository.TweetRepository repository;


    // Eu vou querer que o Consumer seja indepotente, logo vou fazer a logica aqui na main
    public static void main(String[] args) {


        KafkaConsumer<String, String> consumer = createConsumer("Tweets_from_Animes");
        int contRecords;

        while(true){
            // criando records para o enviar ao banco
            ConsumerRecords<String, String> record = consumer.poll(Duration.ofMillis(1000));
            contRecords = record.count();
            log.info("Quantidade de dados recebidos: ", contRecords);

            for(ConsumerRecord<String, String> recordData : record){
                try{
                    int id = extrairIdTweet(recordData.value());
                    repository.save();
                }
            }

        }

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

    private static JsonParser jsonParser;

    public static Integer extrairIdTweet(String json){
        String idString = jsonParser.parse(json).getAsJsonObject().get("id_str").getAsString();
        int id = Integer.parseInt(idString);
        return id;
    }

}
