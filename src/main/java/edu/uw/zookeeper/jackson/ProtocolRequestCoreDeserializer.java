package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;

import edu.uw.zookeeper.protocol.Operation;
import edu.uw.zookeeper.protocol.ProtocolRequestMessage;

public class ProtocolRequestCoreDeserializer implements JacksonCoreDeserializer<Operation.ProtocolRequest<?>> {

    public static ProtocolRequestCoreDeserializer create() {
        return new ProtocolRequestCoreDeserializer();
    }

    public ProtocolRequestCoreDeserializer() {
        super();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class<Operation.ProtocolRequest> handledType() {
        return Operation.ProtocolRequest.class;
    }

    @Override
    public Operation.ProtocolRequest<?> deserialize(JsonParser json)
            throws IOException, JsonProcessingException {
        if (! json.isExpectedStartArrayToken()) {
            throw new JsonParseException(String.valueOf(json.getCurrentToken()), json.getCurrentLocation());
        }
        json.nextToken();
        JacksonInputArchive archive = new JacksonInputArchive(json);
        Operation.ProtocolRequest<?> value = ProtocolRequestMessage.deserialize(archive);
        if (json.getCurrentToken() != JsonToken.END_ARRAY) {
            throw new JsonParseException(String.valueOf(json.getCurrentToken()), json.getCurrentLocation());
        }
        json.nextToken();
        return value;
    }
}
