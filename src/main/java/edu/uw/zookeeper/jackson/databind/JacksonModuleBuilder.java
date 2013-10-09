package edu.uw.zookeeper.jackson.databind;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;

import edu.uw.zookeeper.common.Builder;
import edu.uw.zookeeper.jackson.Version;

public abstract class JacksonModuleBuilder implements Builder<Module> {

    protected JacksonModuleBuilder() {}
    
    @Override
    public Module build() {
        return new SimpleModule(
                getDefaultProjectName(),
                getDefaultVersion(),
                getDefaultDeserializers(),
                getDefaultSerializers());
    }
    
    protected com.fasterxml.jackson.core.Version getDefaultVersion() {
        edu.uw.zookeeper.Version version = Version.getDefault();
        return new com.fasterxml.jackson.core.Version(
                version.getMajor(),
                version.getMinor(),
                version.getPatch(),
                version.getLabel(),
                Version.getGroup(),
                Version.getArtifact());
    }
    
    protected String getDefaultProjectName() {
        return Version.getProjectName();
    }

    protected abstract Map<Class<?>, JsonDeserializer<?>> getDefaultDeserializers();

    protected abstract List<JsonSerializer<?>> getDefaultSerializers();
}
