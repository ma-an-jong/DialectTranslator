package com.springboot.api.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MyKafkaConsumer {

    private final static Logger logger = LoggerFactory.getLogger(MyKafkaConsumer.class);
    private final static String TOPIC_NAME = "java-topic";
    private final static String BOOTSTRAP_SERVERS = "202.31.202.34:80";
    private final static String GROUP_ID = "spring_01";
    private Map<TopicPartition, OffsetAndMetadata> curOffSet = new HashMap<>();
    private  KafkaConsumer<byte[],byte[]> kafkaConsumer;

    public MyKafkaConsumer()
    {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,ByteArrayDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,GROUP_ID);
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"false");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 60000);

        kafkaConsumer = new KafkaConsumer<byte[], byte[]>(properties);
        kafkaConsumer.subscribe(Arrays.asList(TOPIC_NAME));
    }


    public KafkaResponse consume()
    {
        try{
            ConsumerRecords<byte[],byte[]> records =kafkaConsumer.poll(Duration.ofSeconds(1));

            for(ConsumerRecord<byte[],byte[]> record : records)
            {
                byte[] keyBytes = record.key();
                byte[] valueBytes = record.value();

                if(keyBytes == null || valueBytes == null) continue;
                String key = new String(keyBytes);
                String value = new String(valueBytes);

                logger.info("{} : {}",key ,value);
                curOffSet.put(
                        new TopicPartition(record.topic(),record.partition()),
                        new OffsetAndMetadata(record.offset()+1,null));
                kafkaConsumer.commitSync(curOffSet);

                return new KafkaResponse(key,value);
            }
        }
        catch (RuntimeException e)
        {
            System.out.println(e.getMessage());
            throw e;
        }

        return new KafkaResponse("","");
    }



}
