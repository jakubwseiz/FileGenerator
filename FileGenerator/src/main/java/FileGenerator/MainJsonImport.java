package FileGenerator;

import java.io.FileReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static FileGenerator.Generators.ProjectStructureBuilder.buildProjectStructure;
import static FileGenerator.newEntity.EntityClassGenerator.generateFirstClass;
import static FileGenerator.newEntity.EntityClassGenerator.generateSecondClass;


public class MainJsonImport {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Użycie: java ImportJSONFile <ścieżka_do_pliku_JSON>");
        }
//        String absolutePath = file.getAbsolutePath();
        Path root = Paths.get("FileGenerator/src/main/resources/test.json").normalize().toAbsolutePath();
        String filePath = root.toString(); //"C:\\Users\\Kozlos\\Desktop\\Inżynierka\\test.json";

        JSONObject jsonObject = null;
        try {
            // Odczytanie zawartości pliku JSON
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(filePath));

            // Sprawdzenie, czy to jest obiekt JSON
            if (obj instanceof JSONObject) {
                jsonObject = (JSONObject) obj;

                // operacje na zawartości pliku JSON
                System.out.println("Zawartość pliku JSON:");
                System.out.println(jsonObject.toString());
            } else {
                System.out.println("Plik JSON nie zawiera obiektu JSON.");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.out.println("Błąd podczas importowania pliku JSON: " + e.getMessage());
        }
        buildProjectStructure(filePath, jsonObject);
//

        //generateClass();
//        System.out.println("--------- Generating classes ---------");
//        generateFirstClass(jsonObject);
//        System.out.println("--------- Finished generating classes ---------");
//
//        generateSecondClass(jsonObject);
////
//        //generate Service class
//        System.out.println("--------- Generating classes ---------");
//        generateServiceClass(jsonObject);
//        System.out.println("--------- Finished generating classes ---------");

//        //generate repository class
//        System.out.println("--------- Generating classes ---------");
//        generateRepositoryClass(jsonObject);
//        System.out.println("--------- Finished generating classes ---------");

//        //generate templates class
//        System.out.println("--------- Generating classes ---------");
//        generateProductTemplate(jsonObject);
//        System.out.println("--------- Finished generating classes ---------");

//        System.out.println("--------- Generating classes ---------");
//        generateControllerClass(jsonObject);
//        System.out.println("--------- Finished generating classes ---------");

//        System.out.println("--------- Generating classes ---------");
//        generateAddProductTemplate(jsonObject);
//        System.out.println("--------- Finished generating classes ---------");

//        System.out.println("--------- Generating classes ---------");
//        generateAllProductsTemplate(jsonObject);
//        System.out.println("--------- Finished generating classes ---------");

//        System.out.println("--------- Generating classes ---------");
//        generateUpdateProductTemplate(jsonObject);
//        System.out.println("--------- Finished generating classes ---------");

//        System.out.println("--------- Generating classes ---------");
//        generateStartClassContent(jsonObject);
//        System.out.println("--------- Finished generating classes ---------");
    }
}

