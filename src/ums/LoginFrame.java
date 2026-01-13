package ums;

import javax.swing.*;
import java.awt.*;

public class LoginFrame {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        frame = new JFrame("Login - User Management System");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 1, 10, 10));
        frame.setLocationRelativeTo(null);

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        frame.add(new JLabel("  Username:"));
        frame.add(usernameField);
        frame.add(new JLabel("  Password:"));
        frame.add(passwordField);

        JButton loginButton = new JButton("Login");
        frame.add(loginButton);

        loginButton.addActionListener(e -> login());

        frame.setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals("admin") && password.equals("admin123")) {
            frame.dispose();
            new GUI_Table_Validated(new UserManager(), true); // admin
        }
        else if (username.equals("user") && password.equals("user123")) {
            frame.dispose();
            new GUI_Table_Validated(new UserManager(), false); // user
        }
        else {
            JOptionPane.showMessageDialog(frame, "Identifiants incorrects !");
        }
    }
}
