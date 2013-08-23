package edu.uw.zookeeper.jackson;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import edu.uw.zookeeper.protocol.Operation;
import edu.uw.zookeeper.protocol.ProtocolResponseMessage;
import edu.uw.zookeeper.protocol.proto.IReplyHeader;
import edu.uw.zookeeper.protocol.proto.Records;

public class ProtocolResponseSerializer extends StdSerializer<Operation.ProtocolResponse<?>> {

    public static ProtocolResponseSerializer create() {
        return new ProtocolResponseSerializer();
    }

    public ProtocolResponseSerializer() {
        super(Operation.ProtocolResponse.class, true);
    }

    @Override
    public void serialize(Operation.ProtocolResponse<?> value, JsonGenerator json, SerializerProvider provider) throws JsonGenerationException, IOException {
        Records.Response record = value.record();
        IReplyHeader header;
        if (value instanceof ProtocolResponseMessage) {
            header = ((ProtocolResponseMessage<?>) value).header();
        } else {
            KeeperException.Code err;
            if (record instanceof Operation.Error) {
                err = ((Operation.Error) record).error();
            } else {
                err = KeeperException.Code.OK;
            }
            header = Records.Responses.Headers.newInstance(value.xid(), value.zxid(), err);
        }
        json.writeStartArray();
        JacksonOutputArchive archive = new JacksonOutputArchive(json);
        Records.Responses.Headers.serialize(header, archive);
        if (KeeperException.Code.OK.intValue() == header.getErr()) {
            Records.Responses.serialize(record, archive);
        }
        json.writeEndArray();
    }
}
