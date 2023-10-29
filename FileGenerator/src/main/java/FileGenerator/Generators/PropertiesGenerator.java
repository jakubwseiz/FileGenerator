package FileGenerator.Generators;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class PropertiesGenerator {

    public static void generateProperties(String projectPath) {
        // Ścieżka do pliku pom.xml
        Path pomXmlPath = Path.of(projectPath + "\\src\\main\\resources\\application.properties");

        StringBuilder propertiesContent = new StringBuilder();
        // Treść pliku pom.xml
        propertiesContent
                .append("""
                        # Konfiguracja bazy danych H2
                        spring.datasource.url=jdbc:h2:mem:product_db
                        spring.datasource.username=sa
                        spring.datasource.password=
                        spring.datasource.driver-class-name=org.h2.Driver
                                                
                        # Konsola H2
                        spring.h2.console.enabled=true
                        spring.h2.console.path=/h2-console
                                                
                        # Konfiguracja Hibernate
                        spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
                        spring.jpa.hibernate.ddl-auto=update
                        
                        # Port serwera
                        server.port=8080
                        """);

        try {
            // Zapisz treść do pliku application.properties
            Files.writeString(pomXmlPath, propertiesContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Plik application.properties został wygenerowany.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
