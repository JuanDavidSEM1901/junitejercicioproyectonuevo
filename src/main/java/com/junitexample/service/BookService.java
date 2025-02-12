package com.junitexample.service;

import java.util.ArrayList;
import java.util.List;

import com.junitexample.exception.NotFoundException;
import com.junitexample.model.Libro;

public class BookService{

  private List<Libro> books;
  public BookService(){
    books = new ArrayList<>();
  }
  public void addBook(String isbn, String title, String author) {
    books.add(new Libro(isbn, title, author));
  }

  public List<Libro> getAllBooks() {
    return books;
  }

  public Libro getBookByIsbn(String isbn) throws NotFoundException {
    for (var book : books) {
      if (book.getIsbn().equals(isbn)) {
        return book;
      }
    }
    throw new NotFoundException("Book not found");
  }

  public void deleteBook(String isbn) throws NotFoundException {
    for (var book : books) {
      if (book.getIsbn().equals(isbn)) {
        books.remove(book);
        return;
      }
    }
    throw new NotFoundException("no se puede eliminar el libro con isbn: "+isbn);
  }
}
