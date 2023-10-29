package FileGenerator.Generators;

import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static FileGenerator.Generators.PropertiesGenerator.generateProperties;
import static FileGenerator.Repository.RepositryClassGenerator.generateRepositoryClass;

import static FileGenerator.newController.ControllerClassGenerator.generateControllerClass;
import static FileGenerator.newEntity.EntityClassGenerator.generateFirstClass;
import static FileGenerator.newEntity.EntityClassGenerator.generateSecondClass;
import static FileGenerator.newService.ServiceClassGenerator.generateServiceClass;
import static FileGenerator.newStartClassGenerator.StartClassGenerator.generateStartClassContent;
import static FileGenerator.newTemplatesContent.AddingTemplateGenerator.generateAddProductTemplate;
import static FileGenerator.newTemplatesContent.AllProductsTemplateGenerator.generateAllProductsTemplate;
import static FileGenerator.newTemplatesContent.ProductTemplateGenerator.generateProductTemplate;
import static FileGenerator.newTemplatesContent.UpdateProductClassGenerator.generateUpdateProductTemplate;

public class ProjectStructureBuilder {
    @SneakyThrows
    public static void buildProjectStructure(String projectPathhh, JSONObject jsonObject) {

        String projectName = jsonObject.get("projectName").toString();
        JSONParser parser = new JSONParser();

        String projectPath = "C:\\Users\\Kozlos\\Desktop\\Inżynierka\\"+ projectName;

        JSONArray classesList = (JSONArray) jsonObject.get("classes");
        JSONObject firstClassJSONObject = (JSONObject) parser.parse(classesList.get(0).toString());
        String firstClassName = firstClassJSONObject.get("name").toString();

        JSONObject secondClassJSONObject = (JSONObject) classesList.get(1);
        String secondClassName = secondClassJSONObject.get("name").toString();

        try {
            // Tworzenie katalogu głównego projektu
            createDirectory(projectPath);

            // Tworzenie struktury projektu
            createDirectory(projectPath + "/src");
            createDirectory(projectPath + "/src/main");
            createDirectory(projectPath + "/src/main/java");
            createDirectory(projectPath + "/src/main/java/com");
            createDirectory(projectPath + "/src/main/java/com/myCompany");
            createDirectory(projectPath + "/src/main/java/com/myCompany/" + projectName + "/Controllers");
            createDirectory(projectPath + "/src/main/java/com/myCompany/" + projectName + "/Models");
            createDirectory(projectPath + "/src/main/java/com/myCompany/" + projectName + "/Services");
            createDirectory(projectPath + "/src/main/java/com/myCompany/" + projectName + "/Repository");
            createDirectory(projectPath + "/src/main/resources");
            createDirectory(projectPath + "/src/main/resources/static");
            createDirectory(projectPath + "/src/main/resources/templates");
            createDirectory(projectPath + "/src/test");
            createDirectory(projectPath + "/src/test/java");
            createDirectory(projectPath + "/target");

            // Dodawanie klas do odpowiednich katalogów
            createJavaClass(projectPath + "/src/main/java/com/myCompany/" + projectName + "/Controllers", firstClassName + "Controller", generateControllerClass(jsonObject));
            createJavaClass(projectPath + "/src/main/java/com/myCompany/" + projectName + "/Models", firstClassName, generateFirstClass(jsonObject));
            createJavaClass(projectPath + "/src/main/java/com/myCompany/" + projectName + "/Models", secondClassName, generateSecondClass(jsonObject));
            createJavaClass(projectPath + "/src/main/java/com/myCompany/" + projectName + "/Services", firstClassName + "Service", generateServiceClass(jsonObject));
            createJavaClass(projectPath + "/src/main/java/com/myCompany/" + projectName + "/Repository", firstClassName + "Repository", generateRepositoryClass(jsonObject));
            createJavaClass(projectPath + "/src/main/java/com/myCompany/"+ projectName, "Start", generateStartClassContent(jsonObject));

            createHtmlFile(projectPath + "/src/main/resources/templates",firstClassName, generateProductTemplate(jsonObject));
            createHtmlFile(projectPath + "/src/main/resources/templates",firstClassName + "s", generateAllProductsTemplate(jsonObject));
            createHtmlFile(projectPath + "/src/main/resources/templates","add" + firstClassName, generateAddProductTemplate(jsonObject));
            createHtmlFile(projectPath + "/src/main/resources/templates","update" + firstClassName, generateUpdateProductTemplate(jsonObject));

            System.out.println("Struktura projektu została zbudowana w: " + projectPath);
        } catch (IOException e) {
            System.err.println("Błąd podczas tworzenia struktury projektu: " + e.getMessage());
        }
        PomXmlGenerator.generatePomXml(projectPath);
        PropertiesGenerator.generateProperties(projectPath);
    }

    private static void createDirectory(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        try {
            Files.createDirectories(path);
        } catch (FileAlreadyExistsException e) {
            // Obsługuje wyjątek, jeśli katalog już istnieje
            System.out.println("Katalog '" + directoryPath + "' już istnieje, pomijam.");
        }
    }

    private static void createJavaClass(String directoryPath, String className, String content) throws IOException {
        Path classPath = Paths.get(directoryPath + "/" + className + ".java");
        try {
            Files.write(classPath, content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (FileAlreadyExistsException e) {
            // Obsługuje wyjątek, jeśli plik już istnieje
            System.out.println("Plik '" + className + ".java' już istnieje w katalogu '" + directoryPath + "', nadpisuję.");
        }
    }

    private static void createHtmlFile(String directoryPath, String fileName, String content) throws IOException {
        Path classPath = Paths.get(directoryPath + "/" + fileName + ".html");
        try {
            Files.write(classPath, content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (FileAlreadyExistsException e) {
            // Obsługuje wyjątek, jeśli plik już istnieje
            System.out.println("Plik '" + fileName + ".html' już istnieje w katalogu '" + directoryPath + "', nadpisuję.");
        }
    }

}
