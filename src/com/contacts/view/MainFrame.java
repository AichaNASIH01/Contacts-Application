package com.contacts.view;

import com.contacts.model.Contact;
import com.contacts.repository.ContactDAO;
import com.contacts.repository.ContactDAOImpl;
import com.contacts.DataBase.DataBase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame {

    private JFrame mainFrame;
    private JTable contactsTable;
    private JTextField searchField;
    private ContactDAO contactDAO;

    public MainFrame() {
        contactDAO = new ContactDAOImpl(DataBase.getDBInstance(DataBase.MYSQL));

        mainFrame = new JFrame("Contacts");
        mainFrame.setSize(800, 600);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setBackground(new Color(238, 237, 235));

        JPanel topPanel = createTopPanel();
        JScrollPane tablePanel = createTablePanel();
        JPanel bottomPanel = createBottomPanel();

        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(tablePanel, BorderLayout.CENTER);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);

        mainFrame.setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        // Search bar
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchContacts());

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        topPanel.add(searchPanel, BorderLayout.CENTER);

        return topPanel;
    }

    private JScrollPane createTablePanel() {
        contactsTable = new JTable(new DefaultTableModel(new Object[]{"ID", "Name", "Phone", "Email", "Action"}, 0));
        contactsTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        contactsTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

        loadContacts();

        return new JScrollPane(contactsTable);
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();

        JButton addContactButton = new JButton("Add Contact");
        addContactButton.addActionListener(e -> new AddContactFrame());

        bottomPanel.add(addContactButton);

        return bottomPanel;
    }

    private void loadContacts() {
        List<Contact> contacts = contactDAO.getContacts();
        DefaultTableModel model = (DefaultTableModel) contactsTable.getModel();
        model.setRowCount(0); // Clear existing data

        for (Contact contact : contacts) {
            model.addRow(new Object[]{contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail(), "Show Info"});
        }
    }

    private void searchContacts() {
        String keyword = searchField.getText();
        List<Contact> contacts = contactDAO.searchContacts(keyword);
        DefaultTableModel model = (DefaultTableModel) contactsTable.getModel();
        model.setRowCount(0); // Clear existing data

        for (Contact contact : contacts) {
            model.addRow(new Object[]{contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail(), "Show Info"});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}

class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
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
    private int contactId;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        contactId = (int) table.getValueAt(row, 0);
        return button;
    }

    public Object getCellEditorValue() {
        if (isPushed) {
            new ShowContactsFrame(contactId);
        }
        isPushed = false;
        return label;
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}