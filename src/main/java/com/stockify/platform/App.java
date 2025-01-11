package com.stockify.platform;

import com.stockify.platform.service.InventoryService;
import com.stockify.platform.controller.CommandController;
import java.util.Scanner;

public class App {
    private static final InventoryService inventoryService = InventoryService.getInstance();
    private static final CommandController commandController = new CommandController(inventoryService);

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String command = scanner.nextLine();
                if (command.equals("EXIT")) {
                    commandController.handleExit();
                    break;
                }
                commandController.processCommand(command);
            }
        } catch (Exception e) {
            System.err.println("Fatal error occurred: " + e.getMessage());
        }
    }
}