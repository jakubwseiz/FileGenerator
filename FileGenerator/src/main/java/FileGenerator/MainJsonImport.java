package FileGenerator;

import java.io.FileReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static FileGenerator.newEntity.EntityClassGenerator.generateClass;


public class MainJsonImport {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Użycie: java ImportJSONFile <ścieżka_do_pliku_JSON>");
        }

        String filePath = "C:\\Users\\Kozlos\\Desktop\\Inżynierka\\test.json";

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

        generateClass(jsonObject);

    }
}

