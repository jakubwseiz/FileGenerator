package FileGenerator.newStartClassGenerator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class StartClassGenerator {
    private static final char END_STATEMENT = ';';
    private static final String NEW_LINE = System.lineSeparator();
    private static final String SPACE = " ";
    private static final String BIG_SPACE = "    ";
    private static final String DOUBLE_NEW_LINE = System.lineSeparator() + System.lineSeparator();

    public static String generateStartClassContent(JSONObject jsonObject) {
        StringBuilder classContent = new StringBuilder();
        String projectName = jsonObject.get("projectName").toString();
        JSONArray classesList = (JSONArray) jsonObject.get("classes");

        JSONObject firstClassJSONObject = (JSONObject) classesList.get(0);
        String firstClassName = firstClassJSONObject.get("name").toString();


        classContent
                .append("package com.myCompany.").append(firstCharToUpperCase(projectName)).append(END_STATEMENT)
                .append(DOUBLE_NEW_LINE)
                .append("import com.myCompany.").append(firstCharToUpperCase(projectName)).append(".Models.").append(firstCharToUpperCase(firstClassName)).append(END_STATEMENT)
                .append(NEW_LINE)
                .append("""
                        import org.springframework.boot.SpringApplication;
                        import org.springframework.boot.autoconfigure.SpringBootApplication;
                                                        
                        @SpringBootApplication
                        public class Start {
                                                        
                            public static void main(String[] args) {
                                SpringApplication.run(Start.class, args);
                            }
                                                        
                        }
                        """);


        System.out.println(classContent);
        return classContent.toString();
    }
    public static String firstCharToLowerCase(String property) {
        return property.substring(0, 1).toLowerCase() + property.substring(1);
    }
    public static String firstCharToUpperCase(String property) {
        return property.substring(0, 1).toUpperCase() + property.substring(1);
    }

}
