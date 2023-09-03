package FileGenerator.FilesContent;

public class ControllerContent {

    public static String getControllerContent() {
        return "import org.springframework.beans.factory.annotation.Autowired;\n"
                + "import org.springframework.web.bind.annotation.*;\n"
                + "import java.util.List;\n\n"
                + "@RestController\n"
                + "@RequestMapping(\"/products\")\n"
                + "public class ProductController {\n\n"
                + "    private final ProductService productService;\n\n"
                + "    @Autowired\n"
                + "    public ProductController(ProductService productService) {\n"
                + "        this.productService = productService;\n"
                + "    }\n\n"
                + "    @GetMapping\n"
                + "    public List<Product> getAllProducts() {\n"
                + "        return productService.getAllProducts();\n"
                + "    }\n\n"
                + "    @GetMapping(\"/{id}\")\n"
                + "    public Product getProductById(@PathVariable Long id) {\n"
                + "        return productService.getProductById(id);\n"
                + "    }\n\n"
                + "    @PostMapping\n"
                + "    public Product createProduct(@RequestBody Product product) {\n"
                + "        return productService.createProduct(product);\n"
                + "    }\n\n"
                + "    @PutMapping(\"/{id}\")\n"
                + "    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {\n"
                + "        return productService.updateProduct(id, product);\n"
                + "    }\n\n"
                + "    @DeleteMapping(\"/{id}\")\n"
                + "    public void deleteProduct(@PathVariable Long id) {\n"
                + "        productService.deleteProduct(id);\n"
                + "    }\n"
                + "}";
    }
}
