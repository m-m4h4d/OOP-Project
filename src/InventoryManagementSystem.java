import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import com.opencsv.*;

public class InventoryManagementSystem extends JFrame implements ActionListener {
    private JLabel nameLabel, quantityLabel, priceLabel;
    private JTextField nameField, quantityField, priceField;
    private JButton addButton, removeButton;
    private JTextArea inventoryArea;
    private String inventory = "";

    public InventoryManagementSystem() {
        setTitle("Inventory Management System");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create components
        nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField(10);
        priceLabel = new JLabel("Price:");
        priceField = new JTextField(20);
        addButton = new JButton("Add to Inventory");
        removeButton = new JButton("Remove from Inventory");
        inventoryArea = new JTextArea(10, 30);
        inventoryArea.setEditable(false);

        // Add components to JFrame
        add(nameLabel);
        add(nameField);
        add(quantityLabel);
        add(quantityField);
        add(priceLabel);
        add(priceField);
        add(addButton);
        add(removeButton);
        add(inventoryArea);

        // Add ActionListener to buttons
        addButton.addActionListener(this);
        removeButton.addActionListener(this);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        startInventory("data.csv");
    }

    public void updateInventory(String[] data)
    {
        try {
            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(new FileWriter("data.csv", true));

            // add data to csv
            writer.writeNext(data);

            // closing writer connection
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startInventory(String file)
    {
        try {
            // create csvReader object
            CSVReader csvReader = new CSVReader(new FileReader(file));
            String[] data;

            // print Data
            while ((data = csvReader.readNext()) != null) {
                inventory += data[0] + ":\t\tx" + data[1] + "\tRs. " + data[2] + "\n";
                inventoryArea.setText(inventory);
            }
            csvReader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeInventory(String name)
    {
        int rowNumber = -1;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data.csv"));
            String string;
            while ((string = reader.readLine()) != null) {
                if (name.equals(string.substring(0, 5)))
                    break;
                rowNumber++;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            CSVReader reader2 = new CSVReader(new FileReader("data.csv"));
            List<String[]> allElements = reader2.readAll();
            allElements.remove(rowNumber);
            FileWriter sw = new FileWriter("data.csv");
            CSVWriter writer = new CSVWriter(sw);
            writer.writeAll(allElements);
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String quantityString = quantityField.getText();
        String priceString = priceField.getText();
        int quantity = 0;
        int price = 0;

        if (e.getSource() == addButton) {

            // Check if quantity is a valid integer
            try {
                quantity = Integer.parseInt(quantityString);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantity must be an integer.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if price is a valid integer
            try {
                price = Integer.parseInt(priceString);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Price must be an integer.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add item to inventory
            inventory += name + ":\t\tx" + quantity + "\tRs. " + price + "\n";
            inventoryArea.setText(inventory);
            nameField.setText("");
            quantityField.setText("");
            priceField.setText("");
            String[] data = {name, Integer.toString(quantity), Integer.toString(price)};
            updateInventory(data);
        } else if (e.getSource() == removeButton) {
            // Remove item from inventory
            if (inventory.contains(name)) {
                int index = inventory.indexOf(name);
                int endOfLine = inventory.indexOf("\n", index);
                inventory = inventory.substring(0, index) + inventory.substring(endOfLine + 1);
                inventoryArea.setText(inventory);
                removeInventory(name);
                nameField.setText("");
                quantityField.setText("");
                priceField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Item not found in inventory.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}