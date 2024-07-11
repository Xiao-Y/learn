package com.billow.aop.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

class CustomDateSerializer extends JsonSerializer<Date> {
    private final SimpleDateFormat dateFormat;
    private static final TimeZone GMT_8_TIMEZONE = TimeZone.getTimeZone("GMT+8");

    public CustomDateSerializer(String pattern) {
        dateFormat = new SimpleDateFormat(pattern);
        dateFormat.setTimeZone(GMT_8_TIMEZONE);
    }

    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String formattedDate = dateFormat.format(date);
        gen.writeString(formattedDate);
    }
}