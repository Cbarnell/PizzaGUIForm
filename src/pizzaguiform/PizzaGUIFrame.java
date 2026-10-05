package pizzaguiform;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main GUI frame for the Pizza Order form, handling crust selection, 
 * size options, toppings, receipt formatting, and validation.
 */
public class PizzaGUIFrame extends JFrame {

    // Crust Radio Buttons
    private JRadioButton rbThin;
    private JRadioButton rbRegular;
    private JRadioButton rbDeepDish;
    private ButtonGroup crustGroup;

    // Size ComboBox
    private JComboBox<String> cbSize;

    // Toppings Checkboxes (Monster theme)
    private JCheckBox chkTentacles;
    private JCheckBox chkEyeballs;
    private JCheckBox chkDragonScales;
    private JCheckBox chkGorgonVenom;
    private JCheckBox chkZombieFlesh;
    private JCheckBox chkLavaRocks;

    // Receipt Text Area
    private JTextArea taReceipt;

    // Prices
    private static final double PRICE_SMALL = 8.00;
    private static final double PRICE_MEDIUM = 12.00;
    private static final double PRICE_LARGE = 16.00;
    private static final double PRICE_SUPER = 20.00;
    private static final double PRICE_TOPPING = 1.00;
    private static final double TAX_RATE = 0.07;

