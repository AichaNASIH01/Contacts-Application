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

/**
 * Main frame of the Contacts application.
 * Displays a list of contacts and allows users to search, add, or view contact details.
 */
public class MainFrame {

    private JFrame mainFrame;
    private JTable contactsTable;
    private JTextField searchField;
    private ContactDAO contactDAO;

    /**
     * Constructor for MainFrame.
     * Initializes the main application window and sets up components.
     */
    public MainFrame() {
        contactDAO = new ContactDAOImpl(DataBase.getDBInstance(DataBase.MYSQL));
        String iconPath = "C:\\Users\\Aicha\\Documents\\Projects\\New one\\Contacts\\src\\img.png";

        // Set up the main frame
        ImageIcon appIcon = new ImageIcon(iconPath);
        mainFrame = new JFrame("Contacts");
        mainFrame.setSize(800, 550);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setIconImage(appIcon.getImage());
        mainFrame.getContentPane().setBackground(new Color(238, 237, 235));
        mainFrame.setTitle("Contacts");

        // Add panels to the main frame
        JPanel topPanel = createTopPanel();
        JPanel middlePanel = createMiddlePanel();
        JScrollPane mainPanel = createMainPanel();

        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(middlePanel, BorderLayout.CENTER);
        mainFrame.add(mainPanel, BorderLayout.SOUTH);

        mainFrame.setVisible(true);
    }

    /**
     * Creates the top panel containing the title and action buttons.
     */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Contacts");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        topPanel.add(titleLabel, BorderLayout.WEST);

        // Add buttons: Refresh and Add Contact
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshButton = new JButton();
        JButton addContactButton = new JButton("Add Contact");

        refreshButton.setBackground(new Color(238, 237, 235));
        refreshButton.setBorder(null);
        refreshButton.setIcon(new ImageIcon("C:\\Users\\Aicha\\Documents\\Projects\\New one\\Contacts\\src\\com\\contacts\\view\\refimg.png"));
        refreshButton.addActionListener(e -> loadContacts());

        addContactButton.setBackground(new Color(230, 185, 166));
        addContactButton.setForeground(new Color(47, 54, 69));
        addContactButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        addContactButton.setBorder(BorderFactory.createLineBorder(new Color(47, 54, 69), 1, true));
        addContactButton.addActionListener(e -> new AddContactFrame());

        buttonPanel.add(refreshButton);
        buttonPanel.add(addContactButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        return topPanel;
    }

    /**
     * Creates the middle panel containing the search bar.
     */
    private JPanel createMiddlePanel() {
        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Border roundedBorder = BorderFactory.createLineBorder(new Color(47, 54, 69), 1, true);

        // Search field with placeholder behavior
        searchField = new JTextField("Search...");
        searchField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        searchField.setForeground(Color.GRAY);
        searchField.setBorder(roundedBorder);
        searchField.setPreferredSize(new Dimension(200, 25));
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

        // Search button
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(230, 185, 166));
        searchButton.setForeground(new Color(47, 54, 69));
        searchButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        searchButton.setPreferredSize(new Dimension(80, 25));
        searchButton.setBorder(roundedBorder);
        searchButton.addActionListener(e -> searchContacts());

        middlePanel.add(searchField);
        middlePanel.add(searchButton);

        return middlePanel;
    }

    /**
     * Creates the main panel containing the contacts table.
     */
    private JScrollPane createMainPanel() {
        contactsTable = new JTable(new DefaultTableModel(
                new Object[]{"ID", "Name", "Phone", "Email", "Action"}, 0
        )) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only the "Action" column is editable
            }
        };

        // Set custom cell renderer and editor for "Action" column
        contactsTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        contactsTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

        // Hide the ID column
        contactsTable.getColumnModel().getColumn(0).setMinWidth(0);
        contactsTable.getColumnModel().getColumn(0).setMaxWidth(0);

        loadContacts(); // Load data into the table

        return new JScrollPane(contactsTable);
    }

    /**
     * Loads contacts from the database into the table.
     */
    private void loadContacts() {
        List<Contact> contacts = contactDAO.getContacts();
        DefaultTableModel model = (DefaultTableModel) contactsTable.getModel();
        model.setRowCount(0); // Clear existing rows

        for (Contact contact : contacts) {
            model.addRow(new Object[]{contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail(), "Show Info"});
        }
    }

    /**
     * Searches contacts based on the keyword entered in the search field.
     */
    private void searchContacts() {
        String keyword = searchField.getText();
        List<Contact> contacts = contactDAO.searchContacts(keyword);
        DefaultTableModel model = (DefaultTableModel) contactsTable.getModel();
        model.setRowCount(0); // Clear existing rows

        for (Contact contact : contacts) {
            model.addRow(new Object[]{contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail(), "Show Info"});
        }
    }

    /**
     * Entry point of the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}

/**
 * Custom cell renderer for the "Action" column.
 */
class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
        setBackground(new Color(230, 185, 166));
        setForeground(new Color(47, 54, 69));
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        setBorder(BorderFactory.createLineBorder(new Color(47, 54, 69), 1, true));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

/**
 * Custom cell editor for the "Action" column.
 * Launches a contact details frame when the button is clicked.
 */
class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private String contactPhone;

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

        // Save contact phone for action handling
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
}
