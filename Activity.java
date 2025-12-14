/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.supermarketmanager;

/**
 * Mascyen Mascarenhas
 * @author User
 */
import java.time.LocalDate;

public class Activity {
    private String activityID;
    private String name;
    private int quantity;
    private LocalDate date;

     /**
    Constructor for creating an activity record
     Validates that quantity is non-negative
    */         
             
    public Activity(String activityID, String name, int quantity, LocalDate date) {
        if (quantity < 0) throw new IllegalArgumentException("Activity quantity cannot be negative.");
        this.activityID = activityID;
        this.name = name;
        this.quantity = quantity;
        this.date = date;
    }

    // Getters - provide read access to activity details
    public String getActivityID() { return activityID; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public LocalDate getDate() { return date; }

    // Returns a formatted string for display purposes
    @Override
    public String toString() {
        return "Activity ID: " + activityID + ", Name: " + name + ", Qty: " + quantity + ", Date: " + date;
    }
}
