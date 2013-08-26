package edu.uw.zookeeper.jackson.databind;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import edu.uw.zookeeper.jackson.JacksonCoreDeserializer;
import edu.uw.zookeeper.jackson.RequestRecordCoreDeserializer;
import edu.uw.zookeeper.protocol.proto.Records;

public class RequestRecordDeserializer extends StdDeserializer<Records.Request> {

    public static RequestRecordDeserializer create() {
        return new RequestRecordDeserializer();
    }

    private static final long serialVersionUID = 191925488788083157L;
    
    protected final JacksonCoreDeserializer<Records.Request> delegate;

    public RequestRecordDeserializer() {
        this(RequestRecordCoreDeserializer.create());
    }
    
    protected RequestRecordDeserializer(JacksonCoreDeserializer<Records.Request> delegate) {
        super(delegate.handledType());
        this.delegate = delegate;
    }

    @Override
    public Records.Request deserialize(JsonParser json, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        return delegate.deserialize(json);
    }
    
    @Override
    public boolean isCachable() { 
        return true; 
    }
}
