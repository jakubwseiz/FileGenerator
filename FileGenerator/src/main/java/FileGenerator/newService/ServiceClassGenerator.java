package FileGenerator.newService;

import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;

public class ServiceClassGenerator {
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
        public static String generateServiceClass(JSONObject jsonObject) {

                StringBuilder classContent = new StringBuilder();
                String projectName = jsonObject.get("projectName").toString();
                JSONArray classesList = (JSONArray) jsonObject.get("classes");

                classContent
                        .append("package com.example.").append(projectName).append(".Services").append(END_STATEMENT)
                        .append(DOUBLE_NEW_LINE)
                        .append(generateServiceImport(jsonObject))
                        .append(NEW_LINE)
                        .append(generateClassContent(classesList))
                        .append(BLOCK_CLOSED);



                System.out.println(classContent);
                return classContent.toString();
        }
        @SneakyThrows
        public static String generateServiceImport(JSONObject jsonObject) {

                StringBuilder stringBuilder = new StringBuilder();
                JSONParser parser = new JSONParser();

                String projectName = jsonObject.get("projectName").toString();

                //Classes
                JSONArray classesList = (JSONArray) jsonObject.get("classes");
                for (Object c : classesList)
                {
                        JSONObject classJSONObject = (JSONObject) parser.parse(c.toString());
                        String className = classJSONObject.get("name").toString();
                        JSONArray properties = (JSONArray) classJSONObject.get("properties");

                        stringBuilder.append(generateImportForEntity(projectName, className));
                }
                stringBuilder
                        .append("import org.springframework.beans.factory.annotation.*;")
                        .append(NEW_LINE)
                        .append("import org.springframework.stereotype.Service;")
                        .append(NEW_LINE)
                        .append("import java.util.List;")
                        .append(NEW_LINE);

                return stringBuilder.toString();
        }

        public static String generateImportForEntity(String projectName, String className) {
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder
                        .append("import com.example.").append(projectName).append(".Entities.").append(className).append(END_STATEMENT)
                        .append(NEW_LINE)
                        .append("import com.example.").append(projectName).append(".Repository.").append(className).append("Repository").append(END_STATEMENT)
                        .append(NEW_LINE);
                return  stringBuilder.toString();
        }
        @SneakyThrows
        public static String generateClassContent(JSONArray properties) {

                StringBuilder stringBuilder = new StringBuilder();
                ;
                String className = null;

                for (Object p : properties) {
                        JSONObject classesList = (JSONObject) p;
                        className = classesList.get("name").toString();
                        break;
                }

                stringBuilder
                        .append("@Service")
                        .append(NEW_LINE)
                        .append("public class ").append(className).append("Service").append(" ").append(BLOCK_OPEN)
                        .append(NEW_LINE)
                        .append(generateProperties(properties))
                        .append(generateServiceMethods(properties));

                return stringBuilder.toString();
        }

        public static String generateProperties(JSONArray jsonArray) {
                StringBuilder stringBuilder = new StringBuilder();

                for (Object array : jsonArray) {
                        JSONObject classes = (JSONObject) array;
                        stringBuilder
                                .append("@Autowired")
                                .append(NEW_LINE)
                                .append("private ").append(classes.get("name")).append("Repository").append(SPACE).append(firstCharToLowerCase(classes.get("name").toString())).append("Repository").append(END_STATEMENT)
                                .append(DOUBLE_NEW_LINE);
                }
                return stringBuilder.toString();
        }

        public static String firstCharToLowerCase(String property) {
                return property.substring(0, 1).toLowerCase() + property.substring(1);
        }

        public static String generateServiceMethods(JSONArray jsonArray) {
                StringBuilder stringBuilder = new StringBuilder();

                String className = null;

                for (Object p : jsonArray) {
                        JSONObject classesList = (JSONObject) p;
                        className = classesList.get("name").toString();
                        break;
                }

                stringBuilder
                        .append("public List<").append(className).append(">").append(" getAll").append(className).append("s() ").append(BLOCK_OPEN).append(" return ").append(firstCharToLowerCase(className)).append("Repository.findAll(); ").append(BLOCK_CLOSED)
                        .append(DOUBLE_NEW_LINE)
                        .append("public ").append(className).append(" get").append(className).append(" ById (Long id) ").append(BLOCK_OPEN).append(" return ").append(firstCharToLowerCase(className)).append("Repository.findById(id).orElse(null);").append(BLOCK_CLOSED)
                        .append(DOUBLE_NEW_LINE)
                        .append("public ").append(className).append(" add").append(className).append("(").append(className).append(SPACE).append(firstCharToLowerCase(className)).append(" { return ").append(firstCharToLowerCase(className)).append("Repository.save(").append(className).append("); }")
                        .append(DOUBLE_NEW_LINE)
                        .append("public ").append(className).append(" update").append(className).append("(").append(className).append(" updated").append(className).append(") { return ").append(firstCharToLowerCase(className)).append("Repository.save(").append(firstCharToLowerCase(className)).append("; }")
                        .append(DOUBLE_NEW_LINE)
                        .append("public void delete").append(className).append("(Long id) { ").append(firstCharToLowerCase(className)).append("Repository.findById(id); }")
                        .append(DOUBLE_NEW_LINE);
                return stringBuilder.toString();
        }

        //TODO: second class service part
}
