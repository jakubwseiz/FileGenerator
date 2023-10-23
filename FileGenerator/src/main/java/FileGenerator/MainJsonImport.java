package FileGenerator;

import java.io.File;
import java.io.FileReader;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static FileGenerator.Repository.RepositryClassGenerator.generateRepositoryClass;
import static FileGenerator.newController.ControllerClassGenerator.generateControllerClass;
import static FileGenerator.newEntity.EntityClassGenerator.generateClass;
import static FileGenerator.newEntity.EntityClassGenerator.generateRealClass;
import static FileGenerator.newService.ServiceClassGenerator.generateServiceClass;
import static FileGenerator.newTemplatesContent.AddingTemplateGenerator.generateAddProductTemplate;
import static FileGenerator.newTemplatesContent.AllProductsTemplateGenerator.generateAllProductsTemplate;
import static FileGenerator.newTemplatesContent.ProductTemplateGenerator.generateProductTemplate;


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

//        //generateClass(jsonObject);
//        System.out.println("--------- Generating classes ---------");
//        generateRealClass(jsonObject);
//        System.out.println("--------- Finished generating classes ---------");
//
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

        System.out.println("--------- Generating classes ---------");
        generateAllProductsTemplate(jsonObject);
        System.out.println("--------- Finished generating classes ---------");
    }
}

