package edu.uw.zookeeper.jackson.databind;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import edu.uw.zookeeper.jackson.JacksonCoreDeserializer;
import edu.uw.zookeeper.jackson.ResponseRecordCoreDeserializer;
import edu.uw.zookeeper.protocol.proto.Records;

public class ResponseRecordDeserializer extends StdDeserializer<Records.Response> {

    public static ResponseRecordDeserializer create() {
        return new ResponseRecordDeserializer();
    }

    private static final long serialVersionUID = -9158763633870689053L;

    protected final JacksonCoreDeserializer<Records.Response> delegate;

    public ResponseRecordDeserializer() {
        this(ResponseRecordCoreDeserializer.create());
    }
    
    protected ResponseRecordDeserializer(JacksonCoreDeserializer<Records.Response> delegate) {
        super(delegate.handledType());
        this.delegate = delegate;
    }

    @Override
    public Records.Response deserialize(JsonParser json, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        return delegate.deserialize(json);
    }
    
    @Override
    public boolean isCachable() { 
        return true; 
    }
}