    /**
     * Constructs and initializes the Pizza GUI Form.
     */
    public PizzaGUIFrame() {
        setTitle("Monster Pizza Order Form");
        setSize(700, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Build UI Layout Sections
        add(createNorthPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createSouthPanel(), BorderLayout.SOUTH);
    }

    /**
     * Creates the North panel containing Title and Size selection.
     */
    private JPanel createNorthPanel() {
        JPanel northPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        // Title Label
        JLabel lblTitle = new JLabel("Monster Pizza Ordering System", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        northPanel.add(lblTitle);

        // Size Panel (ComboBox)
        JPanel sizePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sizePanel.setBorder(new TitledBorder("Pizza Size"));

        String[] sizes = {"Small ($8.00)", "Medium ($12.00)", "Large ($16.00)", "Super ($20.00)"};
        cbSize = new JComboBox<>(sizes);
        sizePanel.add(cbSize);

        northPanel.add(sizePanel);
        return northPanel;
    }

    /**
     * Creates the Center panel featuring Crust options and Toppings arranged side-by-side.
     */
    private JPanel createCenterPanel() {
        JPanel centerPanel1 = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // 1. Crust Panel (Radio Buttons)
        JPanel crustPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        crustPanel.setBorder(new TitledBorder("Type of Crust"));

        rbThin = new JRadioButton("Thin Crust");
        rbRegular = new JRadioButton("Regular Crust");
        rbDeepDish = new JRadioButton("Deep-Dish Crust");

        crustGroup = new ButtonGroup();
        crustGroup.add(rbThin);
        crustGroup.add(rbRegular);
        crustGroup.add(rbDeepDish);

        crustPanel.add(rbThin);
        crustPanel.add(rbRegular);
        crustPanel.add(rbDeepDish);

        // 2. Toppings Panel (Checkboxes)
        JPanel toppingsPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        toppingsPanel.setBorder(new TitledBorder("Monster Toppings ($1.00 each)"));

        chkTentacles = new JCheckBox("Kraken Tentacles");
        chkEyeballs = new JCheckBox("Basilisk Eyeballs");
        chkDragonScales = new JCheckBox("Dragon Scales");
        chkGorgonVenom = new JCheckBox("Gorgon Venom");
        chkZombieFlesh = new JCheckBox("Zombie Flesh");
        chkLavaRocks = new JCheckBox("Lava Rocks");

        toppingsPanel.add(chkTentacles);
        toppingsPanel.add(chkEyeballs);
        toppingsPanel.add(chkDragonScales);
        toppingsPanel.add(chkGorgonVenom);
        toppingsPanel.add(chkZombieFlesh);
        toppingsPanel.add(chkLavaRocks);

        centerPanel1.add(crustPanel);
        centerPanel1.add(toppingsPanel);

        return centerPanel1;
    }

    /**
     * Creates the South panel containing the Receipt text area and Action buttons.
     */
    private JPanel createSouthPanel() {
        JPanel southPanel = new JPanel(new BorderLayout(10, 10));
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        // Receipt Text Area with ScrollPane
        taReceipt = new JTextArea(14, 50);
        taReceipt.setEditable(false);
        taReceipt.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(taReceipt);
        scrollPane.setBorder(new TitledBorder("Order Receipt"));
        southPanel.add(scrollPane, BorderLayout.CENTER);

        // Button Panel (Order, Clear, Quit)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnOrder = new JButton("Order");
        JButton btnClear = new JButton("Clear");
        JButton btnQuit = new JButton("Quit");

        btnOrder.addActionListener(new OrderButtonListener());
        btnClear.addActionListener(_ -> clearForm());
        btnQuit.addActionListener(_ -> confirmQuit());

        buttonPanel.add(btnOrder);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnQuit);

        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        return southPanel;
    }

    /**
     * Action listener handling the Order computation and receipt generation.
     */
    private class OrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            // Validation: Must select a crust
            String crustType = "";
            if (rbThin.isSelected()) crustType = "Thin Crust";
            else if (rbRegular.isSelected()) crustType = "Regular Crust";
            else if (rbDeepDish.isSelected()) crustType = "Deep-Dish Crust";

            if (crustType.isEmpty()) {
                JOptionPane.showMessageDialog(PizzaGUIFrame.this,
                        "Please select a type of crust.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validation: Must select at least one topping
            boolean hasTopping = chkTentacles.isSelected() || chkEyeballs.isSelected() ||
                    chkDragonScales.isSelected() || chkGorgonVenom.isSelected() ||
                    chkZombieFlesh.isSelected() || chkLavaRocks.isSelected();
            if (!hasTopping) {
                JOptionPane.showMessageDialog(PizzaGUIFrame.this,
                        "Please select at least one topping.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calculate Size & Base Price
            int sizeIndex = cbSize.getSelectedIndex();
            String sizeName;
            double basePrice;
            switch (sizeIndex) {
                case 0 -> {
                    sizeName = "Small";
                    basePrice = PRICE_SMALL;
                }
                case 1 -> {
                    sizeName = "Medium";
                    basePrice = PRICE_MEDIUM;
                }
                case 2 -> {
                    sizeName = "Large";
                    basePrice = PRICE_LARGE;
                }
                case 3 -> {
                    sizeName = "Super";
                    basePrice = PRICE_SUPER;
                }
                default -> {
                    JOptionPane.showMessageDialog(PizzaGUIFrame.this,
                            "Please select a valid pizza size.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Build Receipt Output
            StringBuilder sb = new StringBuilder();
            sb.append("=========================================\n");
            sb.append(String.format("%-35s %s\n", "Type of Crust & Size", "Price"));
            sb.append("=========================================\n");
            sb.append(String.format("%-35s $%6.2f\n", crustType + " (" + sizeName + ")", basePrice));
            sb.append("\n");

            double toppingsTotal = 0.0;
            if (chkTentacles.isSelected()) {
                toppingsTotal += PRICE_TOPPING;
                sb.append(String.format("%-35s $%6.2f\n", "  - Kraken Tentacles", PRICE_TOPPING));
            }
            if (chkEyeballs.isSelected()) {
                toppingsTotal += PRICE_TOPPING;
                sb.append(String.format("%-35s $%6.2f\n", "  - Basilisk Eyeballs", PRICE_TOPPING));
            }
            if (chkDragonScales.isSelected()) {
                toppingsTotal += PRICE_TOPPING;
                sb.append(String.format("%-35s $%6.2f\n", "  - Dragon Scales", PRICE_TOPPING));
            }
            if (chkGorgonVenom.isSelected()) {
                toppingsTotal += PRICE_TOPPING;
                sb.append(String.format("%-35s $%6.2f\n", "  - Gorgon Venom", PRICE_TOPPING));
            }
            if (chkZombieFlesh.isSelected()) {
                toppingsTotal += PRICE_TOPPING;
                sb.append(String.format("%-35s $%6.2f\n", "  - Zombie Flesh", PRICE_TOPPING));
            }
            if (chkLavaRocks.isSelected()) {
                toppingsTotal += PRICE_TOPPING;
                sb.append(String.format("%-35s $%6.2f\n", "  - Lava Rocks", PRICE_TOPPING));
            }

            double subTotal = basePrice + toppingsTotal;
            double tax = subTotal * TAX_RATE;
            double total = subTotal + tax;

            sb.append("\n");
            sb.append(String.format("Sub-total:                         $%6.2f\n", subTotal));
            sb.append(String.format("Tax (7%%):                          $%6.2f\n", tax));
            sb.append("---------------------------------------------------------------------\n");
            sb.append(String.format("Total:                             $%6.2f\n", total));
            sb.append("=========================================\n");

            taReceipt.setText(sb.toString());
        }
    }

    /**
     * Clears all form components to reset for a new order.
     */
    private void clearForm() {
        crustGroup.clearSelection();
        cbSize.setSelectedIndex(0);
        chkTentacles.setSelected(false);
        chkEyeballs.setSelected(false);
        chkDragonScales.setSelected(false);
        chkGorgonVenom.setSelected(false);
        chkZombieFlesh.setSelected(false);
        chkLavaRocks.setSelected(false);
        taReceipt.setText("");
    }

    /**
     * Prompts the user with a confirmation dialog before quitting.
     */
    private void confirmQuit() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to quit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}