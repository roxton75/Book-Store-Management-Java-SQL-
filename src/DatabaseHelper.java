//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import static javax.management.remote.JMXConnectorFactory.connect;
//
//public class DatabaseHelper {
//
//    private static final String DB_URL = "jdbc:sqlite:db/bookstore.db";
//
//    public static Connection getConnection() throws SQLException {
//
//        return DriverManager.getConnection(DB_URL);
//    }
//
//    // Get total number of books
//    public static int getTotalBooks() throws SQLException {
//        String query = "SELECT COUNT(*) FROM books";
//        try (Connection connection = getConnection();
//             Statement stmt = connection.createStatement()) {
//            ResultSet rs = stmt.executeQuery(query);
//            return rs.getInt(1); // Total number of books
//        }
//    }
//
//    // Get total quantity of all books
//    public static int getTotalQuantity() throws SQLException {
//        String query = "SELECT SUM(quantity) FROM books";
//        try (Connection connection = getConnection();
//             Statement stmt = connection.createStatement()) {
//            ResultSet rs = stmt.executeQuery(query);
//            return rs.getInt(1); // Total quantity
//        }
//    }
//
//    public static List<Object[]> getBooks(double minQuantity) throws SQLException {
//        List<Object[]> books = new ArrayList<>();
//        String query = "SELECT book_id, name, author, genre, price FROM books WHERE quantity > ?";
//        try (Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setDouble(1, minQuantity);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                books.add(new Object[]{
//                        rs.getInt("book_id"),
//                        rs.getString("name"),
//                        rs.getString("author"),
//                        rs.getString("genre"),
//                        rs.getDouble("price")
//                });
//            }
//        }
//        return books;
//    }
//
//    public static List<Object[]> searchBooksByPrice(double price) throws SQLException {
//        List<Object[]> books = new ArrayList<>();
//        String query = "SELECT book_id, name, author, genre, price FROM books WHERE price < ? ORDER BY price DESC";
//        try (Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setDouble(1, price);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                books.add(new Object[]{
//                        rs.getInt("book_id"),
//                        rs.getString("name"),
//                        rs.getString("author"),
//                        rs.getString("genre"),
//                        rs.getDouble("price")
//                });
//            }
//        }
//        return books;
//    }
//
//    public static List<Object[]> searchBooks(String keyword) throws SQLException {
//        List<Object[]> books = new ArrayList<>();
//        String like = "%" + keyword + "%";
//        String query = "SELECT book_id, name, author, genre, price FROM books " +
//                "WHERE name LIKE ? OR author LIKE ? OR genre LIKE ?";
//        try (Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setString(1, like);
//            stmt.setString(2, like);
//            stmt.setString(3, like);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                books.add(new Object[]{
//                        rs.getInt("book_id"),
//                        rs.getString("name"),
//                        rs.getString("author"),
//                        rs.getString("genre"),
//                        rs.getDouble("price")
//                });
//            }
//        }
//        return books;
//    }
//
//    public static List<Object[]> searchBooksData(String keyword) throws SQLException {
//        List<Object[]> books = new ArrayList<>();
//        String like = "%" + keyword + "%";
//        String query = "SELECT book_id, name, publisher, author, quantity, price, category, genre FROM books " +
//                "WHERE name LIKE ? OR author LIKE ? OR genre LIKE ?";
//        try (Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setString(1, like);
//            stmt.setString(2, like);
//            stmt.setString(3, like);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                books.add(new Object[]{
//                        rs.getInt("book_id"),
//                        rs.getString("name"),
//                        rs.getString("publisher"),
//                        rs.getString("author"),
//                        rs.getInt("quantity"),
//                        rs.getDouble("price"),
//                        rs.getString("category"),
//                        rs.getString("genre")
//                });
//            }
//        }
//        return books;
//    }
//
//    // Updated getBooks method to return a List<Object[]>
//    public static List<Object[]> getBooksDB() throws SQLException {
//        List<Object[]> books = new ArrayList<>();
//        String query = "SELECT book_id, name, publisher, author, quantity, price, category, genre FROM books";
//        try (Connection conn = getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
//
//            while (rs.next()) {
//                books.add(new Object[]{
//                        rs.getInt("book_id"),
//                        rs.getString("name"),
//                        rs.getString("publisher"),
//                        rs.getString("author"),
//                        rs.getInt("quantity"),
//                        rs.getDouble("price"),
//                        rs.getString("category"),
//                        rs.getString("genre")
//                });
//            }
//        }
//        return books;
//    }
//
//    // Method to delete a book by ID
//    public static void deleteBook(int bookId) throws SQLException {
//        String query = "DELETE FROM books WHERE book_id = ?";
//        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db/bookstore.db");
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setInt(1, bookId);
//            stmt.executeUpdate();
//        }
//    }
//
//    // Method to update book details
//    public static void updateBook(int bookId, String name, String publisher, String author, int quantity, double price, String category, String genre) throws SQLException {
//        String query = "UPDATE books SET name = ?, publisher = ?, author = ?, quantity = ?, price = ?, category = ?, genre = ? WHERE book_id = ?";
//        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:db/bookstore.db");
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setString(1, name);
//            stmt.setString(2, publisher);
//            stmt.setString(3, author);
//            stmt.setInt(4, quantity);
//            stmt.setDouble(5, price);
//            stmt.setString(6, category);
//            stmt.setString(7, genre);
//            stmt.setInt(8, bookId);
//            stmt.executeUpdate();
//        }
//    }
//
//    public static boolean insertBook(String title, String publisher, String author,
//                                     int quantity, double price, String category, String genre) {
//        String sql = "INSERT INTO books (name, publisher, author, quantity, price, category, genre) VALUES (?, ?, ?, ?, ?, ?, ?)";
//
//        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, title);
//            pstmt.setString(2, publisher);
//            pstmt.setString(3, author);
//            pstmt.setInt(4, quantity);
//            pstmt.setDouble(5, price);
//            pstmt.setString(6, category);
//            pstmt.setString(7, genre);
//            pstmt.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public static ResultSet getAllBooksForBilling() throws SQLException {
//        Connection conn = getConnection(); // your existing getConnection() method
//        String query = "SELECT book_id, name, author, quantity, price, genre FROM books";
//        PreparedStatement stmt = conn.prepareStatement(query);
//        return stmt.executeQuery(); // caller must close ResultSet and Connection
//    }
//
//    public static ResultSet getBookById(String bookId) throws SQLException {
//        Connection conn = getConnection(); // Your existing method to get DB connection
//        String query = "SELECT * FROM books WHERE book_id = ?";
//        PreparedStatement stmt = conn.prepareStatement(query);
//        stmt.setString(1, bookId);
//        return stmt.executeQuery(); // Caller will handle the ResultSet
//    }
//
//    public static void updateBookQuantity(String bookId, int newQuantity) throws SQLException {
//        // Query to update the quantity of a book based on its book_id
//        String query = "UPDATE books SET quantity = ? WHERE book_id = ?";
//
//        // Use PreparedStatement to handle the query safely
//        PreparedStatement statement = getConnection().prepareStatement(query);
//
//        // Set the values for the query parameters
//        statement.setInt(1, newQuantity);  // Set the new quantity
//        statement.setString(2, bookId);   // Set the book ID
//
//        // Execute the update query
//        statement.executeUpdate();
//
//        // Close the statement to free resources
//        statement.close();
//    }
//
//    public static void insertBill(String bookId, String title, String author, int quantity, double price, String genre, String customerName, String paymentMode) throws SQLException {
//        // SQL query to insert data into the bills table
//        String query = "INSERT INTO bills (book_id, title, author, quantity, price, genre, cus_name, pay_mode) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//
//        // Use PreparedStatement to handle the query safely
//        PreparedStatement statement = getConnection().prepareStatement(query);
//
//        // Set the values for the query parameters
//        statement.setString(1, bookId);       // Book ID
//        statement.setString(2, title);       // Title
//        statement.setString(3, author);      // Author
//        statement.setInt(4, quantity);       // Quantity
//        statement.setDouble(5, price);       // Price
//        statement.setString(6, genre);       // Genre
//        statement.setString(7, customerName); // Customer Name
//        statement.setString(8, paymentMode); // Payment Mode
//
//        // Execute the insert query
//        statement.executeUpdate();
//
//        // Close the statement to free resources
//        statement.close();
//    }
//
//
//
//}
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private static final String DB_URL = "jdbc:sqlite:db/bookstore.db";

    // Initialize the database with WAL mode and busy timeout
    static {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            // Enable Write-Ahead Logging (WAL) for better concurrency
            stmt.execute("PRAGMA journal_mode=WAL;");

            // Set busy timeout to 3 seconds
            stmt.execute("PRAGMA busy_timeout=3000;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get a database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Get total number of books
    public static int getTotalBooks() throws SQLException {
        String query = "SELECT COUNT(*) FROM books";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.getInt(1); // Total number of books
        }
    }

//        public static List<Object[]> getBooks(double minQuantity) throws SQLException {
//        List<Object[]> books = new ArrayList<>();
//        String query = "SELECT book_id, name, author, genre, price FROM books WHERE quantity > ?";
//        try (Connection conn = getConnection();
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setDouble(1, minQuantity);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                books.add(new Object[]{
//                        rs.getInt("book_id"),
//                        rs.getString("name"),
//                        rs.getString("author"),
//                        rs.getString("genre"),
//                        rs.getDouble("price")
//                });
//            }
//        }
//        return books;
//    }

//     Method to update book details

    // Get total quantity of all books
    public static int getTotalQuantity() throws SQLException {
        String query = "SELECT SUM(quantity) FROM books";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.getInt(1); // Total quantity
        }
    }

    public static synchronized ResultSet getAllBooksForBilling() throws SQLException {
        String query = "SELECT book_id, name, author, quantity, price, genre FROM books";
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        return stmt.executeQuery(); // Caller must close ResultSet and Connection
    }

    public static synchronized ResultSet getBookById(String bookId) throws SQLException {
        String query = "SELECT * FROM books WHERE book_id = ?";
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, bookId);
        return stmt.executeQuery(); // Caller must close ResultSet and Connection
    }

    public static synchronized void updateBookQuantity(String bookId, int newQuantity) throws SQLException {
        String query = "UPDATE books SET quantity = ? WHERE book_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, newQuantity);  // Set the new quantity
            statement.setString(2, bookId);   // Set the book ID
            statement.executeUpdate();
        }
    }

    public static synchronized void insertBill(String bookId, String title, String author, int quantity,
                                               double price, String genre, String customerName, String paymentMode) throws SQLException {
        String query = "INSERT INTO bills (book_id, title, author, quantity, price, genre, cus_name, pay_mode) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, bookId);       // Book ID
            statement.setString(2, title);       // Title
            statement.setString(3, author);      // Author
            statement.setInt(4, quantity);       // Quantity
            statement.setDouble(5, price);       // Price
            statement.setString(6, genre);       // Genre
            statement.setString(7, customerName); // Customer Name
            statement.setString(8, paymentMode); // Payment Mode
            statement.executeUpdate();
        }
    }

    // Search books by keyword
    public static List<Object[]> searchBooks(String keyword) throws SQLException {
        List<Object[]> books = new ArrayList<>();
        String like = "%" + keyword + "%";
        String query = "SELECT book_id, name, author, genre, price FROM books " +
                "WHERE name LIKE ? OR author LIKE ? OR genre LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, like);
            stmt.setString(2, like);
            stmt.setString(3, like);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(new Object[]{
                            rs.getInt("book_id"),
                            rs.getString("name"),
                            rs.getString("author"),
                            rs.getString("genre"),
                            rs.getDouble("price")
                    });
                }
            }
        }
        return books;
    }

    // Delete a book by ID
    public static void deleteBook(int bookId) throws SQLException {
        String query = "DELETE FROM books WHERE book_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
        }
    }

    // Update book details
    public static void updateBook(int bookId, String name, String publisher, String author,
                                  int quantity, double price, String category, String genre) throws SQLException {
        String query = "UPDATE books SET name = ?, publisher = ?, author = ?, quantity = ?, price = ?, category = ?, genre = ? WHERE book_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, publisher);
            stmt.setString(3, author);
            stmt.setInt(4, quantity);
            stmt.setDouble(5, price);
            stmt.setString(6, category);
            stmt.setString(7, genre);
            stmt.setInt(8, bookId);
            stmt.executeUpdate();
        }
    }

    public static boolean insertBook(String title, String publisher, String author,
                                     int quantity, double price, String category, String genre) {
        String sql = "INSERT INTO books (name, publisher, author, quantity, price, category, genre) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, publisher);
            pstmt.setString(3, author);
            pstmt.setInt(4, quantity);
            pstmt.setDouble(5, price);
            pstmt.setString(6, category);
            pstmt.setString(7, genre);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Object[]> getBooks(double minQuantity) throws SQLException {
        List<Object[]> books = new ArrayList<>();
        String query = "SELECT book_id, name, author, genre, price FROM books WHERE quantity > ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, minQuantity);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                books.add(new Object[]{
                        rs.getInt("book_id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getDouble("price")
                });
            }
        }
        return books;
    }

    public static List<Object[]> searchBooksByPrice(double price) throws SQLException {
        List<Object[]> books = new ArrayList<>();
        String query = "SELECT book_id, name, author, genre, price FROM books WHERE price < ? ORDER BY price DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, price);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                books.add(new Object[]{
                        rs.getInt("book_id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getDouble("price")
                });
            }
        }
        return books;
    }

        public static List<Object[]> searchBooksData(String keyword) throws SQLException {
        List<Object[]> books = new ArrayList<>();
        String like = "%" + keyword + "%";
        String query = "SELECT book_id, name, publisher, author, quantity, price, category, genre FROM books " +
                "WHERE name LIKE ? OR author LIKE ? OR genre LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, like);
            stmt.setString(2, like);
            stmt.setString(3, like);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                books.add(new Object[]{
                        rs.getInt("book_id"),
                        rs.getString("name"),
                        rs.getString("publisher"),
                        rs.getString("author"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("category"),
                        rs.getString("genre")
                });
            }
        }
        return books;
    }

    // Updated getBooks method to return a List<Object[]>
    public static List<Object[]> getBooksDB() throws SQLException {
        List<Object[]> books = new ArrayList<>();
        String query = "SELECT book_id, name, publisher, author, quantity, price, category, genre FROM books";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                books.add(new Object[]{
                        rs.getInt("book_id"),
                        rs.getString("name"),
                        rs.getString("publisher"),
                        rs.getString("author"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("category"),
                        rs.getString("genre")
                });
            }
        }
        return books;
    }

    // Method to get the total sales (sum of all prices in the bills table)
    public static double getTotalSales() throws SQLException {
        String query = "SELECT SUM(price) AS total_sales FROM bills";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getDouble("total_sales");
            }
        }
        return 0; // Return 0 if no sales are found
    }

//    // Method to get recent bills based on the limit
//    public static List<String> getRecentBills(int limit) throws SQLException {
//        String query = "SELECT book_id, name, price, date FROM bills ORDER BY date DESC LIMIT ?";
//        List<String> recentBills = new ArrayList<>();
//
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setInt(1, limit); // Set the limit for the number of recent bills
//            try (ResultSet resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    String bookId = resultSet.getString("book_id");
//                    String name = resultSet.getString("name");
//                    double price = resultSet.getDouble("price");
//                    String date = resultSet.getString("date");
//
//                    // Format the data for display
//                    recentBills.add(String.format("Book ID: %s | Name: %s | Price: %.2f | Date: %s",
//                            bookId, name, price, date));
//                }
//            }
//        }
//        return recentBills;
//    }
}

