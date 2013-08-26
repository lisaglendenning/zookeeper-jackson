package edu.uw.zookeeper.jackson.databind;

import java.io.IOException;
import java.lang.reflect.Type;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import edu.uw.zookeeper.jackson.ProtocolResponseCoreSerializer;
import edu.uw.zookeeper.protocol.Operation;

public class ProtocolResponseSerializer extends StdSerializer<Operation.ProtocolResponse<?>> {

    public static ProtocolResponseSerializer create() {
        return new ProtocolResponseSerializer();
    }
    
    protected final ProtocolResponseCoreSerializer delegate;

    public ProtocolResponseSerializer() {
        this(ProtocolResponseCoreSerializer.create());
    }
    
    protected ProtocolResponseSerializer(ProtocolResponseCoreSerializer delegate) {
        super(delegate.handledType(), true);
        this.delegate = delegate;
    }

    @Override
    public void serialize(Operation.ProtocolResponse<?> value, JsonGenerator json, SerializerProvider provider) throws JsonGenerationException, IOException {
        delegate.serialize(value, json);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
        throws JsonMappingException {
        return createSchemaNode("array");
    }
}
