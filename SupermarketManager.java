/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.supermarketmanager;

/**
 * Veer Manoram 
 * @author User
 */
import java.util.ArrayList; // Java Collections Framework
import java.util.List;

public class SupermarketManager {
    private List<Product> products; // Using ArrayList from Java Collections

    // Clears all input fields after successful operations
    public SupermarketManager() {
        products = new ArrayList<>();
    }

    /**
    Adds a new product after checking that its ID is unique
    Uses linear search for uniqueness check
    */
    
    public void addProduct(Product product) {
        // Check for unique Product ID using linear search
        for (Product p : products) {
            if (p.getProductID().equals(product.getProductID())) {
                throw new IllegalArgumentException("Product ID must be unique.");
            }
        }
        products.add(product);
    }

    /**
    Displays all products currently in the system (console output)
    Used primarily by the console version
    */
    
    public void displayProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (Product p : products) {
                System.out.println(p);
            }
        }
    }

    /**
    Deletes a product by ID using linear search and removal
    Throws exception if not found
    */
    
    public boolean deleteProduct(String productID) {
        // Custom linear search to find and delete
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductID().equals(productID)) {
                products.remove(i);
                return true;
            }
        }
        throw new IllegalArgumentException("Product not found.");
    }

    /**
    Applies an activity (add/remove stock) to a specific product
    Delegates to the Product's addActivity method
    */
   
    public void updateActivity(String productID, Activity activity) {
        Product product = findProduct(productID);
        if (product == null) throw new IllegalArgumentException("Product not found.");
        product.addActivity(activity);
    }

    /**
    Applies an activity (add/remove stock) to a specific product
    Delegates to the Product's addActivity method
    */
    
    public void displaySortedActivities(String productID) {
        Product product = findProduct(productID);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        Object[] activities = product.getActivities().toArray();
        // Custom bubble sort by quantity
        for (int i = 0; i < activities.length - 1; i++) {
            for (int j = 0; j < activities.length - i - 1; j++) {
                Activity a1 = (Activity) activities[j];
                Activity a2 = (Activity) activities[j + 1];
                if (a1.getQuantity() > a2.getQuantity()) {
                    Object temp = activities[j];
                    activities[j] = activities[j + 1];
                    activities[j + 1] = temp;  // Fixed typo: was 'acts[j + 1] = temp;'
                }
            }
        }
        for (Object act : activities) {
            System.out.println(act);
        }
    }

    /**
    Helper method to find a product by ID using linear search
    Returns null if not found. Used internally by other methods
    */
    public Product findProduct(String productID) {
        for (Product p : products) {
            if (p.getProductID().equals(productID)) {
                return p;
            }
        }
        return null;
    }

    // Getter used by the GUI to access the full product list for display
    public List<Product> getProducts() {
        return products;
    }
}