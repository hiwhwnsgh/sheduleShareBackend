package com.scheduleManagement.schedule.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.scheduleManagement.schedule.domain.Tag;

import java.io.IOException;
import java.util.List;

// tags JSON 직렬화
public class TagListSerializer extends JsonSerializer<List<Tag>> {

    @Override
    public void serialize(List<Tag> tags, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for (Tag tag : tags) {
            gen.writeString(tag.getName());
        }
        gen.writeEndArray();
    }
}
