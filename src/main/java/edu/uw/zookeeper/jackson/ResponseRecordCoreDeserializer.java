package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;

import edu.uw.zookeeper.protocol.ConnectMessage;
import edu.uw.zookeeper.protocol.proto.OpCode;
import edu.uw.zookeeper.protocol.proto.Records;
import edu.uw.zookeeper.protocol.proto.Records.Response;

public class ResponseRecordCoreDeserializer implements JacksonCoreDeserializer<Records.Response> {

    public static ResponseRecordCoreDeserializer create() {
        return new ResponseRecordCoreDeserializer();
    }

    public ResponseRecordCoreDeserializer() {
        super();
    }

    @Override
    public Class<Response> handledType() {
        return Records.Response.class;
    }

    @Override
    public Records.Response deserialize(JsonParser json)
            throws IOException, JsonProcessingException {
        if (! json.isExpectedStartArrayToken()) {
            throw new JsonParseException(String.valueOf(json.getCurrentToken()), json.getCurrentLocation());
        }
        json.nextToken();
        OpCode opcode = OpCode.of(json.getIntValue());
        json.nextToken();
        JacksonInputArchive archive = new JacksonInputArchive(json);
        Records.Response value;
        switch (opcode) {
        case CREATE_SESSION:
            value = ConnectMessage.Response.deserialize(archive);
            break;
        default:
            value= Records.Responses.deserialize(opcode, archive);
            break;
        }
        if (json.getCurrentToken() != JsonToken.END_ARRAY) {
            throw new JsonParseException(String.valueOf(json.getCurrentToken()), json.getCurrentLocation());
        }
        json.nextToken();
        return value;
    }
}
