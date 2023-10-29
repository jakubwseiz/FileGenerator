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
                .append(generateBodyPart(firstClassName, firstClassJSONObject, secondClassName, secondClassJSONObject))
                .append(NEW_LINE)
                .append("</html>");

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

    public static String generateBodyPart(String firstClassName, JSONObject firstClassJSONObject, String secondClassName, JSONObject secondClassJSONObject) {
        StringBuilder stringBuilder = new StringBuilder();

        JSONArray firstClassProperties = (JSONArray) firstClassJSONObject.get("properties");
        String listName = null;

        stringBuilder
                .append("<body>")
                .append(NEW_LINE)
                .append("<h2> Add").append(firstCharToUpperCase(firstClassName)).append("</h2>")
                .append(NEW_LINE)
                .append("<form th:action=\"@{/").append(firstCharToLowerCase(firstClassName)).append("s/add}\" method=\"post\">")
                .append(NEW_LINE);

        for (Object o : firstClassProperties) {
            JSONObject classProperty = (JSONObject) o;
            String propertyName = (String) classProperty.get("name");
            String propertyType = (String) classProperty.get("type");

            if (classProperty.containsKey("mapField")) {
                listName = (String) classProperty.get("name");
                continue;
            }
            stringBuilder
                    .append(NEW_LINE)
                    .append(BIG_SPACE).append("<div class=\"form-group\">")
                    .append(NEW_LINE)
                    .append(BIG_SPACE).append(BIG_SPACE).append("<label for=\"").append(firstCharToLowerCase(propertyName)).append("\" class=\"narrow-input\">").append(firstCharToUpperCase(propertyName)).append("</label>")
                    .append(NEW_LINE)
                    .append(BIG_SPACE).append(BIG_SPACE).append("<input type=\"").append(generateFieldType(propertyType)).append("\" class=\"form-control narrow-input\" id=\"").append(firstCharToLowerCase(propertyName)).append("\" name=\"").append(firstCharToLowerCase(propertyName)).append("\" required>")
                    .append(NEW_LINE)
                    .append(BIG_SPACE).append("</div>");
        }

        stringBuilder
                .append(DOUBLE_NEW_LINE)
                .append(BIG_SPACE).append("<h3>").append(firstCharToUpperCase(listName)).append("</h3>")
                .append(NEW_LINE)
                .append(BIG_SPACE).append("<div id=\"items-container\"></div>")
                .append(NEW_LINE)
                .append(BIG_SPACE).append("<button type=\"button\" class=\"btn btn-primary\" onclick=\"addItem()\">Add ").append(firstCharToUpperCase(secondClassName)).append("</button>")
                .append(NEW_LINE)
                .append(BIG_SPACE).append("<button type=\"submit\" class=\"btn btn-success\">Save ").append(firstCharToUpperCase(firstClassName)).append("</button>")
                .append(NEW_LINE)
                .append("</form>")
                .append(NEW_LINE)
                .append("<a th:href=\"@{/").append(firstCharToLowerCase(firstClassName)).append("s}\" class=\"mt-2\">Back to ").append(firstCharToUpperCase(firstClassName)).append("s</a>")
                .append(NEW_LINE)
                .append(generateScriptPart(secondClassJSONObject, firstClassName, listName))
                .append(NEW_LINE)
                .append("</body>");



        return stringBuilder.toString();
    }

    public static String firstCharToLowerCase(String property) {
        return property.substring(0, 1).toLowerCase() + property.substring(1);
    }
    public static String firstCharToUpperCase(String property) {
        return property.substring(0, 1).toUpperCase() + property.substring(1);
    }

    public static String generateFieldType(String propertyType) {
        StringBuilder stringBuilder = new StringBuilder();

        if (propertyType.equals("LocalDate")) {
            stringBuilder.append("date");
        } else if (propertyType.equals("Float") || propertyType.equals("Integer") || propertyType.equals("Long") || propertyType.equals("Decimal") || propertyType.equals("Short") || propertyType.equals("Double")) {
            stringBuilder.append("number");
        } else {
            stringBuilder.append("text");
        }

        return stringBuilder.toString();
    }

    public static String generateScriptPart(JSONObject secondClassJSONObject, String firstClassName, String listName) {
        StringBuilder stringBuilder = new StringBuilder();
        JSONArray secondClassProperties =  (JSONArray) secondClassJSONObject.get("properties");

        stringBuilder
                .append("""
                        <script th:inline="javascript">
                            var itemCounter = 0;
                                                
                            function addItem() {
                                var itemRow = document.createElement("div");
                                itemRow.className = "item-row";
                                itemRow.innerHTML = `
                        """);

        for (Object o : secondClassProperties) {
            JSONObject classProperty = (JSONObject) o;
            String propertyName = (String) classProperty.get("name");
            String propertyType = (String) classProperty.get("type");

            if (propertyName.equals(firstCharToLowerCase(firstClassName))) {
                continue;
            }

            stringBuilder
                    .append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append("<div class=\"form-group\">")
                    .append(NEW_LINE)
                    .append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append("<label for=\"").append(firstCharToLowerCase(listName)).append("[${itemCounter}].").append(firstCharToLowerCase(propertyName)).append("\" class=\"narrow-input\">").append(firstCharToUpperCase(propertyName)).append("</label>")
                    .append(NEW_LINE)
                    .append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append("<input type=\"").append(generateFieldType(propertyType)).append("\" class=\"form-control narrow-input\" id=\"").append(firstCharToLowerCase(listName)).append("[${itemCounter}].").append(firstCharToLowerCase(propertyName)).append("\" name=\"").append(firstCharToLowerCase(listName)).append("[${itemCounter}].").append(firstCharToLowerCase(propertyName)).append("\" required>")
                    .append(NEW_LINE)
                    .append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append("</div>")
                    .append(NEW_LINE);

        }

        stringBuilder
                .append("""
                                    <button type="button" class="btn btn-danger" onclick="removeItem(this)">Remove</button>
                                `;
                                document.getElementById("items-container").appendChild(itemRow);
                                itemCounter++;
                            }
                                                
                            function removeItem(button) {
                                button.parentNode.remove();
                            }
                        </script>
                        """);
        return stringBuilder.toString();
    }

}
