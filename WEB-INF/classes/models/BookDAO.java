package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BookDAO {

	private static BookDAO instance;

	private BookDAO() {
	}

	public static synchronized BookDAO getInstance() {
		if (instance == null) {
			instance = new BookDAO();
		}
		return instance;
	}

	Book oneBook = null;
	Connection conn = null;
	Statement stmt = null;
	String database = "DATABASE_NAME";
	String user = "USERNAME";
	String password = "PASSWORD";
	// Note none default port used, 6306 not 3306
	String url = "MYSQL_URL" + database;

	private void openConnection() {
		// loading jdbc driver for mysql
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			System.out.println(e);
		}

		// connecting to database
		try {
			// connection string for demos database, username demos, password demos
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
		} catch (SQLException se) {
			System.out.println(se);
		}
	}

	private void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Book getNextBook(ResultSet rs) {
		Book thisBook = null;
		try {

			thisBook = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getString("date"),
					rs.getString("genres"), rs.getString("characters"), rs.getString("synopsis"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return thisBook;
	}

	public ArrayList<Book> getAllBooks() {

		ArrayList<Book> allBooks = new ArrayList<Book>();
		openConnection();

		// Create select statement and execute it
		try {
			String selectSQL = "SELECT * FROM books";
			ResultSet rs1 = stmt.executeQuery(selectSQL);
			// Retrieve the results
			while (rs1.next()) {
				oneBook = getNextBook(rs1);
				allBooks.add(oneBook);
			}

			stmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return allBooks;
	}

	public ArrayList<Book> searchBook(String searchStr) {

		ArrayList<Book> someBooks = new ArrayList<Book>();
		openConnection();

		// Create select statement and execute it
		try {
			String selectSQL = "SELECT * FROM books WHERE synopsis LIKE ?";
			PreparedStatement pstmt = conn.prepareStatement(selectSQL);
			pstmt.setString(1, "%" + searchStr + "%");
			ResultSet rs1 = pstmt.executeQuery();
			// Retrieve the results
			while (rs1.next()) {
				oneBook = getNextBook(rs1);
				someBooks.add(oneBook);
			}

			stmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return someBooks;
	}

	public Book getBookByID(int id) {

		openConnection();
		oneBook = null;
		// Create select statement and execute it
		try {
			String selectSQL = "SELECT * FROM books WHERE id=?";
			PreparedStatement pstmt = conn.prepareStatement(selectSQL);
			pstmt.setInt(1, id);
			ResultSet rs1 = pstmt.executeQuery();
			// Retrieve the results
			while (rs1.next()) {
				oneBook = getNextBook(rs1);
			}

			pstmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}

		return oneBook;
	}

	public boolean insertBook(Book book) throws SQLException {
		boolean flag = false;
		openConnection();
		try {
			String insertSQL = "INSERT INTO books (id,title,author,date,genres,characters,synopsis) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(insertSQL);
			pstmt.setInt(1, book.id);
			pstmt.setString(2, book.title);
			pstmt.setString(3, book.author);
			pstmt.setString(4, book.date);
			pstmt.setString(5, book.genres);
			pstmt.setString(6, book.characters);
			pstmt.setString(7, book.synopsis);
			int rowsAffected = pstmt.executeUpdate();
			System.out.println("BACK-BookDAO : Insert, number of row affected " + rowsAffected);
			if (rowsAffected > 0) {

				flag = true;
			}
			pstmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}
		return flag;
	}

	public boolean updateBook(Book book) throws SQLException {
		boolean flag = false;
		openConnection();
		try {
			String updateSQL = "UPDATE books SET title = ?, author = ?, date = ?, genres = ?, characters = ?, synopsis = ? WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(updateSQL);

			pstmt.setString(1, book.title);
			pstmt.setString(2, book.author);
			pstmt.setString(3, book.date);
			pstmt.setString(4, book.genres);
			pstmt.setString(5, book.characters);
			pstmt.setString(6, book.synopsis);
			pstmt.setInt(7, book.id);

			int rowsAffected = pstmt.executeUpdate();
			System.out.println("BACK-BookDAO : Update, number of row affected " + rowsAffected);
			if (rowsAffected > 0) {

				flag = true;
			}
			pstmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}
		return flag;
	}

	public boolean deleteBook(int id) throws SQLException {
		boolean flag = false;
		openConnection();

		try {
			String deleteSQL = "DELETE FROM books WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(deleteSQL);

			pstmt.setInt(1, id);
			int rowsAffected = pstmt.executeUpdate();
			System.out.println("BACK-BookDAO : Delete, number of row affected " + rowsAffected);
			if (rowsAffected > 0) {
				flag = true;
			}
			pstmt.close();
			closeConnection();
		} catch (SQLException se) {
			System.out.println(se);
		}
		return flag;
	}

}
