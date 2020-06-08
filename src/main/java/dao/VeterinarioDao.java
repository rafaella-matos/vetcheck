package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import model.Veterinario;

/**
 * Representa uma camada de acesso a dados de um veterinario.
 */
public class VeterinarioDao implements Dao<Veterinario, Integer> {
	private List<Veterinario> veterinarios;
	private int maxId = 0;
	private File file;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;

	/**
	 * Initializa uma instância de {VeterinarioDao}
	 * @param filename O nome do arquivo de persistência
	 * @throws IOException Em caso de falha na manipulação do arquivo de persistência
	 */
	public VeterinarioDao(String filename) throws IOException {
		file = new File(filename);
		veterinarios = new ArrayList<Veterinario>();
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
	 * Lista veterinarios com possibilidade de filtro
	 * @param filtro O filtro a ser aplicado
	 * @return A lista de veterinários filtrada
	 */
	public List<Veterinario> listar(String filtro) {

		final List<Veterinario> temp = filtro.isEmpty() ? this.veterinarios : new ArrayList<>();
		if (!filtro.isEmpty()) {
			veterinarios.forEach((veterinario) -> {
				if (veterinario.getNome().contains(filtro) || veterinario.getEmail().contains(filtro) || veterinario.getCrmv().contains(filtro)) {
					temp.add(veterinario);
				}
			});
		}

		return temp;
	}

	/**
	 * Adiciona um veterinario
	 * @param veterinario O veterinario a ser adicionado
	 */
	public void adicionar(Veterinario veterinario) {
		try {
			veterinarios.add(veterinario);
			this.maxId = (veterinario.getId() > this.maxId) ? veterinario.getId() : this.maxId;
			this.saveToFile();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar o produto '" + veterinario.getNome() + "' no disco!");
		}
	}

	/**
	 * Localiza um veterinario pela sua chave
	 * @param id A chave do veterinario
	 * @return O veterinario localizado, ou nulo
	 */
	public Veterinario localizarPelaChave(Integer id) {
		for (Veterinario produto : veterinarios) {
			if (id == produto.getId()) {
				return produto;
			}
		}
		return null;
	}

	/**
	 * Localiza um veterinario pelo seu email
	 * @param email O email do veterinario
	 * @return O veterinario locailzado, ou nulo
	 */
	public Veterinario localizarPeloEmail(String email) {
		for (Veterinario veterinario : veterinarios) {
			if (veterinario.getEmail().equals(email)) {
				return veterinario;
			}
		}
		return null;
	}

	/**
	 * Atualiza um veterinario
	 * @param p O veterinario a ser atualizado
	 */
	public void atualizar(Veterinario p) {
		int index = veterinarios.indexOf(p);
		if (index != -1) {
			veterinarios.set(index, p);
			this.saveToFile();
		}
	}

	/**
	 * Remove um veterinario
	 * @param p O veterinario a ser removido
	 */
	public void remover(Veterinario p) {
		int index = veterinarios.indexOf(p);
		if (index != -1) {
			veterinarios.remove(index);
			this.saveToFile();
		}
	}

	private List<Veterinario> readFromFile() {
		veterinarios.clear();
		Veterinario produto = null;
		try (FileInputStream fis = new FileInputStream(file);
				ObjectInputStream inputFile = new ObjectInputStream(fis)) {

			while (fis.available() > 0) {
				produto = (Veterinario) inputFile.readObject();
				veterinarios.add(produto);
				maxId = (produto.getId() > maxId) ? produto.getId() : maxId;
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar produto no disco!");
			e.printStackTrace();
		}
		return veterinarios;
	}

	private void saveToFile() {
		try {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);

			for (Veterinario produto : veterinarios) {
				outputFile.writeObject(produto);
			}
			outputFile.flush();
			this.close();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar produto no disco!");
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
