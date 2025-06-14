package at.org.useCases;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 *  CLASSE ONDE REALIZA CHAMADAS HTTP PARA A API
 */

public class ApiUseCases {

  private static final String BASE_URL = "http://localhost:7000";

  /*
   * MÉTODOS
   */
  public static String checkStatus() {
    try {
      return sendGetRequest("/status");
    } catch (Exception e) {
      return "Erro ao verificar status: " + e.getMessage();
    }
  }

  public static String createUser(String name, String email, int age) {
    try {
      String jsonInput = String.format(
          "{\"name\":\"%s\",\"email\":\"%s\",\"age\":%d}",
          name, email, age);
      return sendPostRequest("/usuarios", jsonInput);
    } catch (Exception e) {
      return "Erro ao criar usuário: " + e.getMessage();
    }
  }

  public static String listUsers() {
    try {
      return sendGetRequest("/usuarios");
    } catch (Exception e) {
      return "Erro ao listar usuários: " + e.getMessage();
    }
  }

  public static String getUserByEmail(String email) {
    try {
      return sendGetRequest("/usuarios/" + email);
    } catch (Exception e) {
      return "Erro ao buscar usuário: " + e.getMessage();
    }
  }

  /*
   * MÉTODOS RESPONSAVEIS POR ENVIAR AS REQUISIÇÕES GET E POST
   */

  private static String sendGetRequest(String endpoint) throws Exception {
    URL url = new URL(BASE_URL + endpoint);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");

    StringBuilder response = new StringBuilder();
    try (BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()))) {

      String line;
      while ((line = in.readLine()) != null) {
        response.append(line);
      }
    }
    return response.toString();
  }

  private static String sendPostRequest(String endpoint, String jsonBody) throws Exception {
    URL url = new URL(BASE_URL + endpoint);

    HttpURLConnection con = (HttpURLConnection) url.openConnection();

    con.setRequestMethod("POST");

    con.setRequestProperty("Content-Type", "application/json");

    con.setDoOutput(true);

    try (OutputStream os = con.getOutputStream()) {
      byte[] input = jsonBody.getBytes("utf-8");
      os.write(input, 0, input.length);
    }

    StringBuilder response = new StringBuilder();
    try (BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()))) {

      String line;
      while ((line = in.readLine()) != null) {
        response.append(line);
      }
    }
    return response.toString();
  }
}