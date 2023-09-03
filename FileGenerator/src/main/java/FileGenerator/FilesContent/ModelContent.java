package FileGenerator.FilesContent;

public class ModelContent {

    public static String getModelContent() {
        return "import javax.persistence.Entity;\n"
                + "import javax.persistence.GeneratedValue;\n"
                + "import javax.persistence.GenerationType;\n"
                + "import javax.persistence.Id;\n\n"
                + "@Entity\n"
                + "public class Product {\n"
                + "    @Id\n"
                + "    @GeneratedValue(strategy = GenerationType.IDENTITY)\n"
                + "    private Long id;\n"
                + "    private String name;\n"
                + "    private double price;\n\n"
                + "    // getters and setters\n\n"
                + "    public Long getId() {\n"
                + "        return id;\n"
                + "    }\n\n"
                + "    public void setId(Long id) {\n"
                + "        this.id = id;\n"
                + "    }\n\n"
                + "    public String getName() {\n"
                + "        return name;\n"
                + "    }\n\n"
                + "    public void setName(String name) {\n"
                + "        this.name = name;\n"
                + "    }\n\n"
                + "    public double getPrice() {\n"
                + "        return price;\n"
                + "    }\n\n"
                + "    public void setPrice(double price) {\n"
                + "        this.price = price;\n"
                + "    }\n"
                + "}";
    }

}
