package FileGenerator.newController;

import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ControllerClassGenerator {
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

    public static String generateControllerClass (JSONObject jsonObject) {
        StringBuilder classContent = new StringBuilder();
        String projectName = jsonObject.get("projectName").toString();
        JSONArray classesList = (JSONArray) jsonObject.get("classes");

        classContent
                .append("package com.myCompany.").append(projectName).append(".Controllers").append(END_STATEMENT)
                .append(DOUBLE_NEW_LINE)
                .append(generateControllerImport(jsonObject))
                .append(generateControllerContent(classesList));

        System.out.println(classContent);
        return classContent.toString();
    }

    @SneakyThrows
    public static String generateControllerImport(JSONObject jsonObject) {

        StringBuilder stringBuilder = new StringBuilder();
        JSONParser parser = new JSONParser();
        String projectName = jsonObject.get("projectName").toString();

        JSONArray classesList = (JSONArray) jsonObject.get("classes");
        JSONObject firstClassJSONObject = (JSONObject) parser.parse(classesList.get(0).toString());
        String firstClassName = firstClassJSONObject.get("name").toString();


        for (Object c : classesList)
        {
            JSONObject classJSONObject = (JSONObject) c;
            String className = classJSONObject.get("name").toString();
            JSONArray properties = (JSONArray) classJSONObject.get("properties");

            stringBuilder
                    .append("import com.myCompany.").append(projectName).append(".Models.").append(className).append(END_STATEMENT)
                    .append(NEW_LINE);
        }

        stringBuilder
                .append("import com.myCompany.").append(projectName).append(".Services.").append(firstClassName).append("Service").append(END_STATEMENT)
                .append(NEW_LINE)
                .append("""
                        import org.springframework.beans.factory.annotation.*;
                        import org.springframework.boot.Banner;
                        import org.springframework.stereotype.Controller;
                        import org.springframework.ui.Model;
                        import org.springframework.web.bind.annotation.*;
                                                
                        import java.text.SimpleDateFormat;
                        import java.util.ArrayList;
                        import java.util.Date;
                        import java.util.List;
              
                        """);
        return  stringBuilder.toString();
    }

    public static String generateControllerContent(JSONArray classesList) {

        StringBuilder stringBuilder = new StringBuilder();


        JSONObject firstClassJSONObject = (JSONObject) classesList.get(0);
        String firstClassName = firstClassJSONObject.get("name").toString();

        JSONObject secondClassJSONObject = (JSONObject) classesList.get(1);
        String secondClassName = secondClassJSONObject.get("name").toString();

        stringBuilder
                .append("@Controller")
                .append(NEW_LINE)
                .append("@RequestMapping(\"/").append(firstCharToLowerCase(firstClassName)).append("s\")")
                .append(NEW_LINE)
                .append("public class ").append(firstClassName).append("Controller {")
                .append(NEW_LINE)
                .append("@Autowired")
                .append(NEW_LINE)
                .append("private ").append(firstClassName).append("Service ").append(firstCharToLowerCase(firstClassName)).append("Service").append(END_STATEMENT)
                .append(DOUBLE_NEW_LINE)
                .append(generateGetAllProductEndpoint(firstClassName))
                .append(DOUBLE_NEW_LINE)
                .append(generateGetProductByIdEndpoint(firstClassName))
                .append(DOUBLE_NEW_LINE)
                .append(generateShowUpdateProductFormEndpoint(classesList, firstClassName, secondClassName))
                .append(DOUBLE_NEW_LINE)
                .append(generateUpdateProduct(firstClassName, secondClassName, firstClassJSONObject, secondClassJSONObject))
                .append(DOUBLE_NEW_LINE)
                .append(generateShowAllProductForm(firstClassName, secondClassName, firstClassJSONObject, secondClassJSONObject))
                .append(DOUBLE_NEW_LINE)
                .append(generateAddProduct(firstClassName, secondClassName, firstClassJSONObject, secondClassJSONObject))
                .append(DOUBLE_NEW_LINE)
                .append(generateDeleteProduct(firstClassName))
                .append(NEW_LINE).append(BLOCK_CLOSED);


        return stringBuilder.toString();
    }

    public static String firstCharToLowerCase(String property) {
        return property.substring(0, 1).toLowerCase() + property.substring(1);
    }
    public static String firstCharToUpperCase(String property) {
        return property.substring(0, 1).toUpperCase() + property.substring(1);
    }

    public static String generateGetAllProductEndpoint(String firstClassName) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("@GetMapping")
                .append(NEW_LINE)
                .append("public String getAll").append(firstClassName).append("s(Model model) {")
                .append(NEW_LINE)
                .append("List<").append(firstClassName).append("> ").append(firstCharToLowerCase(firstClassName)).append("s = ").append(firstCharToLowerCase(firstClassName)).append("Service.getAll").append(firstClassName).append("s();")
                .append(NEW_LINE)
                .append("model.addAttribute(\"").append(firstCharToLowerCase(firstClassName)).append("s\", ").append(firstCharToLowerCase(firstClassName)).append("s);")
                .append(NEW_LINE)
                .append("return \"").append(firstCharToLowerCase(firstClassName)).append("s\";")
                .append(NEW_LINE).append(BLOCK_CLOSED);

        return stringBuilder.toString();
    }

    public static String generateGetProductByIdEndpoint(String firstClassName) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("@GetMapping(\"/{id}\")")
                .append(NEW_LINE)
                .append("public String get").append(firstClassName).append("ById(@PathVariable Long id, Model model) {")
                .append(NEW_LINE)
                .append(firstClassName).append(SPACE).append(firstCharToLowerCase(firstClassName)).append(" = ").append(firstCharToLowerCase(firstClassName)).append("Service").append(".get").append(firstClassName).append("ById(id);")
                .append(NEW_LINE)
                .append("model.addAttribute(\"").append(firstCharToLowerCase(firstClassName)).append("\", ").append(firstCharToLowerCase(firstClassName)).append(");")
                .append(NEW_LINE)
                .append("return \"").append(firstCharToLowerCase(firstClassName)).append("\";")
                .append(NEW_LINE).append(BLOCK_CLOSED);

        return stringBuilder.toString();
    }

    public static String generateShowUpdateProductFormEndpoint(JSONArray classesList, String firstClassName, String secondClassName) {
        StringBuilder stringBuilder = new StringBuilder();

        String listName = null;

        JSONObject firstClass = (JSONObject) classesList.get(0);
        JSONArray properties =  (JSONArray) firstClass.get("properties");

        for (Object c : properties) {
            JSONObject propertyJSONObject = (JSONObject) c;
            if (propertyJSONObject.containsKey("mapField")) {
                listName = (String) propertyJSONObject.get("name");
            }
        }

        stringBuilder
                .append("@GetMapping(\"/update/{id}\")")
                .append(NEW_LINE)
                .append("public String showUpdate").append(firstClassName).append("Form(@PathVariable Long id, Model model) {")
                .append(NEW_LINE)
                .append(firstClassName).append(" update").append(firstClassName).append(" = ").append(firstCharToLowerCase(firstClassName)).append("Service.get").append(firstClassName).append("ById(id);")
                .append(NEW_LINE)
                .append("List<").append(secondClassName).append("> items = update").append(firstClassName).append(".get").append(firstCharToUpperCase(listName)).append("()").append(END_STATEMENT)
                .append(DOUBLE_NEW_LINE)
                .append("model.addAttribute(").append("\"update").append(firstClassName).append("\", update").append(firstClassName).append(")").append(END_STATEMENT)
                .append(NEW_LINE)
                .append("model.addAttribute(\"").append(listName).append("\", ").append(listName).append(")").append(END_STATEMENT)
                .append(NEW_LINE)
                .append("model.addAttribute(\"").append(listName).append("Size").append("\", ").append(listName).append(".size())").append(END_STATEMENT)
                .append(NEW_LINE)
                .append("return \"update").append(firstClassName).append("\"").append(END_STATEMENT)
                .append(NEW_LINE)
                .append(BLOCK_CLOSED);

        return stringBuilder.toString();
    }

    public static String generateUpdateProduct(String firstClassName, String secondClassName, JSONObject firstClassJSONObject, JSONObject secondClassJSONObject) {
        StringBuilder stringBuilder = new StringBuilder();
        JSONArray firstClassProperties =  (JSONArray) firstClassJSONObject.get("properties");
        JSONArray secondClassProperties =  (JSONArray) secondClassJSONObject.get("properties");
        String listName = null;
        JSONObject singleProperty = (JSONObject) secondClassProperties.get(0);
        String firstPropertyNameInSecondClass = (String) singleProperty.get("name");

        stringBuilder
                .append("@PostMapping(\"/update/{id}\")")
                .append(NEW_LINE)
                .append("public String update").append(firstClassName).append("(@PathVariable Long id, @ModelAttribute(\"update").append(firstClassName).append("\") ").append(firstClassName).append(SPACE).append(firstCharToLowerCase(firstClassName)).append("Data) {")
                .append(DOUBLE_NEW_LINE)
                .append(firstClassName).append(" updated").append(firstClassName).append(" = new ").append(firstClassName).append("()").append(END_STATEMENT)
                .append(NEW_LINE)
                .append("updated").append(firstClassName).append(".setId(id);")
                .append(NEW_LINE);

        for (Object o : firstClassProperties) {
            JSONObject classProperty = (JSONObject) o;
            String propertyName = (String) classProperty.get("name");

            if (classProperty.containsKey("mapField")) {
                listName = (String) classProperty.get("name");
                continue;
            }
            stringBuilder
                    .append("updated").append(firstClassName).append(".set").append(firstCharToUpperCase(propertyName)).append("(").append((firstCharToLowerCase(firstClassName))).append("Data.get").append(firstCharToUpperCase(propertyName)).append("())").append(END_STATEMENT)
                    .append(NEW_LINE);
        }

        stringBuilder
                .append(NEW_LINE)
                .append("List<").append(secondClassName).append("> items = new ArrayList<>();")
                .append(NEW_LINE)
                .append("for (").append(secondClassName).append(" newItem : ").append(firstCharToLowerCase(firstClassName)).append("Data.get").append(firstCharToUpperCase(listName)).append("()) {")
                .append(NEW_LINE)
                .append(firstCharToUpperCase(secondClassName)).append(" item = new ").append(firstCharToUpperCase(secondClassName)).append("()").append(END_STATEMENT)
                .append(NEW_LINE)
                .append("if (newItem.get").append(firstCharToUpperCase(firstPropertyNameInSecondClass)).append("() == null) {")
                .append(NEW_LINE)
                .append("continue;")
                .append(NEW_LINE).append("}")
                .append(NEW_LINE);

        for (Object k : secondClassProperties) {
            JSONObject classProperty = (JSONObject) k;
            String propertyName = (String) classProperty.get("name");

            if (firstCharToUpperCase(propertyName).equals(firstClassName)) {
                continue;
            }

            stringBuilder
                    .append("item.set").append(firstCharToUpperCase(propertyName)).append("(newItem.get").append(firstCharToUpperCase(propertyName)).append("());")
                    .append(NEW_LINE);
        }

        stringBuilder
                .append("item.set").append(firstCharToUpperCase(firstClassName)).append("(updated").append(firstCharToUpperCase(firstClassName)).append(");")
                .append(NEW_LINE)
                .append("items.add(item);")
                .append(NEW_LINE).append(BLOCK_CLOSED).append(NEW_LINE)
                .append("updated").append(firstCharToUpperCase(firstClassName)).append(".set").append(firstCharToUpperCase(listName)).append("(items)").append(END_STATEMENT)
                .append(NEW_LINE)
                .append(firstCharToLowerCase(firstClassName)).append("Service.update").append(firstCharToUpperCase(firstClassName)).append("(updated").append(firstCharToUpperCase(firstClassName)).append(");")
                .append(DOUBLE_NEW_LINE)
                .append("return \"redirect:/").append(firstCharToLowerCase(firstClassName)).append("s\";")
                .append(NEW_LINE)
                .append(BLOCK_CLOSED);

        return stringBuilder.toString();
    }

    public static String generateShowAllProductForm(String firstClassName, String secondClassName, JSONObject firstClassJSONObject, JSONObject secondClassJSONObject) {
        StringBuilder stringBuilder = new StringBuilder();
        JSONArray firstClassProperties =  (JSONArray) firstClassJSONObject.get("properties");
        String listName = null;

        for (Object o : firstClassProperties) {
            JSONObject classProperty = (JSONObject) o;
            String propertyName = (String) classProperty.get("name");

            if (classProperty.containsKey("mapField")) {
                listName = (String) classProperty.get("name");
            }
        }

        stringBuilder
                .append("@GetMapping(\"/add\")")
                .append(NEW_LINE)
                .append("public String showAdd").append(firstCharToUpperCase(firstClassName)).append("Form(Model model) {")
                .append(NEW_LINE)
                .append(firstCharToUpperCase(firstClassName)).append(" new").append(firstCharToUpperCase(firstClassName)).append(" = new ").append(firstCharToUpperCase(firstClassName)).append("()").append(END_STATEMENT)
                .append(NEW_LINE)
                .append("new").append(firstCharToUpperCase(firstClassName)).append(".set").append(firstCharToUpperCase(listName)).append("(new ArrayList<>());")
                .append(NEW_LINE)
                .append("new").append(firstCharToUpperCase(firstClassName)).append(".get").append(firstCharToUpperCase(listName)).append("().add(new ").append(firstCharToUpperCase(secondClassName)).append("());")
                .append(NEW_LINE)
                .append("model.addAttribute(\"new").append(firstCharToUpperCase(firstClassName)).append("\", new").append(firstCharToUpperCase(firstClassName)).append(");")
                .append(NEW_LINE)
                .append("return \"add").append(firstCharToUpperCase(firstClassName)).append("\"").append(END_STATEMENT)
                .append(NEW_LINE).append(BLOCK_CLOSED);

        return stringBuilder.toString();
    }

    public static String generateAddProduct(String firstClassName, String secondClassName, JSONObject firstClassJSONObject, JSONObject secondClassJSONObject) {
        StringBuilder stringBuilder = new StringBuilder();
        JSONArray firstClassProperties =  (JSONArray) firstClassJSONObject.get("properties");
        JSONArray secondClassProperties =  (JSONArray) secondClassJSONObject.get("properties");
        JSONObject singleProperty = (JSONObject) secondClassProperties.get(0);
        String firstPropertyNameInSecondClass = (String) singleProperty.get("name");
        String listName = null;

        stringBuilder
                .append("@PostMapping(\"/add\")")
                .append(NEW_LINE)
                .append("public String add").append(firstCharToUpperCase(firstClassName)).append("(@ModelAttribute ").append(firstCharToUpperCase(firstClassName)).append(SPACE).append(firstCharToLowerCase(firstClassName)).append("Data) {")
                .append(DOUBLE_NEW_LINE)
                .append(firstCharToUpperCase(firstClassName)).append(" new").append(firstCharToUpperCase(firstClassName)).append(" = new ").append(firstCharToUpperCase(firstClassName)).append("();")
                .append(NEW_LINE);

        for (Object o : firstClassProperties) {
            JSONObject classProperty = (JSONObject) o;
            String propertyName = (String) classProperty.get("name");

            if (classProperty.containsKey("mapField")) {
                listName = (String) classProperty.get("name");
                continue;
            }
            stringBuilder
                    .append("new").append(firstCharToUpperCase(firstClassName)).append(".set").append(firstCharToUpperCase(propertyName)).append("(").append(firstCharToLowerCase(firstClassName)).append("Data.get").append(firstCharToUpperCase(propertyName)).append("());")
                    .append(NEW_LINE);
        }

        stringBuilder
                .append(NEW_LINE)
                .append("List<").append(secondClassName).append("> items = new ArrayList<>();")
                .append(NEW_LINE)
                .append("for (").append(secondClassName).append(" newItem : ").append(firstCharToLowerCase(firstClassName)).append("Data.get").append(firstCharToUpperCase(listName)).append("()) {")
                .append(NEW_LINE)
                .append(firstCharToUpperCase(secondClassName)).append(" item = new ").append(firstCharToUpperCase(secondClassName)).append("()").append(END_STATEMENT)
                .append(NEW_LINE)
                .append("if (newItem.get").append(firstCharToUpperCase(firstPropertyNameInSecondClass)).append("() == null) {")
                .append(NEW_LINE)
                .append("continue;")
                .append(NEW_LINE).append("}")
                .append(NEW_LINE);

        for (Object k : secondClassProperties) {
            JSONObject classProperty = (JSONObject) k;
            String propertyName = (String) classProperty.get("name");

            if (firstCharToUpperCase(propertyName).equals(firstClassName)) {
                continue;
            }

            stringBuilder
                    .append("item.set").append(firstCharToUpperCase(propertyName)).append("(newItem.get").append(firstCharToUpperCase(propertyName)).append("());")
                    .append(NEW_LINE);
        }

        stringBuilder
                .append("item.set").append(firstCharToUpperCase(firstClassName)).append("(new").append(firstCharToUpperCase(firstClassName)).append(");")
                .append(NEW_LINE)
                .append("items.add(item);")
                .append(NEW_LINE).append(BLOCK_CLOSED).append(NEW_LINE)
                .append("new").append(firstCharToUpperCase(firstClassName)).append(".set").append(firstCharToUpperCase(listName)).append("(items)").append(END_STATEMENT)
                .append(NEW_LINE)
                .append(firstCharToLowerCase(firstClassName)).append("Service.add").append(firstCharToUpperCase(firstClassName)).append("(new").append(firstCharToUpperCase(firstClassName)).append(");")
                .append(DOUBLE_NEW_LINE)
                .append("return \"redirect:/").append(firstCharToLowerCase(firstClassName)).append("s\";")
                .append(NEW_LINE).append(BLOCK_CLOSED);

        return stringBuilder.toString();
    }

    public static String generateDeleteProduct(String firstClassName) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("@GetMapping(\"/delete/{id}\")")
                .append(NEW_LINE)
                .append("public String delete").append(firstCharToUpperCase(firstClassName)).append("(@PathVariable Long id) {")
                .append(NEW_LINE)
                .append(firstCharToLowerCase(firstClassName)).append("Service.delete").append(firstCharToUpperCase(firstClassName)).append("(id)").append(END_STATEMENT)
                .append(DOUBLE_NEW_LINE)
                .append("return \"redirect:/").append(firstCharToLowerCase(firstClassName)).append("s\";")
                .append(NEW_LINE).append(BLOCK_CLOSED);

        return stringBuilder.toString();
    }
}
