package com.junitexample.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.junitexample.exception.NotFoundException;
import com.junitexample.model.Libro;
import com.junitexample.model.LoanState;
import com.junitexample.model.User;

class LoanServiceTest {

  // variables de la clase
  /*
   * bookService: Es un servicio que maneja los libros.
   * 
   * userService: Es un servicio que maneja los usuarios.
   * 
   * service: Es el servicio de préstamos que estamos probando.
   */
  private BookService bookService;
  private UserService userService;
  private LoanService service;

  // metodo setup() que se ejecuta antes de cada prueba

  /*
   * Este método se ejecuta antes de cada prueba.
   * 
   * Mockito.mock(...): Crea versiones "falsas" de BookService y UserService.
   * Estas versiones falsas nos permiten simular cómo se comportarían sin
   * necesidad de usar los servicios reales.
   * 
   * service = new LoanService(bookService, userService): Crea una instancia de
   * LoanService usando los servicios falsos.
   */

  @BeforeEach
  void setup() {
    bookService = Mockito.mock(BookService.class);
    userService = Mockito.mock(UserService.class);
    service = new LoanService(bookService, userService);
  }

  @Test
  void testAddLoanWithExistingUserAndExistingBook() throws NotFoundException {
    // GIVEN - preparar los datos de la prueba
    var id = "1234"; // ID DEL USUARIO
    var isbn = "5678"; // ISBN DEL LIBRO
    // CREACION DE USUARIO FALSO CON EL ID 1234
    var mockUser = new User(id, "Paco Perez", "paco@paco");
    // CREACION DEL LIBRO FALSO CON EL ISBN 5678
    var mockBook = new Libro(isbn, "Aprendiendo Java", "Juan Ruiz");

    // Le decimos a los servicios falsos qué deben devolver cuando se les pida un
    // usuario o un libro.
    Mockito.when(bookService.getBookByIsbn(isbn)).thenReturn(mockBook);
    Mockito.when(userService.getUserById(id)).thenReturn(mockUser);

    // WHEN - Llamar o ejecutar el metodo a probar
    service.addLoan(id, isbn); // Intentamos prestar el libro al usuario

    // THEN - Verificaciones que el metodo se ejecuto bien

    var loans = service.getLoans(); // Obtenemos la lista de prestamos
    assertEquals(1, loans.size()); // Verificamos que haya exactamente 1 préstamo.
    var loan = loans.get(0); // Obtenemos el primer libro de la lista de prestamos
    assertNotNull(loan.getLibro()); // verificamos que el libro no sea null
    assertNotNull(loan.getUser()); // verificamos que el usuario no sea null
    assertEquals(LoanState.STARTED, loan.getState()); // verificamos que el estado del prestamo sea STARTED

  }

  @Test
  void testReturnBookWithExistingLoan() throws NotFoundException {
    var id = "1234"; // ID DEL USUARIO
    var isbn = "5678"; // ISBN DEL LIBRO
    // CREACION DE USUARIO FALSO CON EL ID 1234
    var mockUser = new User(id, "Paco Perez", "paco@paco");
    // CREACION DEL LIBRO FALSO CON EL ISBN 5678
    var mockBook = new Libro(isbn, "Aprendiendo Java", "Juan Ruiz");

    // Le decimos a los servicios falsos qué deben devolver cuando se les pida un
    // usuario o un libro.
    Mockito.when(bookService.getBookByIsbn(isbn)).thenReturn(mockBook);
    Mockito.when(userService.getUserById(id)).thenReturn(mockUser);

    service.addLoan(id, isbn); // Intentamos prestar el libro al usuario

    // WHEN - Llamar o ejecutar el metodo a probar
    service.returnLibro(id, isbn); // Intentamos devolver el libro al usuario

    // then - Verificaciones que el metodo se ejecuto bien
    var loans = service.getLoans(); // Obtenemos la lista de prestamos
    assertEquals(1, loans.size()); // Verificamos que haya exactamente 1 préstamo.

    var loan = loans.getFirst(); // Obtenemos el primer libro de la lista de prestamos
    assertEquals(id, loan.getUser().getId()); // Verificamos que el id del usuario sea el mismo
    assertEquals(isbn, loan.getLibro().getIsbn()); // Verificamos que el isbn del libro sea el mismo
    assertEquals(LoanState.FINISHED, loan.getState()); // Verificamos que el estado del prestamo sea FINISHED
  }

  @Test
  void testReturnBookWithNonExistingLoan() {
    var id = "1234"; // ID DEL USUARIO
    var isbn = "5678"; // ISBN DEL LIBRO

    // WHEN - THEN - Llamar o ejecutar el metodo a probar
    assertThrows(NotFoundException.class, () -> service.returnLibro(id, isbn));
  }
}
