import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import com.opencsv.*;

public class InventoryManagementSystem extends JFrame implements ActionListener {
    private JLabel titleLabel, nameLabel, quantityLabel;
    private JTextField nameField, quantityField;
    private JButton addButton, removeButton;
    private JTextArea inventoryArea;
    private String inventory = "";

    public InventoryManagementSystem() {
        setTitle("Inventory Management System");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
        writeDataLineByLine("data.csv");
    }

    public void writeDataLineByLine(String filePath)
    {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(filePath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = {"Item", "Quantity", "Price"};
            writer.writeNext(header);

            // add data to csv
            String[] data1 = { "Aman", "10", "620" };
            writer.writeNext(data1);
            String[] data2 = { "Suraj", "10", "630" };
            writer.writeNext(data2);

            // closing writer connection
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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