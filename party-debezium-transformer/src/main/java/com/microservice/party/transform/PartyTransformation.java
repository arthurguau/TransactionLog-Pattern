package com.microservice.party.transform;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.header.Headers;
import org.apache.kafka.connect.transforms.Transformation;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class is configured and invoked when a changes occur on any OutBox Schema.
 *
 * @author Arthur
 */
public class PartyTransformation<R extends ConnectRecord<R>> implements Transformation<R> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * This method is invoked when a change is made on the outbox schema.
     *
     * @param sourceRecord
     * @return
     */
    public R apply(R sourceRecord) {

    	System.out.println ("=========================================> sourceRecord: " + sourceRecord.toString());
        Struct kStruct = (Struct) sourceRecord.value();
        String databaseOperation = kStruct.getString("op");
        System.out.println ("=========================================> KStruct: " + kStruct.toString());

        //Handle only the Create's
        if ("c".equalsIgnoreCase(databaseOperation)) {

            // Get the details.
            Struct after = (Struct) kStruct.get("after");
            
            System.out.println ("=========================================> Struct: " + after.toString());

            
            String UUID = after.getString("uuid");
            String payload = after.getString("payload");
            String eventType = after.getString("event_type").toLowerCase();
            String topic = eventType.toLowerCase();

//            String UUID = "06a988ac-bd5b-49d2-997e-8aef742a5e43";
//            String topic="partyenrolled";
//            String payload='{"partyId":1,"name":"Meike","email":"mike@gmail.com","address":"Toronto, ON"}';

            Headers headers = sourceRecord.headers();
            headers.addString("eventId", UUID);

            // Build the event to be published.
            sourceRecord = sourceRecord.newRecord(topic, null, Schema.STRING_SCHEMA, UUID,
                    null, payload, sourceRecord.timestamp(), headers);
            logger.info ("Payload: " + payload + " topic: " + topic);
        }

        return sourceRecord;
    }

    public ConfigDef config() {
        return new ConfigDef();
    }

    public void close() {
    }

    public void configure(Map<String, ?> configs) {
    }
}
