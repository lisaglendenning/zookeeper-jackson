package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;

public interface JacksonCoreSerializer<T> {
    
    Class<?> handledType();
    
    void serialize(T value, JsonGenerator json) throws JsonGenerationException, IOException;
}
