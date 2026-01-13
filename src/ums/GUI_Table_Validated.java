package ums;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GUI_Table_Validated {

    private UserManager userManager;
    private boolean isAdmin;

    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    // ✅ CONSTRUCTEUR AVEC ROLE (utilisé par LoginFrame)
    public GUI_Table_Validated(UserManager userManager, boolean isAdmin) {
        this.userManager = userManager;
        this.isAdmin = isAdmin;
        createAndShowGUI();
    }

    // ✅ CONSTRUCTEUR DE COMPATIBILITÉ (sécurité)
    public GUI_Table_Validated(UserManager userManager) {
        this(userManager, true); // par défaut ADMIN
    }


    private void createAndShowGUI() {
        frame = new JFrame("User Management System");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Email"}, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton addButton = new JButton("Ajouter");
        JButton editButton = new JButton("Modifier");
        JButton removeButton = new JButton("Supprimer");
        JButton refreshButton = new JButton("Actualiser");

        JPanel panel = new JPanel(new GridLayout(1, 4, 10, 10));
        panel.add(addButton);
        panel.add(editButton);
        panel.add(removeButton);
        panel.add(refreshButton);

        frame.add(panel, BorderLayout.SOUTH);

        // 🔒 Permissions USER
        if (!isAdmin) {
            addButton.setEnabled(false);
            editButton.setEnabled(false);
            removeButton.setEnabled(false);
        }

        addButton.addActionListener(e -> ajouterUtilisateur());
        editButton.addActionListener(e -> modifierUtilisateur());
        removeButton.addActionListener(e -> supprimerUtilisateur());
        refreshButton.addActionListener(e -> refreshTable());

        refreshTable();
        frame.setVisible(true);
    }

    private void ajouterUtilisateur() {
        String name;
        while (true) {
            name = JOptionPane.showInputDialog(frame, "Nom :");
            if (name == null) return;
            if (validateName(name)) break;
        }

        String email;
        while (true) {
            email = JOptionPane.showInputDialog(frame, "Email :");
            if (email == null) return;
            if (!validateEmail(email)) continue;
            if (userManager.emailExists(email)) {
                JOptionPane.showMessageDialog(frame, "Email déjà utilisé !");
                continue;
            }
            break;
        }

        userManager.addUser(name, email);
        refreshTable();
    }

    private void modifierUtilisateur() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "Sélectionnez un utilisateur !");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        String currentEmail = (String) tableModel.getValueAt(row, 2);

        String name;
        while (true) {
            name = JOptionPane.showInputDialog(frame, "Nouveau nom :");
            if (name == null) return;
            if (validateName(name)) break;
        }

        String email;
        while (true) {
            email = JOptionPane.showInputDialog(frame, "Nouvel email :");
            if (email == null) return;
            if (!validateEmail(email)) continue;
            if (!email.equals(currentEmail) && userManager.emailExists(email)) {
                JOptionPane.showMessageDialog(frame, "Email déjà utilisé !");
                continue;
            }
            break;
        }

        userManager.updateUser(id, name, email);
        refreshTable();
    }

    private void supprimerUtilisateur() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        int id = (int) tableModel.getValueAt(row, 0);
        userManager.removeUser(id);
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (User u : userManager.getUsers()) {
            tableModel.addRow(new Object[]{u.getId(), u.getName(), u.getEmail()});
        }
    }

    private boolean validateName(String name) {
        if (name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nom vide !");
            return false;
        }
        if (!name.matches("[a-zA-Z\\s]+")) {
            JOptionPane.showMessageDialog(frame, "Nom invalide !");
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {
        if (!email.matches("^.+@.+\\..+$")) {
            JOptionPane.showMessageDialog(frame, "Email invalide !");
            return false;
        }
        return true;
    }
}
