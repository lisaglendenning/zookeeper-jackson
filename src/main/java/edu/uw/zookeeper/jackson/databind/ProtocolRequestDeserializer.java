package edu.uw.zookeeper.jackson.databind;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import edu.uw.zookeeper.jackson.ProtocolRequestCoreDeserializer;
import edu.uw.zookeeper.protocol.Operation;

public class ProtocolRequestDeserializer extends StdDeserializer<Operation.ProtocolRequest<?>> {

    public static ProtocolRequestDeserializer create() {
        return new ProtocolRequestDeserializer();
    }

    private static final long serialVersionUID = 4139226121738370357L;

    protected final ProtocolRequestCoreDeserializer delegate;

    public ProtocolRequestDeserializer() {
        this(ProtocolRequestCoreDeserializer.create());
    }
    
    protected ProtocolRequestDeserializer(ProtocolRequestCoreDeserializer delegate) {
        super(delegate.handledType());
        this.delegate = delegate;
    }

    @Override
    public Operation.ProtocolRequest<?> deserialize(JsonParser json, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        return delegate.deserialize(json);
    }
}
