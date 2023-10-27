package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handlers.DataHandler;
import handlers.MIMEToHandler;
import models.Book;
import models.BookDAO;

/**
 * Servlet implementation class SearchBookAPIController
 */
@WebServlet("/books-api/search")
public class SearchBookAPIController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MIMEToHandler mimeToHandlers;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchBookAPIController() {
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
		String searchStr = request.getParameter("searchText");

		ArrayList<Book> someBooks = dao.searchBook(searchStr);
		dataHandler.returnBookList(response, someBooks);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
