package FileGenerator;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import FileGenerator.Generators.PomXmlGenerator;

import static FileGenerator.FilesContent.ControllerContent.getControllerContent;
import static FileGenerator.FilesContent.ModelContent.getModelContent;
import static FileGenerator.FilesContent.ModelItemsContent.getModelItemsContent;
import static FileGenerator.FilesContent.RepositoryContent.getRepositoryContent;
import static FileGenerator.FilesContent.ServiceContent.getServiceContent;


public class ProjectStructureBuilder {
    public static void main(String[] args) {
        // Ścieżka do miejsca, gdzie chcesz zapisać struktury projektu
        String projectPath = "C:\\Users\\Kozlos\\Desktop\\Inżynierka\\output";

        try {
            // Tworzenie katalogu głównego projektu
            createDirectory(projectPath);

            // Tworzenie struktury projektu
            createDirectory(projectPath + "/src");
            createDirectory(projectPath + "/src/main");
            createDirectory(projectPath + "/src/main/java");
            createDirectory(projectPath + "/src/main/java/com");
            createDirectory(projectPath + "/src/main/java/com/mycompany");
            createDirectory(projectPath + "/src/main/java/com/mycompany/controller");
            createDirectory(projectPath + "/src/main/java/com/mycompany/model");
            createDirectory(projectPath + "/src/main/java/com/mycompany/service");
            createDirectory(projectPath + "/src/main/java/com/mycompany/repository");
            createDirectory(projectPath + "/src/main/resources");
            createDirectory(projectPath + "/src/main/resources/static");
            createDirectory(projectPath + "/src/main/resources/templates");
            createDirectory(projectPath + "/src/test");
            createDirectory(projectPath + "/src/test/java");
            createDirectory(projectPath + "/target");

            // Dodawanie klas do odpowiednich katalogów
            createJavaClass(projectPath + "/src/main/java/com/mycompany/controller", "ProductController", getControllerContent());
            createJavaClass(projectPath + "/src/main/java/com/mycompany/model", "Product", getModelContent());
            createJavaClass(projectPath + "/src/main/java/com/mycompany/model", "ProductItems", getModelItemsContent());
            createJavaClass(projectPath + "/src/main/java/com/mycompany/service", "ProductService", getServiceContent());
            createJavaClass(projectPath + "/src/main/java/com/mycompany/repository", "ProductRepository", getRepositoryContent());


            System.out.println("Struktura projektu została zbudowana w: " + projectPath);
        } catch (IOException e) {
            System.err.println("Błąd podczas tworzenia struktury projektu: " + e.getMessage());
        }
        PomXmlGenerator.generatePomXml();
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

}
