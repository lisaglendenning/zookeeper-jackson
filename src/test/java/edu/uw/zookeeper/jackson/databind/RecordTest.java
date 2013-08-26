package edu.uw.zookeeper.jackson.databind;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uw.zookeeper.data.Operations;
import edu.uw.zookeeper.jackson.databind.JacksonModule;
import edu.uw.zookeeper.jackson.databind.RequestRecordDeserializer;
import edu.uw.zookeeper.jackson.databind.RequestRecordSerializer;
import edu.uw.zookeeper.jackson.databind.ResponseRecordDeserializer;
import edu.uw.zookeeper.jackson.databind.ResponseRecordSerializer;
import edu.uw.zookeeper.protocol.proto.OpCode;
import edu.uw.zookeeper.protocol.proto.Records;

@RunWith(JUnit4.class)
public class RecordTest extends SerializeTest {
    
    @Test
    public void testRequests() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(
                JacksonModule.create()
                    .addSerializer(RequestRecordSerializer.create())
                    .addDeserializer(Records.Request.class, RequestRecordDeserializer.create()));
        for (OpCode opcode: OpCode.values()) {
            Operations.Builder<? extends Records.Request> builder;
            try {
                builder = Operations.Requests.fromOpCode(opcode);
            } catch (IllegalArgumentException e) {
                continue;
            }
            testStringSerialization(builder.build(), Records.Request.class, mapper);
        }
    }
    
    @Test
    public void testResponses() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(
                JacksonModule.create()
                    .addSerializer(ResponseRecordSerializer.create())
                    .addDeserializer(Records.Response.class, ResponseRecordDeserializer.create()));
        for (OpCode opcode: OpCode.values()) {
            Operations.Builder<? extends Records.Response> builder;
            try {
                builder = Operations.Responses.fromOpCode(opcode);
            } catch (IllegalArgumentException e) {
                continue;
            }
            testStringSerialization(builder.build(), Records.Response.class, mapper);
        }
    }
}
