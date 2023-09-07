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
import static FileGenerator.FilesContent.ProductItemRepositoryContent.getProductItemRepositoryContent;
import static FileGenerator.FilesContent.ServiceContent.getServiceContent;
import static FileGenerator.FilesContent.StartContent.getStartContent;
import static FileGenerator.TempaltesContent.ProductTemplate.getInvoiceTemplateContent;
import static FileGenerator.TempaltesContent.ListOfProductsTemplate.getInvoicesTemplateContent;
import static FileGenerator.TempaltesContent.AddProductTemplate.getAddInvoiceTemplateContent;
import static FileGenerator.TempaltesContent.UpdateProductTemplate.getUpdateInvoiceTemplateContent;


public class ProjectStructureBuilder {
    public static void main(String[] args) {

        String projectPath = "C:\\Users\\Kozlos\\Desktop\\Inżynierka\\NazwaProjektu";

        try {
            // Tworzenie katalogu głównego projektu
            createDirectory(projectPath);

            // Tworzenie struktury projektu
            createDirectory(projectPath + "/src");
            createDirectory(projectPath + "/src/main");
            createDirectory(projectPath + "/src/main/java");
            createDirectory(projectPath + "/src/main/java/com");
            createDirectory(projectPath + "/src/main/java/com/myCompany");
            createDirectory(projectPath + "/src/main/java/com/myCompany/ProjectName/Controllers");
            createDirectory(projectPath + "/src/main/java/com/myCompany/ProjectName/Models");
            createDirectory(projectPath + "/src/main/java/com/myCompany/ProjectName/Service");
            createDirectory(projectPath + "/src/main/java/com/myCompany/ProjectName/Repository");
            createDirectory(projectPath + "/src/main/resources");
            createDirectory(projectPath + "/src/main/resources/static");
            createDirectory(projectPath + "/src/main/resources/templates");
            createDirectory(projectPath + "/src/test");
            createDirectory(projectPath + "/src/test/java");
            createDirectory(projectPath + "/target");

            // Dodawanie klas do odpowiednich katalogów
            createJavaClass(projectPath + "/src/main/java/com/myCompany/ProjectName/Controllers", "ThymeleafController", getControllerContent());
            createJavaClass(projectPath + "/src/main/java/com/myCompany/ProjectName/Models", "Invoice", getModelContent());
            createJavaClass(projectPath + "/src/main/java/com/myCompany/ProjectName/Models", "InvoiceItem", getModelItemsContent());
            createJavaClass(projectPath + "/src/main/java/com/myCompany/ProjectName/Service", "InvoiceService", getServiceContent());
            createJavaClass(projectPath + "/src/main/java/com/myCompany/ProjectName/Repository", "InvoiceRepository", getRepositoryContent());
            createJavaClass(projectPath + "/src/main/java/com/myCompany/ProjectName/Repository", "InvoiceItemRepository", getProductItemRepositoryContent());
            createJavaClass(projectPath + "/src/main/java/com/myCompany/ProjectName", "Start", getStartContent());

            createHtmlFile(projectPath + "/src/main/resources/templates","invoice", getInvoiceTemplateContent());
            createHtmlFile(projectPath + "/src/main/resources/templates","invoices", getInvoicesTemplateContent());
            createHtmlFile(projectPath + "/src/main/resources/templates","addInvoice", getAddInvoiceTemplateContent());
            createHtmlFile(projectPath + "/src/main/resources/templates","updateInvoice", getUpdateInvoiceTemplateContent());


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
