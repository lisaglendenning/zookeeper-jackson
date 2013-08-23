package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.common.base.Function;

import edu.uw.zookeeper.protocol.Operation;
import edu.uw.zookeeper.protocol.ProtocolResponseMessage;
import edu.uw.zookeeper.protocol.proto.OpCode;

public class ProtocolResponseDeserializer extends StdDeserializer<Operation.ProtocolResponse<?>> {

    public static ProtocolResponseDeserializer create(Function<Integer, OpCode> xidToOpCode) {
        return new ProtocolResponseDeserializer(xidToOpCode);
    }

    private static final long serialVersionUID = 1992286420065993598L;
    
    protected final Function<Integer, OpCode> xidToOpCode;

    public ProtocolResponseDeserializer(Function<Integer, OpCode> xidToOpCode) {
        super(Operation.ProtocolResponse.class);
        this.xidToOpCode = xidToOpCode;
    }

    @Override
    public Operation.ProtocolResponse<?> deserialize(JsonParser json, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        if (! json.isExpectedStartArrayToken()) {
            throw new JsonParseException(String.valueOf(json.getCurrentToken()), json.getCurrentLocation());
        }
        json.nextToken();
        JacksonInputArchive archive = new JacksonInputArchive(json);
        Operation.ProtocolResponse<?> value = ProtocolResponseMessage.deserialize(xidToOpCode, archive);
        if (json.getCurrentToken() != JsonToken.END_ARRAY) {
            throw new JsonParseException(String.valueOf(json.getCurrentToken()), json.getCurrentLocation());
        }
        json.nextToken();
        return value;
    }
}
