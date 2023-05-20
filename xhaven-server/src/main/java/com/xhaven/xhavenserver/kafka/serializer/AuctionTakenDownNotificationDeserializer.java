package com.xhaven.xhavenserver.kafka.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.xhaven.xhavenserver.dto.AuctionTakenDownNotificationDto;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class AuctionTakenDownNotificationDeserializer implements Deserializer<AuctionTakenDownNotificationDto> {

    private final ObjectMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public AuctionTakenDownNotificationDto deserialize(String s, byte[] notification) {
        try {
            if (notification == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(new String(notification, StandardCharsets.UTF_8),
                    AuctionTakenDownNotificationDto.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }

    @Override
    public void close() {
    }

}
