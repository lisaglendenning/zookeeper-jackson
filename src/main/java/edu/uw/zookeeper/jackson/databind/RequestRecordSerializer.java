package edu.uw.zookeeper.jackson.databind;

import java.io.IOException;
import java.lang.reflect.Type;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import edu.uw.zookeeper.jackson.RequestRecordCoreSerializer;
import edu.uw.zookeeper.protocol.proto.Records;

public class RequestRecordSerializer extends StdSerializer<Records.Request> {

    public static RequestRecordSerializer create() {
        return new RequestRecordSerializer();
    }

    protected final RequestRecordCoreSerializer delegate;
    
    public RequestRecordSerializer() {
        this(RequestRecordCoreSerializer.create());
    }
    
    protected RequestRecordSerializer(RequestRecordCoreSerializer delegate) {
        super(delegate.handledType());
        this.delegate = delegate;
    }

    @Override
    public void serialize(Records.Request value, JsonGenerator json, SerializerProvider provider) throws JsonGenerationException, IOException {
        delegate.serialize(value, json);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
        throws JsonMappingException {
        return createSchemaNode("array");
    }
}
