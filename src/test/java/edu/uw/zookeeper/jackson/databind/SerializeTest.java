package edu.uw.zookeeper.jackson.databind;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializeTest {

    protected final Logger logger = LogManager.getLogger(getClass());
    
    public void testStringSerialization(Object value, Class<?> cls, ObjectMapper mapper) throws IOException {
        String encoded = mapper.writeValueAsString(value);
        logger.debug(encoded);
        Object output;
        try {
            output = mapper.readValue(encoded, cls);
        } catch (IOException e) {
            throw new IOException(String.valueOf(value), e);
        }
        assertEquals(value, output);
    }
}
