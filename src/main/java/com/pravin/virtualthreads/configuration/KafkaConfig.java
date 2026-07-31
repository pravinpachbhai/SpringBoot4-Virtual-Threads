package com.pravin.virtualthreads.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.EmbeddedKafkaKraftBroker;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public EmbeddedKafkaBroker broker() {
        return new EmbeddedKafkaKraftBroker(1, 1)
                .kafkaPorts(9092)
                .brokerListProperty("spring.kafka.bootstrap-servers");
    }


}