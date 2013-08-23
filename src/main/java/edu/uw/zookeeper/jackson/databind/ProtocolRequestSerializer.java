package edu.uw.zookeeper.jackson.databind;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import edu.uw.zookeeper.jackson.ProtocolRequestCoreSerializer;
import edu.uw.zookeeper.protocol.Operation;

public class ProtocolRequestSerializer extends StdSerializer<Operation.ProtocolRequest<?>> {

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
    public void serialize(Operation.ProtocolRequest<?> value, JsonGenerator json, SerializerProvider provider) throws JsonGenerationException, IOException {
        delegate.serialize(value, json);
    }
}
