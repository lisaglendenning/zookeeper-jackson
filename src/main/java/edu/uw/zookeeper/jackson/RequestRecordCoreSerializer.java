package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import edu.uw.zookeeper.protocol.proto.Records;
import edu.uw.zookeeper.protocol.proto.Records.Request;

public class RequestRecordCoreSerializer extends ListCoreSerializer<Records.Request> {

    public static RequestRecordCoreSerializer create() {
        return new RequestRecordCoreSerializer();
    }

    public RequestRecordCoreSerializer() {
        super();
    }

    @Override
    public Class<Request> handledType() {
        return Records.Request.class;
    }

    @Override
    protected void serializeValue(Records.Request value, JsonGenerator jgen) throws JsonGenerationException, IOException {
        jgen.writeNumber(value.opcode().intValue());
        Records.Requests.serialize(value, new JacksonOutputArchive(jgen));
    }
}
