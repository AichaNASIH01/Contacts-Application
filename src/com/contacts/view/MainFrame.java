package com.contacts.view;

import com.contacts.model.Contact;
import com.contacts.repository.ContactDAO;
import com.contacts.repository.ContactDAOImpl;
import com.contacts.DataBase.DataBase;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

public class MainFrame {

    private JFrame mainFrame;
    private JTable contactsTable;
    private JTextField searchField;
    private ContactDAO contactDAO;

    public MainFrame() {
        contactDAO = new ContactDAOImpl(DataBase.getDBInstance(DataBase.MYSQL));
        // Paths for the icons (Use relative paths or load as resources)
        String iconPath = "C:\\Users\\Aicha\\Documents\\Projects\\New one\\Contacts\\src\\img.png";
        String labelPath = "src/img2.png";

        // Loading icons
        ImageIcon appIcon = new ImageIcon(iconPath);

        mainFrame = new JFrame("Contacts");
        mainFrame.setSize(800, 600);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setBackground(new Color(238, 237, 235));
        mainFrame.setIconImage(appIcon.getImage());
        mainFrame.getContentPane().setBackground(new Color(238, 237, 235));
        mainFrame.setTitle("Contacts");

        JPanel topPanel = createTopPanel();
        JScrollPane tablePanel = createTablePanel();
        JPanel bottomPanel = createBottomPanel();
        topPanel.setBackground(new Color(147, 145, 133));

        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(tablePanel, BorderLayout.CENTER);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);

        mainFrame.setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        // Create a custom rounded border
        Border roundedBorder = BorderFactory.createLineBorder(new Color(47, 54, 69), 1, true);

        // Search bar
        searchField = new JTextField();
        searchField.setText("Search...");
        searchField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        searchField.setForeground(Color.GRAY);
        searchField.setBorder(roundedBorder);
        searchField.setPreferredSize(new Dimension(150, 25)); // Set preferred size to make it smaller
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("Search...");
                }
            }
        });

        GradientButton1 searchButton = new GradientButton1("Search", new Color(230, 185, 166), new Color(230, 185, 166).darker());
        GradientButton1 refreshButton = new GradientButton1("", new Color(230, 185, 166), new Color(230, 185, 166).darker());
        searchButton.setForeground(new Color(47, 54, 69));
        searchButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        searchButton.setBorder(roundedBorder);
        refreshButton.setForeground(new Color(47, 54, 69));
        refreshButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        refreshButton.setBorder(roundedBorder);

        ImageIcon refIcon = new ImageIcon("C:\\Users\\Aicha\\Documents\\Projects\\New one\\Contacts\\src\\refresh.png");
        refreshButton.setIcon(refIcon);
        searchButton.addActionListener(e -> searchContacts());
        refreshButton.addActionListener(e -> loadContacts());

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(refreshButton, BorderLayout.EAST); // Add the refresh button to the top panel

        return topPanel;
    }

    private JScrollPane createTablePanel() {
        contactsTable = new JTable(new DefaultTableModel(
                new Object[]{"ID", "Name", "Phone", "Email", "Action"}, 0
        )) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the "Action" column is editable
                return column == 4;
            }
        };

        contactsTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        contactsTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

        // Hide the ID column immediately after table creation
        contactsTable.getColumnModel().getColumn(0).setMinWidth(0);
        contactsTable.getColumnModel().getColumn(0).setMaxWidth(0);
        contactsTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        contactsTable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));

        loadContacts();

        return new JScrollPane(contactsTable);
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();

        GradientButton1 addContactButton = new GradientButton1("Add Contact", new Color(230, 185, 166), new Color(230, 185, 166).darker());
        addContactButton.addActionListener(e -> new AddContactFrame());
        addContactButton.setForeground(new Color(47, 54, 69));
        addContactButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        addContactButton.setBorder(BorderFactory.createLineBorder(new Color(47, 54, 69), 1, true));

        bottomPanel.add(addContactButton);

        return bottomPanel;
    }

    private void loadContacts() {
        List<Contact> contacts = contactDAO.getContacts();
        DefaultTableModel model = (DefaultTableModel) contactsTable.getModel();
        model.setRowCount(0); // Clear existing data

        for (Contact contact : contacts) {
            // Add contact ID (hidden), Name, Phone, Email, and "Show Info" button
            model.addRow(new Object[]{contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail(), "Show Info"});
        }

        // Hide the contact ID column
        contactsTable.getColumnModel().getColumn(0).setMinWidth(0);
        contactsTable.getColumnModel().getColumn(0).setMaxWidth(0);
        contactsTable.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    private void searchContacts() {
        String keyword = searchField.getText();
        searchField.setText("Search");
        searchField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        searchField.setPreferredSize(new Dimension(10, 25));

        List<Contact> contacts = contactDAO.searchContacts(keyword);
        DefaultTableModel model = (DefaultTableModel) contactsTable.getModel();
        model.setRowCount(0); // Clear existing data

        for (Contact contact : contacts) {
            model.addRow(new Object[]{contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail(), "Show Info"});
        }

        // Hide the contact ID column
        contactsTable.getColumnModel().getColumn(0).setMinWidth(0);
        contactsTable.getColumnModel().getColumn(0).setMaxWidth(0);
        contactsTable.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}

class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
        setBackground(new Color(230, 185, 166));
        setForeground(new Color(47, 54, 69));
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        setBorder(BorderFactory.createLineBorder(new Color(47, 54, 69), 1, true));
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private String contactPhone; // Variable to store contact ID

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.setBackground(new Color(230, 185, 166));
        button.setForeground(new Color(47, 54, 69));
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        button.setBorder(BorderFactory.createLineBorder(new Color(47, 54, 69), 1, true));
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;

        contactPhone = (String) table.getModel().getValueAt(row, 3);

        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            SwingUtilities.invokeLater(() -> new ShowContactsFrame(contactPhone));
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}

class GradientButton extends JButton {

    private Color startColor;
    private Color endColor;

    public GradientButton(String text, Color startColor, Color endColor) {
        super(text);
        this.startColor = startColor;
        this.endColor = endColor;
        setContentAreaFilled(false); // Disable default button background
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();

        // Draw the gradient background
        GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, 0, height, endColor);
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();

        super.paintComponent(g);
    }
}