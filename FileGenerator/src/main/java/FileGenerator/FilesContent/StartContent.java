package FileGenerator.FilesContent;

public class StartContent {

    public static String getStartContent() {
        return "package com.myCompany.ProjectName;\n"
                + "\n"
                + "import com.myCompany.ProjectName.Models.Invoice;\n"
                + "import org.springframework.boot.SpringApplication;\n"
                + "import org.springframework.boot.autoconfigure.SpringBootApplication;\n"
                + "\n"
                + "@SpringBootApplication\n"
                + "public class Start {\n"
                + "\n"
                + "    public static void main(String[] args) {\n"
                + "        SpringApplication.run(Start.class, args);\n"
                + "    }\n"
                + "}\n";
    }

}
