package com.junitexample.model;

import java.time.LocalDate;

public class Loan {
  private User user;
  private Libro libro;
  private LocalDate loanDate;
  private LoanState state;

  public Loan(User user, Libro libro) {
    this(user, libro, LocalDate.now(), LoanState.STARTED);
  }

  

  public Loan(User user, Libro libro, LocalDate loanDate) {
    this(user, libro, loanDate, LoanState.STARTED);
  }



  public Loan(User user, Libro libro, LocalDate loanDate, LoanState state) {
    this.user = user;
    this.libro = libro;
    this.loanDate = loanDate;
    this.state = state;
  }



  public User getUser() {
    return user;
  }



  public Libro getLibro() {
    return libro;
  }



  public LocalDate getLoanDate() {
    return loanDate;
  }



  public LoanState getState() {
    return state;
  }



  public void setState(LoanState state) {
    this.state = state;
  }

  
}
