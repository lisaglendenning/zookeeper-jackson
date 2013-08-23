package edu.uw.zookeeper.jackson;

import static com.google.common.base.Preconditions.*;

import java.io.IOException;

import org.apache.jute.Index;
import org.apache.jute.InputArchive;
import org.apache.jute.Record;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;

public class JacksonInputArchive implements InputArchive {

    protected final JsonParser json;
    
    public JacksonInputArchive(JsonParser json) {
        this.json = json;
    }

    @Override
    public byte readByte(String tag) throws IOException {
        return readBuffer(tag)[0];
    }

    @Override
    public boolean readBool(String tag) throws IOException {
        boolean value = json.getBooleanValue();
        json.nextToken();
        return value;
    }

    @Override
    public int readInt(String tag) throws IOException {
        int value = json.getIntValue();
        json.nextToken();
        return value;
    }

    @Override
    public long readLong(String tag) throws IOException {
        long value = json.getLongValue();
        json.nextToken();
        return value;
    }

    @Override
    public float readFloat(String tag) throws IOException {
        float value = json.getFloatValue();
        json.nextToken();
        return value;
    }

    @Override
    public double readDouble(String tag) throws IOException {
        double value = json.getDoubleValue();
        json.nextToken();
        return value;
    }

    @Override
    public String readString(String tag) throws IOException {
        String value;
        if (json.getCurrentToken() == JsonToken.VALUE_NULL) {
            value = null;
        } else {
            value = json.getValueAsString();
        }
        json.nextToken();
        return value;
    }

    @Override
    public byte[] readBuffer(String tag) throws IOException {
        byte[] value;
        if (json.getCurrentToken() == JsonToken.VALUE_NULL) {
            value = null;
        } else {
            value = json.getBinaryValue();
        }
        json.nextToken();
        return value;
    }

    @Override
    public void readRecord(Record r, String tag) throws IOException {
        if (r == null) {
            if (! json.hasCurrentToken()) {
                json.nextToken();
            }
            if (json.getCurrentToken() != JsonToken.VALUE_NULL) {
                throw new IllegalArgumentException(String.valueOf(r));
            }
            json.nextToken();
        } else {
            r.deserialize(this, tag);
        }
    }

    @Override
    public void startRecord(String tag) throws IOException {
        if (! json.hasCurrentToken()) {
            json.nextToken();
        }
        if (! json.isExpectedStartArrayToken()) {
            throw new JsonParseException(String.valueOf(json.getCurrentToken()), json.getCurrentLocation());
        }
        json.nextToken();
    }

    @Override
    public void endRecord(String tag) throws IOException {
        if (json.getCurrentToken() != JsonToken.END_ARRAY) {
            throw new JsonParseException(String.valueOf(json.getCurrentToken()), json.getCurrentLocation());
        }
        json.nextToken();
    }

    @Override
    public Index startVector(String tag) throws IOException {
        Index value;
        if (json.getCurrentToken() == JsonToken.VALUE_NULL) {
            value = null;
        } else {
            if (! json.isExpectedStartArrayToken()) {
                throw new JsonParseException(String.valueOf(json.getCurrentToken()), json.getCurrentLocation());
            }
            value = new ArrayIndex();
        }
        json.nextToken();
        return value;
    }

    @Override
    public void endVector(String tag) throws IOException {
        if (json.getCurrentToken() == JsonToken.END_ARRAY) {
            json.nextToken();
        }
    }

    @Override
    public Index startMap(String tag) throws IOException {
        Index value;
        if (json.getCurrentToken() == JsonToken.VALUE_NULL) {
            value = null;
        } else {
            if (! json.isExpectedStartArrayToken()) {
                throw new JsonParseException(String.valueOf(json.getCurrentToken()), json.getCurrentLocation());
            }
            value = new ArrayIndex();
        }
        json.nextToken();
        return value;
    }

    @Override
    public void endMap(String tag) throws IOException {
        if (json.getCurrentToken() == JsonToken.END_ARRAY) {
            json.nextToken();
        }
    }

    protected class ArrayIndex implements Index {
        
        private final JsonStreamContext context;
        
        public ArrayIndex() {
            this.context = json.getParsingContext();
            checkState(context.inArray());
        }
        
        @Override
        public boolean done() {
            JsonStreamContext c = json.getParsingContext();
            while (!context.equals(c) && (c != null)) {
                c = c.getParent();
            }
            return ((!context.equals(c)) ||
                    (json.getCurrentToken() == JsonToken.END_ARRAY));
        }

        @Override
        public void incr() {
        }
    }
}
