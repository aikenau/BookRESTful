package handlers;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jakarta.xml.bind.JAXBException;
import models.Book;

public interface DataHandler {
   
    Book getSingleBook(HttpServletRequest request) throws IOException;
    int getID(HttpServletRequest request) throws IOException;
    void returnSingleBook(HttpServletResponse response, Book book) throws IOException, JAXBException;
    void returnBookList(HttpServletResponse response, ArrayList<Book> allBooks) throws ServletException, IOException;
    String getEntireRequestBody(HttpServletRequest request) throws IOException;
}
