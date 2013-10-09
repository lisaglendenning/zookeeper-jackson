package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;

import edu.uw.zookeeper.protocol.Message;
import edu.uw.zookeeper.protocol.ProtocolRequestMessage;

public class ProtocolRequestCoreDeserializer extends ListCoreDeserializer<Message.ClientRequest<?>> {

    public static ProtocolRequestCoreDeserializer create() {
        return new ProtocolRequestCoreDeserializer();
    }

    public ProtocolRequestCoreDeserializer() {
        super();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class<Message.ClientRequest> handledType() {
        return Message.ClientRequest.class;
    }

    @Override
    protected ProtocolRequestMessage<?> deserializeValue(JsonParser json)
            throws IOException, JsonProcessingException {
        return ProtocolRequestMessage.deserialize(new JacksonInputArchive(json));
    }
}
