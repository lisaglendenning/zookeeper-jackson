package edu.uw.zookeeper.jackson.databind;

import java.util.List;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.uw.zookeeper.common.Builder;

public abstract class ObjectMapperBuilder implements Builder<ObjectMapper> {

    protected ObjectMapperBuilder() {}
    
    @Override
    public ObjectMapper build() {
        ObjectMapper instance = getDefaultObjectMapper();
        for (Module module: getDefaultModules()) {
            instance.registerModule(module);
        }
        return instance;
    }
    
    protected ObjectMapper getDefaultObjectMapper() {
        return new ObjectMapper().disable(SerializationFeature.FLUSH_AFTER_WRITE_VALUE).enable(SerializationFeature.WRITE_ENUMS_USING_INDEX);
    }
    
    protected abstract List<Module> getDefaultModules();
}
