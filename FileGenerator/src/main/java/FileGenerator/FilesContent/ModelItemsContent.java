package FileGenerator.FilesContent;

public class ModelItemsContent {
    public static String getModelItemsContent() {
        return "package com.myCompany.ProjectName.Models;\n"
                + "\n"
                + "import com.fasterxml.jackson.annotation.JsonBackReference;\n"
                + "import jakarta.persistence.*;\n"
                + "\n"
                + "import java.math.BigDecimal;\n"
                + "\n"
                + "@Entity\n"
                + "public class InvoiceItem {\n"
                + "    @Id\n"
                + "    @GeneratedValue(strategy = GenerationType.IDENTITY)\n"
                + "    private Long id;\n"
                + "    private String itemName;\n"
                + "    private BigDecimal itemAmount;\n"
                + "\n"
                + "    @ManyToOne\n"
                + "    @JoinColumn(name = \"invoice_id\")\n"
                + "    @JsonBackReference\n"
                + "    private Invoice invoice;\n"
                + "\n"
                + "    public InvoiceItem() {\n"
                + "    }\n"
                + "\n"
                + "    public InvoiceItem(Long id, String itemName, BigDecimal itemAmount, Invoice invoice) {\n"
                + "        this.id = id;\n"
                + "        this.itemName = itemName;\n"
                + "        this.itemAmount = itemAmount;\n"
                + "        this.invoice = invoice;\n"
                + "    }\n"
                + "\n"
                + "    public Long getId() {\n"
                + "        return id;\n"
                + "    }\n"
                + "\n"
                + "    public void setId(Long id) {\n"
                + "        this.id = id;\n"
                + "    }\n"
                + "\n"
                + "    public String getItemName() {\n"
                + "        return itemName;\n"
                + "    }\n"
                + "\n"
                + "    public void setItemName(String itemName) {\n"
                + "        this.itemName = itemName;\n"
                + "    }\n"
                + "\n"
                + "    public BigDecimal getItemAmount() {\n"
                + "        return itemAmount;\n"
                + "    }\n"
                + "\n"
                + "    public void setItemAmount(BigDecimal itemAmount) {\n"
                + "        this.itemAmount = itemAmount;\n"
                + "    }\n"
                + "\n"
                + "    public Invoice getInvoice() {\n"
                + "        return invoice;\n"
                + "    }\n"
                + "\n"
                + "    public void setInvoice(Invoice invoice) {\n"
                + "        this.invoice = invoice;\n"
                + "    }\n"
                + "}\n";
    }

}
