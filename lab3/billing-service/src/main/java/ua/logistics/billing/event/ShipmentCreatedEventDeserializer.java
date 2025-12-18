package ua.logistics.billing.event;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ShipmentCreatedEventDeserializer extends ObjectMapperDeserializer<ShipmentCreatedEvent> {
    public ShipmentCreatedEventDeserializer() {
        super(ShipmentCreatedEvent.class);
    }
}