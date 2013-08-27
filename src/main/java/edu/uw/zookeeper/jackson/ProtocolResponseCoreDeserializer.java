package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Function;

import edu.uw.zookeeper.protocol.Operation;
import edu.uw.zookeeper.protocol.ProtocolResponseMessage;
import edu.uw.zookeeper.protocol.proto.OpCode;

public class ProtocolResponseCoreDeserializer extends ListCoreDeserializer<Operation.ProtocolResponse<?>> {

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
    protected ProtocolResponseMessage<?> deserializeValue(JsonParser json)
            throws IOException, JsonProcessingException {
        return ProtocolResponseMessage.deserialize(xidToOpCode, new JacksonInputArchive(json));
    }
}
