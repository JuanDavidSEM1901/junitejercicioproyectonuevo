package com.junitexample.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.junitexample.exception.NotFoundException;

class UserServiceTest {

  private UserService userService;

  @BeforeEach
  void setup() {
    userService = new UserService();
  }

  @Test
  void testAddUser() {
    // agregar un usuario y verificar que este en la lista
    // GIVEN - Preparar los datos de la prueba
    var id = "1234";
    var name = "Paco Perez";
    var email = "paco@paco";

    // WHEN - Llamar o ejecutar el metodo a probar
    userService.addUser(id, name, email);

    // THEN - Verificaciones que el metodo se ejecuto bien

    var users = userService.getAllUsers();

    assertEquals(1, users.size());

    var user = users.get(0);

    assertEquals(id, user.getId());
    assertEquals(name, user.getName());
    assertEquals(email, user.getEmail());

    assertNotNull(user);

  }

  @Test
  void testAddUserWithRegisterDate() {

    var id = "1234";
    var name = "Paco Perez";
    var email = "paco@paco";
    var registerDate = LocalDate.of(2024, 10, 23);

    // WHEN - Llamar o ejecutar el metodo a probar
    userService.addUser(id, name, email, registerDate);

    // THEN - Verificaciones que el metodo se ejecuto bien

    var users = userService.getAllUsers();
    assertEquals(1, users.size());

    var user = users.get(0);
    assertEquals(id, user.getId());
    assertEquals(name, user.getName());
    assertEquals(email, user.getEmail());
    assertEquals(registerDate, user.getRegisterDate());

    assertNotNull(user);
  }

  @Test
  void testDeleteUser() {
    // GIVEN - Preparar los datos de la prueba

    var id = "1234";
  }

  @Test
  void testGetAllUsers() {

  }

  @Test
  void testGetUserByIdWhenUserExists() throws NotFoundException {
    // GIVEN - Preparar los datos de la prueba
    // agregar usuario a la lista
    var id = "1234";
    var name = "Paco Perez";
    var email = "paco@paco";
    userService.addUser(id, name, email);
    // WHEN - Llamar o ejecutar el metodo a probar
    var user = userService.getUserById(id);

    // THEN - Verificaciones que el metodo se ejecuto bien
    assertNotNull(user);
    assertEquals(id, user.getId());
    assertEquals(name, user.getName());
    assertEquals(email, user.getEmail());
  }

  @Test
  void testGetUserByIdWhenUserDoesNotExist() {
    // GIVEN - Preparar los datos de la prueba
    var idNoExist = "99999";

    // WHEN - THEN
    var exception = assertThrows(NotFoundException.class, () -> userService.getUserById(idNoExist));

    assertEquals("User with id " + idNoExist + " not found", exception.getMessage());

  }

  @Test
  void testUpdateEmailExistingUser() throws NotFoundException {
    // given - preparar los datos de la prueba
    var id = "1234";
    var name = "Paco Perez";
    var email = "paco@paco";
    userService.addUser(id, name, email);
    // WHEN - Llamar o ejecutar el metodo a probar
    var newEmail = "paco2@paco2";
    userService.updateEmail(id, newEmail);
    // THEN - Verificamos que el correo se haya actualizado
    var userEmailUpdated = userService.getUserById(id);
    // VERIFICAR QUE EL NUEVO EMAIL SE HA ACTUALIZADO
    assertEquals(newEmail, userEmailUpdated.getEmail());
    // VERIFICAR QUE EL USUARIO NO SEA NULL
    assertNotNull(userEmailUpdated);
    // VERIFICAR LOS DEMAS DATOS DEL USUARIO
    assertEquals(id, userEmailUpdated.getId());
    assertEquals(name, userEmailUpdated.getName());

  }

  /*
   * Intenta actualizar el correo de un
   * usuario que no existe y verifica que se lance una excepción.
   */
  @Test
  void testUpdateEmailUserNoExist() {
    // GIVEN - Preparar los datos de la prueba
    var idNoExist = "7777";
    var newEmail = "paco3@js";
    // WHEN THEN
    var exception = assertThrows(NotFoundException.class, () -> userService.updateEmail(idNoExist, newEmail));
    assertEquals("User with id " + idNoExist + " not found", exception.getMessage());
  }

  @Test
  void testUpdateNameExistingUser() throws NotFoundException {
    // given - preparar los datos de la prueba
    var id = "1234";
    var name = "Paco Perez";
    var email = "paco@paco";
    userService.addUser(id, name, email);
    // WHEN - Llamar o ejecutar el metodo a probar
    var newName = "Andres Diaz";
    userService.updateName(id, newName);
    // THEN - Verificamos que el correo se haya actualizado
    var userNameUpdated = userService.getUserById(id);
    // VERIFICAR QUE EL USUARIO NO SEA NULL
    assertNotNull(userNameUpdated);
    // VERIFICAR QUE EL NUEVO EMAIL SE HA ACTUALIZADO
    assertEquals(newName, userNameUpdated.getName());

    // VERIFICAR LOS DEMAS DATOS DEL USUARIO
    assertEquals(id, userNameUpdated.getId());
    assertEquals(email, userNameUpdated.getEmail());
  }

  @Test
  void testUpdateNameNotExistingUser() {
    // GIVEN - Preparar los datos de la prueba
    var idNoExist = "33333";
    var newName = "Pacho Perez";
    // WHEN THEN
    var exception = assertThrows(NotFoundException.class, () -> userService.updateName(idNoExist, newName));
    assertEquals("User with id " + idNoExist + " not found", exception.getMessage());
  }
}
