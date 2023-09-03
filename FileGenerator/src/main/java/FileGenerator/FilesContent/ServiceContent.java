package FileGenerator.FilesContent;

public class ServiceContent {

    public static String getServiceContent() {
        return "import org.springframework.stereotype.Service;\n\n"
                + "@Service\n"
                + "public class ProductService {\n\n"
                + "    // Implementacja serwisu, w tym operacje CRUD na produktach\n"
                + "}";
    }
}
