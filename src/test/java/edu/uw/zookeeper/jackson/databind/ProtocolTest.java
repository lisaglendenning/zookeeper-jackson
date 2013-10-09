package edu.uw.zookeeper.jackson.databind;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import edu.uw.zookeeper.data.Operations;
import edu.uw.zookeeper.protocol.Message;
import edu.uw.zookeeper.protocol.ProtocolRequestMessage;
import edu.uw.zookeeper.protocol.ProtocolResponseMessage;
import edu.uw.zookeeper.protocol.proto.OpCode;
import edu.uw.zookeeper.protocol.proto.Records;

@RunWith(JUnit4.class)
public class ProtocolTest extends SerializeTest {

    public static class RequestModuleBuilder extends JacksonModuleBuilder {
        
        public RequestModuleBuilder() {}
        
        @Override
        protected List<JsonSerializer<?>> getDefaultSerializers() {
            return ImmutableList.<JsonSerializer<?>>of(
                    ProtocolRequestSerializer.create());
        }

        @Override
        protected Map<Class<?>, JsonDeserializer<?>> getDefaultDeserializers() {
            return ImmutableMap.<Class<?>, JsonDeserializer<?>>of(
                    Message.ClientRequest.class, ProtocolRequestDeserializer.create());
        }   
    }
    
    public static class RequestObjectMapperBuilder extends ObjectMapperBuilder {

        public RequestObjectMapperBuilder() {}
        
        @Override
        protected List<Module> getDefaultModules() {
            return ImmutableList.<Module>of(new RequestModuleBuilder().build());
        }
    }
    
    public static class ResponseModuleBuilder extends JacksonModuleBuilder {
        
        protected final Function<Integer, OpCode> xidToOpCode;
        
        public ResponseModuleBuilder(Function<Integer, OpCode> xidToOpCode) {
            this.xidToOpCode = xidToOpCode;
        }
        
        @Override
        protected List<JsonSerializer<?>> getDefaultSerializers() {
            return ImmutableList.<JsonSerializer<?>>of(
                    ProtocolResponseSerializer.create());
        }

        @Override
        protected Map<Class<?>, JsonDeserializer<?>> getDefaultDeserializers() {
            return ImmutableMap.<Class<?>, JsonDeserializer<?>>of(
                    Message.ServerResponse.class, ProtocolResponseDeserializer.create(xidToOpCode));
        }   
    }
    
    public static class ResponseObjectMapperBuilder extends ObjectMapperBuilder {

        protected final Function<Integer, OpCode> xidToOpCode;
        
        public ResponseObjectMapperBuilder(Function<Integer, OpCode> xidToOpCode) {
            this.xidToOpCode = xidToOpCode;
        }
        
        @Override
        protected List<Module> getDefaultModules() {
            return ImmutableList.<Module>of(new ResponseModuleBuilder(xidToOpCode).build());
        }
    }
    
    @Test
    public void testRequests() throws IOException {
        ObjectMapper mapper = new RequestObjectMapperBuilder().build();
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
                    Message.ClientRequest.class, mapper);
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
        ObjectMapper mapper = new ResponseObjectMapperBuilder(xidToOpCode).build();
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
                    Message.ServerResponse.class,
                    mapper);
        }
    }
}
