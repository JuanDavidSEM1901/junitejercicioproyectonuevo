package com.junitexample.service;

import java.util.ArrayList;
import java.util.List;

import com.junitexample.exception.NotFoundException;
import com.junitexample.model.Loan;
import com.junitexample.model.LoanState;

public class LoanService {
  private List<Loan> loans;
  private BookService bookService;
  private UserService userService;

  public LoanService(BookService bookService, UserService userService) {
    this.bookService = bookService;
    this.userService = userService;
    this.loans = new ArrayList<>();
  }

  public void addLoan(String id, String isbn) throws NotFoundException {
    var user = userService.getUserById(id);
    var book = bookService.getBookByIsbn(isbn);
    for (Loan loan : loans) {
      if (loan.getLibro().getIsbn().equals(isbn) && loan.getState().equals(LoanState.STARTED)) {
        throw new NotFoundException("El libro con isbn: " + isbn + " ya esta prestado");
      }
    }
    loans.add(new Loan(user, book));

  }

  public void returnLibro(String id, String isbn) throws NotFoundException {
    for (var loan : loans) {
      if (loan.getUser().getId().equals(id) && loan.getLibro().getIsbn().equals(isbn)
          && loan.getState().equals(LoanState.STARTED)) {
        loan.setState(LoanState.FINISHED);
        return;

      }
    }
    throw new NotFoundException("No hay un prestamo del libro: " + isbn + " para el usuario: " + id);
  }

  public List<Loan> getLoans() {
    return loans;
  }

}
