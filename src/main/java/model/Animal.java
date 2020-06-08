package model;
import java.io.Serializable;

/**
 * Representa um animal
 */
public class Animal implements Serializable  {

	private static final long serialVersionUID = 1;
	private int id;
	private int idCliente;
	private String nome;
	private String raca;
	private String especie;

	/**
	 * Obtém o identificador do animal
	 * @return O identificador do animal
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Configura o identificador do animal
	 * @param id O identificador do animal
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtém o identificador do cliente proprietário
	 * @return O identificador do cliente proprietário
	 */
	public int getIdCliente() { return this.idCliente; }

	/**
	 * Configura o identificador do cliente proprietário
	 * @param idCliente O identificador do cliente proprietário
	 */
	public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

	/**
	 * Obtém o nome do aimal
	 * @return O nome do aimal
	 */
	public String getNome() { return this.nome; }

	/**
	 * Configura o nome do aimal
	 * @param nome O nome do aimal
	 */
	public void setNome(String nome) { this.nome = nome; }

	/**
	 * Obtém a raça do animal
	 * @return A raça do animal
	 */
	public String getRaca() { return this.raca; }

	/**
	 * Cofigura a raça do animal
	 * @param raca A raça do animal
	 */
	public void setRaca(String raca) { this.raca = raca; }

	/**
	 * Obtém a espécie do animal
	 * @return A espécie do animal
	 */
	public String getEspecie() { return this.especie; }

	/**
	 * Configura a espécie do animal
	 * @param especie A espécie do animal
	 */
	public void setEspecie(String especie) { this.especie = especie; }

	/**
	 * Inicializa uma nova instância de {Animal};
	 * @param id O identificador do animal
	 * @param idCliente O identificador do cliente proprietário
	 * @param nome O nome do animal
	 * @param raca A raça do animal
	 * @param especie A espécie do animal
	 */
	public Animal(int id, int idCliente, String nome, String raca, String especie) {
		this.id = id;
		this.idCliente = idCliente;
		this.nome = nome;
		this.raca = raca;
		this.especie = especie;		setNome(nome);
	}

	/**
	 * Permite comprar detalhes entre os animais
	 * @param obj O animal a ser comparado
	 * @return True caso sejam equivalentes, False caso contrário.
	 */
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Animal) obj).getId());
	}
}



