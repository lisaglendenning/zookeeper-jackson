package edu.uw.zookeeper.jackson;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

import org.apache.jute.OutputArchive;
import org.apache.jute.Record;

import com.fasterxml.jackson.core.JsonGenerator;

public class JacksonOutputArchive implements OutputArchive {

    protected final JsonGenerator json;
    
    public JacksonOutputArchive(JsonGenerator json) {
        this.json = json;
    }
    
    @Override
    public void writeByte(byte b, String tag) throws IOException {
        byte[] buf = { b };
        writeBuffer(buf, tag);
    }

    @Override
    public void writeBool(boolean b, String tag) throws IOException {
        json.writeBoolean(b);
    }

    @Override
    public void writeInt(int i, String tag) throws IOException {
        json.writeNumber(i);
    }

    @Override
    public void writeLong(long l, String tag) throws IOException {
        json.writeNumber(l);
    }

    @Override
    public void writeFloat(float f, String tag) throws IOException {
        json.writeNumber(f);
    }

    @Override
    public void writeDouble(double d, String tag) throws IOException {
        json.writeNumber(d);
    }

    @Override
    public void writeString(String s, String tag) throws IOException {
        if (s == null) {
            json.writeNull();
        } else {
            json.writeString(s);
        }
    }

    @Override
    public void writeBuffer(byte[] buf, String tag) throws IOException {
        if (buf == null) {
            json.writeNull();
        } else {
            json.writeBinary(buf);
        }
    }

    @Override
    public void writeRecord(Record r, String tag) throws IOException {
        if (r == null) {
            json.writeNull();
        } else {
            r.serialize(this, tag);
        }
    }

    @Override
    public void startRecord(Record r, String tag) throws IOException {
        checkNotNull(r);
        json.writeStartArray();
    }

    @Override
    public void endRecord(Record r, String tag) throws IOException {
        checkNotNull(r);
        json.writeEndArray();
    }

    @Override
    public void startVector(List<?> v, String tag) throws IOException {
        if (v == null) {
            json.writeNull();
        } else {
            json.writeStartArray();
        }
    }

    @Override
    public void endVector(List<?> v, String tag) throws IOException {
        if (v != null) {
            json.writeEndArray();
        }
    }

    @Override
    public void startMap(TreeMap<?, ?> v, String tag) throws IOException {
        if (v == null) {
            json.writeNull();
        } else {
            json.writeStartArray();
        }
    }

    @Override
    public void endMap(TreeMap<?, ?> v, String tag) throws IOException {
        if (v != null) {
            json.writeEndArray();
        }
    }
}
