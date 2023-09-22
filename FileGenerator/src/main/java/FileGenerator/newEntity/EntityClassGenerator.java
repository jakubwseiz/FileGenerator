package FileGenerator.newEntity;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;

public class EntityClassGenerator {

    private static int index;

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
}
