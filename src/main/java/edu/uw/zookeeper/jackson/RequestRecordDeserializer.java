package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import edu.uw.zookeeper.protocol.ConnectMessage;
import edu.uw.zookeeper.protocol.proto.OpCode;
import edu.uw.zookeeper.protocol.proto.Records;

public class RequestRecordDeserializer extends StdDeserializer<Records.Request> {

    public static RequestRecordDeserializer create() {
        return new RequestRecordDeserializer();
    }

    private static final long serialVersionUID = 191925488788083157L;

    public RequestRecordDeserializer() {
        super(Records.Request.class);
    }

    @Override
    public Records.Request deserialize(JsonParser json, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        if (! json.isExpectedStartArrayToken()) {
            throw new JsonParseException(String.valueOf(json.getCurrentToken()), json.getCurrentLocation());
        }
        json.nextToken();
        OpCode opcode = OpCode.of(json.getIntValue());
        json.nextToken();
        JacksonInputArchive archive = new JacksonInputArchive(json);
        Records.Request value;
        switch (opcode) {
        case CREATE_SESSION:
            value = ConnectMessage.Request.deserialize(archive);
            break;
        default:
            value = Records.Requests.deserialize(opcode, archive);
            break;
        }
        if (json.getCurrentToken() != JsonToken.END_ARRAY) {
            throw new JsonParseException(String.valueOf(json.getCurrentToken()), json.getCurrentLocation());
        }
        json.nextToken();
        return value;
    }
}
