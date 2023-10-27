package models;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "books")
public class BookList {

	private ArrayList<Book> book;

	public BookList() {
	}

	public BookList(ArrayList<Book> booksList) {
		this.book = booksList;
	}

	public ArrayList<Book> getBooksList() {
		return book;
	}

	public void setBooksList(ArrayList<Book> booksList) {
		this.book = booksList;
	}

}
