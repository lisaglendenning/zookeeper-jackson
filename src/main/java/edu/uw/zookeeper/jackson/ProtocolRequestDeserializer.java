package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import edu.uw.zookeeper.protocol.Operation;
import edu.uw.zookeeper.protocol.ProtocolRequestMessage;

public class ProtocolRequestDeserializer extends StdDeserializer<Operation.ProtocolRequest<?>> {

    public static ProtocolRequestDeserializer create() {
        return new ProtocolRequestDeserializer();
    }

    private static final long serialVersionUID = 4139226121738370357L;

    public ProtocolRequestDeserializer() {
        super(Operation.ProtocolRequest.class);
    }

    @Override
    public Operation.ProtocolRequest<?> deserialize(JsonParser json, DeserializationContext ctxt)
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
