package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handlers.DataHandler;
import handlers.MIMEToHandler;
import jakarta.xml.bind.JAXBException;
import models.Book;
import models.BookDAO;

/**
 * Servlet implementation class BooksAPIController
 */
@WebServlet("/books-api")
public class BooksAPIController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MIMEToHandler mimeToHandlers;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BooksAPIController() {
		super();
		// TODO Auto-generated constructor stub

	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		mimeToHandlers = new MIMEToHandler();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String format = request.getParameter("format");
		if (format == null || format.isEmpty())
			format = "json";

		DataHandler dataHandler = mimeToHandlers.getHandlerByFormat(format);

		BookDAO dao = BookDAO.getInstance();

		ArrayList<Book> allBooks = dao.getAllBooks();
		dataHandler.returnBookList(response, allBooks);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String mimeType = request.getHeader("Content-Type");

		DataHandler dataHandler = mimeToHandlers.getHandlerByMIME(mimeType);

		Book book = dataHandler.getSingleBook(request);
		BookDAO dao = BookDAO.getInstance();

		PrintWriter out = response.getWriter();

		try {

			boolean success = dao.insertBook(book);
			dataHandler.returnSingleBook(response, book);
			if (!success)
				out.print("BACK-API : Failed to add book!\n");

		} catch (SQLException | JAXBException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String mimeType = request.getHeader("Content-Type");

		DataHandler dataHandler = mimeToHandlers.getHandlerByMIME(mimeType);

		Book book = dataHandler.getSingleBook(request);

		BookDAO dao = BookDAO.getInstance();
		PrintWriter out = response.getWriter();

		try {

			boolean success = dao.updateBook(book);
			dataHandler.returnSingleBook(response, book);
			if (!success)
				out.print("BACK-API : Failed to updated!\n");

		} catch (SQLException | JAXBException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String mimeType = request.getHeader("Content-Type");
		DataHandler dataHandler = mimeToHandlers.getHandlerByMIME(mimeType);
		int id = dataHandler.getID(request);

		BookDAO dao = BookDAO.getInstance();
		PrintWriter out = response.getWriter();

		try {

			boolean success = dao.deleteBook(id);
			if (!success)
				out.print("BACK-API : Delete Failed!\n");
			else
				out.print("BACK-API : Book Deleted!\n");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
