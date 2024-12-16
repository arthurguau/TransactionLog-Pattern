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

        logger.info ("===> incoming sourceRecord: " + sourceRecord.toString());
        Struct kStruct = (Struct) sourceRecord.value();
        R newSourceRecord = null;
        
        /*
         * operation includes create (event type include - c,u,d)
         */
        String databaseOperation = kStruct.getString("op");
        logger.info ("****> database Operation: " + databaseOperation);
        
        //Handle only the Create's
        if ("c".equalsIgnoreCase(databaseOperation)) {

            // Get the details.
            Struct after = (Struct) kStruct.get("after");
            String UUID = after.getString("uuid");
            
            //String payload = after.getString("payload");
            Object payload = after.getWithoutDefault("payload");
            
            String eventType = after.getString("event_type").trim().toUpperCase();
            
            // Set event type as topic name
            String topic = "PARTY_EVENT";

            // set up headers
            Headers headers = sourceRecord.headers();
            headers.addString("eventType", eventType);
            headers.addString("eventId", UUID);

            // Build the event to be published.
            newSourceRecord = 
                sourceRecord.newRecord(
                    topic, 
                    null, 
                    Schema.STRING_SCHEMA, 
                    UUID,
                    null, 
                    payload, 
                    sourceRecord.timestamp(), 
                    headers);
            
            logger.info("===> outgoing sourceRecord: " + newSourceRecord.toString());
            logger.info ("Event type: " +  eventType + " Payload: " + payload + " topic: " + topic);
        }

        return newSourceRecord;
    }

    public ConfigDef config() {
        return new ConfigDef();
    }

    public void close() {
    }

    public void configure(Map<String, ?> configs) {
    }
}
