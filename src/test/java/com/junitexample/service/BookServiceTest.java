package com.junitexample.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.junitexample.exception.NotFoundException;


  class BookServiceTest{

  //clase BookService que vamos a probar
  private BookService service;

  // necesito inicializar este servicio con un setup
  @BeforeEach
  void setup() {
    service = new BookService();
  }


  @Test
  void testAddBook() throws NotFoundException {
    // GIVEN - Preparar los datos d ela prueba
    var isbn = "123456";
    var title = "Aprendiendo Java";
    var author = "Juan Ruiz";
    // WHEN - LLamar o ejecutar el metodo a probar
    service.addBook(isbn, title, author);

    // THEN - Verificaciones que el metodo se ejecuto bien
    var book = service.getBookByIsbn(isbn); // busque el libro en el sistema
    // asegurarme que no sea null
    assertNotNull(book);
    // verificar que el titulo sea igual al titulo
    assertEquals(title, book.getTitle());
    // verificar que el autor sea igual al autor
    assertEquals(author, book.getAuthor());

  }

  @Test
  void testDeleteExistedBook() throws NotFoundException {
    // GIVEN - Preparar los datos de la prueba
    var isbn = "123456";
    var title = "Aprendiendo Java";
    var author = "Juan Ruiz";
    service.addBook(isbn, title, author);
    
    // WHEN - Llamar o ejecutar el metodo a probar
    service.deleteBook(isbn);

    // THEN - Verificaciones que el metodo se ejecuto bien
    try {
      // INTENTAR BUSCAR EL LIBRO
      service.getBookByIsbn(isbn);
      // SI EL LIBRO EXISTE NO TIRA EXCEPTION ESO SIGNIFICA QUE FALLO
      fail();
    } catch (NotFoundException e) {
      

      assertTrue(true);
    }

  }

  @Test
  void testGetAllBooks() {
    // GIVEN - Preparar los datos de la prueba
    //
    var isbn = "123456";
    var title = "Aprendiendo Java";
    var author = "Juan Ruiz";
    service.addBook(isbn, title, author);
    // WHEN - Llamar o ejecutar el metodo a probar
    var books = service.getAllBooks();
    // THEN - Verificaciones que el metodo se ejecuto bien
    assertNotNull(books);
    
  }

  @Test
  void testGetBookByIsbn_WhenBookExists_ShouldReturnBook() throws NotFoundException {
    // GIVEN - Preparar los datos de la prueba
    // CASO DE QUE EL LIBRO EXISTA EN LA LISTA
    var isbn = "123456";
    var title = "Aprendiendo Java";
    var author = "Juan Ruiz";
    service.addBook(isbn, title, author);
    // WHEN - Llamar o ejecutar el metodo a probar
    var book = service.getBookByIsbn(isbn);
    // THEN - Verificaciones que el metodo se ejecuto bien
    // verificar que el libro retornado no sea null
    assertNotNull(book);
    // verificar que el isbn sea igual al isbn
    assertEquals(isbn, book.getIsbn());
    // verificar que el titulo sea igual al titulo
    assertEquals(title, book.getTitle());
    // verificar que el autor sea igual al autor
    assertEquals(author, book.getAuthor());
  }

  @Test
  void testGetBookByIsbn_WhenBookDoesNotExist_ShouldThrowNotFoundException() {
    // GIVEN - Preparar los datos de la prueba
    // CASO DE QUE EL LIBRO NO EXISTA EN LA LISTA
    // 1. ISBN QUE NO EXISTE EN LA LISTA
    var isbn = "123456";
    var title = "Aprendiendo Java";
    var author = "Juan Ruiz";
    service.addBook(isbn, title, author);
    // WHEN - Llamar o ejecutar el metodo a probar
    // 2. ISBN QUE NO EXISTE EN LA LISTA
    var nonIsbnExists = "654321";
    // THEN - Verificaciones que el metodo se ejecuto bien
    // asserttrows es para verificar que lanza una exception
    assertThrows(NotFoundException.class, () -> service.getBookByIsbn(nonIsbnExists));


  }
}
