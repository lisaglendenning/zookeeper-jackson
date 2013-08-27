package edu.uw.zookeeper.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;


public abstract class ListCoreDeserializer<T> implements JacksonCoreDeserializer<T> {

    @Override
    public T deserialize(JsonParser json)
            throws IOException, JsonProcessingException {
        JsonToken token = json.getCurrentToken();
        if (token == null) {
            token = json.nextToken();
            if (token == null) {
                return null;
            }
        }
        if (! json.isExpectedStartArrayToken()) {
            throw new JsonParseException(String.valueOf(json.getCurrentToken()), json.getCurrentLocation());
        }
        json.clearCurrentToken();
        
        T value = deserializeValue(json);
        
        token = json.getCurrentToken();
        if (token == null) {
            token = json.nextToken();
        }
        if (token != JsonToken.END_ARRAY) {
            throw new JsonParseException(String.valueOf(token), json.getCurrentLocation());
        }
        json.clearCurrentToken();

        return value;
    }

    protected abstract T deserializeValue(JsonParser json)
            throws IOException, JsonProcessingException;
}
