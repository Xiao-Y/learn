package com.billow.aop.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

class CustomDateDeserializer extends JsonDeserializer<Date> {
    private final SimpleDateFormat dateFormat;
    private static final TimeZone GMT_8_TIMEZONE = TimeZone.getTimeZone("GMT+8");


    public CustomDateDeserializer(String pattern) {
        dateFormat = new SimpleDateFormat(pattern);
        dateFormat.setTimeZone(GMT_8_TIMEZONE);
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateAsString = p.getText();
        try {
            return dateFormat.parse(dateAsString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}