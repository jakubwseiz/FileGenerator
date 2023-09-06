package FileGenerator.FilesContent;

public class ProductItemRepositoryContent {
    public static String getProductItemRepositoryContent() {
        return "package com.myCompany.ProjectName.Repository;\n"
                + "\n"
                + "import com.myCompany.ProjectName.Models.InvoiceItem;\n"
                + "import org.springframework.data.jpa.repository.JpaRepository;\n"
                + "import org.springframework.stereotype.Repository;\n"
                + "\n"
                + "@Repository\n"
                + "public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {\n"
                + "}\n";
    }

}