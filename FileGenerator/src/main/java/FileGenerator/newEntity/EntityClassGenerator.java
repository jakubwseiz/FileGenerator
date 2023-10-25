package FileGenerator.newEntity;


import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;

import static FileGenerator.newEntity.EntityImportGenerator.generateEntityImportContent;

public class EntityClassGenerator {

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

    public static StringBuilder gettersAndSetters;
    public static StringBuilder properties;

    public static HashMap<String,String> fieldMap = new HashMap<>();

    public static String declareClass(String className, String packagename)  {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append(packagename).append(END_STATEMENT)
                .append(DOUBLE_NEW_LINE)
                .append("%s") //import statements
                .append(DOUBLE_NEW_LINE)
                .append("@Entity")
                .append(NEW_LINE)
                .append("public class ").append(className).append(SPACE)
                .append(BLOCK_OPEN)
                .append(DOUBLE_NEW_LINE)
                .append("%s") //properties
                .append(NEW_LINE)
                .append("%s") //constructor
                .append(NEW_LINE)
                .append("%s") //getters and setters
                .append(DOUBLE_NEW_LINE)
                .append(BLOCK_CLOSED);

        return stringBuilder.toString();
    }

    public static void addProperty(String propertyName, String declareType) {
        appendAnnotations(propertyName, declareType);
        String listInitializator = "";
        if (declareType.startsWith("List")) {
            listInitializator = " = new ArrayList<>()";
        }
        properties
                .append(BIG_SPACE)
                .append("private ")
                .append(declareType)
                .append(SPACE)
                .append(propertyName)
                .append(listInitializator)
                .append(END_STATEMENT)
                .append(NEW_LINE)
                .append(NEW_LINE);
        addGettersAndSetters(propertyName, declareType);
    }

    public static void addPropertyWithMapField(String propertyName, String declareType, String mapField) {
        properties
                .append(BIG_SPACE)
                .append("@OneToMany(mappedBy = \"").append(mapField).append("\", cascade = CascadeType.ALL, orphanRemoval = true)").append(NEW_LINE)
                .append(BIG_SPACE)
                .append("@JsonManagedReference").append(NEW_LINE);
        addProperty(propertyName,declareType);
    }

    public static void addPropertyWithJoinColumn(String propertyName, String declareType) {
        properties
                .append(BIG_SPACE)
                .append(" @ManyToOne")
                .append(NEW_LINE)
                .append(BIG_SPACE)
                .append("@JoinColumn(name = \"").append(propertyName).append("_id\")").append(NEW_LINE)
                .append(BIG_SPACE)
                .append("@JsonBackReference").append(NEW_LINE);
        addProperty(propertyName,declareType);
    }

    private static void addGettersAndSetters(String propertyName, String propertyType) {
        String firstUpperProperty = firstCharToUpperCase(propertyName);
        gettersAndSetters
                .append(BIG_SPACE)
                .append("public ")
                .append(propertyType).append(SPACE)
                .append("get").append(firstUpperProperty).append(METHOD_NO_ARGS)
                .append(BLOCK_OPEN).append(NEW_LINE).append(BIG_SPACE).append(BIG_SPACE)
                .append("return this.").append(propertyName).append(END_STATEMENT)
                .append(NEW_LINE).append(BIG_SPACE)
                .append(BLOCK_CLOSED)
                .append(DOUBLE_NEW_LINE);

        gettersAndSetters
                .append(BIG_SPACE)
                .append("public void").append(SPACE)
                .append("set").append(firstUpperProperty)
                .append(METHOD_OPEN).append(propertyType).append(SPACE).append(propertyName).append(METHOD_CLOSED)
                .append(BLOCK_OPEN).append(NEW_LINE).append(BIG_SPACE).append(BIG_SPACE)
                .append("this.").append(propertyName).append(" = ").append(propertyName).append(END_STATEMENT)
                .append(NEW_LINE).append(BIG_SPACE)
                .append(BLOCK_CLOSED)
                .append(DOUBLE_NEW_LINE);
    }

    private static void appendAnnotations(String propertyName, String propertyType) {
        switch (propertyType) {
            case "LocalDate":
                properties
                        .append(BIG_SPACE)
                        .append("@DateTimeFormat(pattern = \"yyyy-MM-dd\")").append(METHOD_CLOSED).append(NEW_LINE);
            case "List":
            default:
                return;
        }

    }

    public static String firstCharToUpperCase(String propertyName) {
        return propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    }

