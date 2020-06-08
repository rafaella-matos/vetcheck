package app;
import static spark.Spark.*;

import service.AnimalService;
import service.ClienteService;
import service.ConsultaAnimalService;
import service.VeterinarioService;

/**
 * Representa uma aplicação web.
 */
public class Aplicacao {
	
	private static ClienteService clienteService = new ClienteService();
	private static VeterinarioService veterinarioService = new VeterinarioService();
	private static AnimalService animalService = new AnimalService();
	private static ConsultaAnimalService consultaAnimalService = new ConsultaAnimalService(animalService);

    /**
     * Ponto de entrada da aplicação.
     * @param args Os argumentos de linha de comando.
     */
    public static void main(String[] args) {
        final int porta = 6789;
        port(porta);
        staticFiles.location("/public");

        // cors
        options("/*",
                (request, response) -> {
                    String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
                    }
                    String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
                    }
                    return "OK";
                });
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Content-Type", "application/json");
            response.header("Content-Encoding", "UTF-8");
        });

        // autenticacao
        post("/login", "application/json", (request, response) -> veterinarioService.login(request, response));

        // clientes
        get("/clientes", (request, response) -> clienteService.listar(request, response));
        get("/clientes/:id", (request, response) -> clienteService.localizar(request, response));
        post("/clientes", "application/json", (request, response) -> clienteService.cadastrar(request, response));

        // animais
        get("/clientes/:id/animais", (request, response) -> animalService.listarPeloProprietario(request, response));
        post("/clientes/:id/animais", "application/json", (request, response) -> animalService.cadastrar(request, response));
        get("/animais/:idAnimal", (request, response) -> animalService.localizar(request, response));
        post("/animais/:idAnimal/consultas", "application/json", (request, response) -> consultaAnimalService.cadastrar(request, response));

        // veterinarios
        post("/veterinarios", (request, response) -> veterinarioService.cadastrar(request, response));
        get("/veterinarios/:id", (request, response) -> veterinarioService.localizar(request, response));
        
        System.out.println("A aplicação back-end está executando no endereço http://localhost:" + porta);
    }
}