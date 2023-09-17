package FileGenerator;

import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MainJsonImport {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Użycie: java ImportJSONFile <ścieżka_do_pliku_JSON>");
            return;
        }

        String filePath = args[0];

        try {
            // Odczytanie zawartości pliku JSON
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(filePath));

            // Sprawdzenie, czy to jest obiekt JSON
            if (obj instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) obj;

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
    }
}

