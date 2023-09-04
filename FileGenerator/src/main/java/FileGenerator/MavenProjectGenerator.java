package FileGenerator;
import java.io.File;
import java.io.IOException;

public class MavenProjectGenerator {

//    public static void main(String[] args) {
//        String groupId = "com.example";
//        String artifactId = "my-project";
//        String version = "1.0-SNAPSHOT";
//        String projectPath = "C:\\Users\\Kozlos\\Desktop\\Inżynierka\\generowany_maven";
//
//        // Tworzenie katalogu projektu
//        File projectDir = new File(projectPath, artifactId);
//        projectDir.mkdirs();
//
//        // Tworzenie pliku pom.xml
//        String pomXml = generatePomXml(groupId, artifactId, version);
//        try {
//            File pomFile = new File(projectDir, "pom.xml");
//            org.apache.commons.io.FileUtils.writeStringToFile(pomFile, pomXml, "UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Tworzenie katalogu src/main/java
//        File javaSrcDir = new File(projectDir, "src/main/java");
//        javaSrcDir.mkdirs();
//
//        System.out.println("Projekt Maven został wygenerowany w katalogu: " + projectDir.getAbsolutePath());
//    }

    private static String generatePomXml(String groupId, String artifactId, String version) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
                "    <modelVersion>4.0.0</modelVersion>\n" +
                "\n" +
                "    <groupId>" + groupId + "</groupId>\n" +
                "    <artifactId>" + artifactId + "</artifactId>\n" +
                "    <version>" + version + "</version>\n" +
                "\n" +
                "    <properties>\n" +
                "        <maven.compiler.source>1.8</maven.compiler.source>\n" +
                "        <maven.compiler.target>1.8</maven.compiler.target>\n" +
                "    </properties>\n" +
                "</project>";
    }
}
