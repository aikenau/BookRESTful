package handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import models.Book;

public class JSONHandler implements DataHandler {

	private String mimeType = "application/json";
	private String charEncoding = "UTF-8";

	public Book getSingleBook(HttpServletRequest request) throws IOException {

		Gson gson = new Gson();
		String body = this.getEntireRequestBody(request);
		return gson.fromJson(body, Book.class);
	}

	public int getID(HttpServletRequest request) throws IOException {

		int id = 0;
		String body = this.getEntireRequestBody(request);
		Type type = new TypeToken<Map<String, Object>>() {
		}.getType();
		Map<String, Object> map = new Gson().fromJson(body, type);
		id = ((Double) map.get("id")).intValue();

		return id;
	}

	public void returnSingleBook(HttpServletResponse response, Book book) throws IOException {
		response.setContentType(mimeType);
		response.setCharacterEncoding(charEncoding);

		Gson gson = new Gson();
		String outputData = gson.toJson(book);
		PrintWriter out = response.getWriter();

		out.print(outputData);
		out.flush();
	}

	public void returnBookList(HttpServletResponse response, ArrayList<Book> allBooks) throws IOException {

		response.setContentType(mimeType);
		response.setCharacterEncoding(charEncoding);
		Gson gson = new Gson();
		String outputData = gson.toJson(allBooks);
		PrintWriter out = response.getWriter();

		out.write(outputData);
		out.close();
	}

	public String getEntireRequestBody(HttpServletRequest request) throws IOException {
		return request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
	}
}
