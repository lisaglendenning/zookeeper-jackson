package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import edu.uw.zookeeper.protocol.proto.OpCode;
import edu.uw.zookeeper.protocol.proto.Records;

public class ResponseRecordDeserializer extends StdDeserializer<Records.Response> {

    public static ResponseRecordDeserializer create() {
        return new ResponseRecordDeserializer();
    }

    private static final long serialVersionUID = -9158763633870689053L;

    public ResponseRecordDeserializer() {
        super(Records.Response.class);
    }

    @Override
    public Records.Response deserialize(JsonParser json, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        if (! json.isExpectedStartArrayToken()) {
            throw new JsonParseException(String.valueOf(json.getCurrentToken()), json.getCurrentLocation());
        }
        json.nextToken();
        OpCode opcode = OpCode.of(json.getIntValue());
        json.nextToken();
        Records.Response value = Records.Responses.deserialize(opcode, new JacksonInputArchive(json));
        if (json.getCurrentToken() != JsonToken.END_ARRAY) {
            throw new JsonParseException(String.valueOf(json.getCurrentToken()), json.getCurrentLocation());
        }
        json.nextToken();
        return value;
    }
}
