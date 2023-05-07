import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class InventoryManagementSystem extends JFrame implements ActionListener {
    private JLabel titleLabel, nameLabel, quantityLabel;
    private JTextField nameField, quantityField;
    private JButton addButton, removeButton;
    private JTextArea inventoryArea;
    private String inventory = "";

    public InventoryManagementSystem() {
        setTitle("Inventory Management System");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        titleLabel = new JLabel("Inventory Management System");
        nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField(10);
        addButton = new JButton("Add to Inventory");
        removeButton = new JButton("Remove from Inventory");
        inventoryArea = new JTextArea(10, 30);
        inventoryArea.setEditable(false);

        // Add components to JFrame
        add(titleLabel);
        add(nameLabel);
        add(nameField);
        add(quantityLabel);
        add(quantityField);
        add(addButton);
        add(removeButton);
        add(inventoryArea);

        // Add ActionListener to buttons
        addButton.addActionListener(this);
        removeButton.addActionListener(this);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String quantityString = quantityField.getText();
        int quantity = 0;

        // Check if quantity is a valid integer
        try {
            quantity = Integer.parseInt(quantityString);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity must be an integer.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (e.getSource() == addButton) {
            // Add item to inventory
            inventory += name + ": " + quantity + "\n";
            inventoryArea.setText(inventory);
            nameField.setText("");
            quantityField.setText("");
        } else if (e.getSource() == removeButton) {
            // Remove item from inventory
            if (inventory.contains(name)) {
                int index = inventory.indexOf(name);
                int endOfLine = inventory.indexOf("\n", index);
                inventory = inventory.substring(0, index) + inventory.substring(endOfLine + 1);
                inventoryArea.setText(inventory);
                nameField.setText("");
                quantityField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Item not found in inventory.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}