    public static void addIdProperty() {
        properties
                .append(BIG_SPACE)
                .append("@Id")
                .append(NEW_LINE).append(BIG_SPACE)
                .append("@GeneratedValue(strategy = GenerationType.IDENTITY)")
                .append(NEW_LINE).append(BIG_SPACE)
                .append("private Long id;").append(NEW_LINE);

    }

    @SneakyThrows
    public static String generateFirstClass(JSONObject jsonObject) {
        StringBuilder classContent = new StringBuilder();
        JSONParser parser = new JSONParser();

        String projectName = jsonObject.get("projectName").toString();
        //Classes
        JSONArray classesList = (JSONArray) jsonObject.get("classes");

        JSONObject firstClassJSONObject = (JSONObject) parser.parse(classesList.get(0).toString());

        gettersAndSetters = new StringBuilder();
        properties = new StringBuilder();
        JSONObject classJSONObject = (JSONObject) parser.parse(firstClassJSONObject.toString());
        String className = classJSONObject.get("name").toString();
        JSONArray properties = (JSONArray) classJSONObject.get("properties");

        classContent
                .append(generateClass(projectName, className, properties));

        return classContent.toString();
    }

    @SneakyThrows
    public static String generateSecondClass(JSONObject jsonObject) {
        StringBuilder classContent = new StringBuilder();
        JSONParser parser = new JSONParser();

        String projectName = jsonObject.get("projectName").toString();
        //Classes
        JSONArray classesList = (JSONArray) jsonObject.get("classes");

        JSONObject secondClassJSONObject = (JSONObject) parser.parse(classesList.get(1).toString());

        gettersAndSetters = new StringBuilder();
        properties = new StringBuilder();
        JSONObject classJSONObject = (JSONObject) parser.parse(secondClassJSONObject.toString());
        String className = classJSONObject.get("name").toString();
        JSONArray properties = (JSONArray) classJSONObject.get("properties");
        generateClass(projectName, className, properties);

        classContent
                .append(generateClass(projectName, className, properties));

        return classContent.toString();
    }

    public static String generateClass(String projectName, String className, JSONArray jsonProperties) {
        String packageName = "package com.myCompany."+ projectName + ".Models";
        String classDeclaration = declareClass(className, packageName);
        addIdProperty();
        generateProperties(jsonProperties,className);
        String constructor = generateConstructor(className, jsonProperties);
        String classGenerated = String.format(classDeclaration, generateEntityImportContent(), properties.toString(), constructor, gettersAndSetters.toString());
        System.out.println(classGenerated);
        return classGenerated;
    }

    @SneakyThrows
    static void generateProperties(JSONArray properties, String className) {
        JSONParser parser = new JSONParser();
        for (Object p : properties)
        {
            JSONObject classJSONObject = (JSONObject) parser.parse(p.toString());
            String propertyName = classJSONObject.get("name").toString();
            String propertyType = classJSONObject.get("type").toString();
            Object mapField = classJSONObject.get("mapField");
            if (mapField != null) {
                fieldMap.put(className,mapField.toString());
                addPropertyWithMapField(propertyName, propertyType, mapField.toString());
            } else if (fieldMap.containsKey(propertyType) && fieldMap.get(propertyType).equals(propertyName)) {
                addPropertyWithJoinColumn(propertyName,propertyType);
            } else {
                addProperty(propertyName, propertyType);
            }
        }
    }

    @SneakyThrows
    static String generateConstructor(String className, JSONArray properties) {
        JSONParser parser = new JSONParser();
        StringBuilder constructorString = new StringBuilder();
        StringBuilder constructorInitializationString = new StringBuilder();
        constructorString
                .append(BIG_SPACE)
                .append("public ")
                .append(className)
                .append(METHOD_OPEN);

        int counter = properties.size() - 1;
        for (Object p : properties)
        {
            JSONObject classJSONObject = (JSONObject) parser.parse(p.toString());
            String propertyName = classJSONObject.get("name").toString();
            String propertyType = classJSONObject.get("type").toString();
            constructorString.append(propertyType).append(SPACE).append(propertyName);
            if (counter != 0) {
                constructorString.append(", ");
                counter--;
            }
            constructorInitializationString.append(BIG_SPACE).append(BIG_SPACE).append("this.").append(propertyName).append(" = ").append(propertyName).append(END_STATEMENT).append(NEW_LINE);
        }

        constructorString
                .append(METHOD_CLOSED)
                .append(SPACE)
                .append(BLOCK_OPEN)
                .append(NEW_LINE)
                .append(constructorInitializationString)
                .append(BIG_SPACE)
                .append(BLOCK_CLOSED)
                .append(NEW_LINE);

        return constructorString.toString();
    }

}
