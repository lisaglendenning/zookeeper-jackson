package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.google.common.base.Function;

import edu.uw.zookeeper.protocol.Operation;
import edu.uw.zookeeper.protocol.ProtocolResponseMessage;
import edu.uw.zookeeper.protocol.proto.OpCode;

public class ProtocolResponseCoreDeserializer implements JacksonCoreDeserializer<Operation.ProtocolResponse<?>> {

    public static ProtocolResponseCoreDeserializer create(Function<Integer, OpCode> xidToOpCode) {
        return new ProtocolResponseCoreDeserializer(xidToOpCode);
    }

    protected final Function<Integer, OpCode> xidToOpCode;

    public ProtocolResponseCoreDeserializer(Function<Integer, OpCode> xidToOpCode) {
        super();
        this.xidToOpCode = xidToOpCode;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class<Operation.ProtocolResponse> handledType() {
        return Operation.ProtocolResponse.class;
    }

    @Override
    public ProtocolResponseMessage<?> deserialize(JsonParser json)
            throws IOException, JsonProcessingException {
        if (! json.isExpectedStartArrayToken()) {
            throw new JsonParseException(String.valueOf(json.getCurrentToken()), json.getCurrentLocation());
        }
        json.nextToken();
        JacksonInputArchive archive = new JacksonInputArchive(json);
        ProtocolResponseMessage<?> value = ProtocolResponseMessage.deserialize(xidToOpCode, archive);
        if (json.getCurrentToken() != JsonToken.END_ARRAY) {
            throw new JsonParseException(String.valueOf(json.getCurrentToken()), json.getCurrentLocation());
        }
        json.nextToken();
        return value;
    }
}
