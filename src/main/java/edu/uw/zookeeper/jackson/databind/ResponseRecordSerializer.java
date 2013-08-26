package edu.uw.zookeeper.jackson.databind;

import java.io.IOException;
import java.lang.reflect.Type;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import edu.uw.zookeeper.jackson.ResponseRecordCoreSerializer;
import edu.uw.zookeeper.protocol.proto.Records;

public class ResponseRecordSerializer extends StdSerializer<Records.Response> {

    public static ResponseRecordSerializer create() {
        return new ResponseRecordSerializer();
    }
    
    protected final ResponseRecordCoreSerializer delegate;
    
    public ResponseRecordSerializer() {
        this(ResponseRecordCoreSerializer.create());
    }

    protected ResponseRecordSerializer(ResponseRecordCoreSerializer delegate) {
        super(delegate.handledType());
        this.delegate = delegate;
    }
    
    @Override
    public void serialize(Records.Response value, JsonGenerator json,
            SerializerProvider provider) throws JsonGenerationException,
            IOException {
        delegate.serialize(value, json);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
        throws JsonMappingException {
        return createSchemaNode("array");
    }
}
