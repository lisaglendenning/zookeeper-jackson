package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;

import edu.uw.zookeeper.protocol.proto.Records;

public class ResponseRecordCoreSerializer implements JacksonCoreSerializer<Records.Response> {

    public static ResponseRecordCoreSerializer create() {
        return new ResponseRecordCoreSerializer();
    }
    
    public ResponseRecordCoreSerializer() {}

    @Override
    public Class<Records.Response> handledType() {
        return Records.Response.class;
    }

    @Override
    public void serialize(Records.Response value, JsonGenerator json)
            throws JsonGenerationException, IOException {
        json.writeStartArray();
        json.writeNumber(value.opcode().intValue());
        Records.Responses.serialize(value, new JacksonOutputArchive(json));
        json.writeEndArray();
    }
}
