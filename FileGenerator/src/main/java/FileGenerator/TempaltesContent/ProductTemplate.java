package FileGenerator.TempaltesContent;

public class ProductTemplate {

    public static String getInvoiceTemplateContent() {
        return "<!DOCTYPE html>\n" +
                "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <title>Invoice</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Invoice Details</h1>\n" +
                "<table class=\"table\">\n" +
                "    <tr>\n" +
                "        <th>Invoice Number:</th>\n" +
                "        <td th:text=\"${invoice.invoiceNumber}\"></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <th>Date:</th>\n" +
                "        <td th:text=\"${invoice.date}\"></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <th>Amount:</th>\n" +
                "        <td th:text=\"${invoice.amount}\"></td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "\n" +
                "<h2>Items</h2>\n" +
                "<table class=\"table\">\n" +
                "    <thead>\n" +
                "    <tr>\n" +
                "        <th>Item Name</th>\n" +
                "        <th>Item Amount</th>\n" +
                "    </tr>\n" +
                "    </thead>\n" +
                "    <tbody>\n" +
                "    <tr th:each=\"item : ${invoice.items}\">\n" +
                "        <td th:text=\"${item.itemName}\"></td>\n" +
                "        <td th:text=\"${item.itemAmount}\"></td>\n" +
                "    </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "\n" +
                "<a th:href=\"@{/invoices}\">Back to Invoices</a>\n" +
                "</body>\n" +
                "</html>";
    }
}
