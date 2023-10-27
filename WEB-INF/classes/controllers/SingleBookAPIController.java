package controllers;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handlers.DataHandler;
import handlers.JSONHandler;
import handlers.MIMEToHandler;
import jakarta.xml.bind.JAXBException;
import models.Book;
import models.BookDAO;

/**
 * Servlet implementation class SingleBookAPIController
 */
@WebServlet("/books-api/single")
public class SingleBookAPIController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MIMEToHandler mimeToHandlers;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SingleBookAPIController() {
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

		int id = Integer.parseInt(request.getParameter("id"));
		BookDAO dao = BookDAO.getInstance();
		Book book = dao.getBookByID(id);

		String mimeType = request.getHeader("Content-Type");

		DataHandler dataHandler = mimeToHandlers.getHandlerByMIME(mimeType);
		if (dataHandler == null)
			dataHandler = new JSONHandler();

		try {
			dataHandler.returnSingleBook(response, book);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
