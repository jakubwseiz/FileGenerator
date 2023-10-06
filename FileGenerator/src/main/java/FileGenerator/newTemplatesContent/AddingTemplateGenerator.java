package FileGenerator.newTemplatesContent;

import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class AddingTemplateGenerator {
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

    @SneakyThrows
    public static String generateAddProductTemplate(JSONObject jsonObject) {
        StringBuilder stringBuilder = new StringBuilder();
        String projectName = jsonObject.get("projectName").toString();

        JSONArray classesList = (JSONArray) jsonObject.get("classes");

        JSONObject firstClassJSONObject = (JSONObject) classesList.get(0);
        String firstClassName = firstClassJSONObject.get("name").toString();

        JSONObject secondClassJSONObject = (JSONObject) classesList.get(1);
        String secondClassName = secondClassJSONObject.get("name").toString();

        stringBuilder
                .append("<!DOCTYPE html>")
                .append(NEW_LINE)
                .append("<html xmlns:th=\"http://www.thymeleaf.org\" lang=\"en\">")
                .append(NEW_LINE)
                .append(generateHeadPart(firstClassName))
                .append(NEW_LINE)
                .append(generateBodyPart(firstClassName))
                .append(DOUBLE_NEW_LINE);

        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    public static String generateHeadPart(String firstClassName) {

        return """
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Add %s</title>
                    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                    <style>
                        /* Dodatkowy styl CSS */
                        .narrow-input {
                            max-width: 300px; /* Ogranicz szerokość pól formularza */
                        }
                        body {
                            margin-left: 50px;
                        }
                    </style>
                </head>
                """.formatted(firstClassName);

    }

    public static String generateBodyPart(String firstClassName) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("<body>")
                .append(NEW_LINE)
                .append("<h2> Add").append(firstCharToUpperCase(firstClassName)).append("</h2>")
                .append(NEW_LINE)
                .append("<form th:action=\"@{/").append(firstCharToLowerCase(firstClassName)).append("s/add}\" method=\"post\">")
                .append(NEW_LINE);



        return stringBuilder.toString();
    }

    public static String firstCharToLowerCase(String property) {
        return property.substring(0, 1).toLowerCase() + property.substring(1);
    }
    public static String firstCharToUpperCase(String property) {
        return property.substring(0, 1).toUpperCase() + property.substring(1);
    }

}
