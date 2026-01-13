# UserManagementSystem

![Java](https://img.shields.io/badge/Java-17-blue)
![License](https://img.shields.io/badge/License-MIT-green)

## Overview
UserManagementSystem is a Java desktop application that provides a full-featured user management system. It includes a secure login, a dynamic user table with add/edit/delete functionalities, and data persistence using SQLite. The project demonstrates Java Swing GUI development and JDBC database integration.

![UserManagementSystem Screenshot](screenshot.png)

## Features
- **Login System**: Authenticate users by email and password.
- **User Management GUI**: Add, edit, and delete users directly in the interface.
- **Role-based Highlighting**: Admin users are highlighted in the table for quick identification.
- **Status Indicators**: Active and inactive users are visually differentiated.
- **Data Persistence**: All users are stored in a SQLite database (`users.db`) to keep data across sessions.
- **Validation**: Email format and password strength are checked when adding a new user.

## Technologies Used
- Java SE 17+
- Swing (GUI development)
- SQLite with JDBC (database)
- Git/GitHub (version control)

## Installation & Usage

### Prerequisites
- Java SE 17+ installed
- Git installed (if cloning from GitHub)

### Clone the repository
```bash
git clone https://github.com/abdelhamiid20/UserManagementSystem.git
cd UserManagementSystem
