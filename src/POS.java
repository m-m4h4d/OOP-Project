import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class POS extends JFrame implements ActionListener {
    private JTextField productField, priceField, quantityField, subtotalField, totalField;
    private JTextArea cartArea, inventoryArea;
    private double subtotal, total;
    private String cart;
    private HashMap<String, Integer> inventory;
    private JButton addButton, removeButton, checkoutButton;

    public POS() {
        super("Point of Sale System");

        // Set up GUI components
        JLabel productLabel = new JLabel("Product:");
        productField = new JTextField(20);
        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField(5);
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField(3);
        addButton = new JButton("Add to Cart");
        addButton.addActionListener(this);
        removeButton = new JButton("Remove from Cart");
        removeButton.addActionListener(this);
        checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(this);
        JLabel cartLabel = new JLabel("Cart:");
        cartArea = new JTextArea(10, 30);
        cartArea.setEditable(false);
        JScrollPane cartScrollPane = new JScrollPane(cartArea);
        JLabel inventoryLabel = new JLabel("Inventory:");
        inventoryArea = new JTextArea(10, 30);
        inventoryArea.setEditable(false);
        JScrollPane inventoryScrollPane = new JScrollPane(inventoryArea);
        JLabel subtotalLabel = new JLabel("Subtotal:");
        subtotalField = new JTextField(8);
        subtotalField.setEditable(false);
        JLabel totalLabel = new JLabel("Total:");
        totalField = new JTextField(8);
        totalField.setEditable(false);

        // Set up main window layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        inputPanel.add(productLabel);
        inputPanel.add(priceLabel);
        inputPanel.add(quantityLabel);
        inputPanel.add(productField);
        inputPanel.add(priceField);
        inputPanel.add(quantityField);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        inputPanel.add(checkoutButton);
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BorderLayout());
        cartPanel.add(cartLabel, BorderLayout.NORTH);
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);
        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new BorderLayout());
        inventoryPanel.add(inventoryLabel, BorderLayout.NORTH);
        inventoryPanel.add(inventoryScrollPane, BorderLayout.CENTER);
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new GridLayout(2, 2));
        totalPanel.add(subtotalLabel);
        totalPanel.add(subtotalField);
        totalPanel.add(totalLabel);
        totalPanel.add(totalField);
        mainPanel.add(inputPanel);
        mainPanel.add(cartPanel);
        mainPanel.add(inventoryPanel);
        add(mainPanel, BorderLayout.CENTER);
        add(totalPanel, BorderLayout.SOUTH);

        // Initialize cart and inventory
        cart = "";
        inventory = new HashMap<String, Integer>();
        inventory.put("Item A", 10);
        inventory.put("Item B", 20);
        inventory.put("Item C", 30);
        inventory.put("Item D", 40);
        inventory.put("Item E", 50);

        // Update inventory display
        updateInventory();

        // Set up window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            // Add item to cart and update totals
            String item = productField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            if (inventory.containsKey(item)) {
                int stock = inventory.get(item);
                if (quantity <= stock) {
                    inventory.put(item, stock - quantity);
                    double price = Double.parseDouble(priceField.getText());
                    subtotal += price * quantity;
                    total = subtotal * 1.06;  // 6% sales tax
                    cart += String.format("%s\tx%d\t$%.2f\n", item, quantity, price);
                    subtotalField.setText(String.format("%.2f", subtotal));
                    totalField.setText(String.format("%.2f", total));
                    cartArea.setText(cart);
                    updateInventory();
                } else {
                    JOptionPane.showMessageDialog(this, "Not enough stock!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Item not found!");
            }
            productField.setText("");
            priceField.setText("");
            quantityField.setText("");
        } else if (e.getSource() == removeButton) {
            // Remove item from cart and update totals
            String item = productField.getText();
            String[] lines = cart.split("\n");
            cart = "";
            for (String line : lines) {
                if (line.startsWith(item)) {
                    String[] parts = line.split(" ");
                    int quantity = Integer.parseInt(parts[1].substring(1));
                    double price = Double.parseDouble(parts[2].substring(1));
                    subtotal -= price * quantity;
                } else {
                    cart += line + "\n";
                }
            }
            cartArea.setText(cart);
            updateInventory();
            productField.setText("");
            priceField.setText("");
            quantityField.setText("");
        } else if (e.getSource() == checkoutButton) {
            // Calculate total and print receipt
            String receipt = String.format("Subtotal: $%.2f\n", subtotal)
                    + String.format("Tax: $%.2f\n", total - subtotal)
                    + String.format("Total: $%.2f\n", total);
            JOptionPane.showMessageDialog(this, receipt);
            cart = "";
            cartArea.setText(cart);
            subtotal = 0;
            total = 0;
            updateInventory();
        }
    }
    
    private void updateInventory() {
        // Update inventory display
        inventoryArea.setText("");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            String item = entry.getKey();
            int stock = entry.getValue();
            inventoryArea.append(String.format("%s x%d\n", item, stock));
        }
    }
    
    public static void main(String[] args) {
        new POS();
    }
}    