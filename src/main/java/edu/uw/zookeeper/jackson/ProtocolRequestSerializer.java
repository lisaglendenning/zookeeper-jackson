package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import edu.uw.zookeeper.protocol.Operation;
import edu.uw.zookeeper.protocol.ProtocolRequestMessage;
import edu.uw.zookeeper.protocol.proto.IRequestHeader;
import edu.uw.zookeeper.protocol.proto.Records;

public class ProtocolRequestSerializer extends StdSerializer<Operation.ProtocolRequest<?>> {

    public static ProtocolRequestSerializer create() {
        return new ProtocolRequestSerializer();
    }

    public ProtocolRequestSerializer() {
        super(Operation.ProtocolRequest.class, true);
    }

    @Override
    public void serialize(Operation.ProtocolRequest<?> value, JsonGenerator json, SerializerProvider provider) throws JsonGenerationException, IOException {
        IRequestHeader header;
        if (value instanceof ProtocolRequestMessage) {
            header = ((ProtocolRequestMessage<?>) value).header();
        } else {
            header = Records.Requests.Headers.newInstance(value.xid(), value.record().opcode());
        }
        Records.Request record = value.record();
        json.writeStartArray();
        JacksonOutputArchive archive = new JacksonOutputArchive(json);
        Records.Requests.Headers.serialize(header, archive);
        Records.Requests.serialize(record, archive);
        json.writeEndArray();
    }
}
