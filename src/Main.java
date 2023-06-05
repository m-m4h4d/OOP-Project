import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Main extends JFrame implements ActionListener
{
    private JButton pos, inventory;
    public Main()
    {
        super("Super Soft");
        // Calling all constructors
        setLayout(new FlowLayout());
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pos = new JButton("POS");
        inventory = new JButton("Inventory");
        add(pos);
        add(inventory);
        pos.addActionListener(this);
        inventory.addActionListener(this);
        pack();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pos)
            new POS();
        else if(e.getSource() == inventory)
            new InventoryManagementSystem();
    }
    public static void main(String[] args)
    {
        new Main();
    }
}