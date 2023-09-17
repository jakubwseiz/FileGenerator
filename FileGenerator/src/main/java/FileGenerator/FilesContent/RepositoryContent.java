package FileGenerator.FilesContent;

public class RepositoryContent {
    public static String getRepositoryContent() {
        return "package com.myCompany.ProjectName.Repository;\n"
                + "\n"
                + "import com.myCompany.ProjectName.Models.Invoice;\n"
                + "import org.springframework.data.jpa.repository.JpaRepository;\n"
                + "import org.springframework.stereotype.Repository;\n"
                + "\n"
                + "@Repository\n"
                + "public interface InvoiceRepository extends JpaRepository<Invoice, Long> {\n"
                + "}\n";
    }

}
