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
        assertEquals(value, mapper.readValue(encoded, cls));
    }
}
