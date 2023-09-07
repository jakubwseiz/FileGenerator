package FileGenerator.TempaltesContent;

public class ListOfProductsTemplate {

    public static String getInvoicesTemplateContent() {
        return "<!DOCTYPE html>\n" +
                "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <title>Invoices</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Invoices</h1>\n" +
                "    <div class=\"container\">\n" +
                "        <table class=\"table\">\n" +
                "            <thead>\n" +
                "            <tr>\n" +
                "                <th>Invoice Number</th>\n" +
                "                <th>Date</th>\n" +
                "                <th>Amount</th>\n" +
                "                <th>Actions</th>\n" +
                "            </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                "            <tr th:each=\"invoice : ${invoices}\">\n" +
                "                <td th:text=\"${invoice.invoiceNumber}\"></td>\n" +
                "                <td th:text=\"${invoice.date}\"></td>\n" +
                "                <td th:text=\"${invoice.amount}\"></td>\n" +
                "                <td>\n" +
                "                    <a th:href=\"@{/invoices/{id}(id=${invoice.id})}\" class=\"btn btn-primary\">View</a>\n" +
                "                    <a th:href=\"@{/invoices/update/{id}(id=${invoice.id})}\" class=\"btn btn-warning\">Edit</a>\n" +
                "                    <a th:href=\"@{/invoices/delete/{id}(id=${invoice.id})}\" class=\"btn btn-danger\">Delete</a>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "    <a th:href=\"@{/invoices/add}\" class=\"btn btn-primary\">Add Invoice with Items</a>\n" +
                "</body>\n" +
                "</html>";
    }
}
