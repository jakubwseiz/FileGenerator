package FileGenerator.newTemplatesContent;

import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Objects;

public class ProductTemplateGenerator {
    private static final char END_STATEMENT = ';';
    private static final String NEW_LINE = System.lineSeparator();
    private static final String SPACE = " ";
    private static final String BIG_SPACE = "    ";
    private static final String DOUBLE_NEW_LINE = System.lineSeparator() + System.lineSeparator();
    private static final char BLOCK_OPEN = '{';
    private static final char BLOCK_CLOSED = '}';
    private static final String METHOD_NO_ARGS = "()";
    private static final char METHOD_OPEN = '(';
    private static final char METHOD_CLOSED = ')';

    public static String generateProductTemplate(JSONObject jsonObject) {
        StringBuilder stringBuilder = new StringBuilder();
        JSONArray classesList = (JSONArray) jsonObject.get("classes");

        String className = null;

        for (Object p : classesList) {
            JSONObject singleClass = (JSONObject) p;
            className = singleClass.get("name").toString();
            break;
        }

        stringBuilder
                .append("<!DOCTYPE html>")
                .append(NEW_LINE)
                .append("<html xmlns:th=\"http://www.thymeleaf.org\">")
                .append(NEW_LINE)
                .append(generateHeadPart(className))
                .append(NEW_LINE)
                .append(generateBodyPart(classesList))
                .append(DOUBLE_NEW_LINE)
                .append("<a th:href=\"@{/").append(firstCharToLowerCase(className)).append("s}\">Back to ").append(className).append("s</a>")
                .append(NEW_LINE)
                .append("</body>")
                .append(NEW_LINE)
                .append("</html>");

        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    public static String generateHeadPart(String className) {

        return """
                <head>
                    <title>%s</title>
                    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                    <style>
                        body {
                            margin-left: 50px;
                        }
                    </style>
                </head>
                
                """.formatted(className);
    }
    @SneakyThrows
    public static String generateBodyPart(JSONArray classesList) {
        StringBuilder stringBuilder = new StringBuilder();
        JSONParser parser = new JSONParser();

        stringBuilder.append("<body>");


        JSONObject classJSONObject = (JSONObject) parser.parse(classesList.get(0).toString());
        String className = classJSONObject.get("name").toString();
        JSONArray properties = (JSONArray) classJSONObject.get("properties");

        stringBuilder
                .append(NEW_LINE)
                .append("<h1>").append(className).append(" Details").append("</h1>")
                .append(NEW_LINE)
                .append(generateTableForFirstClass(properties, className))
                .append(NEW_LINE);

        JSONObject secondClassObject = (JSONObject) parser.parse(classesList.get(1).toString());
        String secondClassName = secondClassObject.get("name").toString();
        JSONArray secondProperties = (JSONArray) secondClassObject.get("properties");

        stringBuilder
                .append(NEW_LINE)
                .append("<h2>").append(secondClassName).append("s").append("</h2>")
                .append(NEW_LINE)
                .append(generateTableForSecondClass(properties, secondProperties, className,secondClassName));

        return stringBuilder.toString();
    }

    public static String generateTableForFirstClass(JSONArray properties, String className) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("<table class=\"table\">")
                .append(NEW_LINE).append(SPACE);

        for (Object c : properties) {
            JSONObject properyJSONObject = (JSONObject) c;
            String propertyName = properyJSONObject.get("name").toString();

            if (properyJSONObject.containsKey("mapField")) {
                continue;
            } else {
                stringBuilder
                        .append(NEW_LINE).append(SPACE)
                        .append("<tr>")
                        .append(NEW_LINE).append(BIG_SPACE)
                        .append("<th>").append(properyJSONObject.get("name")).append(":</th>")
                        .append(NEW_LINE).append(BIG_SPACE)
                        .append("<td th:text=\"${").append(firstCharToLowerCase(className)).append(".").append(properyJSONObject.get("name")).append("}\"></td>")
                        .append(NEW_LINE).append(SPACE).append("</tr>");
            }

        }
        stringBuilder
                .append("/table")
                .append(NEW_LINE);

        return stringBuilder.toString();

    }

    public static String firstCharToLowerCase(String property) {
        return property.substring(0, 1).toLowerCase() + property.substring(1);
    }

    public static String generateTableForSecondClass( JSONArray properties, JSONArray secondProperties, String className ,String secondClassName) {

        StringBuilder stringBuilder = new StringBuilder();
        JSONObject lastObject = (JSONObject) properties.get(properties.size() - 1);


        stringBuilder
                .append("<table class=\"table\">")
                .append(NEW_LINE).append(SPACE)
                .append("<thead>")
                .append(NEW_LINE).append(SPACE)
                .append("<tr>");

        for (Object c : secondProperties) {
            JSONObject properyJSONObject = (JSONObject) c;
            String propertyName = properyJSONObject.get("name").toString();

            if (Objects.equals(propertyName, firstCharToLowerCase(className))) {
                continue;
            }
            stringBuilder
                    .append(NEW_LINE).append(BIG_SPACE)
                    .append("<th>").append(propertyName).append("</th>");
        }


        stringBuilder
                .append(NEW_LINE).append(SPACE)
                .append("</tr>")
                .append(NEW_LINE).append(SPACE)
                .append("</thead>")
                .append(NEW_LINE).append(SPACE)
                .append("<tbody>")
                .append(NEW_LINE).append(SPACE);

        for (Object c : properties) {
            JSONObject properyJSONObject = (JSONObject) c;
            String propertyType = properyJSONObject.get("type").toString();

            if (Objects.equals(propertyType, "List<" + secondClassName + ">")) {

                stringBuilder
                        .append("<tr th:each=\"item : ${").append(firstCharToLowerCase(className)).append(".").append(lastObject.get("name")).append("}\">")
                        .append(NEW_LINE).append(BIG_SPACE);

                for (Object d : secondProperties) {
                    JSONObject secondProperyJSONObject = (JSONObject) d;
                    String secondPropertyName = secondProperyJSONObject.get("name").toString();

                    if (Objects.equals(secondPropertyName, firstCharToLowerCase(className))) {
                        continue;
                    }
                    stringBuilder
                            .append(NEW_LINE).append(BIG_SPACE)
                            .append("<td:text=\"$item.").append(secondPropertyName).append("}\"></td>");
                }
            }
        }
        stringBuilder
                .append(NEW_LINE).append(SPACE)
                .append("</tr>")
                .append(NEW_LINE).append(SPACE)
                .append("</tbody>")
                .append(NEW_LINE)
                .append("</table>");

        return stringBuilder.toString();
    }
}
