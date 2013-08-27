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

public class ResponseRecordCoreDeserializer extends ListCoreDeserializer<Records.Response> {

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
    protected Records.Response deserializeValue(JsonParser json)
            throws IOException, JsonProcessingException {
        JsonToken token = json.getCurrentToken();
        if (token == null) {
            token = json.nextToken();
            if (token == null) {
                return null;
            }
        }
        if (token != JsonToken.VALUE_NUMBER_INT) {
            throw new JsonParseException(String.valueOf(json.getCurrentToken()), json.getCurrentLocation());
        }
        OpCode opcode = OpCode.of(json.getIntValue());
        json.clearCurrentToken();
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
        return value;
    }
}
