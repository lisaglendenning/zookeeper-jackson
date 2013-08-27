package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;

import edu.uw.zookeeper.protocol.ConnectMessage;
import edu.uw.zookeeper.protocol.proto.OpCode;
import edu.uw.zookeeper.protocol.proto.Records;

public class RequestRecordCoreDeserializer extends ListCoreDeserializer<Records.Request> {

    public static RequestRecordCoreDeserializer create() {
        return new RequestRecordCoreDeserializer();
    }

    public RequestRecordCoreDeserializer() {
        super();
    }

    @Override
    public Class<Records.Request> handledType() {
        return Records.Request.class;
    }

    @Override
    protected Records.Request deserializeValue(JsonParser json)
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
        Records.Request value;
        switch (opcode) {
        case CREATE_SESSION:
            value = ConnectMessage.Request.deserialize(archive);
            break;
        default:
            value = Records.Requests.deserialize(opcode, archive);
            break;
        }
        return value;
    }
}
