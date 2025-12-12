/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.supermarketmanager;

/**
 * Kacey Ashley
 * @author User
 */
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    
/**    
     Entry point of the console-based application
     Creates a SupermarketManager instance and provides a text-based menu loop
     for interacting with the system (adding products, managing activities, etc.)
     Used to demonstrate the core functionality without the GUI
*/    
    
    public static void main(String[] args) {
        SupermarketManager manager = new SupermarketManager();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Add Product");
                System.out.println("2. Display Products");
                System.out.println("3. Delete Product");
                System.out.println("4. Update Activity");
                System.out.println("5. Display Sorted Activities");
                System.out.println("6. Exit");
                System.out.print("Choose: ");
                int choice = scanner.nextInt();
                scanner.nextLine();// Consume newline

                try {
                    switch (choice) {
                        case 1:
                            System.out.print("Product ID: ");
                            String id = scanner.nextLine();
                            System.out.print("Name: ");
                            String name = scanner.nextLine();
                            System.out.print("Price: ");
                            double price = scanner.nextDouble();
                            System.out.print("Quantity: ");
                            int qty = scanner.nextInt();
                            manager.addProduct(new Product(id, name, LocalDate.now(), qty, price));
                            break;
                        case 2:
                            manager.displayProducts();
                            break;
                        case 3:
                            System.out.print("Product ID to delete: ");
                            String delId = scanner.nextLine();
                            manager.deleteProduct(delId);
                            break;
                        case 4:
                            System.out.print("Product ID: ");
                            String actId = scanner.nextLine();
                            System.out.print("Activity Name (AddToStock/RemoveFromStock): ");
                            String actName = scanner.nextLine();
                            System.out.print("Quantity: ");
                            int actQty = scanner.nextInt();
                            manager.updateActivity(actId, new Activity("ACT" + System.currentTimeMillis(), actName, actQty, LocalDate.now()));
                            break;
                        case 5:
                            System.out.print("Product ID: ");
                            String dispId = scanner.nextLine();
                            manager.displaySortedActivities(dispId);
                            break;
                        case 6:
                            System.exit(0);
                        default:
                            System.out.println("Invalid choice.");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }
}
