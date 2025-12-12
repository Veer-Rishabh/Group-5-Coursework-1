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

public class Product {
    private String productID;
    private String name;
    private LocalDate entryDate;
    private int quantity;
    private double price;
    private CustomQueue<Activity> activities;
    
    /**
    Constructor for creating a new product
    Validates that quantity and price are non-negative
    Initializes an empty activity queue to track stock changes
    */
    
    public Product(String productID, String name, LocalDate entryDate, int quantity, double price) {
        if (quantity < 0) throw new IllegalArgumentException("Product quantity cannot be negative.");
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative.");
        this.productID = productID;
        this.name = name;
        this.entryDate = entryDate;
        this.quantity = quantity;
        this.price = price;
        this.activities = new CustomQueue<>();
    }

    /**
    Adds a new activity (Add or Remove) and updates current stock quantity accordingly
    Enforces stock rules (cannot remove more than available)
    Limits history to the last 4 activities by dequeuing oldest ones
    */
    
    public void addActivity(Activity activity) {
        if (activity.getName().equals("Add")) {
            quantity += activity.getQuantity();
        } else if (activity.getName().equals("Remove")) {
            if (quantity < activity.getQuantity()) throw new IllegalArgumentException("Cannot remove more than available stock.");
            quantity -= activity.getQuantity();
        }
        activities.enqueue(activity);
        // Limit to last 4 activities
        while (activities.size() > 4) {
            activities.dequeue(); // Remove oldest
        }
    }

    // Getters - provide read-only access to product fields
    public String getProductID() { return productID; }
    public String getName() { return name; }
    public LocalDate getEntryDate() { return entryDate; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public CustomQueue<Activity> getActivities() { return activities; }

    /**
    Returns a formatted string representation of the product
    Used for display purposes in both console and GUI
    */
    
    @Override
    public String toString() {
        return "ID: " + productID + ", Name: " + name + ", Entry Date: " + entryDate + ", Qty: " + quantity + ", Price: " + price;
    }
}
