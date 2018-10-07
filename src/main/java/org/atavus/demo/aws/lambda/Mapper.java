package org.atavus.demo.aws.lambda;

import java.io.InputStream;
import java.io.OutputStream;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public class Mapper {
    private ObjectMapper mapper;

    public Mapper() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(Include.NON_NULL);
    }

    @SneakyThrows
    public <T> T readValue(InputStream in, Class<T> cls) {
        return mapper.readValue(in, cls);
    }

    @SneakyThrows
    public <T> T readValue(String in, Class<T> cls) {
        return mapper.readValue(in, cls);
    }

    @SneakyThrows
    public <T> void writeValue(OutputStream out, T value) {
        mapper.writeValue(out, value);
    }

    @SneakyThrows
    public <T> String writeValueAsString(T value) {
        return mapper.writeValueAsString(value);
    }

}
