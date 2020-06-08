package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import model.Animal;

/**
 * Representa uma camada de acesso a dados de um animal.
 */
public class AnimalDao implements Dao<Animal, Integer> {
	private List<Animal> animais;
	private int maxId = 0;
	private File file;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;

	/**
	 * Initializa uma instância de {AnimalDao}
	 * @param filename O nome do arquivo de persistência
	 * @throws IOException Em caso de falha na manipulação do arquivo de persistência
	 */
	public AnimalDao(String filename) throws IOException {
		file = new File(filename);
		animais = new ArrayList<Animal>();
		if (file.exists()) {
			readFromFile();
		}
	}

	/**
	 * Obtém a última chave persistida.
	 * @return A última chave inserida no repositório.
	 */
	public Integer obterChave() {
		return this.maxId;
	}

	/**
	 * Lista os animais com possibilidade de filtro.
	 * @param filtro O filtro para aplicar na listagem
	 * @return A lista de animais filtrada
	 */
	public List<Animal> listar(String filtro) {

		final List<Animal> temp = filtro.isEmpty() ? this.animais : new ArrayList<>();
		if (!filtro.isEmpty()) {
			animais.forEach((animal) -> {
				if (animal.getNome().contains(filtro)) {
					temp.add(animal);
				}
			});
		}

		return temp;
	}

	/**
	 * Lista animais pelo código do proprietário
	 * @param idProprietario O código do proprietário
	 * @return A lista de animais para o proprietário
	 */
	public List<Animal> listarPeloProprietario(Integer idProprietario) {
		final List<Animal> temp = new ArrayList<>();
		for (Animal animal : animais) {
			if (animal.getIdCliente() == idProprietario) { temp.add(animal); }
		}
		return temp;
	}

	/**
	 * Localiza um animal pela sua chave
	 * @param id A chave do animal
	 * @return O animal localizado, ou nulo
	 */
	public Animal localizarPelaChave(Integer id) {
		for (Animal animal : animais) {
			if (id == animal.getId()) {
				return animal;
			}
		}
		return null;
	}

	/**
	 * Adiciona um animal
	 * @param animal O animal a ser adicionado
	 */
	public void adicionar(Animal animal) {
		try {
			animais.add(animal);
			this.maxId = (animal.getId() > this.maxId) ? animal.getId() : this.maxId;
			this.saveToFile();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar o animal '" + animal.getNome() + "' no disco!");
		}
	}

	/**
	 * Atualiza um animal
	 * @param p O animal para atualizar
	 */
	public void atualizar(Animal p) {
		int index = animais.indexOf(p);
		if (index != -1) {
			animais.set(index, p);
			this.saveToFile();
		}
	}

	/**
	 * Remove um animal
	 * @param p O animal a ser removido
	 */
	public void remover(Animal p) {
		int index = animais.indexOf(p);
		if (index != -1) {
			animais.remove(index);
			this.saveToFile();
		}
	}

	private List<Animal> readFromFile() {
		animais.clear();
		Animal animal = null;
		try (FileInputStream fis = new FileInputStream(file);
				ObjectInputStream inputFile = new ObjectInputStream(fis)) {

			while (fis.available() > 0) {
				animal = (Animal) inputFile.readObject();
				animais.add(animal);
				maxId = (animal.getId() > maxId) ? animal.getId() : maxId;
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar produto no disco!");
			e.printStackTrace();
		}
		return animais;
	}

	private void saveToFile() {
		try {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);

			for (Animal animal : animais) {
				outputFile.writeObject(animal);
			}
			outputFile.flush();
			this.close();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar animal no disco!");
			e.printStackTrace();
		}
	}

	private void close() throws IOException {
		outputFile.close();
		fos.close();
	}

	/**
	 * Finaliza a instância e remove referências pendentes.
	 */
	@Override
	protected void finalize() {
		try {
			this.saveToFile();
			this.close();
		} catch (Exception e) {
			System.out.println("ERRO ao salvar a base de dados no disco!");
			e.printStackTrace();
		}
	}

}
