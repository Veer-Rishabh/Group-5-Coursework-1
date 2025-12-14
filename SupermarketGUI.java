/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.supermarketmanager;

/**
 * Mascyen Mascarenhas
 * @author User
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

//existing imports and class declaration

public class SupermarketGUI extends JFrame {
    //fields
    private SupermarketManager manager;
    private JTextField productIDField, nameField, priceField, quantityField;
    private JTextArea outputArea;

    /**
    Applies a global font to all Swing components
    Used to increase readability with larger text throughout the entire GUI
    */
    
    public static void setGlobalFont(Font f) {
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof Font) {
                UIManager.put(key, f);
            }
        }
    }

    // Constructor - initializes the GUI components and layout using the provided manager
    public SupermarketGUI(SupermarketManager manager) {
        this.manager = manager;
        initializeUI();
    }

    /**
    Main method to launch the GUI application
    Sets a large global font and creates the main window on the Event Dispatch Thread
    */
    
    public static void main(String[] args) {

        setGlobalFont(new Font("Segoe UI", Font.PLAIN, 20));  // Change size here if needed

        SwingUtilities.invokeLater(() -> new SupermarketGUI(new SupermarketManager()).setVisible(true));
    }

    /**
    Sets up the entire user interface: title, input fields, buttons, and output area
    Uses BorderLayout with GridBagLayout for input alignment and larger components for usability
    */
    
    private void initializeUI() {
        setTitle("Supermarket Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Group 5 Supermarket Management System", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26)); // Slightly boosted manually
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels and fields
        String[] labels = {"Product ID:", "Name:", "Price:", "Quantity:"};
        JTextField[] fields = {productIDField = new JTextField(12),
                               nameField = new JTextField(20),
                               priceField = new JTextField(10),
                               quantityField = new JTextField(8)};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.anchor = GridBagConstraints.EAST;
            inputPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 1.0;
            fields[i].setMaximumSize(fields[i].getPreferredSize());
            inputPanel.add(fields[i], gbc);
        }

        add(inputPanel, BorderLayout.CENTER);

        // Buttons + Output 
        JPanel southPanel = new JPanel(new BorderLayout());

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        String[] buttonTexts = {
            "Add Product", "Delete Product", "Display All Products",
            "Update Activity", "Display Sorted Activities"
        };

        ActionListener[] listeners = {
            new AddProductListener(),
            new DeleteProductListener(),
            new DisplayProductsListener(),
            new UpdateActivityListener(),
            new DisplaySortedActivitiesListener()
        };

        for (int i = 0; i < buttonTexts.length; i++) {
            JButton btn = new JButton(buttonTexts[i]);
            btn.setPreferredSize(new Dimension(250, 40)); // Bigger buttons
            buttonPanel.add(btn);
            btn.addActionListener(listeners[i]);
        }

        southPanel.add(buttonPanel, BorderLayout.NORTH);

        // Output Area
        outputArea = new JTextArea(12, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 18)); // Larger text
        outputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("System Output"));
        southPanel.add(scrollPane, BorderLayout.CENTER);

        add(southPanel, BorderLayout.SOUTH);

        pack();
        setMinimumSize(new Dimension(900, 600));
    }

     /**
    Action Listeners
    Listener for "Add Product" button
    Validates input, creates a Product object, adds it via manager, and shows feedback
    */
    
    private class AddProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String id = productIDField.getText().trim();
                String name = nameField.getText().trim();
                String priceText = priceField.getText().trim();
                String qtyText = quantityField.getText().trim();

                if (id.isEmpty() || name.isEmpty() || priceText.isEmpty() || qtyText.isEmpty()) {
                    showError("All fields are required.");
                    return;
                }

                double price = Double.parseDouble(priceText);
                int quantity = Integer.parseInt(qtyText);

                Product product = new Product(id, name, LocalDate.now(), quantity, price);
                manager.addProduct(product);
                outputArea.append("Product added: " + product + "\n");
                clearFields();
            } catch (NumberFormatException ex) {
                showError("Price and Quantity must be valid numbers.");
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        }
    }
    
    /**
    Listener for "Delete Product" button
    Confirms deletion with dialog and removes product if confirmed
    */
    private class DeleteProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = productIDField.getText().trim();
            if (id.isEmpty()) {
                showError("Enter Product ID to delete.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(SupermarketGUI.this,
                    "Delete product ID: " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    manager.deleteProduct(id);
                    outputArea.append("Deleted product: " + id + "\n");
                    clearFields();
                } catch (IllegalArgumentException ex) {
                    showError(ex.getMessage());
                }
            }
        }
    }

    /**
    Listener for "Display All Products" button
    Clears output and lists all current products
    */
    
    private class DisplayProductsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            outputArea.setText("");
            if (manager.getProducts().isEmpty()) {
                outputArea.append("No products in the system.\n");
            } else {
                for (Product p : manager.getProducts()) {
                    outputArea.append(p.toString() + "\n");
                }
            }
        }
    }

    /**
    Listener for "Update Activity" button
    Shows a dialog to input activity details and applies the change
    */
    
    private class UpdateActivityListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField idField = new JTextField(10);
            JTextField nameField = new JTextField(15);
            JTextField qtyField = new JTextField(8);

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Product ID:"));
            panel.add(idField);
            panel.add(new JLabel("Activity (Add/Remove):"));
            panel.add(nameField);
            panel.add(new JLabel("Quantity:"));
            panel.add(qtyField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Update Activity",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String id = idField.getText().trim();
                    String actName = nameField.getText().trim();
                    int qty = Integer.parseInt(qtyField.getText().trim());

                    manager.updateActivity(id, new Activity("ACT" + System.currentTimeMillis(), actName, qty, LocalDate.now()));
                    outputArea.append("Activity updated for product: " + id + "\n");
                } catch (Exception ex) {
                    showError("Invalid input: " + ex.getMessage());
                }
            }
        }
    }

    /**
    Listener for "Display Sorted Activities" button
    Prompts for product ID, retrieves activities, sorts them by quantity (ascending),
    and displays the sorted list
    */
    
    private class DisplaySortedActivitiesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = JOptionPane.showInputDialog("Enter Product ID:");
            if (id == null || id.trim().isEmpty()) return;

            outputArea.setText("");
            Product p = manager.findProduct(id.trim());
            if (p == null) {
                outputArea.append("Product not found.\n");
                return;
            }

            CustomQueue<Activity> queue = p.getActivities();
            if (queue.isEmpty()) {
                outputArea.append("No activities recorded for this product.\n");
                return;
            }

            Object[] acts = queue.toArray();
            for (int i = 0; i < acts.length - 1; i++) {
                for (int j = 0; j < acts.length - i - 1; j++) {
                    Activity a1 = (Activity) acts[j];
                    Activity a2 = (Activity) acts[j + 1];
                    if (a1.getQuantity() > a2.getQuantity()) {
                        Object temp = acts[j];
                        acts[j] = acts[j + 1];
                        acts[j + 1] = temp;
                    }
                }
            }

            outputArea.append("=== Sorted Activities for " + p.getName() + " (by Quantity) ===\n");
            for (Object act : acts) {
                outputArea.append(act.toString() + "\n");
            }
        }
    }

    
    // Utility method to show error messages in both dialog and output area
    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
        outputArea.append("ERROR: " + msg + "\n");
    }
    
    // Clears all input fields after successful operations
    private void clearFields() {
        productIDField.setText("");
        nameField.setText("");
        priceField.setText("");
        quantityField.setText("");
    }
}
