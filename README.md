# FurniFind – Smart Furnishing Catalogue Search & Room Visualization System

A Java-based desktop application designed to simplify furniture catalogue searching and management.

## Features
- Admin login system
- Furniture catalogue search
- Company-wise catalogue management
- Product information and pricing
- MySQL database integration
- Graphical user interface

## Technologies Used
- Java
- Java Swing
- JDBC
- MySQL

## Project Structure

```text
FurniFind/
├── database/
│   └── furnifind.sql
├── screenshots/
├── FurniFind.java
├── mysql-connector-j-26.7.0.jar
├── README.md
└── .gitignore
```

## Database Setup
1. Open phpMyAdmin or MySQL.
2. Create a database named `furnifind`.
3. Import `database/furnifind.sql`.

## How to Run

Compile:

```bash
javac -cp ".;mysql-connector-j-26.7.0.jar" FurniFind.java
```

Run:

```bash
java -cp ".;mysql-connector-j-26.7.0.jar" FurniFind
```

## Project Type
Academic Mini Project

## Developer
Mantasha Taibani
