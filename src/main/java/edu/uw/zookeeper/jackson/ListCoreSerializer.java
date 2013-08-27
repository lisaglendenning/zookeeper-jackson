package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;

public abstract class ListCoreSerializer<T> implements JacksonCoreSerializer<T> {

    @Override
    public void serialize(T value, JsonGenerator json) throws JsonGenerationException, IOException {
        json.writeStartArray();
        serializeValue(value, json);
        json.writeEndArray();
    }
    
    protected abstract void serializeValue(T value, JsonGenerator json) throws JsonGenerationException, IOException;
}
