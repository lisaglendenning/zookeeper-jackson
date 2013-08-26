package edu.uw.zookeeper.jackson.databind;


import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;

import edu.uw.zookeeper.data.Operations;
import edu.uw.zookeeper.jackson.databind.JacksonModule;
import edu.uw.zookeeper.protocol.Operation;
import edu.uw.zookeeper.protocol.ProtocolRequestMessage;
import edu.uw.zookeeper.protocol.ProtocolResponseMessage;
import edu.uw.zookeeper.protocol.proto.OpCode;
import edu.uw.zookeeper.protocol.proto.Records;

@RunWith(JUnit4.class)
public class ProtocolTest extends SerializeTest {
    
    @Test
    public void testRequests() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(
                JacksonModule.create()
                    .addSerializer(ProtocolRequestSerializer.create())
                    .addDeserializer(Operation.ProtocolRequest.class, ProtocolRequestDeserializer.create()));
        for (OpCode opcode: OpCode.values()) {
            if (opcode == OpCode.CREATE_SESSION) {
                continue;
            }
            Operations.Builder<? extends Records.Request> builder;
            try {
                builder = Operations.Requests.fromOpCode(opcode);
            } catch (IllegalArgumentException e) {
                continue;
            }
            testStringSerialization(
                    ProtocolRequestMessage.of(opcode.ordinal(), builder.build()), 
                    Operation.ProtocolRequest.class, mapper);
        }
    }
    
    @Test
    public void testResponses() throws IOException {
        final OpCode[] opcodes = OpCode.values();
        Function<Integer, OpCode> xidToOpCode = new Function<Integer, OpCode>() {
            @Override
            public OpCode apply(Integer input) {
                return opcodes[input];
            }  
        };
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(
                JacksonModule.create()
                    .addSerializer(ProtocolResponseSerializer.create())
                    .addDeserializer(Operation.ProtocolResponse.class, ProtocolResponseDeserializer.create(xidToOpCode)));
        for (OpCode opcode: opcodes) {
            if (opcode == OpCode.CREATE_SESSION) {
                continue;
            }
            Operations.Builder<? extends Records.Response> builder;
            try {
                builder = Operations.Responses.fromOpCode(opcode);
            } catch (IllegalArgumentException e) {
                continue;
            }
            testStringSerialization(
                    ProtocolResponseMessage.of(opcode.ordinal(), (long) opcode.ordinal(), builder.build()),
                    Operation.ProtocolResponse.class,
                    mapper);
        }
    }
}
