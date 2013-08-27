package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;

import edu.uw.zookeeper.protocol.Operation;
import edu.uw.zookeeper.protocol.ProtocolResponseMessage;

public class ProtocolResponseCoreSerializer extends ListCoreSerializer<Operation.ProtocolResponse<?>> {

    public static ProtocolResponseCoreSerializer create() {
        return new ProtocolResponseCoreSerializer();
    }

    public ProtocolResponseCoreSerializer() {
        super();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class<Operation.ProtocolResponse> handledType() {
        return Operation.ProtocolResponse.class;
    }

    @Override
    protected void serializeValue(Operation.ProtocolResponse<?> value, JsonGenerator json) throws JsonGenerationException, IOException {
        ProtocolResponseMessage.serialize(value, new JacksonOutputArchive(json));
    }
}
