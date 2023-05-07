import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class POSSystem extends JFrame implements ActionListener {
    private JLabel productLabel, priceLabel, quantityLabel, subtotalLabel, totalLabel;
    private JTextField productField, priceField, quantityField, subtotalField, totalField;
    private JButton addButton, removeButton, checkoutButton;
    private JTextArea cartArea;
    private String cart = "";
    private double subtotal = 0;
    private double total = 0;

    public POSSystem() {
        super("Point of Sale System");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        productLabel = new JLabel("Product:");
        productField = new JTextField(20);
        priceLabel = new JLabel("Price:");
        priceField = new JTextField(10);
        quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField(10);
        addButton = new JButton("Add to Cart");
        removeButton = new JButton("Remove from Cart");
        checkoutButton = new JButton("Checkout");
        cartArea = new JTextArea(10, 30);
        cartArea.setEditable(false);
        subtotalLabel = new JLabel("Subtotal:");
        subtotalField = new JTextField(10);
        subtotalField.setEditable(false);
        totalLabel = new JLabel("Total:");
        totalField = new JTextField(10);
        totalField.setEditable(false);

        // Add components to JFrame
        add(productLabel);
        add(productField);
        add(priceLabel);
        add(priceField);
        add(quantityLabel);
        add(quantityField);
        add(addButton);
        add(removeButton);
        add(checkoutButton);
        add(cartArea);
        add(subtotalLabel);
        add(subtotalField);
        add(totalLabel);
        add(totalField);

        // Add ActionListener to buttons
        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        checkoutButton.addActionListener(this);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String product = productField.getText();
        String priceString = priceField.getText();
        String quantityString = quantityField.getText();
        double price = 0;
        int quantity = 0;

        // Check if price is a valid double
        try {
            price = Double.parseDouble(priceString);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if quantity is a valid integer
        try {
            quantity = Integer.parseInt(quantityString);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity must be an integer.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (e.getSource() == addButton) {
            // Add item to cart
            cart += product + "\t" + price + "\t" + quantity + "\n";
            cartArea.setText(cart);
            subtotal += price * quantity;
            total = subtotal * 1.05; // 5% tax
            subtotalField.setText(String.format("%.2f", subtotal));
            totalField.setText(String.format("%.2f", total));
            productField.setText("");
            priceField.setText("0");
            quantityField.setText("0");
        } else if (e.getSource() == removeButton) {
            // Remove item from cart
            if (cart.contains(product)) {
                int index = cart.indexOf(product);
                int endOfLine = cart.indexOf("\n", index);
                String item = cart.substring(index, endOfLine + 1);
                cart = cart.replace(item, "");
                cartArea.setText(cart);
                subtotal -= price * quantity;
                total = subtotal * 1.05; // 5% tax
                subtotalField.setText(String.format("%.2f", subtotal));
                totalField.setText(String.format("%.2f", total));
                productField.setText("");
                priceField.setText("");
                quantityField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Item not found in cart.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == checkoutButton) {
            // Display receipt and reset cart
            String receipt = "Receipt:\n" + cart + "Subtotal:\t" + subtotal + "\nTax:\t5%\nTotal:\t" + total;
            JOptionPane.showMessageDialog(this, receipt, "Receipt", JOptionPane.PLAIN_MESSAGE);
            cart = "";
            cartArea.setText(cart);
            subtotal = 0;
            total = 0;
            subtotalField.setText("");
            totalField.setText("");
        }
    }
    
    public static void main(String[] args) {
        new POSSystem();
    }
}    
