package service;

import java.io.IOException;

import com.google.gson.Gson;
import dao.VeterinarioDao;
import model.Login;
import model.Veterinario;
import spark.Request;
import spark.Response;

/**
 * Representa um serviço de controle de veterinários.
 */
public class VeterinarioService {

	private VeterinarioDao veterinarioDAO;
	private Gson gson;

	/**
	 * Initializa uma instância de {VeterinarioService}.
	 */
	public VeterinarioService() {
		try {
			veterinarioDAO = new VeterinarioDao("veterinario.dat");
			gson = new Gson();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Cadastra um veterinário
	 * @param request A requisição
	 * @param response A resposta
	 * @return O código do novo veterinário cadastrado
	 */
	public Object cadastrar(Request request, Response response) {
		System.out.println("Cadastro de veterinário");
		Veterinario veterinario = gson.fromJson(request.body(), Veterinario.class);
		veterinario.setId(veterinarioDAO.obterChave() + 1);
		veterinarioDAO.adicionar(veterinario);
		response.status(201); // 201 Created
        return veterinario.getId();
	}

	/**
	 * Realiza login para um veterinario
	 * @param request A requisição
	 * @param response A resposta
	 * @return O veteriário que realizou login
	 */
	public Object login(Request request, Response response) {
		Login login = gson.fromJson(request.body(), Login.class);
		Veterinario veterinario = veterinarioDAO.localizarPeloEmail(login.getEmail());

		if (veterinario != null && veterinario.getSenha().equals(login.getSenha())) {
			response.status(200);
			return veterinario;
		} else if (veterinario == null) {
			System.out.println("Veterinário não localizado - login inválido.");
		} else if (veterinario != null && !veterinario.getSenha().equals(login.getSenha())) {
			System.out.println("A senha informada [" + login.getSenha() + "] não equivale à do veterinário [" + veterinario.getSenha() + "]");
		}

		response.status(400);
		return "";
	}

	/**
	 * Localiza um veterinário
	 * @param request A requisição
	 * @param response A resposta
	 * @return O veterinário localizado
	 */
	public Object localizar(Request request, Response response) {
		try {
			int id = Integer.parseInt(request.params(":id"));

			Veterinario veterinario = veterinarioDAO.localizarPelaChave(id);

			if (veterinario != null) {
				response.status(200);
				return gson.toJson(veterinario);
			} else {
				response.status(404); // 404 Not found
				return "";
			}
		} catch (Exception erro) {
			response.status(500);
			return gson.toJson(erro);
		}
	}

}
