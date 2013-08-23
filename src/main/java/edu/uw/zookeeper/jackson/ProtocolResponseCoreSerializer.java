package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import edu.uw.zookeeper.protocol.Operation;
import edu.uw.zookeeper.protocol.ProtocolResponseMessage;

public class ProtocolResponseCoreSerializer implements JacksonCoreSerializer<Operation.ProtocolResponse<?>> {

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
    public void serialize(Operation.ProtocolResponse<?> value, JsonGenerator json) throws JsonGenerationException, IOException {
        json.writeStartArray();
        JacksonOutputArchive archive = new JacksonOutputArchive(json);
        ProtocolResponseMessage.serialize(value, archive);
        json.writeEndArray();
    }
}
