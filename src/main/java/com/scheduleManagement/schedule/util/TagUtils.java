package com.scheduleManagement.schedule.util;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class TagUtils {
    public static List<String> extractTagNamesFromRequestBody(JsonNode requestBody){
        List<String> tagNames = new ArrayList<>();

        JsonNode tagsNode = requestBody.get("tags"); // 가정: "tags"라는 키로 태그 목록이 전송됨

        if (tagsNode != null && tagsNode.isArray()) {
            for (JsonNode tagNode : tagsNode) {
                if (tagNode.isTextual()) {
                    tagNames.add(tagNode.asText());
                }
            }
        }

        return tagNames;
    }
}
