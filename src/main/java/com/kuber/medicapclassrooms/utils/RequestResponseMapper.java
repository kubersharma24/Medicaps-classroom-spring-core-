package com.kuber.medicapclassrooms.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestResponseMapper<T> {

    private ObjectMapper objectMapper;

    public RequestResponseMapper() {
        this.objectMapper = new ObjectMapper();
    }

    public T getRequestObject(HttpServletResponse resp, HttpServletRequest req, Class<T> t) throws IOException {
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader br = req.getReader();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return objectMapper.readValue(sb.toString(), t);
    }

    public String setResponseObject(T t) throws JsonProcessingException {
        return objectMapper.writeValueAsString(t);
    }
}
