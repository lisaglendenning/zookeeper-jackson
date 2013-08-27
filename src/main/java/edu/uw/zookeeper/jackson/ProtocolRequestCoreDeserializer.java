package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import edu.uw.zookeeper.protocol.Operation;
import edu.uw.zookeeper.protocol.ProtocolRequestMessage;

public class ProtocolRequestCoreDeserializer extends ListCoreDeserializer<Operation.ProtocolRequest<?>> {

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
    protected ProtocolRequestMessage<?> deserializeValue(JsonParser json)
            throws IOException, JsonProcessingException {
        return ProtocolRequestMessage.deserialize(new JacksonInputArchive(json));
    }
}
