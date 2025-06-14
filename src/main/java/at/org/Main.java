package at.org;

import java.time.Instant;
import java.util.Map;

import at.org.entities.User;
import at.org.repositories.UserRepository;
import at.org.useCases.ApiUseCases;
import io.javalin.Javalin;

public class Main {

    private static final UserRepository userRepository = new UserRepository();

    public static Javalin createApp() {
        Javalin app = Javalin.create();

        /*
         * ENDPOITS DA API
         */

        app.get("/hello", ctx -> ctx.result("Hello, Javalin!"));

        app.get("/status", ctx -> ctx.json(new Status("ok", Instant.now().toString())));

        app.post("/echo", ctx -> {
            EchoMessage msg = ctx.bodyAsClass(EchoMessage.class);
            ctx.json(msg);
        });

        app.get("/saudacao/{nome}", ctx -> {
            String nome = ctx.pathParam("nome");
            String message = "Olá, " + nome + "!";
            ctx.json(Map.of("mensagem: ", message));
        });

        app.post("/usuarios", ctx -> {
            try {
                User newUser = ctx.bodyAsClass(User.class);
                System.out.println("Usuário recebido: " + newUser.getEmail());
                userRepository.addUser(newUser);
                ctx.status(201);
            } catch (Exception e) {
                ctx.status(500).json(Map.of("error", "Erro ao criar usuário"));
            }
        });

        app.get("/usuarios", ctx -> ctx.json(userRepository.getAllUsers()));

        app.get("/usuarios/{email}", ctx -> {
            String email = ctx.pathParam("email");
            User user = userRepository.findByEmail(email);
            if (user != null) {
                ctx.json(user);
            } else {
                ctx.status(404);
            }
        });
        return app;
    };

    private static void UseCases() {
        /*
         * Instancio os métodos HTTP de consumo da API
         */

        System.out.println("\n[GET /status]");
        System.out.println(ApiUseCases.checkStatus());

        System.out.println("\n[POST /usuarios]");
        System.out.println(ApiUseCases.createUser("Java da Silva", "java_da_silva@test.com", 30));
        System.out.println(ApiUseCases.createUser("JUnit Rodrigues", "junito@test.com", 30));
        System.out.println(ApiUseCases.createUser("Javali Javalin ", "java_li@test.com", 30));

        System.out.println("\n[GET /usuarios]");
        System.out.println(ApiUseCases.listUsers());

        System.out.println("\nGET /usuarios/java_da_silva@test.com");
        System.out.println(ApiUseCases.getUserByEmail("java_da_silva@test.com"));
    }

    public static void main(String[] args) {
        // inicia o servidor
        createApp().start(7000);
        // chamo os métodos HTTP de consumo da API
        UseCases();
    }

}