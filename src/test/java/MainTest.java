import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import at.org.Main;
import at.org.entities.User;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

/*
 *  TESTES DOS ENDPOITS DA API
 */

public class MainTest {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void testHelloEndpoint() {
    Javalin app = Main.createApp();

    JavalinTest.test(app, (server, client) -> {
      var response = client.get("/hello");

      assertEquals(200, response.code());

      var responseBody = response.body();

      assert responseBody != null;

      assertEquals("Hello, Javalin!", responseBody.string());
    });
  }

  @Test
  void testCreateUserEndpoint() {
    Javalin app = Main.createApp();

    JavalinTest.test(app, (server, client) -> {
      User newUser = new User(30, "test@example.com", "Test User");

      String jsonBody = objectMapper.writeValueAsString(newUser);

      var response = client.post("/usuarios", jsonBody);

      assertEquals(201, response.code());
    });
  }

  @Test
  void testGetUserByEmailEndpoint() {
    Javalin app = Main.createApp();

    JavalinTest.test(app, (server, client) -> {
      User newUser = new User(30, "test2@example.com", "Test User 2");

      String jsonBody = objectMapper.writeValueAsString(newUser);
      client.post("/usuarios", jsonBody);

      var response = client.get("/usuarios/test2@example.com");

      assertEquals(200, response.code());

      var responseBody = response.body();
      assertNotNull(responseBody);

      User retrievedUser = objectMapper.readValue(responseBody.string(), User.class);
      assertEquals(newUser.getEmail(), retrievedUser.getEmail());
      assertEquals(newUser.getName(), retrievedUser.getName());
      assertEquals(newUser.getAge(), retrievedUser.getAge());
    });
  }

  @Test
  void testListUsersEndpoint() {
    Javalin app = Main.createApp();

    JavalinTest.test(app, (server, client) -> {
      User newUser = new User(25, "test3@example.com", "Test User 3");

      String jsonBody = objectMapper.writeValueAsString(newUser);
      client.post("/usuarios", jsonBody);

      var response = client.get("/usuarios");

      assertEquals(200, response.code());

      var responseBody = response.body();
      assertNotNull(responseBody);

      String jsonResponse = responseBody.string();
      assertTrue(jsonResponse.contains("test3@example.com"));
    });
  }

}