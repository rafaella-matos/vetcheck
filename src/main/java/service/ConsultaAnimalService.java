package service;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import java.io.IOException;

import dao.AnimalDao;
import dao.ConsultaAnimalDao;
import model.Animal;
import model.ConsultaAnimal;

/**
 * Representa um serviço de controle de consultas de animais
 */
public class ConsultaAnimalService {

	private AnimalService animalService;
	private ConsultaAnimalDao consultaAnimalDAO;
	private Gson gson;

	/**
	 * Inicializa uma nova instância de {ConsultaAnimalService}
	 * @param animalService O serviço de controle de animais
	 */
	public ConsultaAnimalService(AnimalService animalService) {
		try {
			consultaAnimalDAO = new ConsultaAnimalDao("consultaAnimal.dat");
			this.animalService = animalService;
			gson = new Gson();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Cadastra uma consulta de um animal
	 * @param request A requisição
	 * @param response A resposta
	 * @return O resultado do cadastramento
	 */
	public Object cadastrar(Request request, Response response) {
		int idAnimal = Integer.parseInt(request.params(":idAnimal"));
		Animal animal = animalService.localizar(idAnimal);

		if (animal == null) {
			response.status(404);
			return gson.toJson(new ServiceException("Animal não localizado"));
		}

		ConsultaAnimal consultaAnimal = gson.fromJson(request.body(), ConsultaAnimal.class);
		consultaAnimal.setId(consultaAnimalDAO.obterChave() + 1);
		consultaAnimalDAO.adicionar(consultaAnimal);

		response.status(201);
		return consultaAnimal.getId();
	}
}
