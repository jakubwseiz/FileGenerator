package FileGenerator.newTemplatesContent;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AllProductsTemplateGenerator {
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

    public static String generateAllProductsTemplate(JSONObject jsonObject) {
        StringBuilder stringBuilder = new StringBuilder();

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
                .append(generateBodyPart(firstClassName, firstClassJSONObject, secondClassName, secondClassJSONObject))
                .append(NEW_LINE);

        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }


    private static String generateHeadPart(String firstClassName) {
        return """
                <head>
                    <title>%ss</title>
                    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                    <style>
                        .header-container {
                            margin-left: 100px;
                            margin-bottom: 20px;
                        }
                        .add-button {
                            margin-top: 20px;
                            margin-left: 100px;
                        }
                    </style>
                </head>
                """.formatted(firstCharToUpperCase(firstClassName));
    }

    public static String firstCharToLowerCase(String property) {
        return property.substring(0, 1).toLowerCase() + property.substring(1);
    }
    public static String firstCharToUpperCase(String property) {
        return property.substring(0, 1).toUpperCase() + property.substring(1);
    }


    private static String generateBodyPart(String firstClassName, JSONObject firstClassJSONObject, String secondClassName, JSONObject secondClassJSONObject) {
        StringBuilder stringBuilder = new StringBuilder();

        JSONArray firstClassProperties = (JSONArray) firstClassJSONObject.get("properties");

        stringBuilder
                .append("<body>")
                .append(NEW_LINE)
                .append("<div class=\"header-container\">")
                .append(NEW_LINE)
                .append(BIG_SPACE).append("<h1>").append(firstCharToUpperCase(firstClassName)).append("s</h1>")
                .append(NEW_LINE)
                .append("</div>")
                .append(NEW_LINE)
                .append("<div class=\"container\">")
                .append(NEW_LINE)
                .append(BIG_SPACE).append("<table class=\"table\">")
                .append(NEW_LINE)
                .append(BIG_SPACE).append(BIG_SPACE).append("<thead>")
                .append(NEW_LINE)
                .append(BIG_SPACE).append(BIG_SPACE).append("<tr>")
                .append(NEW_LINE);

        for (Object o : firstClassProperties) {
            JSONObject classProperty = (JSONObject) o;
            String propertyName = (String) classProperty.get("name");

            if (classProperty.containsKey("mapField")) {
                continue;
            }
            stringBuilder
                    .append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append("<th>").append(firstCharToUpperCase(propertyName)).append("</th>")
                    .append(NEW_LINE);

        }

        stringBuilder
                .append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append("<th>Actions</th>")
                .append(NEW_LINE)
                .append(BIG_SPACE).append(BIG_SPACE).append("</tr>")
                .append(NEW_LINE)
                .append(BIG_SPACE).append(BIG_SPACE).append("</thead>")
                .append(NEW_LINE)
                .append(generateTBodyPart(firstClassJSONObject, firstClassName))
                .append(NEW_LINE)
                .append(BIG_SPACE).append("</table>")
                .append(NEW_LINE)
                .append("</div>")
                .append(NEW_LINE)
                .append("<a th:href=\"@{/").append(firstCharToLowerCase(firstClassName)).append("s/add}\" class=\"btn btn-primary add-button\">Add ").append(firstCharToUpperCase(firstClassName)).append(" with Items</a>")
                .append(NEW_LINE)
                .append("</body>")
                .append(NEW_LINE)
                .append("</html>");

        return stringBuilder.toString();
    }

    private static String generateTBodyPart(JSONObject firstClassJSONObject, String firstClassName) {
        StringBuilder stringBuilder = new StringBuilder();
        JSONArray firstClassProperties = (JSONArray) firstClassJSONObject.get("properties");

        stringBuilder
                .append(BIG_SPACE).append(BIG_SPACE).append("<tbody>")
                .append(NEW_LINE)
                .append(BIG_SPACE).append(BIG_SPACE).append("<tr th:each=\"").append(firstCharToLowerCase(firstClassName)).append(" : ${").append(firstCharToLowerCase(firstClassName)).append("s}\">")
                .append(NEW_LINE);

        for (Object o : firstClassProperties) {
            JSONObject classProperty = (JSONObject) o;
            String propertyName = (String) classProperty.get("name");

            if (classProperty.containsKey("mapField")) {
                continue;
            }
            stringBuilder
                    .append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append("<td th:text=\"${").append(firstCharToLowerCase(firstClassName)).append(".").append(firstCharToLowerCase(propertyName)).append("}\"></td>")
                    .append(NEW_LINE);
        }

        stringBuilder
                .append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append("<td class=\"table-actions\">")
                .append(NEW_LINE)
                .append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append("<a th:href=\"@{/").append(firstCharToLowerCase(firstClassName)).append("s/{id}(id=${").append(firstCharToLowerCase(firstClassName)).append(".id})}\" class=\"btn btn-primary\">View</a>")
                .append(NEW_LINE)
                .append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append("<a th:href=\"@{/").append(firstCharToLowerCase(firstClassName)).append("s/update/{id}(id=${").append(firstCharToLowerCase(firstClassName)).append(".id})}\" class=\"btn btn-warning\">Edit</a>")
                .append(NEW_LINE)
                .append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append("<a th:href=\"@{/").append(firstCharToLowerCase(firstClassName)).append("s/delete/{id}(id=${").append(firstCharToLowerCase(firstClassName)).append(".id})}\" class=\"btn btn-danger\">Delete</a>")
                .append(NEW_LINE)
                .append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append("</td>")
                .append(NEW_LINE)
                .append(BIG_SPACE).append(BIG_SPACE).append("</tr>")
                .append(NEW_LINE)
                .append(BIG_SPACE).append(BIG_SPACE).append("</tbody>");

        return stringBuilder.toString();
    }
}

