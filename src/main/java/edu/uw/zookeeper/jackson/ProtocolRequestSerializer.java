package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import edu.uw.zookeeper.protocol.Operation;
import edu.uw.zookeeper.protocol.ProtocolRequestMessage;

public class ProtocolRequestSerializer extends StdSerializer<Operation.ProtocolRequest<?>> {

    public static ProtocolRequestSerializer create() {
        return new ProtocolRequestSerializer();
    }

    public ProtocolRequestSerializer() {
        super(Operation.ProtocolRequest.class, true);
    }

    @Override
    public void serialize(Operation.ProtocolRequest<?> value, JsonGenerator json, SerializerProvider provider) throws JsonGenerationException, IOException {
        json.writeStartArray();
        ProtocolRequestMessage.serialize(value, new JacksonOutputArchive(json));
        json.writeEndArray();
    }
}
