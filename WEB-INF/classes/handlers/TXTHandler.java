package handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jakarta.xml.bind.JAXBException;
import models.Book;

public class TXTHandler implements DataHandler {
	
	private String mimeType = "text/plain";
	private String charEncoding = "UTF-8";
    
    public Book getSingleBook(HttpServletRequest request) throws IOException {
    	
	 	String body = this.getEntireRequestBody(request);
		String[] bookInfo = body.split("#");
		
		int id = Integer.parseInt(bookInfo[0]);
		String title = bookInfo[1];
		String author = bookInfo[2];
		String date = bookInfo[3];
		String genres = bookInfo[4];
		String characters = bookInfo[5];
		String synopsis = bookInfo[6];
		
    	return new Book(id, title, author, date, genres, characters, synopsis); 
    }
    
    public int getID(HttpServletRequest request) throws IOException {
	 	String body = this.getEntireRequestBody(request);
		String idString = body.trim();
	    return Integer.parseInt(idString);
    }
    
    
    public void returnSingleBook(HttpServletResponse response, Book book) throws IOException, JAXBException {


        response.setContentType(mimeType);
        response.setCharacterEncoding(charEncoding);

        PrintWriter out = response.getWriter();
	    StringBuilder txtData = new StringBuilder();
	    
	    txtData.append(book.getId()).append("#");
	    txtData.append(book.getTitle()).append("#");
	    txtData.append(book.getAuthor()).append("#");
	    txtData.append(book.getDate()).append("#");
	    txtData.append(book.getGenres()).append("#");
	    txtData.append(book.getCharacters()).append("#");
	    txtData.append(book.getSynopsis()).append("\n");

	    
        out.print(txtData.toString());
        out.flush();
    }
    
    
    public void returnBookList(HttpServletResponse response, ArrayList<Book> allBooks) throws IOException {
    	

        response.setContentType(mimeType);
        response.setCharacterEncoding(charEncoding);
        
        StringBuilder sb = new StringBuilder();
        
        for (Book book : allBooks) {
            sb.append(book.getId()).append("#")
              .append(book.getTitle()).append("#")
              .append(book.getAuthor()).append("#")
              .append(book.getDate()).append("#")
              .append(book.getGenres()).append("#")
              .append(book.getCharacters()).append("#")
              .append(book.getSynopsis()).append("\n");;
        }
        
		String outputData = sb.toString();
		
		PrintWriter out = response.getWriter();
		
		out.write(outputData);
		out.close();
    }
    
    public String getEntireRequestBody(HttpServletRequest request) throws IOException {
        return request.getReader().lines().reduce("", (accumulator,actual) ->accumulator+actual);
    }
}