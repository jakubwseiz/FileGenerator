package FileGenerator.TempaltesContent;

public class UpdateProductTemplate {
    public static String getUpdateInvoiceTemplateContent() {
        return "<!DOCTYPE html>\n" +
                "<html xmlns:th=\"http://www.thymeleaf.org\" lang=\"en\">\n" +
                "<head>\n" +
                "    <title>Update Invoice</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Update Invoice</h1>\n" +
                "\n" +
                "<form th:action=\"@{/invoices/update/{id}(id=${updateInvoice.id})}\" th:object=\"${updateInvoice}\" method=\"post\">\n" +
                "    <input type=\"hidden\" th:field=\"*{id}\"/>\n" +
                "    Invoice Number: <input type=\"text\" th:field=\"*{invoiceNumber}\" required/><br/>\n" +
                "    Date: <input type=\"date\" th:field=\"*{date}\" required/><br/> \n" +
                "    Amount: <input type=\"number\" th:field=\"*{amount}\" required/><br/>\n" +
                "\n" +
                "    <h2>Items</h2>\n" +
                "    <table id=\"itemsTable\">\n" +
                "        <tr>\n" +
                "            <th>Name</th>\n" +
                "            <th>Price</th>\n" +
                "            <th>Action</th>\n" +
                "        </tr>\n" +
                "        <tr th:each=\"item, itemStat : ${updateInvoice.items}\">\n" +
                "            <td><input type=\"text\" th:field=\"*{items[__${itemStat.index}__].itemName}\"/></td>\n" +
                "            <td><input type=\"number\" th:field=\"*{items[__${itemStat.index}__].itemAmount}\"/></td>\n" +
                "            <td><button type=\"button\" class=\"btn btn-danger\" id=\"remove[__${itemStat.index}__]\" onclick=\"removeItem(this)\">Remove</button></td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "\n" +
                "    <button type=\"button\" class=\"btn btn-primary\" onclick=\"addItem()\">Add Item</button>\n" +
                "\n" +
                "    <button type=\"submit\" class=\"btn btn-success\">Save Changes</button>\n" +
                "</form>\n" +
                "\n" +
                "<a th:href=\"@{/invoices}\">Back to Invoices</a>\n" +
                "<script th:inline=\"javascript\">\n" +
                "    var itemCounter = /*[[${itemSize}]]*/;\n" +
                "\n" +
                "    function removeItem(button) {\n" +
                "        var row = button.parentNode.parentNode;\n" +
                "        row.parentNode.removeChild(row);\n" +
                "    }\n" +
                "\n" +
                "    function addItem() {\n" +
                "        itemCounter++;\n" +
                "        var itemRow = document.createElement(\"tr\");\n" +
                "        itemRow.className = \"item-row\";\n" +
                "        itemRow.innerHTML = `\n" +
                "        <tr>\n" +
                "            <td><input type=\"text\" class=\"form-control\" id=\"items[${itemCounter}].itemName\" name=\"items[${itemCounter}].itemName\" required/></td>\n" +
                "            <td><input type=\"number\" class=\"form-control\" id=\"items[${itemCounter}].itemAmount\" name=\"items[${itemCounter}].itemAmount\" required/></td>\n" +
                "            <td><button type=\"button\" class=\"btn btn-danger\" onclick=\"removeItem(this)\">Remove</button></td>\n" +
                "        </tr>\n" +
                "        `;\n" +
                "        document.getElementById(\"itemsTable\").appendChild(itemRow);\n" +
                "    }\n" +
                "</script>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }
}
