package FileGenerator.newEntity;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;

import static FileGenerator.newEntity.EntityImportGenerator.generateEntityImportContent;

public class EntityClassGenerator {

    public static String generateClass(JSONObject jsonObject) {

        StringBuilder content = new StringBuilder();
        JSONArray jsonArray = (JSONArray) jsonObject.get("fields");

        content.append(jsonObject.get("Date"));
        content.append("\r\n\r\n");
        content.append("test").append("\r\n").append(jsonObject.get("projectName")).append(" a czy można ciągiem dodawać");

        System.out.println(content.toString());
        System.out.println("\r\n\r\n");

        for (Object fieldObject : jsonArray) {
            if (fieldObject instanceof JSONObject field) {
                String fieldName = (String) field.get("fieldName");
                System.out.println(fieldName + "\r\n");
            }
        }

        return content.toString();
    }

    public static String generateRealClass(JSONObject jsonObject) {

        StringBuilder classContent = new StringBuilder();
        classContent.append("package com.example.").append(jsonObject.get("projectName")).append(".Entities;")
                .append("\r\n")
                .append(generateEntityImportContent());

        System.out.println(classContent.toString());
        return classContent.toString();
    }
}
