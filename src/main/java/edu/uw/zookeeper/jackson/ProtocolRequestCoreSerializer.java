package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;

import edu.uw.zookeeper.protocol.Message;
import edu.uw.zookeeper.protocol.ProtocolRequestMessage;

public class ProtocolRequestCoreSerializer extends ListCoreSerializer<Message.ClientRequest<?>> {

    public static ProtocolRequestCoreSerializer create() {
        return new ProtocolRequestCoreSerializer();
    }

    public ProtocolRequestCoreSerializer() {
        super();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class<Message.ClientRequest> handledType() {
        return Message.ClientRequest.class;
    }

    @Override
    protected void serializeValue(Message.ClientRequest<?> value, JsonGenerator json) throws JsonGenerationException, IOException {
        ProtocolRequestMessage.serialize(value, new JacksonOutputArchive(json));
    }
}
