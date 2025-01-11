package com.stockify.platform;


import org.junit.jupiter.api.*;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import static org.junit.jupiter.api.Assertions.assertEquals;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppIntegrationBasicTest {


    private void assertAppOutput(String input, String expectedOutput) {
        // Set up input stream
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);


        // Set up output stream
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));


        // Run the main method
        App.main(new String[]{});


        // Capture and process the output
        String actualOutput = out.toString().replaceAll("\\r\\n", "\n").trim();
        String formattedExpectedOutput = expectedOutput.replaceAll("\\r\\n", "\n").trim();


        if (!formattedExpectedOutput.equals(actualOutput)) {
            // Split the expected and actual outputs into lines
            String[] expectedLines = formattedExpectedOutput.split("\n");
            String[] actualLines = actualOutput.split("\n");


            // Determine the maximum length of the expected and actual output lines for proper alignment
            int maxExpectedLength = 0;
            int maxActualLength = 0;
            for (String line : expectedLines) {
                if (line.length() > maxExpectedLength) {
                    maxExpectedLength = line.length();
                }
            }
            for (String line : actualLines) {
                if (line.length() > maxActualLength) {
                    maxActualLength = line.length();
                }
            }


            // Determine the width of the header and format
            int columnWidth = Math.max(maxExpectedLength, maxActualLength) + 5;
            String expectedHeader = "===EXPECTED OUTPUT===";
            String actualHeader = "===ACTUAL OUTPUT===";
            String header = String.format("%-" + columnWidth + "s | %s", expectedHeader, actualHeader);


            // ANSI escape codes for coloring
            String redColor = "\u001B[31m";
//            String blue = "\u001B[34m";
            String magentaColor = "\u001B[35m";
            String resetColor = "\u001B[0m";


            // Build the failure message with aligned columns
            StringBuilder failureMessage = new StringBuilder();
            failureMessage.append(redColor).append("Test Failed For:\n").append(resetColor);
//            failureMessage.append(blue).append("====INPUT SEQUENCE====\n").append(input.trim()).append(resetColor).append("\n");
            failureMessage.append(magentaColor).append("====INPUT SEQUENCE====\n").append(resetColor).append(input.trim()).append("\n");
            failureMessage.append(magentaColor).append(header).append(resetColor).append("\n");


            int maxLines = Math.max(expectedLines.length, actualLines.length);
            for (int i = 0; i < maxLines; i++) {
                String expectedLine = i < expectedLines.length ? expectedLines[i] : "";
                String actualLine = i < actualLines.length ? actualLines[i] : "";


                // Add spaces to the expected line to align with the actual output column
                String formattedExpectedLine = String.format("%-" + columnWidth + "s", expectedLine);


                if (!expectedLine.equals(actualLine)) {
                    failureMessage.append(redColor).append(formattedExpectedLine).append(" | ").append(actualLine).append(resetColor).append("\n");
                } else {
                    failureMessage.append(formattedExpectedLine).append(" | ").append(actualLine).append("\n");
                }
            }


            Assertions.fail(failureMessage.toString());
        }


        // Assert equality with detailed message
        assertEquals(formattedExpectedOutput, actualOutput);
    }


    @Test
    @Order(1)
    public void testAddProduct() {
        String input = "ADD_PRODUCT 101 Apple 10 50\nADD_PRODUCT 101 Apple 20 30\nADD_PRODUCT 102 \"Banana Juice\" 15 20\nEXIT";
        String expectedOutput = "SUCCESS\nPRODUCT_ALREADY_EXISTS\nREQUEST_PATTERN_INVALID\nProduct Count: 1\nTotal Inventory Value: 500\nGoodbye!\n";


        assertAppOutput(input, expectedOutput);
    }


    @Test
    @Order(2)
    public void testUpdateQuantity() {
        String input = "UPDATE_QUANTITY 102 20\nUPDATE_QUANTITY 101 20\nUPDATE_QUANTITY 101 -10\nEXIT";
        String expectedOutput = "PRODUCT_NOT_FOUND\nSUCCESS\nREQUEST_PATTERN_INVALID\nProduct Count: 1\nTotal Inventory Value: 1000\nGoodbye!\n";


        assertAppOutput(input, expectedOutput);
    }


    @Test
    @Order(3)
    public void testUpdatePrice() {
        String input = "UPDATE_PRICE 102 30\nUPDATE_PRICE 101 30\nUPDATE_PRICE 101 -30\nEXIT";
        String expectedOutput = "PRODUCT_NOT_FOUND\nSUCCESS\nREQUEST_PATTERN_INVALID\nProduct Count: 1\nTotal Inventory Value: 600\nGoodbye!\n";


        assertAppOutput(input, expectedOutput);
    }


    @Test
    @Order(4)
    public void testViewProduct() {
        String input = "VIEW_PRODUCT 102\nVIEW_PRODUCT 101\nEXIT";
        String expectedOutput = "PRODUCT_NOT_FOUND\nProduct ID: 101\nName: Apple\nQuantity: 20\nPrice: 30\nProduct Count: 1\nTotal Inventory Value: 600\nGoodbye!\n";


        assertAppOutput(input, expectedOutput);
    }


    @Test
    @Order(5)
    public void testRemoveProduct() {
        String input = "REMOVE_PRODUCT 102\nREMOVE_PRODUCT 101\nEXIT";
        String expectedOutput = "PRODUCT_NOT_FOUND\nSUCCESS\nProduct Count: 0\nTotal Inventory Value: 0\nGoodbye!\n";


        assertAppOutput(input, expectedOutput);
    }


    @Test
    @Order(6)
    public void testListProducts() {
        String input = "LIST_PRODUCTS\nADD_PRODUCT 101 Apple 10 50\nADD_PRODUCT P001 Phone 10 500\nADD_PRODUCT P002 Laptop 5 1000\nLIST_PRODUCTS\nEXIT";
        String expectedOutput = "NO_PRODUCTS_AVAILABLE\nSUCCESS\nSUCCESS\nSUCCESS\n101:Apple:10:50\nP001:Phone:10:500\nP002:Laptop:5:1000\nProduct Count: 3\nTotal Inventory Value: 10500\nGoodbye!\n";


        assertAppOutput(input, expectedOutput);
    }


    @Test
    @Order(7)
    public void testSortProducts() {
        String input = "SORT_PRODUCTS name\nEXIT";
        String expectedOutput = "101:Apple:10:50\nP002:Laptop:5:1000\nP001:Phone:10:500\nProduct Count: 3\nTotal Inventory Value: 10500\nGoodbye!\n";


        assertAppOutput(input, expectedOutput);
    }


    @Test
    @Order(8)
    public void testExit() {
        String input = "EXIT";
        String expectedOutput = "Product Count: 3\nTotal Inventory Value: 10500\nGoodbye!\n";


        assertAppOutput(input, expectedOutput);
    }


}

