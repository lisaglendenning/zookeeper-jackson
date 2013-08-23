package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import edu.uw.zookeeper.protocol.Operation;
import edu.uw.zookeeper.protocol.ProtocolResponseMessage;

public class ProtocolResponseSerializer extends StdSerializer<Operation.ProtocolResponse<?>> {

    public static ProtocolResponseSerializer create() {
        return new ProtocolResponseSerializer();
    }

    public ProtocolResponseSerializer() {
        super(Operation.ProtocolResponse.class, true);
    }

    @Override
    public void serialize(Operation.ProtocolResponse<?> value, JsonGenerator json, SerializerProvider provider) throws JsonGenerationException, IOException {
        json.writeStartArray();
        JacksonOutputArchive archive = new JacksonOutputArchive(json);
        ProtocolResponseMessage.serialize(value, archive);
        json.writeEndArray();
    }
}
