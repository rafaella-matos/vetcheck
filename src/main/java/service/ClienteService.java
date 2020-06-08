package service;

import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import dao.ClienteDao;
import model.Cliente;

/**
 * Depresenta um serviço de controle de clientes
 */
public class ClienteService {

	private ClienteDao clienteDAO;
	private Gson gson;

	/**
	 * Inicializa uma instância de {ClienteService}
	 */
	public ClienteService() {
		try {
			clienteDAO = new ClienteDao("cliente.dat");
			gson = new Gson();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Lista clientes
	 * @param request A requisição
	 * @param response A resposta
	 * @return A listagem de clientes
	 */
	public Object listar(Request request, Response response) {
		String filtro = request.queryParams("filtro");
		System.out.println("Filtro: " + filtro);

		if (filtro == null) filtro = "";

		List<Cliente> clientes = clienteDAO.listar(filtro);

		if (clientes != null) {
			response.status(200);
			return gson.toJson(clientes);
		} else {
			response.status(404); // 404 Not found
			return gson.toJson(new ServiceException("Clientes não localizados"));
		}
	}

	/**
	 * Cadastra um cliente
	 * @param request A requisição
	 * @param response A resposta
	 * @return O código do novo cliente cadastrado
	 */
	public Object cadastrar(Request request, Response response) {
		Cliente cliente = gson.fromJson(request.body(), Cliente.class);
		int id = clienteDAO.obterChave() + 1;
		cliente.setId(id);
		clienteDAO.adicionar(cliente);
		response.status(201); // 201 Created
        return Integer.valueOf(id);
	}

	/**
	 * Localiza um cliente
	 * @param request A requisição
	 * @param response A resposta
	 * @return O cliente localizado
	 */
	public Object localizar(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));

		Cliente cliente = clienteDAO.localizarPelaChave(id);

        if (cliente != null) {
			response.status(200);
            return gson.toJson(cliente);
        } else {
            response.status(404); // 404 Not found
            return gson.toJson(new ServiceException("Cliente " + id + " não localizado.").add("id", id));
        }
	}
}
