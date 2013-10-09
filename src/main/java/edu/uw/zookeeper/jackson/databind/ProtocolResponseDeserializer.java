package edu.uw.zookeeper.jackson.databind;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.common.base.Function;

import edu.uw.zookeeper.jackson.ProtocolResponseCoreDeserializer;
import edu.uw.zookeeper.protocol.Message;
import edu.uw.zookeeper.protocol.proto.OpCode;

public class ProtocolResponseDeserializer extends StdDeserializer<Message.ServerResponse<?>> {

    public static ProtocolResponseDeserializer create(Function<Integer, OpCode> xidToOpCode) {
        return new ProtocolResponseDeserializer(xidToOpCode);
    }

    private static final long serialVersionUID = 1992286420065993598L;
    
    protected final ProtocolResponseCoreDeserializer delegate;

    public ProtocolResponseDeserializer(Function<Integer, OpCode> xidToOpCode) {
        this(ProtocolResponseCoreDeserializer.create(xidToOpCode));
    }
    
    protected ProtocolResponseDeserializer(ProtocolResponseCoreDeserializer delegate) {
        super(delegate.handledType());
        this.delegate = delegate;
    }

    @Override
    public Message.ServerResponse<?> deserialize(JsonParser json, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        return delegate.deserialize(json);
    }
}
