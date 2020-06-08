package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import model.ConsultaAnimal;

/**
 * Representa uma camada de acesso a dados de uma consulta animal.
 */
public class ConsultaAnimalDao implements Dao<ConsultaAnimal, Integer> {
	private List<ConsultaAnimal> consultas;
	private int maxId = 0;
	private File file;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;

	/**
	 * Initializa uma instância de {ConsultaAnimalDao}
	 * @param filename O nome do arquivo de persistência
	 * @throws IOException Em caso de falha na manipulação do arquivo de persistência
	 */
	public ConsultaAnimalDao(String filename) throws IOException {

		file = new File(filename);
		consultas = new ArrayList<ConsultaAnimal>();
		if (file.exists()) {
			readFromFile();
		}

	}

	/**
	 * Obtém a última chave persistida.
	 * @return A última chave persistida.
	 */
	public Integer obterChave() {
		return this.maxId;
	}

	/**
	 * Lista consultas de um animal com possibilidade de filtro
	 * @param filtro O filtro a ser aplicado nas consultas
	 * @return As consultas filtradas
	 */
	public List<ConsultaAnimal> listar(String filtro) {
		return this.consultas;
	}

	/**
	 * Lista consultas para um animal
	 * @param idAnimal O identificador do animal
	 * @return A lista de consultas do animal
	 */
	public List<ConsultaAnimal> listarPeloAnimal(Integer idAnimal) {
		final List<ConsultaAnimal> temp = new ArrayList<>();
		for (ConsultaAnimal consulta : consultas) {
			if (consulta.getIdAnimal() == idAnimal) { temp.add(consulta); }
		}
		return temp;
	}

	/**
	 * Localiza uma consulta pela chave
	 * @param id A chave da consulta
	 * @return A consulta localizada, ou nulo
	 */
	public ConsultaAnimal localizarPelaChave(Integer id) {
		for (ConsultaAnimal consulta : consultas) {
			if (id == consulta.getId()) {
				return consulta;
			}
		}
		return null;
	}

	/**
	 * Adiciona uma consulta animal
	 * @param consulta A consulta a ser adicionada
	 */
	public void adicionar(ConsultaAnimal consulta) {
		try {
			consultas.add(consulta);
			this.maxId = (consulta.getId() > this.maxId) ? consulta.getId() : this.maxId;
			this.saveToFile();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar a consulta do animal '" + consulta.getIdAnimal() + "' no disco!");
		}
	}

	/**
	 * Atualiza uma consulta
	 * @param p A consulta a ser atualizada
	 */
	public void atualizar(ConsultaAnimal p) {
		int index = consultas.indexOf(p);
		if (index != -1) {
			consultas.set(index, p);
			this.saveToFile();
		}
	}

	/**
	 * Remove uma consulta
	 * @param p A consulta a ser removida
	 */
	public void remover(ConsultaAnimal p) {
		int index = consultas.indexOf(p);
		if (index != -1) {
			consultas.remove(index);
			this.saveToFile();
		}
	}

	private List<ConsultaAnimal> readFromFile() {
		consultas.clear();
		ConsultaAnimal animal = null;
		try (FileInputStream fis = new FileInputStream(file);
				ObjectInputStream inputFile = new ObjectInputStream(fis)) {

			while (fis.available() > 0) {
				animal = (ConsultaAnimal) inputFile.readObject();
				consultas.add(animal);
				maxId = (animal.getId() > maxId) ? animal.getId() : maxId;
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar a consulta no disco!");
			e.printStackTrace();
		}
		return consultas;
	}

	private void saveToFile() {
		try {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);

			for (ConsultaAnimal animal : consultas) {
				outputFile.writeObject(animal);
			}
			outputFile.flush();
			this.close();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar a consulta no disco!");
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
