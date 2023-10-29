package FileGenerator.Repository;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RepositryClassGenerator {
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


    public static String generateRepositoryClass(JSONObject jsonObject) {
        StringBuilder classContent = new StringBuilder();
        String projectName = jsonObject.get("projectName").toString();
        JSONArray classesList = (JSONArray) jsonObject.get("classes");

        classContent
                .append("package com.myCompany.").append(projectName).append(".Repository").append(END_STATEMENT)
                .append(DOUBLE_NEW_LINE)
                .append(generateRepositoryImport(jsonObject))
                .append(generateRepositoryContent(classesList));

        System.out.println(classContent.toString());
        return classContent.toString();
    }

    public static String generateRepositoryImport(JSONObject jsonObject) {
        StringBuilder stringBuilder = new StringBuilder();
        JSONParser parser = new JSONParser();

        String projectName = jsonObject.get("projectName").toString();
        //Classes
        JSONArray classesList = (JSONArray) jsonObject.get("classes");

        String className = null;

        for (Object p : classesList) {
            JSONObject k = (JSONObject) p;
            className = k.get("name").toString();
            break;
        }

        stringBuilder
                .append("import com.myCompany.").append(projectName).append(".Models.").append(className).append(END_STATEMENT)
                .append(NEW_LINE)
                .append("import org.springframework.data.jpa.repository.JpaRepository;")
                .append(NEW_LINE)
                .append("import org.springframework.stereotype.Repository;")
                .append(DOUBLE_NEW_LINE);

        return stringBuilder.toString();
    }

    public static String generateRepositoryContent(JSONArray jsonArray) {
        StringBuilder stringBuilder = new StringBuilder();

        String className = null;

        for (Object p : jsonArray) {
            JSONObject k = (JSONObject) p;
            className = k.get("name").toString();
            break;
        }

        stringBuilder
                .append("@Repository")
                .append(NEW_LINE)
                .append("public interface ").append(className).append("Repository").append(" extends JpaRepository<").append(className).append(", Long> {")
                .append(NEW_LINE).append(BLOCK_CLOSED);

        return stringBuilder.toString();
    }
}
