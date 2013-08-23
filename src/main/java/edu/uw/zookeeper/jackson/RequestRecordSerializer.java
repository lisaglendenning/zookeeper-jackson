package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import edu.uw.zookeeper.protocol.proto.Records;

public class RequestRecordSerializer extends StdSerializer<Records.Request> {

    public static RequestRecordSerializer create() {
        return new RequestRecordSerializer();
    }

    public RequestRecordSerializer() {
        super(Records.Request.class);
    }

    @Override
    public void serialize(Records.Request value, JsonGenerator jgen, SerializerProvider provider) throws JsonGenerationException, IOException {
        jgen.writeStartArray();
        jgen.writeNumber(value.opcode().intValue());
        Records.Requests.serialize(value, new JacksonOutputArchive(jgen));
        jgen.writeEndArray();
    }
}
