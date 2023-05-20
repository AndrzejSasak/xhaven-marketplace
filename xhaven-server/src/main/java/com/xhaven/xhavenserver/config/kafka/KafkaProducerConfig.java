package com.xhaven.xhavenserver.config.kafka;

import com.xhaven.xhavenserver.dto.AuctionTakenDownNotificationDto;
import com.xhaven.xhavenserver.kafka.serializer.AuctionTakenDownNotificationSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String, Object> producerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AuctionTakenDownNotificationSerializer.class.getName());
        return props;
    }

    @Bean
    public ProducerFactory<String, AuctionTakenDownNotificationDto> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, AuctionTakenDownNotificationDto> kafkaTemplate(
            ProducerFactory<String, AuctionTakenDownNotificationDto> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }
}
