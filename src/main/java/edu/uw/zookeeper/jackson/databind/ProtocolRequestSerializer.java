package edu.uw.zookeeper.jackson.databind;

import java.io.IOException;
import java.lang.reflect.Type;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import edu.uw.zookeeper.jackson.ProtocolRequestCoreSerializer;
import edu.uw.zookeeper.protocol.Message;

public class ProtocolRequestSerializer extends StdSerializer<Message.ClientRequest<?>> {

    public static ProtocolRequestSerializer create() {
        return new ProtocolRequestSerializer();
    }

    protected final ProtocolRequestCoreSerializer delegate;

    public ProtocolRequestSerializer() {
        this(ProtocolRequestCoreSerializer.create());
    }
    
    protected ProtocolRequestSerializer(ProtocolRequestCoreSerializer delegate) {
        super(delegate.handledType(), true);
        this.delegate = delegate;
    }

    @Override
    public void serialize(Message.ClientRequest<?> value, JsonGenerator json, SerializerProvider provider) throws JsonGenerationException, IOException {
        delegate.serialize(value, json);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
        throws JsonMappingException {
        return createSchemaNode("array");
    }
}
