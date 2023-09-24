package FileGenerator.newService;

import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;

public class ServiceGenerator {
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

        @SneakyThrows
        public static String generateServiceClass(JSONObject jsonObject) {

                StringBuilder classContent = new StringBuilder();
                JSONParser parser = new JSONParser();
                String projectName = jsonObject.get("projectName").toString();

                //Classes
                JSONArray classesList = (JSONArray) jsonObject.get("classes");
                for (Object c : classesList)
                {
                        gettersAndSetters = new StringBuilder();
                        properties = new StringBuilder();
                        JSONObject classJSONObject = (JSONObject) parser.parse(c.toString());
                        String className = classJSONObject.get("name").toString();
                        JSONArray properties = (JSONArray) classJSONObject.get("properties");

                        generateServiceImport(projectName, className);
                        //TODO: save class to file
                }

                return classContent.toString();
        }

        public static String generateServiceImport(String projectName, String className) {
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder
                        .append("import com.example.").append(projectName).append("Entities.").append(className)
                        .append(NEW_LINE)
                        .append("import com.example.");

                return stringBuilder.toString();
        }

        public static String generateImportForEntity() {
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder
                        .append()
        }
}
