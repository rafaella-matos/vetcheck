package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;

/**
 * Representa uma camada de acesso a dados de um animal.
 */
public class ClienteDao implements Dao<Cliente, Integer> {
	private List<Cliente> clientes;
	private int maxId = 0;
	private File file;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;

	/**
	 * Initializa uma instância de {ClienteDao}
	 * @param filename O nome do arquivo de persistência
	 * @throws IOException Em caso de falha na manipulação do arquivo de persistência
	 */
	public ClienteDao(String filename) throws IOException {

		file = new File(filename);
		clientes = new ArrayList<Cliente>();
		if (file.exists()) {
			readFromFile();
		}

	}

	/**
	 * Obtém a última chave persistida.
	 * @return A última chave persistida
	 */
	public Integer obterChave() {
		return this.maxId;
	}

	/**
	 * Lista clientes com possibilidade de filtro
	 * @param filtro O filtro a ser aplicado
	 * @return A lista filtrada de clientes
	 */
	public List<Cliente> listar(String filtro) {

		System.out.println("Quantidade de clientes no array: " + this.clientes.size());

		if (filtro.isEmpty()) return this.clientes;

		final List<Cliente> temp = new ArrayList<>();
		clientes.forEach((cliente) -> {
			if (cliente.getNome().contains(filtro) || cliente.getEmail().contains(filtro) || cliente.getEndereco().contains(filtro)) {
				temp.add(cliente);
			}
		});

		System.out.println("Total de clientes filtrados: " + this.clientes.size());

		return temp;
	}

	/**
	 * Localiza um cliente pela sua chave
	 * @param id A chave do cliente
	 * @return O cliente localizado, ou nulo
	 */
	public Cliente localizarPelaChave(Integer id) {
		for (Cliente produto : clientes) {
			if (id == produto.getId()) {
				return produto;
			}
		}
		return null;
	}

	/**
	 * Adiciona um cliente
	 * @param cliente O cliente a ser adicionado
	 */
	public void adicionar(Cliente cliente) {
		try {
			clientes.add(cliente);
			this.maxId = (cliente.getId() > this.maxId) ? cliente.getId() : this.maxId;
			this.saveToFile();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar o produto '" + cliente.getNome() + "' no disco!");
		}
	}

	/**
	 * Atualiza um cliente
	 * @param p O cliente a ser atualizado
	 */
	public void atualizar(Cliente p) {
		int index = clientes.indexOf(p);
		if (index != -1) {
			clientes.set(index, p);
			this.saveToFile();
		}
	}

	/**
	 * Remove um cliente
	 * @param p O cliente a ser removido
	 */
	public void remover(Cliente p) {
		int index = clientes.indexOf(p);
		if (index != -1) {
			clientes.remove(index);
			this.saveToFile();
		}
	}

	private List<Cliente> readFromFile() {
		clientes.clear();
		Cliente produto = null;
		try (FileInputStream fis = new FileInputStream(file);
				ObjectInputStream inputFile = new ObjectInputStream(fis)) {

			while (fis.available() > 0) {
				produto = (Cliente) inputFile.readObject();
				clientes.add(produto);
				maxId = (produto.getId() > maxId) ? produto.getId() : maxId;
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar produto no disco!");
			e.printStackTrace();
		}
		return clientes;
	}

	private void saveToFile() {
		try {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);

			for (Cliente produto : clientes) {
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
