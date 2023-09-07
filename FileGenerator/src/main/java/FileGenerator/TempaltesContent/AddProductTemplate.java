package FileGenerator.TempaltesContent;

public class AddProductTemplate {
    public static String getAddInvoiceTemplateContent() {
        return "<!DOCTYPE html>\n" +
                "<html xmlns:th=\"http://www.thymeleaf.org\" lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Add Invoice</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<h2>Add Invoice</h2>\n" +
                "<form th:action=\"@{/invoices/add}\" method=\"post\">\n" +
                "    <div class=\"form-group\">\n" +
                "        <label for=\"invoiceNumber\">Invoice Number</label>\n" +
                "        <input type=\"text\" class=\"form-control\" id=\"invoiceNumber\" name=\"invoiceNumber\" required>\n" +
                "    </div>\n" +
                "    <div class=\"form-group\">\n" +
                "        <label for=\"date\">Date</label>\n" +
                "        <input type=\"date\" class=\"form-control\" id=\"date\" name=\"date\" required>\n" +
                "    </div>\n" +
                "    <div class=\"form-group\">\n" +
                "        <label for=\"amount\">Amount</label>\n" +
                "        <input type=\"number\" class=\"form-control\" id=\"amount\" name=\"amount\" required>\n" +
                "    </div>\n" +
                "\n" +
                "    <h3>Items</h3>\n" +
                "    <div id=\"items-container\">\n" +
                "    </div>\n" +
                "    <button type=\"button\" class=\"btn btn-primary\" onclick=\"addItem()\">Add Item</button>\n" +
                "\n" +
                "    <button type=\"submit\" class=\"btn btn-success\">Save Invoice</button>\n" +
                "</form>\n" +
                "<a th:href=\"@{/invoices}\" class=\"mt-2\">Back to Invoices</a>\n" +
                "<script th:inline=\"javascript\">\n" +
                "    var itemCounter = 0;\n" +
                "\n" +
                "    function addItem() {\n" +
                "        var itemRow = document.createElement(\"div\");\n" +
                "        itemRow.className = \"item-row\";\n" +
                "        itemRow.innerHTML = `\n" +
                "            <div class=\"form-group\">\n" +
                "                <label for=\"items[${itemCounter}].itemName\">Item Name</label>\n" +
                "                <input type=\"text\" class=\"form-control\" id=\"items[${itemCounter}].itemName\" name=\"items[${itemCounter}].itemName\" required>\n" +
                "\n" +
                "            </div>\n" +
                "            <div class=\"form-group\">\n" +
                "                <label for=\"items[${itemCounter}].itemAmount\">Price</label>\n" +
                "                <input type=\"number\" class=\"form-control\" id=\"items[${itemCounter}].itemAmount\" name=\"items[${itemCounter}].itemAmount\" required>\n" +
                "            </div>\n" +
                "            <button type=\"button\" class=\"btn btn-danger\" onclick=\"removeItem(this)\">Remove</button>\n" +
                "        `;\n" +
                "        document.getElementById(\"items-container\").appendChild(itemRow);\n" +
                "        itemCounter++;\n" +
                "    }\n" +
                "\n" +
                "    function removeItem(button) {\n" +
                "        button.parentNode.remove();\n" +
                "    }\n" +
                "</script>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }
}
