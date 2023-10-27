package handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import models.Book;
import models.BookList;
import models.ID;

public class XMLHandler implements DataHandler {

	private String mimeType = "application/xml";
	private String charEncoding = "UTF-8";

	public Book getSingleBook(HttpServletRequest request) throws IOException {

		Book book = null;

		try {

			String body = this.getEntireRequestBody(request);
			JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			book = (Book) jaxbUnmarshaller.unmarshal(new StringReader(body));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return book;
	}

	public int getID(HttpServletRequest request) throws IOException {

		int id = 0;

		try {
			String body = this.getEntireRequestBody(request);
			JAXBContext jaxbContext = JAXBContext.newInstance(ID.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			ID bodyID = (ID) unmarshaller.unmarshal(new StringReader(body));
			id = bodyID.getValue();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id;
	}

	public void returnSingleBook(HttpServletResponse response, Book book) throws IOException, JAXBException {

		response.setContentType(mimeType);
		response.setCharacterEncoding(charEncoding);

		JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter sw = new StringWriter();
		marshaller.marshal(book, sw);
		String xmlData = sw.toString();

		PrintWriter out = response.getWriter();
		out.print(xmlData);
		out.flush();
	}

	public void returnBookList(HttpServletResponse response, ArrayList<Book> allBooks)
			throws ServletException, IOException {

		String outputData = null;
		response.setContentType(mimeType);
		response.setCharacterEncoding(charEncoding);

		StringWriter writer = new StringWriter();
		try {

			BookList list = new BookList(allBooks);

			JAXBContext context = JAXBContext.newInstance(Book.class, BookList.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(list, writer);

			outputData = writer.toString();

		} catch (JAXBException e) {
			throw new ServletException("Error marshalling XML data", e);
		} finally {
			writer.close();
		}

		PrintWriter out = response.getWriter();

		out.write(outputData);
		out.close();
	}

	public String getEntireRequestBody(HttpServletRequest request) throws IOException {
		return request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
	}

}
