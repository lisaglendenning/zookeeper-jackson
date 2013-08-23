package edu.uw.zookeeper.jackson;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uw.zookeeper.data.Operations;
import edu.uw.zookeeper.protocol.ConnectMessage;
import edu.uw.zookeeper.protocol.proto.OpCode;
import edu.uw.zookeeper.protocol.proto.Records;

@RunWith(JUnit4.class)
public class RecordTest {
    
    protected final Logger logger = LogManager.getLogger(getClass());
    
    @Test
    public void testRequests() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(
                JacksonModule.create()
                    .addSerializer(RequestRecordSerializer.create())
                    .addDeserializer(Records.Request.class, RequestRecordDeserializer.create()));
        Records.Request input = ConnectMessage.Request.NewRequest.newInstance();
        String encoded = mapper.writeValueAsString(input);
        logger.debug(encoded);
        Records.Request output = mapper.readValue(encoded, Records.Request.class);
        assertEquals(input, output);
        for (OpCode opcode: OpCode.values()) {
            Operations.Builder<? extends Records.Request> builder;
            try {
                builder = Operations.Requests.fromOpCode(opcode);
            } catch (IllegalArgumentException e) {
                continue;
            }
            input = builder.build();
            encoded = mapper.writeValueAsString(input);
            logger.debug(encoded);
            output = mapper.readValue(encoded, Records.Request.class);
            assertEquals(input, output);
        }
    }
    
    @Test
    public void testResponses() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(
                JacksonModule.create()
                    .addSerializer(ResponseRecordSerializer.create())
                    .addDeserializer(Records.Response.class, ResponseRecordDeserializer.create()));
        Records.Response input = ConnectMessage.Response.Invalid.newInstance();
        String encoded = mapper.writeValueAsString(input);
        logger.debug(encoded);
        Records.Response output = mapper.readValue(encoded, Records.Response.class);
        assertEquals(input, output);
        for (OpCode opcode: OpCode.values()) {
            Operations.Builder<? extends Records.Response> builder;
            try {
                builder = Operations.Responses.fromOpCode(opcode);
            } catch (IllegalArgumentException e) {
                continue;
            }
            input = builder.build();
            encoded = mapper.writeValueAsString(input);
            logger.debug(encoded);
            output = mapper.readValue(encoded, Records.Response.class);
            assertEquals(input, output);
        }
    }
}
