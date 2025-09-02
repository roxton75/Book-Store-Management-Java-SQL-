

# 📚 Book Store Management System

A modern and responsive **Book Store Management System** built using **Java Swing** and **SQLite**. This desktop application is designed for bookstore administrators to manage inventory efficiently with a clean UI and smooth functionality.

## 🚀 Features

- 🧾 **Admin Login System** with secure validation
- 📦 **Inventory Management** – Add, update, delete, and search books
- 📚 **Book Categorization** – Based on category and genre
- 💰 **Price and Quantity Handling**
- 🔎 **Dynamic Search** with filters
- 🧠 **Auto-Fill Fields** by selecting Book ID
- 💡 **Responsive GUI** using `CardLayout` and modern design principles
- 🎨 **Custom Buttons & Dialog Boxes** with curved UI
- 🛠 **Database Integration** using `SQLite`
- 🔐 **Error Handling** for invalid entries and duplicates


## 🛠 Technologies Used

| Technology        | Description                            |
|------------------|----------------------------------------|
| Java Swing        | GUI development                       |
| SQLite            | Lightweight database                   |
| JDBC              | Java Database Connectivity             |
| CardLayout        | For responsive UI navigation           |
| IntelliJ | IDE for development and testing                 |

## 💻 UI Screenshots

<p align="center">
  <img width="581" height="385" alt="Screenshot 2025-04-13 132100" src="https://github.com/user-attachments/assets/0b664e76-70c8-4987-a88d-c9e398ea5b7a" />

  <img width="1681" height="887" alt="Screenshot 2025-04-13 132242" src="https://github.com/user-attachments/assets/e09b2762-03b6-42a3-9639-fb7f8e8521f1" />
  <img width="1681" height="886" alt="Screenshot 2025-04-13 132553" src="https://github.com/user-attachments/assets/7ae6b36d-e2b1-4764-984d-6c10080f21ed" />
  <img width="1682" height="887" alt="Screenshot 2025-04-13 133003" src="https://github.com/user-attachments/assets/3d724b49-9b34-4ee6-8c2b-6bad670f2652" />
  <img width="1681" height="887" alt="Screenshot 2025-04-13 133050" src="https://github.com/user-attachments/assets/d5e6fa0f-28f6-42d9-b1da-348853e65a2e" />

</p>

---


## 📝 How to Run

    1. Clone the repository  
    `git clone https://github.com/rudrx75/bookstore-management-system.git`

    2. Open in your preferred Java IDE (IntelliJ Idea Community Edition)

    3. Run `LoginPage.java` as the entry point

    4. Make sure `SQLite` is connected and `books.db` exists in the path



## 📂 Database Schema
    CREATE TABLE books (
        book_id TEXT PRIMARY KEY,
        name TEXT NOT NULL,
        publisher TEXT,
        author TEXT,
        quantity INTEGER,
        price REAL,
        category TEXT,
        genre TEXT
    );



## 📚 References

- [Java Swing Documentation](https://docs.oracle.com/javase/tutorial/uiswing/)
- [SQLite Java Integration](https://www.sqlitetutorial.net/sqlite-java/)
- [Java JDBC Guide](https://www.javatpoint.com/java-jdbc)

## 🤝🏼Contributing

Contributions are welcome! Please fork the repository and create a pull request with your improvements or bug fixes.

## ⚖️License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📧Contact

Created by [roxton75](https://github.com/roxton75) - feel free to reach out for questions or collaboration.



****
