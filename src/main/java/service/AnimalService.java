package service;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import java.io.IOException;
import java.util.List;

import dao.AnimalDao;
import model.Animal;

/**
 * Representa um serviço de controle de animai
 */
public class AnimalService {

	private AnimalDao animalDAO;
	private Gson gson;

	/**
	 * Inicializa uma instância de {AnimalService}
	 */
	public AnimalService() {
		try {
			animalDAO = new AnimalDao("animal.dat");
			gson = new Gson();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Lista animais pelo seu proprietário
	 * @param request A requisição
	 * @param response A resposta
	 * @return A listagem de animais
	 */
	public Object listarPeloProprietario(Request request, Response response) {
		String idProprietario = request.params(":id");
		request.queryParams().forEach((x) -> {
			System.out.println(x);
		});
		System.out.println("ID do proprietário: " + idProprietario);
		List<Animal> animais = animalDAO.listarPeloProprietario(Integer.parseInt(idProprietario));

		animais.forEach((x) -> {
			System.out.println(x.getNome() + " | " + gson.toJson(x));
		});

		if (animais != null) {
			System.out.println("Reparando resposta com " + animais.size() + " animais no array");
			response.status(200);
			return gson.toJson(animais);
		} else {
			response.status(404); // 404 Not found
			return gson.toJson(new ServiceException("Animais não localizados"));
		}
	}

	/**
	 * Cadastra um cliente
	 * @param request A requisição
	 * @param response A resposta
	 * @return O código do novo animal cadastrado
	 */
	public Object cadastrar(Request request, Response response) {
		Animal animal = gson.fromJson(request.body(), Animal.class);
		animal.setId(animalDAO.obterChave() + 1);
		animalDAO.adicionar(animal);
		response.status(201); // 201 Created
        return Integer.valueOf(animal.getId());
	}

	/**
	 * Localiza um animal
	 * @param request A requisição
	 * @param response A resposta
	 * @return O animal localizado
	 */
	public Object localizar(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));

		Animal animal = localizar(id);

        if (animal != null) {
			response.status(200);
            return gson.toJson(animal);
        } else {
            response.status(404); // 404 Not found
            return gson.toJson(new ServiceException("Cliente " + id + " não localizado.").add("id", id));
        }
	}

	/**
	 * Localiza um animal
	 * @param idAnimal O identificador do animal
	 * @return O animal localizado
	 */
	public Animal localizar(int idAnimal) {
		return animalDAO.localizarPelaChave(idAnimal);
	}
}
