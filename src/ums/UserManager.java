package ums;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users = new ArrayList<>();
    private int nextId = 1;
    private final String fileName = "users.txt";

    public UserManager() {
        loadUsers();
    }

    // Ajouter un utilisateur
    public void addUser(String name, String email) {
        User user = new User(nextId++, name, email);
        users.add(user);
        saveUsers();
    }

    // Supprimer un utilisateur par ID
    public void removeUser(int id) {
        users.removeIf(user -> user.getId() == id);
        saveUsers();
    }

    // Modifier un utilisateur
    public void updateUser(int id, String newName, String newEmail) {
        for (User user : users) {
            if (user.getId() == id) {
                if (newName != null && !newName.trim().isEmpty()) user.setName(newName);
                if (newEmail != null && !newEmail.trim().isEmpty()) user.setEmail(newEmail);
                break;
            }
        }
        saveUsers();
    }

    // Retourne la liste des utilisateurs
    public List<User> getUsers() {
        return users;
    }

    // Vérifie si un email existe déjà (pour éviter les doublons)
    public boolean emailExists(String email) {
        return users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    // Sauvegarde des utilisateurs dans le fichier
    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (User user : users) {
                writer.write(user.getId() + ";" + user.getName() + ";" + user.getEmail());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erreur sauvegarde : " + e.getMessage());
        }
    }

    // Chargement des utilisateurs depuis le fichier
    private void loadUsers() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String email = parts[2];
                    users.add(new User(id, name, email));
                    if (id >= nextId) nextId = id + 1; // Assure ID unique
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur chargement : " + e.getMessage());
        }
    }
}
