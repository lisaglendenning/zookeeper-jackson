package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface JacksonCoreDeserializer<T> {
    
    Class<?> handledType();

    T deserialize(JsonParser json) throws IOException, JsonProcessingException;
}
