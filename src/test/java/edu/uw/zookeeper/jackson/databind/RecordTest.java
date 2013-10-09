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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import edu.uw.zookeeper.data.Operations;
import edu.uw.zookeeper.jackson.databind.RequestRecordDeserializer;
import edu.uw.zookeeper.jackson.databind.RequestRecordSerializer;
import edu.uw.zookeeper.jackson.databind.ResponseRecordDeserializer;
import edu.uw.zookeeper.jackson.databind.ResponseRecordSerializer;
import edu.uw.zookeeper.protocol.proto.OpCode;
import edu.uw.zookeeper.protocol.proto.Records;

@RunWith(JUnit4.class)
public class RecordTest extends SerializeTest {
    
    public static class RequestModuleBuilder extends JacksonModuleBuilder {
        
        public RequestModuleBuilder() {}
        
        @Override
        protected List<JsonSerializer<?>> getDefaultSerializers() {
            return ImmutableList.<JsonSerializer<?>>of(
                    RequestRecordSerializer.create());
        }

        @Override
        protected Map<Class<?>, JsonDeserializer<?>> getDefaultDeserializers() {
            return ImmutableMap.<Class<?>, JsonDeserializer<?>>of(
                    Records.Request.class, RequestRecordDeserializer.create());
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
        
        public ResponseModuleBuilder() {}
        
        @Override
        protected List<JsonSerializer<?>> getDefaultSerializers() {
            return ImmutableList.<JsonSerializer<?>>of(
                    ResponseRecordSerializer.create());
        }

        @Override
        protected Map<Class<?>, JsonDeserializer<?>> getDefaultDeserializers() {
            return ImmutableMap.<Class<?>, JsonDeserializer<?>>of(
                    Records.Response.class, ResponseRecordDeserializer.create());
        }   
    }
    
    public static class ResponseObjectMapperBuilder extends ObjectMapperBuilder {

        public ResponseObjectMapperBuilder() {}
        
        @Override
        protected List<Module> getDefaultModules() {
            return ImmutableList.<Module>of(new ResponseModuleBuilder().build());
        }
    }
    
    @Test
    public void testRequests() throws IOException {
        ObjectMapper mapper = new RequestObjectMapperBuilder().build();
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
        ObjectMapper mapper = new ResponseObjectMapperBuilder().build();
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
