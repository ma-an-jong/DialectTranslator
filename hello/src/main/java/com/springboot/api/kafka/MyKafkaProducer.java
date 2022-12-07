package com.springboot.api.kafka;



import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class MyKafkaProducer {
    private final static Logger logger = LoggerFactory.getLogger(MyKafkaProducer.class);
    private final static String TOPIC_NAME = "testtopic";
    private final static String BOOTSTRAP_SERVERS = "202.31.202.34:80";
    private KafkaProducer<Integer, byte[]> producer;

    public MyKafkaProducer()
    {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

        producer = new KafkaProducer<Integer, byte[]>(properties);
    }

    public void send(Integer key,String value)
    {
        ProducerRecord<Integer, byte[]> producerRecord = new ProducerRecord<>(TOPIC_NAME, key,value.getBytes());
        producer.send(producerRecord);
        producer.flush();
    }

    public static void main(String[] args) {
        MyKafkaProducer myKafkaProducer = new MyKafkaProducer();
        myKafkaProducer.send(1,"사투리 사투리");
    }


}