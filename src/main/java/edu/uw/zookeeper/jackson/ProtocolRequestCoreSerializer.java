package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import edu.uw.zookeeper.protocol.Operation;
import edu.uw.zookeeper.protocol.ProtocolRequestMessage;

public class ProtocolRequestCoreSerializer implements JacksonCoreSerializer<Operation.ProtocolRequest<?>> {

    public static ProtocolRequestCoreSerializer create() {
        return new ProtocolRequestCoreSerializer();
    }

    public ProtocolRequestCoreSerializer() {
        super();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class<Operation.ProtocolRequest> handledType() {
        return Operation.ProtocolRequest.class;
    }

    @Override
    public void serialize(Operation.ProtocolRequest<?> value, JsonGenerator json) throws JsonGenerationException, IOException {
        json.writeStartArray();
        ProtocolRequestMessage.serialize(value, new JacksonOutputArchive(json));
        json.writeEndArray();
    }
}
