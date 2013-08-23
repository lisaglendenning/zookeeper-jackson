package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import edu.uw.zookeeper.protocol.proto.Records;

public class ResponseRecordSerializer extends StdSerializer<Records.Response> {

    public static ResponseRecordSerializer create() {
        return new ResponseRecordSerializer();
    }
    
    public ResponseRecordSerializer() {
        super(Records.Response.class);
    }

    @Override
    public void serialize(Records.Response value, JsonGenerator jgen,
            SerializerProvider provider) throws JsonGenerationException,
            IOException {
        jgen.writeStartArray();
        jgen.writeNumber(value.opcode().intValue());
        Records.Responses.serialize(value, new JacksonOutputArchive(jgen));
        jgen.writeEndArray();
    }
}
