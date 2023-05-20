package com.xhaven.xhavenserver.kafka.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.xhaven.xhavenserver.dto.AuctionTakenDownNotificationDto;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class AuctionTakenDownNotificationSerializer implements Serializer<AuctionTakenDownNotificationDto> {
    private final ObjectMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }
    @Override
    public byte[] serialize(String topic, AuctionTakenDownNotificationDto notification) {
        try {
            if (notification == null){
                return null;
            }
            return objectMapper.writeValueAsBytes(notification);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing AuctionTakenDownNotification to byte[]");
        }
    }

    @Override
    public void close() {
    }
}
