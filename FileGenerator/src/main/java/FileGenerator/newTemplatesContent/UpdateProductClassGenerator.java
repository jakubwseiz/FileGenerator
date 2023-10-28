package FileGenerator.newTemplatesContent;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class UpdateProductClassGenerator {
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

    public static String generateUpdateProductTemplate(JSONObject jsonObject) {
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
                .append(generateBodyPart(firstClassName, firstClassJSONObject, secondClassName, secondClassJSONObject))
                .append(NEW_LINE)
                .append("</html>");

        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    private static String generateHeadPart(String firstClassName) {
        return """
                <head>
                    <title>Update %s</title>
                    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
                    <style>
                        body {
                            margin-left: 50px;
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
        JSONArray secondClassProperties = (JSONArray) secondClassJSONObject.get("properties");

        String listName = null;

        stringBuilder
                .append("<body>")
                .append(NEW_LINE)
                .append("<h1>Update ").append(firstCharToUpperCase(firstClassName)).append("</h1>")
                .append(DOUBLE_NEW_LINE)
                .append("<form th:action=\"@{/").append(firstCharToLowerCase(firstClassName)).append("s/update/{id}(id=${update").append(firstCharToUpperCase(firstClassName)).append(".id})}\" th:object=\"${update").append(firstCharToUpperCase(firstClassName)).append("}\" method=\"post\">")
                .append(NEW_LINE)
                .append(BIG_SPACE).append("<input type=\"hidden\" th:field=\"*{id}\"/>")
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
                    .append(BIG_SPACE).append(firstCharToUpperCase(propertyName)).append(": <input type=\"").append(generateFieldType(propertyType)).append("\" th:field=\"*{").append(firstCharToLowerCase(propertyName)).append("}\" required/><br/>")
                    .append(NEW_LINE);
        }

        stringBuilder
                .append(NEW_LINE)
                .append(BIG_SPACE).append("<h2>").append(firstCharToUpperCase(listName)).append("</h2>")
                .append(NEW_LINE)
                .append(BIG_SPACE).append("<table id=\"itemsTable\">")
                .append(NEW_LINE)
                .append(BIG_SPACE).append(BIG_SPACE).append("<tr>")
                .append(NEW_LINE);

        for (Object o : firstClassProperties) {
            JSONObject classProperty = (JSONObject) o;
            String propertyName = (String) classProperty.get("name");
            String propertyType = (String) classProperty.get("type");

            if (classProperty.containsKey("mapField")) {
                continue;
            }
            stringBuilder
                    .append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append("<th>").append(firstCharToUpperCase(propertyName)).append("</th>")
                    .append(NEW_LINE);
        }
        stringBuilder
                .append(BIG_SPACE).append(BIG_SPACE).append("</tr>")
                .append(NEW_LINE)
                .append(BIG_SPACE).append(BIG_SPACE).append("<tr th:each=\"item, itemStat : ${update").append(firstCharToUpperCase(firstClassName)).append(".").append(firstCharToLowerCase(listName)).append("}\">")
                .append(NEW_LINE);

        for (Object o : secondClassProperties) {
            JSONObject classProperty = (JSONObject) o;
            String propertyName = (String) classProperty.get("name");
            String propertyType = (String) classProperty.get("type");

            if (firstCharToUpperCase(propertyName).equals(firstClassName)) {
                continue;
            }
            stringBuilder
                    .append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append("<td><input type=\"").append(generateFieldType(propertyType)).append("\" th:field=\"*{").append(firstCharToLowerCase(listName)).append("[__${itemStat.index}__].").append(firstCharToLowerCase(propertyName)).append("}\"/></td>")
                    .append(NEW_LINE);

        }

        stringBuilder
                .append(BIG_SPACE).append(BIG_SPACE).append(BIG_SPACE).append("<td><button type=\"button\" class=\"btn btn-danger\" id=\"remove[__${itemStat.index}__]\" onclick=\"removeItem(this)\">Remove</button></td>")
                .append(NEW_LINE)
                .append(BIG_SPACE).append(BIG_SPACE).append("</tr>")
                .append(NEW_LINE)
                .append(BIG_SPACE).append("</table>")
                .append(DOUBLE_NEW_LINE)
                .append(BIG_SPACE).append("<button type=\"button\" class=\"btn btn-primary\" onclick=\"addItem()\">Add Item</button>")
                .append(DOUBLE_NEW_LINE)
                .append(BIG_SPACE).append("<button type=\"submit\" class=\"btn btn-success\">Save Changes</button>")
                .append(NEW_LINE)
                .append("</form>")
                .append(DOUBLE_NEW_LINE)
                .append("<a th:href=\"@{/").append(firstCharToLowerCase(firstClassName)).append("s}\">Back to ").append(firstCharToUpperCase(firstClassName)).append("s</a>")
                .append(NEW_LINE)
                .append(generateScriptPart(firstClassName, secondClassProperties))
                .append(NEW_LINE)
                .append("</body>");

        return stringBuilder.toString();
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

    private static String generateScriptPart(String firstClassName, JSONArray secondClassProperties) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("<script th:inline=\"javascript\">")
                .append(NEW_LINE)
                .append(BIG_SPACE).append("var itemCounter = /*[[${itemsSize}]]*/;")
                .append(DOUBLE_NEW_LINE)
                .append("""
                        function removeItem(button) {
                            var row = button.parentNode.parentNode;
                            row.parentNode.removeChild(row);
                        }
                        function addItem() {
                            itemCounter++;
                            var itemRow = document.createElement("tr");
                            itemRow.className = "item-row";
                            itemRow.innerHTML = `
                            <tr>
                        """);

        for (Object o : secondClassProperties) {
            JSONObject classProperty = (JSONObject) o;
            String propertyName = (String) classProperty.get("name");
            String propertyType = (String) classProperty.get("type");

            if (firstCharToUpperCase(propertyName).equals(firstClassName)) {
                continue;
            }
            stringBuilder
                    .append(BIG_SPACE).append(BIG_SPACE).append("<td><input type=\"").append(generateFieldType(propertyType)).append("\" class=\"form-control\" id=\"items[${itemCounter}].").append(firstCharToLowerCase(propertyName)).append("\" name=\"items[${itemCounter}].").append(firstCharToLowerCase(propertyName)).append("\" required/></td>")
                    .append(NEW_LINE);
        }

        stringBuilder
                .append("""
                                <td><button type="button" class="btn btn-danger" onclick="removeItem(this)">Remove</button></td>
                            </tr>
                            `;
                            document.getElementById("itemsTable").appendChild(itemRow);
                            }
                        </script>""");

        return stringBuilder.toString();
    }
}
